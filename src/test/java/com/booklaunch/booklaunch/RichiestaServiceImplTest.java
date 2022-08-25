package com.booklaunch.booklaunch;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.model.Richiesta;
import com.booklaunch.booklaunch.model.Utente;
import com.booklaunch.booklaunch.repository.PrenotazioneRepository;
import com.booklaunch.booklaunch.repository.RichiestaRepository;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.impl.RichiestaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Ogni metodo testato viene annotato con @Test e al loro interno vengono richiamati i metodi della classe Assertions e Mockito.
 * Gli Assertions servono per verificare la coerenza tra i dati che ci aspettiamo e che effettivamente vengono restituiti.
 *
 * Alcuni metodi utilizzati della classe Assertion:
 * -assertTrue(): verifica che il valore restituito sia true;
 * -assertFalse(): verifica che il valore restituito sia false;
 * -assertNotNull(): verifica che il valore restituito non sia null;
 * -assertEquals(): verifica che il valore atteso sia uguale al valore restituito;
 *
 * Alcuni metodi utilizzati della classe Mockito:
 * -lenient(): effettua l'override del comportamento del metodo "mockato" della repository utilizzata
 * -verify(): verifica se il metodo della repository mockato è stata effettivamente chiamato;
 * -reset(): resetta il mock fatto al metodi della repository.
 */
@ExtendWith(MockitoExtension.class)
public class RichiestaServiceImplTest {

    @Mock
    private RichiestaRepository richiestaRepository;

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteRepository utenteRepository;

    private RichiestaServiceImpl richiestaServiceImpl;

    @BeforeEach
    void setUp() {
        richiestaServiceImpl = new RichiestaServiceImpl(richiestaRepository, prenotazioneRepository, utenteRepository);
    }

    /**
     * Testing metodo delete_richiesta:
     * -Con lenient() verifico che esiste la richiesta
     * -Verifico che sia richiamato almeno una volta,
     *  così da accertarmi che sia stata cancellata
     */
    @Test
    void delete_richiesta() {

        Long id = 1L;

        lenient().when(richiestaRepository.existsById(id)).thenReturn(true);
        Assertions.assertTrue(richiestaServiceImpl.delete_richiesta(id));
        verify(richiestaRepository, atLeastOnce()).existsById(id);
        reset(richiestaRepository);

    }

    /**
     * Testing metodo findAll:
     * -Con asserTrye verifico che la condizione all'interno di essa sia vera
     * -Con il verify verifico che il findAll sia richiamato almeno una volta
     *  così da accertarmi che ritorna tutte le richieste
     */
    @Test
    void findAll() {

        assertTrue(richiestaRepository.findAll().isEmpty());

        verify(richiestaRepository, atLeast(1)).findAll();
        reset(richiestaRepository);
    }

    /**
     * Testing metodo getRichiesteUtente:
     * -Creo un utente, due prenotazioni e una lista di richieste
     * -Verifico che gli id delle richieste create
     *  e gli id delle richieste che ritorna siano uguali
     */
    @Test
    void getRichiesteUtente() {
        Long id = 1l;
        LocalDate date = LocalDate.now().minusDays(2);
        LocalDate date1 = LocalDate.now().minusDays(3);

        Utente utente = new Utente.UtenteBuilder()
                .id(id)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Prenotazione prenotazione1 = new Prenotazione.PrenotazioneBuilder()
                .id(2l)
                .data_prenotazione(date1)
                .cena(true)
                .colazione(false)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(true)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(1l)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(false)
                .utente(utente)
                .prenotazione(prenotazione).build();

        Richiesta richiesta1 = Richiesta.builder()
                .id(2l)
                .post_cena(true)
                .post_pranzo(true)
                .stato_richiesta(false)
                .utente(utente)
                .prenotazione(prenotazione1).build();

        List<Richiesta> list = List.of(richiesta, richiesta1);

        assertNotNull(id);
        lenient().when(utenteRepository.existsById(utente.getId())).thenReturn(true);
        lenient().when(richiestaRepository.getRichiesteUtente(utente.getId())).thenReturn(list);

        List <RichiestaDTO> richiestaDTO = List.of(new RichiestaDTO(richiesta), new RichiestaDTO(richiesta1));
        assertNotNull(richiestaDTO);
        assertEquals(richiestaDTO.get(0).getId(), richiestaServiceImpl.getRichiesteUtente(id).get(0).getId());

        reset(richiestaRepository);
        reset(utenteRepository);
    }

