package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static File byteTofile(byte[] bytesArquivo, String caminhoArquivo) {
        File file;
        try {
            byte[] bytes = bytesArquivo;
            file = new File(caminhoArquivo);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
            }
            return file;
        } catch (IOException e) {
            throw new GeralException("Erro ao converter certificado byteTofile ");
        }
    }
}
