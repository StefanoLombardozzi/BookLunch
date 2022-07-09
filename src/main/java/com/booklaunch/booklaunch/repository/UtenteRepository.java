package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    boolean existsByEmail(String email);

    Utente findByEmail(String email);

    boolean existsByCognomeAndNomeAndEmail(String cognome, String nome, String email);

    boolean existsById(Long id_utente);

    boolean existsByCognome(String cognome);

    List<Utente> findByCognome(String cognome);
}
