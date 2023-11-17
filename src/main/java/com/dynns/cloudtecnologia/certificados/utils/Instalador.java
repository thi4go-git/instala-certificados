package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;

public class Instalador {

    String caminhoCertificadoTemp = System.getProperty("java.io.tmpdir") + "CERTIFICADO.pfx";

    public void instalarCertificado(ConfiguracaoCertificado configuracao, Certificado certificado) {
        FileUtils.byteTofile(certificado.getCertificadoByte(), caminhoCertificadoTemp);
        File certificadoTemp = new File(caminhoCertificadoTemp);

        if (certificadoTemp.exists()) {
            String comando = "certutil -f -user -p " + configuracao.getSenhaCertificado() + " -importpfx \"" + caminhoCertificadoTemp + "\" NoExport";
            ProcessBuilder processBuilder = new ProcessBuilder(comando.split(" "));
            processBuilder.redirectErrorStream(true);

            try {
                Process process = processBuilder.start();
                process.waitFor();
                int exitCode = process.exitValue();

                deletarCertificado();
                JOptionPane.showMessageDialog(null, "Sucesso ao instalar certificado. CÓD: " + exitCode);
            } catch (IOException e) {
                throw new GeralException("Erro ao instalar IOException: CÓD: 1 ");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new GeralException("Erro ao instalar InterruptedException: CÓD: 1 ");
            }
        }
    }

    private void deletarCertificado() {
        Path certificadoPath = FileSystems.getDefault().getPath(caminhoCertificadoTemp);
        try {
            Files.delete(certificadoPath);
        } catch (IOException e) {
            throw new GeralException("Erro ao deletar o certificado. ");
        }
    }

}