    /**
     * Testing metodo getRichiesteAdmin:
     * -Creo un utente, due prenotazioni e una lista di richieste
     * -Verifico che gli id delle richieste create
     *  e gli id delle richieste che ritorna siano uguali
     */
    @Test
    void getRichiesteAdmin() {
        Long id = 1l;
        LocalDate date = LocalDate.now().minusDays(2);
        LocalDate date1 = LocalDate.now().minusDays(3);

        Utente utente = new Utente.UtenteBuilder()
                .id(id)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Prenotazione prenotazione1 = new Prenotazione.PrenotazioneBuilder()
                .id(2l)
                .data_prenotazione(date1)
                .cena(true)
                .colazione(false)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(true)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(1l)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(null)
                .utente(utente)
                .prenotazione(prenotazione).build();

        Richiesta richiesta1 = Richiesta.builder()
                .id(2l)
                .post_cena(true)
                .post_pranzo(true)
                .stato_richiesta(null)
                .utente(utente)
                .prenotazione(prenotazione1).build();

        List<Richiesta> list = List.of(richiesta, richiesta1);

        assertNotNull(id);
        lenient().when(utenteRepository.existsById(utente.getId())).thenReturn(true);


        List <RichiestaDTO> richiestaDTO = List.of(new RichiestaDTO(richiesta), new RichiestaDTO(richiesta1));
        lenient().when(richiestaRepository.getRichiesteAdmin()).thenReturn(list);
        assertNotNull(richiestaDTO);
        assertEquals(richiestaDTO.get(0).getId(), richiestaServiceImpl.getRichiesteAdmin(id).get(0).getId());

        reset(richiestaRepository);
        reset(utenteRepository);
    }

    /**
     * Testing metodo getRichiesteByUtente:
     * -Creo un utente, due prenotazioni e una lista di richieste
     * -Verifico che gli id delle richieste create
     *  e gli id delle richieste che ritorna siano uguali
     */
    @Test
    void getRichiesteByUtente() {
        Long id = 1l;
        LocalDate date = LocalDate.now().minusDays(2);
        LocalDate date1 = LocalDate.now().minusDays(3);

        Utente utente = new Utente.UtenteBuilder()
                .id(id)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l).
                data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Prenotazione prenotazione1 = new Prenotazione.PrenotazioneBuilder()
                .id(2l)
                .data_prenotazione(date1)
                .cena(true)
                .colazione(false)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(true)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(1l)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(false)
                .utente(utente)
                .prenotazione(prenotazione).build();

        Richiesta richiesta1 = Richiesta.builder()
                .id(2l)
                .post_cena(true)
                .post_pranzo(true)
                .stato_richiesta(false)
                .utente(utente).
                prenotazione(prenotazione1).build();

        List<Richiesta> list = List.of(richiesta, richiesta1);

        assertNotNull(id);
        lenient().when(utenteRepository.existsById(utente.getId())).thenReturn(true);
        lenient().when(richiestaRepository.getRichiesteByUtente(utente.getId())).thenReturn(list);

        List <RichiestaDTO> richiestaDTO = List.of(new RichiestaDTO(richiesta), new RichiestaDTO(richiesta1));
        assertNotNull(richiestaDTO);
        assertEquals(richiestaDTO.get(0).getId(), richiestaServiceImpl.getRichiesteByUtente(id).get(0).getId());

