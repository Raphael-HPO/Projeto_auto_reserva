package br.com.ativa.auto_nfes.service.ServiceSefaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import com.fincatto.documentofiscal.DFAmbiente;
import com.fincatto.documentofiscal.DFModelo;
import com.fincatto.documentofiscal.DFUnidadeFederativa;
import com.fincatto.documentofiscal.nfe.NFeConfig;

public class NFeSefazConfig extends NFeConfig {
    Dotenv dotenv = Dotenv.load();
    String senha = dotenv.get("senha-cacerts");

    private KeyStore keyStoreCertificado = null;
    private KeyStore keyStoreCadeia = null;

    @Override
    public DFAmbiente getAmbiente() {
        return DFAmbiente.HOMOLOGACAO;
    }

    @Override
    public DFModelo getModelo() {
        return DFModelo.NFE;
    }

    @Override
    public DFUnidadeFederativa getCUF() {
        return DFUnidadeFederativa.SP;
    }

    @Override
    public String getCertificadoSenha() {
        // Senha Certificado da empresa.
        return "senha123";
    }

    @Override
    public String getCadeiaCertificadosSenha() {
        // Senha criada por mim ao gerar a cadeia de certificados na main.
        return senha;
    }

    @Override
    public KeyStore getCadeiaCertificadosKeyStore() throws KeyStoreException {
        if (this.keyStoreCadeia == null) {
            NFeGerarCadeiaCertificados cGerarCertificado = new NFeGerarCadeiaCertificados();
            // Caminho dos arquivos em ambos os casos
            File file_homologacao = new File(
                    "./src/main/resources/certs/homologacao.cacerts");
            File file_producao = new File(
                    "./src/main/resources/certs/producao.cacerts");
            // Criar um função separada para realizar essas ações e não repetir tudo isso de
            // código...
            this.keyStoreCadeia = KeyStore.getInstance("JKS");
            if (!file_producao.exists()) {
                cGerarCertificado.GerarCertificados();
            }
            if (getAmbiente() == DFAmbiente.PRODUCAO) {
                try (InputStream cadeia = new FileInputStream(file_producao.getAbsoluteFile())) {
                    this.keyStoreCadeia.load(cadeia, this.getCadeiaCertificadosSenha().toCharArray());
                } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
                    this.keyStoreCadeia = null;
                    throw new KeyStoreException("Nao foi possível montar o KeyStore com a cadeia de certificado.", e);
                }
            } else if (getAmbiente() == DFAmbiente.HOMOLOGACAO) {
                try (InputStream cadeia = new FileInputStream(file_homologacao.getAbsoluteFile())) {
                    this.keyStoreCadeia.load(cadeia, this.getCadeiaCertificadosSenha().toCharArray());
                } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
                    this.keyStoreCadeia = null;
                    throw new KeyStoreException("Nao foi possível montar o KeyStore com a cadeia de certificado.", e);
                }
            }
        }
        return this.keyStoreCadeia;
    }

    @Override
    public KeyStore getCertificadoKeyStore() throws KeyStoreException {
        if (this.keyStoreCertificado == null) {
            this.keyStoreCertificado = KeyStore.getInstance("PKCS12");
            try (InputStream certificadoStream = new FileInputStream(
                    "./src/main/resources/certs/certificado_mock.pfx")) {
                this.keyStoreCertificado.load(certificadoStream, this.getCertificadoSenha().toCharArray());
            } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
                this.keyStoreCadeia = null;
                throw new KeyStoreException("Nao foi possível montar o KeyStore com o certificado.", e);
            }
        }
        return this.keyStoreCertificado;
    }

}
