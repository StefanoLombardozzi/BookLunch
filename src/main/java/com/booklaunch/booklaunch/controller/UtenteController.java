package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.service.UtenteService;
import com.booklaunch.booklaunch.service.impl.UtenteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/utente")
@CrossOrigin("*")

/**
 * -Nella classe UtenteController vengono gestiti e organizzati tutti gli endpoint relativi all'utente.
 * -I path delle api, ovvero delle attività che si possono svolgere iniziano con:
 * "http://localhost:8080/api/utente/...".
 * -Nei metodi presenti in questa classe vengono semplicemente richiamati i metodi dela classe UtenteService
 * per il controllo e la validità dei dati in input delle request dal front-end, nonchè lo svolgimento dell'algoritmo implementato nel service.
 * -Infine tutte le response ricevute dal livello "service" verranno inviare al front-end.
 */
public class UtenteController {

    private final UtenteService utenteService;

    /**
     * Ritorna tutti gli utenti
     *
     * @return List<UtenteDTO>
     */
    @GetMapping("/findAll")
    public List<UtenteDTO> findAll() {
        return utenteService.findAll();
    }

    @PostMapping("/createUtente")
    public UtenteDTO create_utente(@RequestBody UtenteDTO utenteDTO){
        return utenteService.create_utente(utenteDTO);
    }

    @DeleteMapping("/deleteUtente")
    public Boolean elimina_utente(@RequestParam Long id_utente){
         return utenteService.elimina_utente(id_utente);
    }

    @PostMapping("/createAdmin")
    public UtenteDTO create_admin(@RequestParam Long id_utente) {
        return utenteService.create_admin(id_utente);
    }

    @PutMapping("/updateUtente")
    public UtenteDTO update_utente(@RequestBody UtenteDTO utenteDTO){
        return utenteService.update_utente(utenteDTO);
    }

    @GetMapping("/findEmail")
    public UtenteDTO findByEmail(@RequestParam String email) {
        return utenteService.findByEmail(email);
    }

    @GetMapping("/findCognome")
    public List<UtenteDTO> findByCognome(@RequestParam String cognome) {
        return utenteService.findByCognome(cognome);
    }
}