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




    @Test
    void findByEmail() {
        String email = "test@gmail.com";

        Utente utente = Utente.builder().id(1l).nome("Stefano").cognome("Boom").password("test").email(email.toUpperCase(Locale.ROOT)).ruolo(RoleEnum.ROLE_USER).build();

        assertNotNull(utente);
        lenient().when(utenteRepository.existsByEmail(email.toUpperCase(Locale.ROOT))).thenReturn(true);
        lenient().when(utenteRepository.findByEmail(email.toUpperCase(Locale.ROOT))).thenReturn(utente);

        UtenteDTO utenteDTO = new UtenteDTO(utente);
        assertNotNull(utenteDTO);
        assertEquals(utenteDTO.getEmail(), utenteServiceImpl.findByEmail(email.toUpperCase(Locale.ROOT)).getEmail());

        reset(utenteRepository);
    }

    @Test
    void findByCognome() {
        String cognome = "Boom";

        Utente utente = Utente.builder().id(1l).nome("Stefano").cognome(cognome).password("test").email("test@gmail.com").ruolo(RoleEnum.ROLE_USER).build();
        Utente utente1 = Utente.builder().id(2l).nome("Marco").cognome(cognome).password("test").email("test123@gmail.com").ruolo(RoleEnum.ROLE_USER).build();
        List<Utente> list = List.of(utente, utente1);

        assertNotNull(utente);
        lenient().when(utenteRepository.existsByCognome(cognome)).thenReturn(true);
        lenient().when(utenteRepository.findByCognome(cognome)).thenReturn(list);

        UtenteDTO utenteDTO = new UtenteDTO(utente);
        assertNotNull(utenteDTO);
        assertEquals(list.get(0).getId(), utenteServiceImpl.findByCognome(cognome).get(0).getId());

        reset(utenteRepository);
    }

    @Test
    void elimina_utente() {

        Long id = 1L;

        lenient().when(utenteRepository.existsById(id)).thenReturn(true);
        Assertions.assertTrue(utenteServiceImpl.elimina_utente(id));
        verify(utenteRepository, atLeastOnce()).existsById(id);
        reset(utenteRepository);

    }

    @Test
    void create_utente() {
        Utente utente = Utente.builder().id(1l).nome("Stefano").cognome("Boom").password("test123").email("test@gmail.com").ruolo(RoleEnum.ROLE_USER).build();

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
     * Questo metodo testa l'update dell'anagrafica
     * dell'utente con i relativi controlli
     */
    @Test
    void update_utente(){

        String password = "abcde";
        String email = "test@gmail.com";

        Utente utente = Utente.builder().id(1l).nome("Stefano").cognome("Boom").email("boom@boom.gmail.com").ruolo(RoleEnum.ROLE_USER).password(password).build();

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

    @Test
    void findAll(){
        Utente utente = Utente.builder().id(1l).nome("Stefano").cognome("Boom").password("test").email("test@gmail.com").ruolo(RoleEnum.ROLE_USER).build();
        Utente utente1 = Utente.builder().id(2l).nome("Marco").cognome("Frufru").password("test").email("test123@gmail.com").ruolo(RoleEnum.ROLE_USER).build();
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
