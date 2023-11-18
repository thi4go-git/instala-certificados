package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DataUtils {

    private DataUtils() {
    }

    private static final SimpleDateFormat FORMATA_BD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMATA_BR = new SimpleDateFormat("dd / MM / yyyy");

    public static int retornarDiferencaEmDias(String dataMenor, String dataMaior) {
        // yyyy-mm-dd 
        LocalDate menor = LocalDate.parse(dataMenor);
        LocalDate maior = LocalDate.parse(dataMaior);
        long diferencaEmDias = ChronoUnit.DAYS.between(menor, maior);
        return (int) diferencaEmDias;
    }

    public static String formataParaBD(Date data) {
        return FORMATA_BD.format(data);
    }

    public static String formataParaBR(Date data) {
        return FORMATA_BR.format(data);
    }

    public static Date parseDataBrStringToDate(String dataStr) {
        try {
            return FORMATA_BR.parse(dataStr);
        } catch (ParseException ex) {
            throw new GeralException("Erro ao Converter Data String para Date");
        }
    }


}
