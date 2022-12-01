
package br.com.instalador.utils;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;


public class Data {

    SimpleDateFormat formataDataBD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataDataBR = new SimpleDateFormat("dd/MM/yyyy");

    public String validarData(JDateChooser DT_INICIO, JDateChooser DT_FIM) {
        if (DT_INICIO.getDate() == null | DT_FIM.getDate() == null) {
            JOptionPane.showMessageDialog(null, " FAVOR PREENCHER A DATA!");
            return "NÃO";
        } else {
            GregorianCalendar DATA_GREGORIANA_MINIMA = new GregorianCalendar();
            GregorianCalendar DATA_GREGORIANA_LIMITE = new GregorianCalendar();
            DATA_GREGORIANA_MINIMA.set(Integer.parseInt(formataDataBD.format(DT_INICIO.getDate()).substring(0, 4)),
                    Integer.parseInt(formataDataBD.format(DT_INICIO.getDate()).substring(5, 7)) - 1,
                    Integer.parseInt(formataDataBD.format(DT_INICIO.getDate()).substring(8, 10)));
            DATA_GREGORIANA_LIMITE.set(Integer.parseInt(formataDataBD.format(DT_FIM.getDate()).substring(0, 4)),
                    Integer.parseInt(formataDataBD.format(DT_FIM.getDate()).substring(5, 7)) - 1,
                    Integer.parseInt(formataDataBD.format(DT_FIM.getDate()).substring(8, 10)));
            if (DATA_GREGORIANA_MINIMA.after(DATA_GREGORIANA_LIMITE)) {
                JOptionPane.showMessageDialog(null, "*** ERRO! A DATA DE INICIO NÃO PODE SER MAIOR QUE A DATA FINAL! ***");
                return "NÃO";
            } else {
                return "SIM";
            }
        }
    }

    //------------------------------------------------------------------------------------------------------
    public String retornaDataMesAtualINICIO_FIM_BD() {
        Calendar c = Calendar.getInstance();
        int DIA_MINIMO = c.getActualMinimum(Calendar.DAY_OF_MONTH);//DIA máximo  
        int DIA_MAXIMO = c.getActualMaximum(Calendar.DAY_OF_MONTH);//DIA máximo       
        int MES_ATUAL = Integer.parseInt(formataDataBD.format(c.getTime()).substring(5, 7));
        int ANO_ATUAL = Integer.parseInt(formataDataBD.format(c.getTime()).substring(0, 4));
        //
        GregorianCalendar DATA_GREGORIANA_INI = new GregorianCalendar();
        DATA_GREGORIANA_INI.set(ANO_ATUAL, MES_ATUAL - 1, DIA_MINIMO);
        //
        GregorianCalendar DATA_GREGORIANA_FIM = new GregorianCalendar();
        DATA_GREGORIANA_FIM.set(ANO_ATUAL, MES_ATUAL - 1, DIA_MAXIMO);
        //
        return formataDataBD.format(DATA_GREGORIANA_INI.getTime()) + "|" + formataDataBD.format(DATA_GREGORIANA_FIM.getTime());
    }

    //
    public String retornarDataAtualStringBR_dd_MM_yyyy() {
        Date dataHoje = new Date();
        String dataHj = formataDataBR.format(dataHoje);
        return dataHj;
    }

    public String retornarDataStringBR_dd_MM_yyyy(Date data) {
        String dataForm;
        try {
            dataForm = formataDataBR.format(data);
        } catch (Exception e) {
            dataForm = "00/00/0000";
        }

        return dataForm;
    }

    //-------------------------------------------------
    public int retornarDiferencaEmDias(String dataMenor, String dataMaior) {// yyyy-mm-dd 
        // yyyy-mm-dd 
        LocalDate menor = LocalDate.parse(dataMenor);
        LocalDate maior = LocalDate.parse(dataMaior);
        long diferencaEmDias = ChronoUnit.DAYS.between(menor, maior);
        return (int) diferencaEmDias;
    }

    //------------------------------------
    public String retornarDataAtualStringBD_yyyy_MM_dd() {
        Date dataHoje = new Date();
        String dataHj = formataDataBD.format(dataHoje);
        return dataHj;
    }
    //

    public int retornarAnoAtualINT() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public int retornarMesAtualINT() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.MONTH);
        return year;
    }

    //
    public String retornarNomeMesString(int numeroDoMes) {
        switch (numeroDoMes) {
            case 1:
                return "JANEIRO";
            case 2:
                return "FEVEREIRO";
            case 3:
                return "MARÇO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAIO";
            case 6:
                return "JUNHO";
            case 7:
                return "JULHO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SETEMBRO";
            case 10:
                return "OUTUBRO";
            case 11:
                return "NOVEMBRO";
            case 12:
                return "DEZEMBRO";
        }
        return null;
    }
    //

    public int retornarDiaMesATUAL_INT() {
        Calendar data = Calendar.getInstance();
        int dia = data.get(Calendar.DAY_OF_MONTH);
        System.out.println("DIA: " + dia);
        return dia;
    }

    public String DataPadraoBR(String dataBD) {
        //2020-12-12
        String dataBR;
        String dia, mes, ano;
        dia = dataBD.substring(8, 10);
        mes = dataBD.substring(5, 7);
        ano = dataBD.substring(0, 4);
        dataBR = dia + "/" + mes + "/" + ano;
        return dataBR;
    }
}
