package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.dto.EmailSendDTO;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.JOptionPane;

public class EmailUtils {

    private EmailUtils() {
    }

    public static void enviarEmail(EmailSendDTO emailSendDTO) {

        BarraProgresso progressoEnviaCert = new BarraProgresso();
        progressoEnviaCert.definirLimites(0, 100);
        progressoEnviaCert.atualizarBarra(50, "Enviando email para " + emailSendDTO.getDestinatario() + " ...");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", emailSendDTO.getTlsEmail());
        props.put("mail.smtp.host", emailSendDTO.getSmtpEmail());
        props.put("mail.smtp.port", emailSendDTO.getSmtpPortEmail());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSendDTO.getUsername(), emailSendDTO.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailSendDTO.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailSendDTO.getDestinatario()));
            message.setSubject(emailSendDTO.getAssunto());

            if (emailSendDTO.getAnexoBytes() != null && emailSendDTO.getAnexoNome() != null) {
                //MENSAGEM COM ANEXO.
                // Criar a parte de texto do e-mail
                MimeBodyPart textoPart = new MimeBodyPart();
                textoPart.setText(emailSendDTO.getMensagemPadrao());

                // Criar a parte do anexo
                MimeBodyPart anexoPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(emailSendDTO.getAnexoBytes(), "application/octet-stream");
                anexoPart.setDataHandler(new DataHandler(source));
                anexoPart.setFileName(emailSendDTO.getAnexoNome());

                // Combinar as partes em um conteúdo multipart
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textoPart);
                multipart.addBodyPart(anexoPart);

                // Adicionar o conteúdo multipart ao e-mail
                message.setContent(multipart);
            } else {
                //MENSAGEM SEM ANEXO.
                message.setText(emailSendDTO.getMensagemPadrao());
            }

            Transport.send(message);
            progressoEnviaCert.dispose();
            String detalhes = "Certificado enviado: " + CertificadoUtils.converterObjetoParaJson(emailSendDTO.getCertificado());
            LogCertificadoController logCertificadoController = new LogCertificadoController();
            logCertificadoController.salvarLog(TipoLog.ADMIN_CERTIFICADO_ENVIADO_EMAIL, detalhes);

            JOptionPane.showMessageDialog(null, "Sucesso ao enviar email: " + emailSendDTO.getDestinatario());

        } catch (MessagingException e) {
            throw new GeralException("ERRO ao enviar email: " + e);
        }
    }
}
