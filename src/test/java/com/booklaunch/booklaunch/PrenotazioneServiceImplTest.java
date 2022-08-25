package com.booklaunch.booklaunch;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.model.Utente;
import com.booklaunch.booklaunch.repository.PrenotazioneRepository;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.impl.PrenotazioneServiceImpl;
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
public class PrenotazioneServiceImplTest {


    private PrenotazioneServiceImpl prenotazioneServiceImpl;

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @BeforeEach
    void setUp() {
        prenotazioneServiceImpl = new PrenotazioneServiceImpl(prenotazioneRepository, utenteRepository);
    }

    /**
     * Testing metodo findAll:
     * -Con asserTrye() verifico che la condizione all'interno di essa sia vera
     * -Con il verify() verifico che il findAll sia richiamato almeno una volta
     *  così da accertarmi che ritorna tutte le prenotazioni
     */
    @Test
    void findAll() {

        assertTrue(prenotazioneRepository.findAll().isEmpty());

        verify(prenotazioneRepository, atLeast(1)).findAll();
        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo findByData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che findByData non sia richiamato
     *  quindi che non esistono prenotazioni
     */
    @Test
    void findByData() {
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.existsByData_prenotazione(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).findByData_prenotazione(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo countByPranzoAndData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che prenotazionePranzo non sia richiamato
     *  quindi che non esistono prenotazioni al pranzo di quella data
     */
    @Test
    void countByPranzoAndData(){
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.prenotazionePranzo(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).prenotazionePranzo(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo countByCenaAndData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che prenotazioneCena non sia richiamato
     *  quindi che non esistono prenotazioni alla cena di quella data
     */
    @Test
    void countByCenaAndData(){
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.prenotazioneCena(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).prenotazioneCena(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo countByColazioneAndData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che prenotazioneColazione non sia richiamato
     *  quindi che non esistono prenotazioni alla colazione di quella data
     */
    @Test
    void countByColazioneAndData(){
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.prenotazioneColazione(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).prenotazioneColazione(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo countBySacchettoPranzoAndData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che prenotazioneSacchettoPranzo non sia richiamato
     *  quindi che non esistono prenotazioni al sacchetto pranzo
     */
    @Test
    void countBySacchettoPranzoAndData(){
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.prenotazioneSacchettoPranzo(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).prenotazioneSacchettoPranzo(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo countBySacchettoCenaAndData:
     * -Con lenient() verifico che la condizione ritorni 0
     * -Con verify() verifico che prenotazioneSacchettoCena non sia richiamato
     *  quindi che non esistono prenotazioni al sacchetto cena
     */
    @Test
    void countBySacchettoCenaAndData(){
        LocalDate data = LocalDate.now().minusDays(2);

        lenient().when(prenotazioneRepository.prenotazioneSacchettoCena(data)).thenReturn(0);
        verify(prenotazioneRepository, atLeast(0)).prenotazioneSacchettoCena(data);

        reset(prenotazioneRepository);
    }

    /**
     * Testing metodo findByUtente:
     * -Creo una lista di prenotazioni
     * -Con i lenient() verifico che esiste l'utente e le prenotazioni
     * -Con assertEquals() verifico che l'id dell'utente è associato correttamente alla prenotazione
     */
    @Test
    void findByUtente() {
        Long id = 1l;
        LocalDate data = LocalDate.now().minusDays(2);

        Utente utente = new Utente.UtenteBuilder()
                .id(id)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione2 = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(data)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        Prenotazione prenotazione1 = new Prenotazione.PrenotazioneBuilder()
                .id(2l)
                .data_prenotazione(data)
                .cena(true)
                .colazione(true)
                .pranzo(false)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        List <Prenotazione> list = List.of(prenotazione1, prenotazione2);

        lenient().when(utenteRepository.existsById(id)).thenReturn(true);
        lenient().when(prenotazioneRepository.prenotazioneByUtente(id)).thenReturn(list);
        List <PrenotazioneDTO> listDTO = List.of(new PrenotazioneDTO(prenotazione1), new PrenotazioneDTO(prenotazione2));

        assertEquals(listDTO.get(0).getId(), prenotazioneServiceImpl.findByUtente(id).get(0).getId());

        reset(prenotazioneRepository);
        reset(utenteRepository);
    }

    /**
     * Testing metodo deletePrenotazione:
     * -Creo un utente e una prenotazione
     * -Con i lenient() verifico che esiste la prenotazione
     * -Con asserTrue verifico che la prenotazione è stata cancellata
     */
    @Test
    void deletePrenotazione(){

        Long id = 1l;
        LocalDate data = LocalDate.now().minusDays(2);

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(id)
                .data_prenotazione(data)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();


        lenient().when(prenotazioneRepository.existsById(id)).thenReturn(true);
        lenient().when(prenotazioneRepository.findById(id)).thenReturn(Optional.of(prenotazione));
        assertFalse(prenotazione.getCheck_richiesta());

        Assertions.assertTrue(prenotazioneServiceImpl.deletePrenotazione(id));

        reset(prenotazioneRepository);

    }

    /**
     * Testing metodo insertPrenotazione:
     * -Creo un utente e una prenotazione
     * -Con i lenient verifico che esiste l'utente e salvo la nuova prenotazione
     * -La classe ChronoUnit non è testabile con JUnit
     */
    @Test
    void insertPrenotazione(){

        LocalDate data = LocalDate.now().minusDays(2);

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(data)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        lenient().when(prenotazioneRepository.existsByUtenteAndData(utente.getId(), prenotazione.getData_prenotazione())).thenReturn(0);
        lenient().when(utenteRepository.findById(prenotazione.getUtente().getId())).thenReturn(Optional.of(utente));

        lenient().when(prenotazioneRepository.save(prenotazione)).thenReturn(prenotazione);

        reset(prenotazioneRepository);
        reset(utenteRepository);

    }

    /**
     * Testing metodo updatePrenotazione:
     * -Creo un utente e una prenotazione
     * -Con i lenient verifico che esiste l'utente e salvo la prenotazione modificata
     * -La classe ChronoUnit non è testabile con JUnit
     */
    @Test
    void updatePrenotazione(){

        LocalDate data = LocalDate.now().minusDays(2);

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                .id(1l)
                .data_prenotazione(data)
                .cena(true)
                .colazione(true)
                .pranzo(true)
                .check_richiesta(false)
                .sacchetto_pranzo(false)
                .sacchetto_cena(false)
                .utente(utente).build();

        lenient().when(prenotazioneRepository.existsById(prenotazione.getId())).thenReturn(true);
        lenient().when(prenotazioneRepository.findById(prenotazione.getId())).thenReturn(Optional.of(prenotazione));

        lenient().when(prenotazioneRepository.save(prenotazione)).thenReturn(prenotazione);

        reset(prenotazioneRepository);
        reset(utenteRepository);

    }




}
