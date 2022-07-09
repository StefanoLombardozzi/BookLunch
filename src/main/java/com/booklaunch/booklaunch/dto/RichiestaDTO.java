package com.booklaunch.booklaunch.dto;

import com.booklaunch.booklaunch.model.Richiesta;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_pranzo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_cena;

    private Boolean stato_richiesta;

    public RichiestaDTO(Richiesta richiesta){
        this.id = richiesta.getId();
        this.post_pranzo = richiesta.getPost_pranzo();
        this.post_cena = richiesta.getPost_cena();
        this.data_pranzo = richiesta.getData_pranzo();
        this.data_cena = richiesta.getData_cena();
        this.stato_richiesta = richiesta.getStato_richiesta();
    }
}
