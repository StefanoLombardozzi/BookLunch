package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Richiesta;
import com.booklaunch.booklaunch.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * L'interfaccia UtenteRepository estende JpaRepository<T, ID>.
 * Quest'interfaccia, offre diversi metodi base (es. findAll(), findById() e tanti altri)
 * per relazionarsi con il db.
 * Invece, se si ha bisogno di query SQL native si può utilizzare l’annotazione @Query.
 */
public interface RichiestaRepository extends JpaRepository<Richiesta, Long> {

    @Query("select count(r) from Richiesta r JOIN Prenotazione p On r.prenotazione.id=:id WHERE r.stato_richiesta is NULL")
    int findByPrenotazione_IdAndStato_richiesta(@Param("id") Long id);

    @Query("select distinct(r) from Richiesta r  WHERE r.prenotazione.utente.id=:cod AND r.stato_richiesta is NULL ")
    List<Richiesta> getRichiesteUtente(@Param("cod") Long cod);

    @Query("select distinct(r) from Richiesta r  WHERE r.stato_richiesta is NULL ")
    List<Richiesta> getRichiesteAdmin();

    @Query("select distinct(r) from Richiesta r  WHERE r.prenotazione.utente.id=:cod")
    List<Richiesta> getRichiesteByUtente(@Param("cod") Long cod);
}
