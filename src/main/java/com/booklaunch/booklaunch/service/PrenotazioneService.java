package com.booklaunch.booklaunch.service;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneService {

    List<PrenotazioneDTO> findAll();

    PrenotazioneDTO insertPrenotazione(PrenotazioneDTO prenotazioneDTO);

    Boolean deletePrenotazione(Long id_prenotazione);

    List<PrenotazioneDTO> findByData(LocalDate data_prenotazione);

    void countByPranzoAndData(LocalDate data_prenotazione);

    void countByCenaAndData(LocalDate data_prenotazione);

    void countByColazioneAndData(LocalDate data_prenotazione);

    void countBySacchettoPranzoAndData(LocalDate data_prenotazione);

    void countBySacchettoCenaAndData(LocalDate data_prenotazione);

    PrenotazioneDTO updatePrenotazione(PrenotazioneDTO prenotazioneDTO);

    List<PrenotazioneDTO> findByUtente(Long id_utente);
}
