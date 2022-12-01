
package br.com.instalador.controle;

import br.com.instalador.model.entity.Certificado;
import br.com.instalador.utils.ArquivoUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.swing.JOptionPane;


public class CERTIFICADO_CLASS {

    String CAMINHO_CERTIFICADO_TMP = System.getProperty("java.io.tmpdir") + "CERTIFICADO.pfx";

    public void INSTALAR_CERTIFICADO(String SENHA_CERTIFICADO, String NOME_CERT,
            int idCertificado, String ALIAS, TelaController controle) {
        if (RECUPERAR_CERTIFICADO_BD(idCertificado, controle) == true) {
            File CERTIFICADO_TMP = new File(CAMINHO_CERTIFICADO_TMP);
            if (CERTIFICADO_TMP.exists()) {
                int CERT_FOI_INSTALADO = INSTALAR_CERTIFICADO_CMD(SENHA_CERTIFICADO);// = 0 sucesso
                if (CERT_FOI_INSTALADO == 0) {
                    JOptionPane.showMessageDialog(null, "SUCESSO AO INSTALAR CERTIFICADO: " + NOME_CERT + " \r\n"
                            + " ALIAS:  " + ALIAS + "\r\n"
                            + " CÓDIGO DE STATUS (0=SUCESSO): " + CERT_FOI_INSTALADO);
                } else {
                    JOptionPane.showMessageDialog(null, "*** ERRO AO INSTALAR CERTIFICADO CMD (public class CERTIFICADO_CLASS) - CÓD erro " + CERT_FOI_INSTALADO + " "
                            + " - FAVOR VERIFICAR A SENHA DE INSTALAÇÃO!!!");
                }
                DELETA_CERTIFICADO_TMP();
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERRO AO RECUPERAR CERTIFICADO DO BANCO DE DADOS!");
        }
    }

    public Boolean RECUPERAR_CERTIFICADO_BD(int idCertificado, TelaController controle) {

        Certificado certificadoBD = controle.baixarCertificadoPorID(idCertificado);
        if (certificadoBD != null) {
            ArquivoUtils.byteTofile(certificadoBD.getBYTES_CERTIFICADO(), CAMINHO_CERTIFICADO_TMP);
            return true;
        }
        return false;
    }

    public void DELETA_CERTIFICADO_TMP() {
        File CERTIFICADO_TMP = new File(CAMINHO_CERTIFICADO_TMP);
        if (CERTIFICADO_TMP.exists()) {
            CERTIFICADO_TMP.delete();
        }
    }

    public int INSTALAR_CERTIFICADO_CMD(String SENHA_CERTIFICADO) {
        String comando = "certutil -f -user -p " + SENHA_CERTIFICADO + " -importpfx \"" + CAMINHO_CERTIFICADO_TMP + "\" NoExport";
        Process exec;
        try {
            exec = Runtime.getRuntime().exec(comando);
            exec.waitFor();
            System.out.println(exec.exitValue());
            return exec.exitValue();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Erro InterruptedException: " + e.getMessage());
            return 1;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro IO:  " + e.getMessage());
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro geral: " + e.getMessage());
            return 1;
        } finally {
            // Fecha o comando DOS

        }
    }

    //--------------------
    public String retornarVencimentoCERTIFICADO(String caminhoCert, String senhaCert) {
        String VENCIMENTO = "";
        String erro;
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(caminhoCert), senhaCert.toCharArray());
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keystore.getCertificate(alias).getType().equals("X.509")) {
                    VENCIMENTO = "" + ((X509Certificate) keystore.getCertificate(alias)).getNotAfter();
                }
            }
            VENCIMENTO = VENCIMENTO.trim();
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException e) {
            erro = "ERRO(retornarVencimentoCERTIFICADO())-" + e;
            System.out.println(erro);
            //-java.io.IOException: keystore password was incorrect
            if (erro.contains("keystore password was incorrect")) {
                return erro;
            }
        }
        //        
        return VENCIMENTO;
    }

    //--------------------------
    public String retornarNomeOriginalCertificado(String caminhoCert, String senhaCert) {
        String ALIAS = "";
        //
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(caminhoCert), senhaCert.toCharArray());
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keystore.getCertificate(alias).getType().equals("X.509")) {
                    ALIAS = alias;
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            System.out.println(e);
        }
        //
        return ALIAS;
    }

}
