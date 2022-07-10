package com.booklaunch.booklaunch.dto;

import com.booklaunch.booklaunch.model.Richiesta;
import com.booklaunch.booklaunch.model.Utente;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RichiestaDTO {

    private Long id;

    private Boolean post_pranzo;

    private Boolean post_cena;

    private Boolean stato_richiesta;

    private Long id_prenotazione;

    private Long id_utente;

    public RichiestaDTO(Richiesta richiesta) {
        this.id = richiesta.getId();
        this.post_pranzo = richiesta.getPost_pranzo();
        this.post_cena = richiesta.getPost_cena();
        this.stato_richiesta = richiesta.getStato_richiesta();
        this.id_prenotazione = richiesta.getPrenotazione().getId();
        this.id_utente = richiesta.getUtente().getId();
    }
}
