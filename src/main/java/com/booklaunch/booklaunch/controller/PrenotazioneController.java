package com.booklaunch.booklaunch.controller;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.service.PrenotazioneService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * -La classe PrenotazioneController ha il compito di elaborare le richieste API REST in entrata,
 *  preparare il model e restituire la view da visualizzare come risposta
 *
 * -Il path delle api inizia con:
 * "http://localhost:8080/api/prenotazione/...".
 *
 * -Per controllare i dati di input delle request provenienti dal front-end,
 *  vengono richiamati i metodi della classe PrenotazioneService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/prenotazione")
@CrossOrigin("*")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    /**
     * -Endpoint che ritorna tutte le prenotazioni presenti nel DB
     * @return List<PrenotazioneDTO>
     */
    @GetMapping("/findAll")
    public List<PrenotazioneDTO> findAll(){
        return prenotazioneService.findAll();
    }

    /**
     * -Endpoint che effettua l' insert di una nuova prenotazione nel DB
     *
     * @param prenotazioneDTO
     * @return PrenotazioneDTO
     */
    @PostMapping("/insertPrenotazione")
    public PrenotazioneDTO insertPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){
        return prenotazioneService.insertPrenotazione(prenotazioneDTO);
    }

    /**
     * -Endpoint che effettua il delete di una prenotazione esistente
     *
     * @param id_prenotazione
     * @return Boolean
     */
    @DeleteMapping("/deletePrenotazione")
    public Boolean deletePrenotazione(@RequestParam Long id_prenotazione){
        return prenotazioneService.deletePrenotazione(id_prenotazione);
    }

    /**
     * -Endpoint che ritorna una lista di prenotazioni ricercate tramite la data
     *
     * @param data_prenotazione
     * @return List<PrenotazioneDTO>
     */
    @GetMapping("/findByData")
    public List<PrenotazioneDTO> findByData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.findByData(data_prenotazione);
    }

    /**
     * -Endpoint che conta le prenotazioni a pranzo in una certa data
     *
     * @param data_prenotazione
     */
    @GetMapping("/countByPranzoAndData")
    public int countByPranzoAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.countByPranzoAndData(data_prenotazione);
    }

    /**
     * -Endpoint che conta le prenotazioni a cena in una certa data
     *
     * @param data_prenotazione
     */
    @GetMapping("/countByCenaAndData")
    public int countByCenaAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.countByCenaAndData(data_prenotazione);
    }

    /**
     * -Endpoint che conta le prenotazioni a colazione in una certa data
     *
     * @param data_prenotazione
     */
    @GetMapping("/countByColazioneAndData")
    public int countByColazioneAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
       return prenotazioneService.countByColazioneAndData(data_prenotazione);
    }

    /**
     * -Endpoint che conta le prenotazioni al sacchetto pranzo in una certa data
     *
     * @param data_prenotazione
     */
    @GetMapping("/countBySacchettoPranzoAndData")
    public int countBySacchettoPranzoAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.countBySacchettoPranzoAndData(data_prenotazione);
    }

    /**
     * -Endpoint che conta le prenotazioni al sacchetto cena in una certa data
     *
     * @param data_prenotazione
     */
    @GetMapping("/countBySacchettoCenaAndData")
    public int countBySacchettoCenaAndData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data_prenotazione){
        return prenotazioneService.countBySacchettoCenaAndData(data_prenotazione);
    }

    /**
     * -Endpoint che ritorna la prenotazione modificata
     *
     * @param prenotazioneDTO
     * @return PrenotazioneDTO
     */
    @PutMapping("/updatePrenotazione")
    public PrenotazioneDTO updatePrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){
        return prenotazioneService.updatePrenotazione(prenotazioneDTO);
    }

    /**
     * -Endpoint che ritorna una lista di prenotazioni ricercate tramite l'utente
     *
     * @param id_utente
     * @return List<PrenotazioneDTO>
     */
    @GetMapping("/findByUtente")
    public List<PrenotazioneDTO> findByUtente(@RequestParam Long id_utente){
        return prenotazioneService.findByUtente(id_utente);
    }
}
