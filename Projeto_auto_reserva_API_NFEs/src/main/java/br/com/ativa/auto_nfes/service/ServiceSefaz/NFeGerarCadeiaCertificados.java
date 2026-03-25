package br.com.ativa.auto_nfes.service.ServiceSefaz;

import java.io.File;

@Component
public class NFeGerarCadeiaCertificados {
    public void GerarCertificados() {

        // gera os arquivos de certificado que o SEFAZ exige.
        // O de PRODUÇÃO configura para buscar dados no ambiente de notas REAIS.
        // O de HOMOLOGAÇÃO configura para buscar dados no ambiente de notas de TESTE.
        Dotenv dotenv = Dotenv.load();
        String senha = dotenv.get("senha-cacerts");
        try {
            FileUtils.writeByteArrayToFile(new File(
                    "./src/main/resources/certs/producao.cacerts"),
                    DFCadeiaCertificados.geraCadeiaCertificados(DFAmbiente.PRODUCAO, senha));
            FileUtils.writeByteArrayToFile(new File(
                    "./src/main/resources/certs/homologacao.cacerts"),
                    DFCadeiaCertificados.geraCadeiaCertificados(DFAmbiente.HOMOLOGACAO, senha));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar arquivos de certificados.", e);
        }
    }
}
