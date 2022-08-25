package com.booklaunch.booklaunch.repository;

import com.booklaunch.booklaunch.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * L'interfaccia PrenotazioneRepository estende JpaRepository<T, ID>.
 * Quest'interfaccia offre dei metodi per l'archiviazione, il recupero, l'aggiornamento, l'eliminazione e la ricerca sugli oggetti.
 *
 * Le interfacce sono richiamate al livello service.
 * Sono particolarmente utili per effettuare query che poi verranno sfruttate nell'implemetazione dei metodi.
 * Se si ha bisogno di query SQL native si può utilizzare l’annotation @Query.
 */
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsById(Long id_prenotazione);

    /**
     * -Query che restituisce il numero di prenotazioni a pranzo in una certa data
     *
     * @param date
     * @return int
     */
    @Query("select count(p) from Prenotazione p WHERE p.pranzo = true AND p.data_prenotazione=:date")
    int prenotazionePranzo(@Param("date") LocalDate date);

    /**
     * -Query che restituisce il numero di prenotazioni a cena in una certa data
     *
     * @param date
     * @return int
     */
    @Query("select count(p) from Prenotazione p WHERE p.cena = true AND p.data_prenotazione=:date")
    int prenotazioneCena(@Param("date") LocalDate date);

    /**
     * -Query che resituisce il numero di prenotazioni a colazione in una certa data
     *
     * @param date
     * @return int
     */
    @Query("select count(p) from Prenotazione p WHERE p.colazione = true AND p.data_prenotazione=:date")
    int prenotazioneColazione(@Param("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    /**
     * -Query che resituisce il numero di prenotazioni per il sacchetto pranzo in una certa data
     *
     * @param date
     * @return int
     */
    @Query("select count(p) from Prenotazione p WHERE p.sacchetto_pranzo = true AND p.data_prenotazione=:date")
    int prenotazioneSacchettoPranzo(@Param("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    /**
     * -Query che resituisce il numero di prenotazioni per il sacchetto cena in una certa data
     *
     * @param date
     * @return int
     */
    @Query("select count(p) from Prenotazione p WHERE p.sacchetto_cena=true AND p.data_prenotazione=:date")
    int prenotazioneSacchettoCena(@Param("date") LocalDate date);

    /**
     * -Query che restituisce la lista di prenotazioni in una certa data
     *
     * @param data_prenotazione
     * @return List<Prenotazione>
     */
    @Query("select p from Prenotazione p where p.data_prenotazione=:data_prenotazione")
    List<Prenotazione> findByData_prenotazione(@Param("data_prenotazione") LocalDate data_prenotazione);

    /**
     * -Query che conta il numero di prenotazioni in una certa data
     *
     * @param data_prenotazione
     * @return int
     */
    @Query("select count(p) from Prenotazione p where p.data_prenotazione=:data_prenotazione")
    int existsByData_prenotazione(@Param("data_prenotazione") LocalDate data_prenotazione);

    /**
     * -Query che restituisce la lista delle prenotazioni di un certo utente
     *
     * @param id_utente
     * @return List<Prenotazione>
     */
    @Query("select p from Prenotazione p join Utente u on p.utente.id = u.id AND u.id=:id")
    List<Prenotazione> prenotazioneByUtente(@Param("id") Long id_utente);

    /**
     *-Query che restituisce il numero di prenotazioni rispetto all'utente e alla data
     *
     * @param id_utente
     * @param data_prenotazione
     * @return int
     */
    @Query("select count(p) from Prenotazione p join Utente u on p.utente.id = u.id AND u.id=:id_utente WHERE p.data_prenotazione=:data_prenotazione")
    int existsByUtenteAndData(@Param("id_utente") Long id_utente,@Param("data_prenotazione")LocalDate data_prenotazione);
}
