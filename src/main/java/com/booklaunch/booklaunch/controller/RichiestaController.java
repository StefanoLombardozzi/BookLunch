package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.model.Richiesta;
import com.booklaunch.booklaunch.service.RichiestaService;
import com.booklaunch.booklaunch.service.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/richiesta")
@CrossOrigin("*")

/**
 * -Nella classe UtenteController vengono gestiti e organizzati tutti gli endpoint relativi all'utente.
 * -I path delle api, ovvero delle attività che si possono svolgere iniziano con:
 * "http://localhost:8080/api/utente/...".
 * -Nei metodi presenti in questa classe vengono semplicemente richiamati i metodi dela classe UtenteService
 * per il controllo e la validità dei dati in input delle request dal front-end, nonchè lo svolgimento dell'algoritmo implementato nel service.
 * -Infine tutte le response ricevute dal livello "service" verranno inviare al front-end.
 */
public class RichiestaController {

    private final RichiestaService richiestaService;

    /**
     * Ritorna tutti gli utenti
     *
     * @return List<UtenteDTO>
     */
    @GetMapping("/findAll")
    public List<RichiestaDTO> findAll() {
        return richiestaService.findAll();
    }

    @PostMapping("/createRichiesta")
    public RichiestaDTO create_richiesta(@RequestBody RichiestaDTO richiestaDTO){
        return richiestaService.create_richiesta(richiestaDTO);
    }

    @DeleteMapping("/deleteRichiesta")
    public Boolean delete_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.delete_richiesta(id_richiesta);
    }

    @GetMapping("/getRichiesteUtente")
    public List<RichiestaDTO> getRichiesteUtente(@RequestParam Long id_utente){
        return richiestaService.getRichiesteUtente(id_utente);
    }

    @GetMapping("/getRichiesteAdmin")
    public List<RichiestaDTO> getRichiesteAdmin(@RequestParam Long id_admin){
        return richiestaService.getRichiesteAdmin(id_admin);
    }

    @GetMapping("/getRichiesteByUtente")
    public List<RichiestaDTO> getRichiesteByUtente(@RequestParam Long id_utente){
        return richiestaService.getRichiesteByUtente((id_utente));
    }

    @PutMapping("/accettaRichiesta")
    public Boolean accetta_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.accetta_richiesta(id_richiesta);
    }

    @PutMapping("/rifiutaRichiesta")
    public Boolean rifiuta_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.rifiuta_richiesta(id_richiesta);
    }
}
