package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * L'interfaccia UtenteRepository estende JpaRepository<T, ID>.
 * Quest'interfaccia, offre diversi metodi base (es. findAll(), findById() e tanti altri)
 * per relazionarsi con il db.
 * Invece, se si ha bisogno di query SQL native si può utilizzare l’annotazione @Query.
 */
public interface RichiestaRepository extends JpaRepository<Utente, Long> {

    /*@Query("select distinct(r) from Richiesta r WHERE r.id IN (select r from Richiesta where r.newCentro=:id OR r.oldCentroVacc=:id) AND r.approved is null ")
    List<Richiesta> getRichieste(@Param("id") Long id);

    @Query("select distinct(r) from Richiesta r  WHERE r.somministrazione.utente.id=:cod AND r.approved is NULL ")
    List<Richiesta> getRichiesteUtente(@Param("cod") Long cod);

    boolean existsById(Long id);


    @Query("select count(r) from Richiesta r JOIN Somministrazione s On r.somministrazione.id=:id WHERE r.approved is NULL")
    int findBySomministrazione_IdAndApproved(@Param("id") Long id);

    Richiesta findBySomministrazione_Id(Long id);*/
}
