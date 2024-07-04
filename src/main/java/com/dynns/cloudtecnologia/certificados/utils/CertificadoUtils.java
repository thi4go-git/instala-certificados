package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.CertificadoInformacoesDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CertificadoUtils {

    private CertificadoUtils() {
    }

    private static final String TYPE_CERTIFICADO = "X.509";

    public static CertificadoInformacoesDTO retornarInformacoesCertificado(String caminhoCert, String senhaCert) {
        CertificadoInformacoesDTO informacoesCertificado = new CertificadoInformacoesDTO();
        try (FileInputStream fileInputStream = new FileInputStream(caminhoCert)) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            Security.addProvider(new BouncyCastleProvider());
            keystore.load(fileInputStream, senhaCert.toCharArray());
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keystore.getCertificate(alias).getType().equals(TYPE_CERTIFICADO)) {
                    informacoesCertificado.setDataVencimento("" + ((X509Certificate) keystore.getCertificate(alias)).getNotAfter());
                    informacoesCertificado.setAlias(alias);
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            informacoesCertificado.setDataVencimento(ex.getMessage());
            informacoesCertificado.setAlias(ex.getMessage());
        }

        return informacoesCertificado;
    }

    public static String converterObjetoParaJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new GeralException("Erro ao converter objeto para JSON!");
        }
        return json;
    }
}
