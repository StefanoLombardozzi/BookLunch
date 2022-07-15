package com.booklaunch.booklaunch.dto;

import com.booklaunch.booklaunch.model.Prenotazione;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneDTO {

    private Long id;

    private Boolean colazione;

    private Boolean pranzo;

    private Boolean cena;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_prenotazione;

    private Boolean sacchetto_pranzo;

    private Boolean sacchetto_cena;

    private Boolean check_richiesta;

    private Long id_utente;

    public PrenotazioneDTO(Prenotazione prenotazione){
        this.id = prenotazione.getId();
        this.colazione = prenotazione.getColazione();
        this.pranzo = prenotazione.getPranzo();
        this.cena = prenotazione.getCena();
        this.data_prenotazione = prenotazione.getData_prenotazione();
        this.sacchetto_pranzo = prenotazione.getSacchetto_pranzo();
        this.sacchetto_cena = prenotazione.getSacchetto_cena();
        this.check_richiesta = prenotazione.getCheck_richiesta();
        this.id_utente = prenotazione.getUtente().getId();
    }
}
