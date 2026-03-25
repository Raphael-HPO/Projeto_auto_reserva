package br.com.ativa.auto_nfes.service.ServiceSefaz;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.fincatto.documentofiscal.nfe400.webservices.WSFacade;

public class NFeWsFacadeFactory {
    NFeSefazConfig config;

    public NFeWsFacadeFactory(NFeSefazConfig config) {
        this.config = config;
    }

    final public WSFacade WsFacadeFactory() {
        try {
            return new WSFacade(config);
        } catch (KeyManagementException | KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Falha ao inicializar os certificados." +
                    "Verifique a senha e os arquivos.", e);
        }

    }
}
