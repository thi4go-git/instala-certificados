package com.dynns.cloudtecnologia.certificados.utils;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class EmailUtils {

    private EmailUtils() {
    }

    public static void enviarEmail(ConfiguracaoCertificado config, String remetente) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", config.getTlsEmail());
        props.put("mail.smtp.host", config.getSmtpEmail());
        props.put("mail.smtp.port", config.getSmtpPortEmail());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUserEmail(), config.getPassEmail());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getUserEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(remetente));
            message.setSubject(config.getAssuntoEmail());
            message.setText(config.getMensagemPadraoEmail());

            Transport.send(message);

            JOptionPane.showMessageDialog(null, "Sucesso ao enviar email: " + remetente);

        } catch (MessagingException e) {
            throw new GeralException("ERRO ao enviar email: " + e.getCause());
        }
    }
}
