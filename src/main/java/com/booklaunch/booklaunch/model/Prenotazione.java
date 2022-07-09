package com.booklaunch.booklaunch.model;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_prenotazione;

    private Boolean sacchetto_pranzo;

    private Boolean sacchetto_cena;


    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @OneToMany(mappedBy = "prenotazione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Richiesta> richieste = new ArrayList<>();
}
