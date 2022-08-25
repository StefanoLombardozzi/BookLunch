package com.booklaunch.booklaunch.dto;

import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.model.Utente;
import lombok.*;

/**
 * La classe UtenteDTO si occupa dello scambio di informazioni (request & response),
 * tramite i vari endpoint, fra back-end e front-end
 */

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
