package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.service.PrenotazioneService;
import com.booklaunch.booklaunch.service.RichiestaService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/prenotazione")
@CrossOrigin("*")

/**
 * -Nella classe UtenteController vengono gestiti e organizzati tutti gli endpoint relativi all'utente.
 * -I path delle api, ovvero delle attività che si possono svolgere iniziano con:
 * "http://localhost:8080/api/utente/...".
 * -Nei metodi presenti in questa classe vengono semplicemente richiamati i metodi dela classe UtenteService
 * per il controllo e la validità dei dati in input delle request dal front-end, nonchè lo svolgimento dell'algoritmo implementato nel service.
 * -Infine tutte le response ricevute dal livello "service" verranno inviare al front-end.
 */
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @GetMapping("/findAll")
    public List<PrenotazioneDTO> findAll(){
        return prenotazioneService.findAll();
    }

    @PostMapping("/insertPrenotazione")
    public PrenotazioneDTO insertPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){
        return prenotazioneService.insertPrenotazione(prenotazioneDTO);
    }

    @DeleteMapping("/deletePrenotazione")
    public Boolean deletePrenotazione(@RequestParam Long id_prenotazione){
        return prenotazioneService.deletePrenotazione(id_prenotazione);
    }

    @GetMapping("/findByData")
    public List<PrenotazioneDTO> findByData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.findByData(data_prenotazione);
    }

    @GetMapping("/countByPranzoAndData")
    public void countByPranzoAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        prenotazioneService.countByPranzoAndData(data_prenotazione);
    }

    @GetMapping("/countByCenaAndData")
    public void countByCenaAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        prenotazioneService.countByCenaAndData(data_prenotazione);
    }

    @GetMapping("/countByColazioneAndData")
    public void countByColazioneAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        prenotazioneService.countByColazioneAndData(data_prenotazione);
    }

    @GetMapping("/countBySacchettoPranzoAndData")
    public void countBySacchettoPranzoAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        prenotazioneService.countBySacchettoPranzoAndData(data_prenotazione);
    }

    @GetMapping("/countBySacchettoCenaAndData")
    public void countBySacchettoCenaAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        prenotazioneService.countBySacchettoCenaAndData(data_prenotazione);
    }

    @PutMapping("/updatePrenotazione")
    public PrenotazioneDTO updatePrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){
        return prenotazioneService.updatePrenotazione(prenotazioneDTO);
    }

    @GetMapping("/findByUtente")
    public List<PrenotazioneDTO> findByUtente(@RequestParam Long id_utente){
        return prenotazioneService.findByUtente(id_utente);
    }
}
