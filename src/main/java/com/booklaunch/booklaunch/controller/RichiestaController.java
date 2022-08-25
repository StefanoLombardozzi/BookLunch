package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.service.RichiestaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -La classe RichiestaController ha il compito di elaborare le richieste API REST in entrata,
 *  preparare il model e restituire la view da visualizzare come risposta
 *
 * -Il path delle api inizia con:
 * "http://localhost:8080/api/richiesta/...".
 *
 * -Per controllare i dati di input delle request provenienti dal front-end,
 *  vengono richiamati i metodi della classe RichiestaService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/richiesta")
@CrossOrigin("*")
public class RichiestaController {

    private final RichiestaService richiestaService;

    /**
     * -Endpoint che ritorna tutti le richieste presenti nel DB
     *
     * @return List<RichiestaDTO>
     */
    @GetMapping("/findAll")
    public List<RichiestaDTO> findAll() {
        return richiestaService.findAll();
    }

    /**
     * -Endpoint che effettua l'insert di una richiesta nel DB
     *
     * @param richiestaDTO
     * @return RichiestaDTO
     */
    @PostMapping("/createRichiesta")
    public RichiestaDTO create_richiesta(@RequestBody RichiestaDTO richiestaDTO){
        return richiestaService.create_richiesta(richiestaDTO);
    }

    /**
     * -Endpoint che effettua il delete di una richiesta
     *
     * @param id_richiesta
     * @return Boolean
     */
    @DeleteMapping("/deleteRichiesta")
    public Boolean delete_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.delete_richiesta(id_richiesta);
    }

    /**
     * -Endpoint che permette la visualizzazione, da parte dell'utente, delle richieste non ancora gestite dall'admin
     *
     * @param id_utente
     * @return List<RichiestaDTO>
     */
    @GetMapping("/getRichiesteUtente")
    public List<RichiestaDTO> getRichiesteUtente(@RequestParam Long id_utente){
        return richiestaService.getRichiesteUtente(id_utente);
    }

    /**
     * -Endpoint che restituisce le richieste non gestite di tutti gli utenti
     *
     * @param id_admin
     * @return List<RichiestaDTO>
     */
    @GetMapping("/getRichiesteAdmin")
    public List<RichiestaDTO> getRichiesteAdmin(@RequestParam Long id_admin){
        return richiestaService.getRichiesteAdmin(id_admin);
    }

    /**
     *-Endpoint che permette la visualizzazione, da parte dell'admin, delle richieste di un utente specifico non ancora gestite
     *
     * @param id_utente
     * @return List<RichiestaDTO>
     */
    @GetMapping("/getRichiesteByUtente")
    public List<RichiestaDTO> getRichiesteByUtente(@RequestParam Long id_utente){
        return richiestaService.getRichiesteByUtente((id_utente));
    }

    /**
     * -Endpoint che restituisce un boolean a seguito di una richiesta accettata
     *
     * @param id_richiesta
     * @return Boolean
     */
    @PutMapping("/accettaRichiesta")
    public Boolean accetta_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.accetta_richiesta(id_richiesta);
    }

    /**
     * -Endpoint che restituisce un boolean a seguito di una richiesta rifiutata
     *
     * @param id_richiesta
     * @return Boolean
     */
    @PutMapping("/rifiutaRichiesta")
    public Boolean rifiuta_richiesta(@RequestParam Long id_richiesta){
        return richiestaService.rifiuta_richiesta(id_richiesta);
    }
}
