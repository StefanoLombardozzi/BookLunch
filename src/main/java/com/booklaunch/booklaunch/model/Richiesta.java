package com.booklaunch.booklaunch.model;

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

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_pranzo;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_cena;


    @ManyToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;


    /*public CentroVaccinale(CentroVaccinaleDTO centroDTO) {
        this.id = centroDTO.id;
        this.nome = centroDTO.nome;
        this.indirizzo = centroDTO.indirizzo;
    }*/
}
