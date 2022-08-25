package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * - Il Model contiene le classi che rappresentano le tabelle del Database.
 * - La classe Richiesta rappresenta l'entità richiesta nel DB
 * - Con l'annotation @Id si indica che quell'attribbuto specifico sarà l'id
 * - Con l'annotation @NonNull si indica un attribbuto che non può essere vuoto
 * - Con l'annotation @ManyToOne e @OneToMany si indicano le cardinalità tra le varie entità del database
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean post_pranzo;

    private Boolean post_cena;

    private Boolean stato_richiesta;

    @ManyToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;


    public Richiesta(RichiestaDTO richiestaDTO) {
        this.id = richiestaDTO.getId();
        this.post_pranzo = richiestaDTO.getPost_pranzo();
        this.post_cena = richiestaDTO.getPost_cena();
        this.stato_richiesta = richiestaDTO.getStato_richiesta();
    }
}
