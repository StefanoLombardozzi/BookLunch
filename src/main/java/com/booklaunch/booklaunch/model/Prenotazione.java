package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {

    @Id  //specifica l'id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ogni tabella inizierà da 1
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

    public Prenotazione(PrenotazioneDTO prenotazioneDTO){
        this.id = prenotazioneDTO.getId();
        this.colazione = prenotazioneDTO.getColazione();
        this.pranzo = prenotazioneDTO.getPranzo();
        this.cena = prenotazioneDTO.getCena();
        this.check_richiesta = prenotazioneDTO.getCheck_richiesta();
        this.data_prenotazione = prenotazioneDTO.getData_prenotazione();
        this.sacchetto_pranzo = prenotazioneDTO.getSacchetto_pranzo();
        this.sacchetto_cena = prenotazioneDTO.getSacchetto_cena();
    }
}
