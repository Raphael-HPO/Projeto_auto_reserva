package br.com.ativa.auto_nfes.service.ServiceSefaz;

import java.util.ArrayList;
import java.util.List;

import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.classes.distribuicao.NFDistribuicaoDocumentoZip;
import com.fincatto.documentofiscal.nfe.classes.distribuicao.NFDistribuicaoIntRetorno;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaInfoItem;
import com.fincatto.documentofiscal.nfe400.classes.nota.NFNotaProcessada;
import com.fincatto.documentofiscal.nfe400.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.documentofiscal.nfe400.webservices.WSFacade;
import com.fincatto.documentofiscal.utils.DFPersister;

import br.com.ativa.auto_nfes.model.ItemNfe;

public class NFeSefazConsultas {
    NFeWsFacadeFactory wsFacadeFactory;

    public NFeSefazConsultas(NFeWsFacadeFactory wsFacadeFactory) {
        this.wsFacadeFactory = wsFacadeFactory;
    }

    String nsu = "";
    String UltNsu = "";
    final WSFacade facade = wsFacadeFactory.WsFacadeFactory();

    /**
     * Consulta dados dos items presentes no XML
     * 
     * @param cnpj
     * @param chaveDeAcesso
     * @param uf
     * @return List com objetos de items encontrados
     * @throws Exception
     */
    public List<ItemNfe> consultarItemsXml(String cnpj, String chaveDeAcesso, DFUnidadeFederativa uf) {
        List<ItemNfe> itemsNfe = new ArrayList<>();
        // Função que realiza a consulta via comunicação SOAP com o SEFAZ
        try {
            final NFDistribuicaoIntRetorno consultaXml = facade.consultarDistribuicaoDFe(cnpj, uf, chaveDeAcesso,
                    this.nsu,
                    this.UltNsu);

            // Itera sobre tudo que o SEFAZ disponibiliza
            for (NFDistribuicaoDocumentoZip docZip : consultaXml.getLote().getDocZip()) {
                String schemaNota = docZip.getSchema();
                String zipXmlBruto = docZip.getValue();
                // Verifica em qual dos retornos está presente o documento com os dados
                if (schemaNota == "procNFe") {
                    NFNotaProcessada objectXML = new DFPersister(false).read(NFNotaProcessada.class, zipXmlBruto);
                    List<NFNotaInfoItem> itensNF = objectXML.getNota().getInfo().getItens();
                    itensNF.forEach(null);
                    int ultItem = itensNF.getLast().getNumeroItem().intValue();
                    int primItem = itensNF.getLast().getNumeroItem().intValue();

                    for (int i = primItem; i <= ultItem; i++) {
                        NFNotaInfoItem produto = itensNF.get(i);
                        ItemNfe itemNfe = new ItemNfe();
                        itemNfe.setCodProduto(produto.getProduto().getCodigo());
                        itemNfe.setDsItem(produto.getProduto().getDescricao());
                        itemNfe.setVlrItem(produto.getProduto().getValorUnitario());
                        itemNfe.setQtdItem(produto.getProduto().getQuantidadeComercial());
                        itemNfe.setQtdLote(produto.getProduto().getQuantidadeTributavel());
                        itemsNfe.add(itemNfe);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao se comunicar com SEFAZ ou problema de conexão com SEFAZ.", e);
        }
        return itemsNfe;
    }

    /**
     * Consulta status de disponibilidade do sefaz
     * 
     * @return String com status e descrição concatenados
     * @throws Exception
     */
    public String consultarStatusSefaz() throws Exception {
        NFStatusServicoConsultaRetorno retorno = facade.consultaStatus(DFUnidadeFederativa.SP, DFModelo.NFE);
        String retornoSefaz = String.join("\n", retorno.getStatus(), retorno.getMotivo());
        return retornoSefaz;
    }

}