        reset(richiestaRepository);
        reset(utenteRepository);
    }

    /**
     * Testing metodo create_richiesta:
     * -Verifico che esiste l'utente
     * -Verifico che esiste la prenotazione
     * -Verifico che l'id della richiesta creata sia uguale
     *  all'id della richiesta ritornata dal metodo
     */
    @Test
    void create_richiesta() {
        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test123")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        LocalDate date = LocalDate.now().minusDays(2);

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(1l)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(false)
                .utente(utente)
                .prenotazione(prenotazione).build();

        RichiestaDTO richiestaDTO = new RichiestaDTO(richiesta);

        lenient().when(utenteRepository.existsById(richiestaDTO.getId_utente())).thenReturn(true);
        lenient().when(utenteRepository.findById(richiestaDTO.getId_utente())).thenReturn(Optional.of(utente));
        lenient().when(prenotazioneRepository.existsById(richiestaDTO.getId_prenotazione())).thenReturn(true);
        lenient().when(prenotazioneRepository.findById(richiestaDTO.getId_prenotazione())).thenReturn(Optional.of(prenotazione));
        lenient().when(utenteRepository.findById(richiestaDTO.getId_utente())).thenReturn(Optional.of(utente));
        lenient().when(richiestaRepository.findByPrenotazione_IdAndStato_richiesta(prenotazione.getId())).thenReturn(0);
        lenient().when(prenotazioneRepository.save(prenotazione)).thenReturn(prenotazione);
        lenient().when(richiestaRepository.save(richiesta)).thenReturn(richiesta);

        assertEquals(richiestaDTO.getId(), richiestaServiceImpl.create_richiesta(richiestaDTO).getId());

        reset(richiestaRepository);
        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo accetta_richiesta:
     * -Verifico che esiste la prenotazione e la richiesta associata
     * -Con assertNull controllo che stato_richiesta sia null (significa che non è stata gestita in precedenza)
     * -Con assertTrue verifico che la richiesta sia stata accettata
     */
    @Test
    void accetta_richiesta(){
        Long id = 1l;

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test123")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        LocalDate date = LocalDate.now().minusDays(2);

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(id)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(null)
                .utente(utente)
                .prenotazione(prenotazione).build();

        RichiestaDTO richiestaDTO = new RichiestaDTO(richiesta);

        lenient().when(richiestaRepository.existsById(id)).thenReturn(true);
        lenient().when(richiestaRepository.findById(id)).thenReturn(Optional.of(richiesta));

        assertNull(richiesta.getStato_richiesta());

        lenient().when(prenotazioneRepository.findById(richiesta.getPrenotazione().getId())).thenReturn(Optional.of(prenotazione));

        lenient().when(prenotazioneRepository.save(prenotazione)).thenReturn(prenotazione);
        lenient().when(richiestaRepository.save(richiesta)).thenReturn(richiesta);

        assertTrue(richiestaServiceImpl.accetta_richiesta(id));

        reset(prenotazioneRepository);
        reset(richiestaRepository);
    }

    /**
     * Testing metodo rifiuta_richiesta:
     * -Verifico che esiste la prenotazione e la richiesta associata
     * -Con assertNull controllo che stato_richiesta sia null (significa che non è stata gestita in precedenza)
     * -Con assertTrue verifico che la richiesta sia stata accettata
     */
    @Test
    void rifiuta_richiesta(){
        Long id = 1l;
        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test123")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        LocalDate date = LocalDate.now().minusDays(2);

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(date)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Richiesta richiesta = Richiesta.builder()
                .id(id)
                .post_cena(true)
                .post_pranzo(false)
                .stato_richiesta(null)
                .utente(utente)
                .prenotazione(prenotazione).build();

        RichiestaDTO richiestaDTO = new RichiestaDTO(richiesta);

        lenient().when(richiestaRepository.existsById(id)).thenReturn(true);
        lenient().when(richiestaRepository.findById(id)).thenReturn(Optional.of(richiesta));

        assertNull(richiesta.getStato_richiesta());

        lenient().when(prenotazioneRepository.findById(richiesta.getPrenotazione().getId())).thenReturn(Optional.of(prenotazione));

        lenient().when(prenotazioneRepository.save(prenotazione)).thenReturn(prenotazione);
        lenient().when(richiestaRepository.save(richiesta)).thenReturn(richiesta);

        assertTrue(richiestaServiceImpl.accetta_richiesta(id));

        reset(prenotazioneRepository);
        reset(richiestaRepository);
    }
}
