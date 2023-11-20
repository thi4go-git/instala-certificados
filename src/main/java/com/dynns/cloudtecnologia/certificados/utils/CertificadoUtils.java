package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CertificadoUtils {

    private CertificadoUtils() {
    }

    private static final String TYPE_CERTIFICADO = "X.509";

    public static String retornarVencimentoCertificado(String caminhoCert, String senhaCert) {
        String retorno = "";

        try (FileInputStream fileInputStream = new FileInputStream(caminhoCert)) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(fileInputStream, senhaCert.toCharArray());

            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keystore.getCertificate(alias).getType().equals(TYPE_CERTIFICADO)) {
                    retorno = "" + ((X509Certificate) keystore.getCertificate(alias)).getNotAfter();
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException ex) {
            retorno = ex.getMessage();
        }

        return retorno;
    }

    public static String retornarAliasCertificado(String caminhoCert, String senhaCert) {
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(caminhoCert), senhaCert.toCharArray());
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keystore.getCertificate(alias).getType().equals(TYPE_CERTIFICADO)) {
                    return alias;
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new GeralException("Erro ao retornar ALIAS do Certificado!");
        }
        return "Sem informações";
    }

}
