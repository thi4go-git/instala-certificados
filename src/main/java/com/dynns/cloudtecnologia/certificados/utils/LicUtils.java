package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LicUtils {
    
    private LicUtils() {
        
    }
    
    private static final String VENCIMENTO_DATA = "15/12/2199";
    
    public static void validarLic() {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVencimentoLic = new Date();
        try {
            dataVencimentoLic.setTime(formata.parse(VENCIMENTO_DATA).getTime());
        } catch (ParseException ex) {
            throw new GeralException("Erro ao obter data da LIC");
        }
        
        Date dataAtual = new Date();
        
        System.out.println("DATA ATUAL:   " + DataUtils.formataParaBR(dataAtual));
        System.out.println("DATA LICENCA: " + DataUtils.formataParaBR(dataVencimentoLic));
        
        boolean isValid = dataAtual.before(dataVencimentoLic);
        
        if (!isValid) {
            throw new GeralException("Licença expirada em " + VENCIMENTO_DATA);
        }
    }
    
}
