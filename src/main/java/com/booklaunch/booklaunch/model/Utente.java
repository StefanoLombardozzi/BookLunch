package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * - Il Model contiene le classi che rappresentano le tabelle del Database.
 * - La classe Utente rappresenta l'entità utente nel DB
 * - Con l'annotation @Id si indica che quell'attribbuto specifico sarà l'id
 * - Con l'annotation @NonNull si indica un attribbuto che non può essere vuoto
 * - Con l'annotation @ManyToOne e @OneToMany si indicano le cardinalità tra le varie entità del database
 */
@Entity
//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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




    public Utente(UtenteBuilder builder){
        this.id = builder.id;
        this.nome = builder.nome;
        this.cognome = builder.cognome;
        this.email = builder.email;
        this.password = builder.password;
        this.ruolo = builder.ruolo;
    }

    /**
     * Aggiungo la classe Builder per la creazione di oggetti builder a livello service,
     * dove è contenuta la business logic
     */
    public static class UtenteBuilder {
        private Long id;
        private String nome;
        private String cognome;
        private String email;
        private String password;
        private RoleEnum ruolo;

        public UtenteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UtenteBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public UtenteBuilder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public UtenteBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UtenteBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UtenteBuilder ruolo(RoleEnum ruolo) {
            this.ruolo = ruolo;
            return this;
        }

        /**
         * Ritorno l'oggetto Utente
         * @return utente
         */
        public Utente build() {
            Utente utente = new Utente(this);
            return utente;
        }

    }



}
