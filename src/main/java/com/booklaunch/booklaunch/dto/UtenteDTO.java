package com.booklaunch.booklaunch.dto;

import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.model.Utente;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UtenteDTO {

    public Long id;
    public String nome;
    public String cognome;
    private String email;
    private String password;

    public RoleEnum ruolo;


    public UtenteDTO(Utente utente) {
        this.id = utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.ruolo = utente.getRuolo();
        this.password = utente.getPassword();
        this.email = utente.getEmail();
    }
}
