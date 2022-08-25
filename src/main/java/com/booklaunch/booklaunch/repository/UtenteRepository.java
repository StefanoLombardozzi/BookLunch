package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * L'interfaccia PrenotazioneRepository estende JpaRepository<T, ID>.
 * Quest'interfaccia offre dei metodi per l'archiviazione, il recupero, l'aggiornamento, l'eliminazione e la ricerca sugli oggetti.
 *
 * Le interfacce sono richiamate al livello service.
 * Sono particolarmente utili per effettuare query che poi verranno sfruttate nell'implemetazione dei metodi.
 * Se si ha bisogno di query SQL native si può utilizzare l’annotation @Query.
 */
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    boolean existsByEmail(String email);

    Utente findByEmail(String email);

    boolean existsById(Long id_utente);

    boolean existsByCognome(String cognome);

    List<Utente> findByCognome(String cognome);
}
