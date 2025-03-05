package com.dynns.cloudtecnologia.certificados.extrator;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.CertificadoInformacoesDTO;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.DetalhesAtualizacaoDTO;
import com.dynns.cloudtecnologia.certificados.model.enums.StatusAtualizacaoEnum;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import com.dynns.cloudtecnologia.certificados.utils.FilesUtils;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
import com.dynns.cloudtecnologia.certificados.view.telas.DetalhesAtualizacao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.ArrayList;

public class CertificadoExtrator {

    BarraProgresso progresso;
    String caminhoPasta;
    boolean isProcessarVencidos;

    private final CertificadoController certificadoController;
    private final ConfiguracaoCertificadoController configuracaoCertificadoController;
    private final LogCertificadoController logCertificadoController;
    
    private static final String MSG_SENHA_INCORRETA = "A Senha do Certificado não confere com a senha do Instalador!";


    public CertificadoExtrator(String caminhoPasta,boolean isProcessarVencidos) {
        this.certificadoController = new CertificadoController();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.logCertificadoController = new LogCertificadoController();
        this.caminhoPasta = caminhoPasta;
        this.isProcessarVencidos = isProcessarVencidos;
    }

    public void processarCertificadosPasta() {

        List<DetalhesAtualizacaoDTO> detalhesAtualizacaoList = new ArrayList<>();

        File pastaCertificados = new File(caminhoPasta);
        if (pastaCertificados.exists() && pastaCertificados.isDirectory()) {

            ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();
            progresso = new BarraProgresso();
            progresso.definirLimites(1, 100);
            progresso.atualizarBarra(1, "AGUARDE... CARREGANDO CERTIFICADOS NA PASTA!!!");

            String[] extensions = new String[]{"pfx"};
            List<File> listaPfxs = (List<File>) FileUtils.listFiles(pastaCertificados, extensions, true);

            progresso.definirLimites(1, listaPfxs.size());

            String senhaCertificado = configuracaoCertificado.getSenhaCertificado();
            int cont = 0;
            for (File certificadoPfx : listaPfxs) {
                cont++;
                String pathCertificado = certificadoPfx.getAbsolutePath();

                long certificadoSize = certificadoPfx.length();
                if (certificadoSize != 0) {
                    CertificadoInformacoesDTO infoCert = CertificadoUtils.retornarInformacoesCertificado(pathCertificado, senhaCertificado);
                    progresso.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + listaPfxs.size() + ") - " + certificadoPfx.getName());
                    if (!infoCert.getDataVencimento().contains("keystore password was incorrect")) {
                        Date dtVencimento = null;
                        String format = "EEE MMM dd HH:mm:ss zzz yyyy";
                        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                        try {
                            dtVencimento = dateFormat.parse(infoCert.getDataVencimento());
                        } catch (ParseException ex) {
                            detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), null, StatusAtualizacaoEnum.ERRO_DE_PROCESSAMENTO));
                        }
                        if (Objects.nonNull(dtVencimento)) {                        
                             
                            Certificado certificado = new Certificado();
                            certificado.setNome(certificadoPfx.getName());
                            certificado.setAlias(infoCert.getAlias());
                            java.sql.Date dataSQL = new java.sql.Date(dtVencimento.getTime());
                            certificado.setDataVencimento(dataSQL);
                            certificado.setHoraVencimento(new SimpleDateFormat("HH:mm:ss").format(dtVencimento));
                            certificado.setDescricaoVencimento("DESCRIÇÃO");
                            certificado.setExpira(0);
                            certificado.setCertificadoByte(FilesUtils.fileTobyte(new File(certificadoPfx.getAbsolutePath())));

                            if(isCertificadoVencido(dtVencimento)){
                                if(isProcessarVencidos){
                                    if (!certificadoController.certificadoExists(certificado)) {
                                        certificadoController.save(certificado);
                                        progresso.atualizarBarra(cont, "SUCESSO AO SALVAR NO BANCO: " + certificado.getNome());
                                        detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), certificado.getDataVencimento(), StatusAtualizacaoEnum.CERTIFICADO_NOVO));
                                    } else {
                                      detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), certificado.getDataVencimento(), StatusAtualizacaoEnum.CERTIFICADO_JA_EXISTE));
                                    }
                                }else{
                                    progresso.atualizarBarra(cont, "CERTIFICADO IGNORADO: " + certificado.getNome());
                                    detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), certificado.getDataVencimento(), StatusAtualizacaoEnum.CERTIFICADO_VENCIDO));                                   
                                }                                
                            }else{
                                if (!certificadoController.certificadoExists(certificado)) {
                                    certificadoController.save(certificado);
                                    progresso.atualizarBarra(cont, "SUCESSO AO SALVAR NO BANCO: " + certificado.getNome());
                                    detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), certificado.getDataVencimento(), StatusAtualizacaoEnum.CERTIFICADO_NOVO));
                                } else {
                                    detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), certificado.getDataVencimento(), StatusAtualizacaoEnum.CERTIFICADO_JA_EXISTE));
                                }
                            }  
                            
                        }
                    } else {
                        detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), null, StatusAtualizacaoEnum.SENHA_DIVERGENTE));
                    }
                } else {
                    detalhesAtualizacaoList.add(new DetalhesAtualizacaoDTO(pathCertificado, certificadoPfx.getName(), null, StatusAtualizacaoEnum.ERRO_DE_PROCESSAMENTO));
                }
            }
            progresso.dispose();

            long qtdeCertificadosProcessados = detalhesAtualizacaoList.stream()
                    .count();

            long qtdeCertificadosNovos = detalhesAtualizacaoList.stream()
                    .filter(detalhe -> detalhe.getStatusAtualizacaoEnum() == StatusAtualizacaoEnum.CERTIFICADO_NOVO)
                    .count();

            long qtdeCertificadosSenhaDivergente = detalhesAtualizacaoList.stream()
                    .filter(detalhe -> detalhe.getStatusAtualizacaoEnum() == StatusAtualizacaoEnum.SENHA_DIVERGENTE)
                    .count();

            long qtdeErrosProcessamento = detalhesAtualizacaoList.stream()
                    .filter(detalhe -> detalhe.getStatusAtualizacaoEnum() == StatusAtualizacaoEnum.ERRO_DE_PROCESSAMENTO)
                    .count();

            long qtdeCertificadosJaExistentes = detalhesAtualizacaoList.stream()
                    .filter(detalhe -> detalhe.getStatusAtualizacaoEnum() == StatusAtualizacaoEnum.CERTIFICADO_JA_EXISTE)
                    .count();

            String detalhes = "Rotina de atualização de certificados realizada com sucesso,"
                    + " RESUMO: Certificados Processados: " + qtdeCertificadosProcessados + ","
                    + "Certificados Instalados: " + qtdeCertificadosNovos
                    + ", Certificados senha Divergente: " + qtdeCertificadosSenhaDivergente
                    + ", Erros de Processamento: " + qtdeErrosProcessamento
                    + ", Certificados Já existentes: " + qtdeCertificadosJaExistentes;
            logCertificadoController.salvarLog(TipoLog.ADMIN_ATUALIZAR_CERTIFICADOS, detalhes);

            JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CONCLUÍDA!!!");

            DetalhesAtualizacao detalhesAtualizacao = new DetalhesAtualizacao();
            detalhesAtualizacao.setDetalhesAtualizacaoList(detalhesAtualizacaoList);
            detalhesAtualizacao.gerarRelatorio();
            detalhesAtualizacao.setLocationRelativeTo(null);
            detalhesAtualizacao.setEnabled(true);
            detalhesAtualizacao.setResizable(false);
            detalhesAtualizacao.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "A PASTA INFORMADA É INVÁLIDA OU ESSE COMPUTADOR"
                    + " NÃO POSSUI ACESSO À ELA!");
        }
    }
    
    private boolean isCertificadoVencido(final Date dataVencimento){
        int diferencaEmDias = 0;
        String dataAtual = DataUtils.formataParaBD(new Date());
        String dataVencimentoStr = DataUtils.formataParaBD(dataVencimento);
        diferencaEmDias = DataUtils.retornarDiferencaEmDias(dataAtual, dataVencimentoStr);        
        return diferencaEmDias < 0? true : false;
    }
}
