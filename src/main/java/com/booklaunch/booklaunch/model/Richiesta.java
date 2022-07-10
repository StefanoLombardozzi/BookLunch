package com.booklaunch.booklaunch.model;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
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
public class Richiesta {

    @Id  //specifica l'id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ogni tabella inizierà da 1
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
