package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * - Il Model contiene le classi che rappresentano le tabelle del Database.
 * - La classe Prenotazione rappresenta l'entità prenotazione nel DB
 * - Con l'annotation @Id si indica che quell'attributo specifico sarà l'id
 * - Con l'annotation @NonNull si indica un attributo che non può essere vuoto
 * - Con l'annotation @ManyToOne e @OneToMany si indicano le cardinalità tra le varie entità del database
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean colazione;

    private Boolean pranzo;

    private Boolean cena;

    private Boolean check_richiesta;

    @NonNull
    private LocalDate data_prenotazione;

    private Boolean sacchetto_pranzo;

    private Boolean sacchetto_cena;


    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;


    @OneToMany(mappedBy = "prenotazione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Richiesta> richieste = new ArrayList<>();


    public Prenotazione(PrenotazioneBuilder builder){
        this.id = builder.id;
        this.colazione = builder.colazione;
        this.pranzo = builder.pranzo;
        this.cena = builder.cena;
        this.check_richiesta = builder.check_richiesta;
        this.data_prenotazione = builder.data_prenotazione;
        this.sacchetto_pranzo = builder.sacchetto_pranzo;
        this.sacchetto_cena = builder.sacchetto_cena;
        this.utente = builder.utente;
    }

    /**
     * Aggiungo la classe Builder per la creazione di oggetti builder implementati a livello service,
     * dove è contenuta la business logic
     */
    public static class PrenotazioneBuilder {
        private Long id;
        private Boolean colazione;
        private Boolean pranzo;
        private Boolean cena;
        private Boolean check_richiesta;
        private LocalDate data_prenotazione;
        private Boolean sacchetto_pranzo;
        private Boolean sacchetto_cena;
        private Utente utente;

        public Prenotazione.PrenotazioneBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder colazione(Boolean colazione) {
            this.colazione = colazione;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder pranzo(Boolean pranzo) {
            this.pranzo = pranzo;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder cena(Boolean cena) {
            this.cena = cena;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder check_richiesta(Boolean check_richiesta) {
            this.check_richiesta = check_richiesta;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder data_prenotazione(LocalDate data_prenotazione) {
            this.data_prenotazione = data_prenotazione;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder sacchetto_pranzo(Boolean sacchetto_pranzo) {
            this.sacchetto_pranzo = sacchetto_pranzo;
            return this;
        }

        public Prenotazione.PrenotazioneBuilder sacchetto_cena(Boolean sacchetto_cena) {
            this.sacchetto_cena = sacchetto_cena;
            return this;
        }

        public PrenotazioneBuilder utente(Utente utente) {
            this.utente = utente;
            return this;
        }

        /**
         * Ritorno l'oggetto Prenotazione
         * @return prenotazione
         */
        public Prenotazione build() {
            Prenotazione prenotazione = new Prenotazione(this);
            return prenotazione;
        }


    }
}
