package br.com.ativa.auto_nfes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fincatto.documentofiscal.DFUnidadeFederativa;

import br.com.ativa.auto_nfes.model.Empresa;
import br.com.ativa.auto_nfes.model.ItemNfe;
import br.com.ativa.auto_nfes.model.DTOs.EmpresaDTO.CreateEmpresaDTO;
import br.com.ativa.auto_nfes.model.DTOs.ItemsDTO.CreateItemsDTO;
import br.com.ativa.auto_nfes.repository.Nfes_repositoryEmpresa;
import br.com.ativa.auto_nfes.repository.Nfes_repositoryItemNf;
import br.com.ativa.auto_nfes.service.ServiceSefaz.NFeSefazConsultas;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service()
public class Nfes_service {
    Nfes_repositoryEmpresa repositoryEmpresa;
    Nfes_repositoryItemNf repositoryItemNf;
    NFeSefazConsultas consultas;

    public Nfes_service(Nfes_repositoryEmpresa repositoryEmpresa,
            NFeSefazConsultas consultas,
            Nfes_repositoryItemNf repositoryItemNf) {
        this.repositoryItemNf = repositoryItemNf;
        this.repositoryEmpresa = repositoryEmpresa;
        this.consultas = consultas;
    }

    /**
     * Salva os dados da empresa no DB respectivo
     * 
     * @param empresaDTO
     * @throws Exception Exceptions repassadas para o consumidor do método
     */
    public Empresa saveEmpresa(CreateEmpresaDTO empresaDTO) throws Exception {
        // Incluir dados no DB utilizando repository (JPA e Hibernate)
        Empresa empresa = repositoryEmpresa
                .save(new Empresa(empresaDTO.cnpj(), empresaDTO.uf()));
        return empresa;
    }

    /**
     * Salva os items no DB respectivo
     * 
     * @param itensDTO
     * @throws Exception Exceptions repassadas para o consumidor do método
     */
    @Transactional
    public void saveProdutos(CreateItemsDTO itensDTO, String cnpj) throws Exception {
        String chaveAcesso = itensDTO.chaveDeAcesso();
        // Busca Empresa utilizando o CNPJ.
        Empresa empresa = repositoryEmpresa.findByCnpj(cnpj).orElseThrow(
                () -> new EntityNotFoundException("Empresa não encontrada para salvar o respectivo produto."));

        // Verifica se já existe essa nota no DB
        repositoryItemNf.findLastByChaveAcessoNFe(chaveAcesso).orElseThrow(
                () -> new EntityExistsException("Os items já existem no banco de dados."));

        // Captura lista de items do XML.
        List<ItemNfe> itemsXml = consultas.consultarItemsXml(
                empresa.getCnpj(),
                chaveAcesso,
                DFUnidadeFederativa.valueOf(empresa.getUf()));

        // Inclui os itens em seu respectivo banco de dados.
        repositoryItemNf.saveAll(itemsXml);
    }
}
