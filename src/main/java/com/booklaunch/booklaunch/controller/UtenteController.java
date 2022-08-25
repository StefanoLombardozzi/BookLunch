package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.service.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -La classe UtenteController ha il compito di elaborare le richieste API REST in entrata,
 *  preparare il model e restituire la view da visualizzare come risposta
 *
 * -Il path delle api inizia con:
 * "http://localhost:8080/api/utente/...".
 *
 * -Per controllare i dati di input delle request provenienti dal front-end,
 *  vengono richiamati i metodi della classe UtenteService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/utente")
@CrossOrigin("*")
public class UtenteController {

    private final UtenteService utenteService;

    /**
     * -Endpoint che ritorna tutti gli utenti presenti nel DB
     *
     * @return List<UtenteDTO>
     */
    @GetMapping("/findAll")
    public List<UtenteDTO> findAll() {
        return utenteService.findAll();
    }

    /**
     * -Endpoint che effettua l'insert di un nuovo utente
     *
     * @param utenteDTO
     * @return UtenteDTO
     */
    @PostMapping("/createUtente")
    public UtenteDTO create_utente(@RequestBody UtenteDTO utenteDTO){
        return utenteService.create_utente(utenteDTO);
    }

    /**
     * -Endpoint che effettua il delete di un utente dal DB
     *
     * @param id_utente
     * @return Boolean
     */
    @DeleteMapping("/deleteUtente")
    public Boolean elimina_utente(@RequestParam Long id_utente){
         return utenteService.elimina_utente(id_utente);
    }

    /**
     * -Endpoint che effettuq il cambio ruolo da USER ad ADMIN
     *
     * @param id_utente
     * @return UtenteDTO
     */
    @PostMapping("/createAdmin")
    public UtenteDTO create_admin(@RequestParam Long id_utente) {
        return utenteService.create_admin(id_utente);
    }

    /**
     * -Endpoint che ritorna l'utente con i parametri modificati
     *
     * @param utenteDTO
     * @return UtenteDTO
     */
    @PutMapping("/updateUtente")
    public UtenteDTO update_utente(@RequestBody UtenteDTO utenteDTO){
        return utenteService.update_utente(utenteDTO);
    }

    /**
     * -Endpoint che ritorna l'utente ricercato tramite email
     *
     * @param email
     * @return UtenteDTO
     */
    @GetMapping("/findEmail")
    public UtenteDTO findByEmail(@RequestParam String email) {
        return utenteService.findByEmail(email);
    }

    /**
     * -Endpoint che ritorna una lista di utenti ricercati tramite cognome
     *
     * @param cognome
     * @return List<UtenteDTO>
     */
    @GetMapping("/findCognome")
    public List<UtenteDTO> findByCognome(@RequestParam String cognome) {
        return utenteService.findByCognome(cognome);
    }
}