package br.com.ativa.auto_nfes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ativa.auto_nfes.model.DTOs.EmpresaDTO.CreateEmpresaDTO;
import br.com.ativa.auto_nfes.service.Nfes_service;
import br.com.ativa.auto_nfes.service.serviceNfe.NFeGerarCadeiaCertificados;
import br.com.ativa.auto_nfes.service.serviceNfe.NFeSefazConfig;
import br.com.ativa.auto_nfes.service.serviceNfe.NFeSefazConsultas;
import br.com.ativa.auto_nfes.service.serviceNfe.NFeWsFacadeFactory;

@ExtendWith(MockitoExtension.class)
class AutoNfesApplicationTests {
	@Mock
	NFeWsFacadeFactory facade;

	@Mock
	NFeSefazConsultas consultas;

	@Mock
	CreateEmpresaDTO empresaDTO;

	@InjectMocks
	Nfes_service service;

	@Test
	void verificarInclusaoDeDadosNoBanco() {
		CreateEmpresaDTO empresa = 
		when(service.save(empresaDTO))
	}

	@Test
	void verificarGeracaoDeCertificados() {
		NFeGerarCadeiaCertificados gerarCertificado = new NFeGerarCadeiaCertificados();
		assertDoesNotThrow(() -> gerarCertificado.GerarCertificados(), "Erro lancado na função de geração.");
	}

	@Test
	void verificarGeracaoDeCadeiaDeKeys() {
		NFeSefazConfig configSefaz = new NFeSefazConfig();
		assertDoesNotThrow(() -> configSefaz.getCadeiaCertificadosKeyStore(),
				"Erro lacado na função de Geração de cadeia de certificados.");
	}

	@Test
	void verificarCertificadoKeyStore() {
		NFeSefazConfig configSefaz = new NFeSefazConfig();
		assertDoesNotThrow(() -> configSefaz.getCertificadoKeyStore(), "Erro lancado na função de Leitura do PFX");
	}
}
