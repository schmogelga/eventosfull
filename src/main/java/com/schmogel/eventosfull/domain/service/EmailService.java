package com.schmogel.eventosfull.domain.service;

import java.util.Optional;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.schmogel.eventosfull.application.dto.EmailRequest;
import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.domain.model.UserInfo;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EventoService eventoService;
    private final String host = System.getenv("HOST");
    private final String port = System.getenv("PORT");
    private final String username = System.getenv("USER");
    private final String password = System.getenv("PASSWORD");

    public void enviarEmail(EmailRequest emailRequest) {

        Evento evento = eventoService.recuperarEvento(emailRequest.eventoId());
        Session session = getSession();

        evento.getInscricoes().stream()
                .map(this::getEmailInfo)
                .forEach(emailInfo -> this.enviarEmail(emailInfo, session));
    }

    private Session getSession() {
        return Session.getInstance(
                getProperties(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return props;
    }

    private void enviarEmail(EmailInfo emailInfo, Session session) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.email()));
            message.setSubject(emailInfo.assunto());
            message.setText(emailInfo.conteudo());

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + emailInfo.email());
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o e-mail: " + e.getMessage());
        }
    }

    private EmailInfo getEmailInfo(Inscricao inscricao) {
        return EmailInfo.builder()
                .email(Optional.ofNullable(inscricao.getUserInfo()).map(UserInfo::getEmail).orElse(null))
                .assunto(Optional.ofNullable(inscricao.getEvento()).map(this::formatarAssunto).orElse(null))
                .conteudo(
                        Optional.ofNullable(inscricao.getEvento()).map(this::formatarConteudo).orElse(null))
                .build();
    }

    private String formatarAssunto(Evento evento) {
        return "Seu evento: " + evento.getNome();
    }

    private String formatarConteudo(Evento evento) {
        return "Gostaríamos de informar que o seu evento "
                + evento.getNome()
                + " do dia "
                + evento.getData()
                + " está se aproximando!";
    }

    @Builder
    private record EmailInfo(String email, String assunto, String conteudo) {}
}
