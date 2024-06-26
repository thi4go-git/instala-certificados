package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.swing.JOptionPane;

public class DataUtils {

    private DataUtils() {
    }

    private static final String FORMATA_BR = "dd / MM / yyyy";
    private static final String FORMATA_BD = "yyyy-MM-dd";

    public static int retornarDiferencaEmDias(String dataMenor, String dataMaior) {
        // yyyy-mm-dd 
        LocalDate menor = LocalDate.parse(dataMenor);
        LocalDate maior = LocalDate.parse(dataMaior);
        long diferencaEmDias = ChronoUnit.DAYS.between(menor, maior);
        return (int) diferencaEmDias;
    }

    public static String formataParaBD(Date data) {
        SimpleDateFormat formataBD = new SimpleDateFormat(FORMATA_BD);
        return formataBD.format(data);
    }

    public static String formataParaBR(Date data) {
        SimpleDateFormat formataBr = new SimpleDateFormat(FORMATA_BR);
        return formataBr.format(data);
    }

    public static Date parseDataBrStringToDate(String dataStr) {
        SimpleDateFormat formataBr = new SimpleDateFormat(FORMATA_BR);
        try {
            return formataBr.parse(dataStr);
        } catch (ParseException ex) {
            throw new GeralException("Erro ao Converter Data String para Date");
        }
    }

    public static String dataBDStringToDataBRString(String dataBD) {
        //deve receber:YYYY-mm-DD
        String ano = dataBD.substring(0, 4);
        String mes = dataBD.substring(5, 7);
        String dia = dataBD.substring(8, 10);

        return dia.concat("/").concat(mes).concat("/").concat(ano);
    }

    public static boolean validarDataInicioAndFim(Date dataInicio, Date dataFim) {
        if (dataFim.before(dataInicio)) {
            JOptionPane.showMessageDialog(null, "Atenção: A data FINAL não pode ser MENOR que a data INICIAL!");
            return false;
        }
        return true;
    }

}
