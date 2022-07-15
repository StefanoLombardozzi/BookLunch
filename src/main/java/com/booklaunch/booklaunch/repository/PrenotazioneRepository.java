package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.model.Richiesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsById(Long id_prenotazione);

    @Query("select count(p) from Prenotazione p WHERE p.pranzo = true AND p.data_prenotazione =:date")
    int prenotazionePranzo(@Param("date") LocalDate date);

    @Query("select count(p) from Prenotazione p WHERE p.cena = true AND p.data_prenotazione =:date")
    int prenotazioneCena(@Param("date") LocalDate date);

    @Query("select count(p) from Prenotazione p WHERE p.colazione = true AND p.data_prenotazione =:date")
    int prenotazioneColazione(@Param("date") LocalDate date);

    @Query("select count(p) from Prenotazione p WHERE p.sacchetto_pranzo = true AND p.data_prenotazione =:date")
    int prenotazioneSacchettoPranzo(@Param("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @Query("select count(p) from Prenotazione p WHERE p.sacchetto_cena = true AND p.data_prenotazione =:date")
    int prenotazioneSacchettoCena(@Param("date") LocalDate date);

    @Query("select p from Prenotazione p where p.data_prenotazione =: data_prenotazione")
    List<Prenotazione> findByData_prenotazione(@Param("data_prenotazione") LocalDate data_prenotazione);

    @Query("select count(p) from Prenotazione p where p.data_prenotazione =: data_prenotazione")
    int existsByData_prenotazione(@Param("data_prenotazione") LocalDate data_prenotazione);

    @Query("select p from Prenotazione p join Utente u on p.utente.id = u.id AND u.id =: id")
    List<Prenotazione> prenotazioneByUtente(@Param("id") Long id_utente);

    @Query("select count(p) from Prenotazione p join Utente u on p.utente.id = u.id AND u.id =: id WHERE p.data_prenotazione =: data_prenotazione")
    int existsByUtenteAndData(@Param("id_utente") Long id_utente,@Param("data_prenotazione") LocalDate data_prenotazione);
}
