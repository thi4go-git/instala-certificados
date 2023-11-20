package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilesUtils {

    private FilesUtils() {
    }

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

    public static byte[] fileTobyte(File file) {
        try {
            byte[] bytes;
            try (InputStream stream = new FileInputStream(file)) {
                bytes = new byte[(int) file.length()];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length && (numRead = stream.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }
            }
            return bytes;
        } catch (FileNotFoundException e) {
            throw new GeralException("Erro ao Converter FileToByte FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            throw new GeralException("Erro ao Converter FileToByte IOException: " + e.getMessage());
        }
    }

}
