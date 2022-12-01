package br.com.instalador.controle;


import br.com.instalador.model.entity.Certificado;
import br.com.instalador.view.PROGRESSO.Progresso;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;


public class ARRAY_LIST_CERTIFICADOS {

    CERTIFICADO_CLASS certClass = new CERTIFICADO_CLASS(); 

    public List<Certificado> OBTER_LISTA_CERTIFICADOS(String CAMINHO_PASTA_CERTIFICADOS, String senhaCert) {
        //
        List<Certificado> LISTA_CERTIFICADOS = new ArrayList<>();
        File PASTA_CERTIFICADOS = new File(CAMINHO_PASTA_CERTIFICADOS);
        String[] extensions = new String[]{"pfx"};
        List<File> PFXS = (List<File>) FileUtils.listFiles(PASTA_CERTIFICADOS, extensions, true);
        int cont = 0;
        //------------------------------------
        Progresso p = new Progresso();
        p.definirLimites(1, PFXS.size());
        p.atualizarBarra(1, "Aguarde...");

        for (File arquivo : PFXS) {
            cont++;
            Certificado pfx = new Certificado();
            pfx.setID(cont);
            pfx.setNOME(arquivo.getName());
            pfx.setALIAS(certClass.retornarNomeOriginalCertificado(arquivo.getAbsolutePath(), senhaCert));
            pfx.setCAMINHO(arquivo.getAbsolutePath());
            String dtVenc = certClass.retornarVencimentoCERTIFICADO(arquivo.getAbsolutePath(), senhaCert);
            //
            p.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + PFXS.size() + ") - " + pfx.getNOME());
            //
            if (!dtVenc.contains("keystore password was incorrect")) {
                //-- Tue Nov 24 17:30:00 BRT 2020
                String format = "EEE MMM dd HH:mm:ss zzz yyyy";
                DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                Date dtVencimento = null;
                try {
                    dtVencimento = dateFormat.parse(dtVenc);
                } catch (ParseException ex) {
                    Logger.getLogger(ARRAY_LIST_CERTIFICADOS.class.getName()).log(Level.SEVERE, null, ex);
                }
                //           
                pfx.setDT_VENCIMENTO((java.sql.Date) dtVencimento);
                pfx.setHORA_VENCIMENTO(new SimpleDateFormat("HH:mm:ss").format(dtVencimento));
                //----------

            } else {
                pfx.setEXPIRA(0);
                pfx.setDESC_VENCIMENTO("A Senha do Certificado não confere com a senha do Instalador!");
            }

            //---------------------------            
            LISTA_CERTIFICADOS.add(pfx);
            //----------------------------

        }//fim laçode repetição
        p.dispose();

        System.out.println("TOTAL: " + cont);
        //
        LISTA_CERTIFICADOS.sort(new ExpiraComparador());//ORDENA A LISTA POR TEMPO EXPIRAÇÃO
        return LISTA_CERTIFICADOS;
    }

    //-------------
    public class ExpiraComparador implements Comparator<Certificado> {

        @Override
        public int compare(Certificado cert1, Certificado cert2) {
            return Integer.compare(cert1.getEXPIRA(), cert2.getEXPIRA());
        }
    }
}
