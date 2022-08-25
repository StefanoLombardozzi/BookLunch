package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Richiesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * L'interfaccia PrenotazioneRepository estende JpaRepository<T, ID>.
 * Quest'interfaccia offre dei metodi per l'archiviazione, il recupero, l'aggiornamento, l'eliminazione e la ricerca sugli oggetti.
 *
 * Le interfacce sono richiamate al livello service.
 * Sono particolarmente utili per effettuare query che poi verranno sfruttate nell'implemetazione dei metodi.
 * Se si ha bisogno di query SQL native si può utilizzare l’annotation @Query.
 */
public interface RichiestaRepository extends JpaRepository<Richiesta, Long> {

    /**
     * -Query che ritorna il numero di richieste con stato_richiesta null
     *
     * @param id
     * @return int
     */
    @Query("select count(r) from Richiesta r JOIN Prenotazione p On r.prenotazione.id=:id WHERE r.stato_richiesta is NULL")
    int findByPrenotazione_IdAndStato_richiesta(@Param("id") Long id);

    /**
     * -Query che ritorna la lista di richieste di un utente specifico con stato_richiesta null
     *
     * @param cod
     * @return List<Richiesta>
     */
    @Query("select distinct(r) from Richiesta r WHERE r.prenotazione.utente.id=:cod AND r.stato_richiesta is NULL ")
    List<Richiesta> getRichiesteUtente(@Param("cod") Long cod);

    /**
     * -Query che ritorna la lista di richieste con stato_richiesta null
     *
     * @return List<Richiesta>
     */
    @Query("select distinct(r) from Richiesta r WHERE r.stato_richiesta is NULL ")
    List<Richiesta> getRichiesteAdmin();

    /**
     * -Query che ritorna una lista di richieste associate ad un utente
     *
     * @param cod
     * @return List<Richiesta>
     */
    @Query("select distinct(r) from Richiesta r WHERE r.prenotazione.utente.id=:cod")
    List<Richiesta> getRichiesteByUtente(@Param("cod") Long cod);
}
