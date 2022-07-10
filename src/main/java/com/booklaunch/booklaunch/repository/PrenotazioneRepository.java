package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.model.Richiesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsById(Long id_prenotazione);
}
