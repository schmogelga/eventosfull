package com.schmogel.eventosfull.application.web.emails;

import org.springframework.web.bind.annotation.RestController;

import com.schmogel.eventosfull.application.dto.EmailRequest;
import com.schmogel.eventosfull.domain.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailsController implements EmailsApi {

    private final EmailService emailService;

    @Override
    public void enviarEmails(EmailRequest emailRequest) {
        emailService.enviarEmail(emailRequest);
    }
}
