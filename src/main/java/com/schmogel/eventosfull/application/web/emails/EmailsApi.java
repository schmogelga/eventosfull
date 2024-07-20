package com.schmogel.eventosfull.application.web.emails;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.schmogel.eventosfull.application.dto.EmailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Emails", description = "Operações de emails")
@RequestMapping(value = "/emails", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EmailsApi {

    @Operation(summary = "Envia um email", description = "Envia um email", method = "POST")
    @PostMapping
    void enviarEmails(@RequestBody EmailRequest emailRequest);
}
