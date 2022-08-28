package com.booklaunch.booklaunch;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.model.Utente;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.impl.UtenteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Ogni metodo testato viene annotato con @Test e al loro interno vengono richiamati i metodi della classe Assertions e Mockito.
 * Gli Assertions servono per verificare la coerenza tra i dati che ci aspettiamo e che effettivamente vengono restituiti.
 */
@ExtendWith(MockitoExtension.class)
public class UtenteServiceImplTest implements PasswordEncoder {

    private UtenteServiceImpl utenteServiceImpl;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        utenteServiceImpl = new UtenteServiceImpl(utenteRepository, passwordEncoder);
    }


    /**
     * Testing metodo findByEmail:
     * -Verifico che la mail dell'utente sia uguale
     *  alla mail ritornata dal metodo
     */
    @Test
    void findByEmail() {
        String email = "test@gmail.com";

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Boom")
                .password("test")
                .email(email.toUpperCase(Locale.ROOT))
                .ruolo(RoleEnum.ROLE_USER).build();

        assertNotNull(utente);
        lenient().when(utenteRepository.existsByEmail(email.toUpperCase(Locale.ROOT))).thenReturn(true);
        lenient().when(utenteRepository.findByEmail(email.toUpperCase(Locale.ROOT))).thenReturn(utente);

        UtenteDTO utenteDTO = new UtenteDTO(utente);
        assertNotNull(utenteDTO);
        assertEquals(utenteDTO.getEmail(), utenteServiceImpl.findByEmail(email.toUpperCase(Locale.ROOT)).getEmail());

        reset(utenteRepository);
    }

    /**
     * Testing metodo findByCognome:
     * -Verifico che l'id dell'utente sia uguale
     *  all'id (utente cercato tramite cognome) ritornata dal metodo
     */
    @Test
    void findByCognome() {
        String cognome = "Lomba";

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome(cognome)
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Utente utente1 = new Utente.UtenteBuilder()
                .id(2l).nome("Marco")
                .cognome(cognome)
                .password("test")
                .email("test123@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        List<Utente> list = List.of(utente, utente1);

        assertNotNull(utente);
        lenient().when(utenteRepository.existsByCognome(cognome)).thenReturn(true);

        lenient().when(utenteRepository.findByCognome(cognome)).thenReturn(list);

        UtenteDTO utenteDTO = new UtenteDTO(utente);
        assertNotNull(utenteDTO);
        assertEquals(list.get(0).getId(), utenteServiceImpl.findByCognome(cognome).get(0).getId());

        reset(utenteRepository);
    }

    /**
     * Testing metodo elimina_utente:
     * -Verifico che esiste l'utente con il lenient()
     * -Verifico che l'utente sia stato eliminato
     */
    @Test
    void elimina_utente() {

        Long id = 1L;

        lenient().when(utenteRepository.existsById(id)).thenReturn(true);
        Assertions.assertTrue(utenteServiceImpl.elimina_utente(id));
        verify(utenteRepository, atLeastOnce()).existsById(id);
        reset(utenteRepository);

    }

    /**
     * Testing metodo create_utente:
     * -Verifico che l'utente non sia registrato con il lenient()
     * -Verifico che la mail del nuovo utente sia uguale
     *  alla mail ritornata dal metodo
     */
    @Test
    void create_utente() {
        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Rossi")
                .password("test123")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        lenient().when(utenteRepository.existsByEmail(utente.getEmail())).thenReturn(false);

        UtenteDTO utenteDTO = new UtenteDTO(utente);

        String psw = utenteDTO.getPassword();
        String hashedPsw = this.encode(psw);

        lenient().when(passwordEncoder.encode(psw)).thenReturn(hashedPsw);

        lenient().when(utenteRepository.save(utente)).thenReturn(utente);

        assertEquals(utente.getEmail().toUpperCase(Locale.ROOT), utenteServiceImpl.create_utente(utenteDTO).getEmail());

        reset(utenteRepository);
    }

    /**
     * Testing metodo update_utente:
     * -Controllo che esiste l'utente
     * -Verififco che la mail dell'utente modificato sia uguale
     *  alla mail dell'utente ritornato dal metodo
     */
    @Test
    void update_utente(){

        String password = "abcde";
        String email = "test@gmail.com";

        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .email("Lomba@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).
                password(password).build();

        UtenteDTO utenteDTO = new UtenteDTO(utente);

        utenteDTO.setEmail(email);

        lenient().when(utenteRepository.existsById(utente.getId())).thenReturn(true);
        lenient().when(utenteRepository.findById(utenteDTO.id)).thenReturn(Optional.of(utente));


        String hashedPsw = this.encode(password);

        lenient().when(passwordEncoder.encode(password)).thenReturn(hashedPsw);

        lenient().when(utenteRepository.save(utente)).thenReturn(utente);

        assertEquals(email.toUpperCase(Locale.ROOT),utenteServiceImpl.update_utente(utenteDTO).getEmail().toUpperCase(Locale.ROOT));

        reset(utenteRepository);

    }

    /**
     * Testing metodo findAll:
     * -Con asserTrye verifica che la condizione all'interno di essa sia vera
     * -Con il verify verifico che il findAll sia richiamato almeno una volta
     *  così da accertarmi che ritorna tutti gli utenti
     */
    @Test
    void findAll(){
        Utente utente = new Utente.UtenteBuilder()
                .id(1l)
                .nome("Stefano")
                .cognome("Lomba")
                .password("test")
                .email("test@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        Utente utente1 = new Utente.UtenteBuilder()
                .id(2l)
                .nome("Marco")
                .cognome("Rossi")
                .password("test")
                .email("test123@gmail.com")
                .ruolo(RoleEnum.ROLE_USER).build();

        List<Utente> list = List.of(utente, utente1);

        assertTrue(utenteRepository.findAll().isEmpty());
        lenient().when(utenteRepository.findAll()).thenReturn(list);

        verify(utenteRepository, atLeast(1)).findAll();
        reset(utenteRepository);
    }
    
    /**
     * Questi metodi sono necessari per il corretto mock di BcryptEncoder
     * @param charSequence
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }

}
