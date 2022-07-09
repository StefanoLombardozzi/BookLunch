package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utente {
    @Id  //specifica l'id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ogni tabella inizierà da 1
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private String cognome;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private RoleEnum ruolo;


    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Richiesta> richieste = new ArrayList<>();


    public Utente(UtenteDTO utenteDTO) {
        this.id = utenteDTO.id;
        this.nome = utenteDTO.nome;
        this.cognome = utenteDTO.cognome;
        this.email = utenteDTO.getEmail();
        this.password = utenteDTO.getPassword();
        this.ruolo = utenteDTO.ruolo;
    }

}
