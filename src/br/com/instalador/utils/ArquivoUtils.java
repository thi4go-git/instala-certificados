
package br.com.instalador.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArquivoUtils {

    protected static String destinoSalvar = System.getProperty("java.io.tmpdir");

    public static byte[] fileTobyte(File file) {
        InputStream stream;
        try {
            stream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = stream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            stream.close();
            return bytes;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    //----------------------
    public static File byteTofile(byte[] bytesArquivo, String nomeArquivo) {
        File file;
        try {
            byte[] bytes = bytesArquivo;
            // Converte o array de bytes em file
            file = new File(nomeArquivo);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
            }
            return file;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
