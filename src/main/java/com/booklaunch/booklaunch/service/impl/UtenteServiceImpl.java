package com.booklaunch.booklaunch.service.impl;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.exception.enums.RoleEnum;
import com.booklaunch.booklaunch.exception.enums.UtenteEnum;
import com.booklaunch.booklaunch.exception.handler.ApiRequestException;
import com.booklaunch.booklaunch.model.Utente;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.UtenteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class UtenteServiceImpl implements UtenteService, UserDetailsService {

    private final UtenteRepository utenteRepository;
    private static UtenteEnum utenteEnum;
    private final PasswordEncoder passwordEncoder;

    /**
     * Metodo che ricerca lo username per security authorities
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email == null || !utenteRepository.existsByEmail(email.toUpperCase(Locale.ROOT))) {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
        Utente utente = utenteRepository.findByEmail(email.toUpperCase(Locale.ROOT));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(utente.getRuolo().toString()));
        return new org.springframework.security.core.userdetails.User(utente.getEmail(), utente.getPassword(), authorities);
    }

    /**
     * -Metodo che effetta l'insert di un nuovo utente
     *  dopo aver verificato la non esistenza dello stesso,
     *  altrimenti verrà inviata l'exception custom
     *
     * @param utenteDTO
     * @return UtenteDTO
     */
    @Override
    public UtenteDTO create_utente(UtenteDTO utenteDTO) {
        if(utenteRepository.existsByEmail(utenteDTO.getEmail())){
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_AE");
            throw new ApiRequestException(utenteEnum.getMessage());
        }else{
            Utente utente = new Utente.UtenteBuilder()
                    .nome(utenteDTO.nome)
                    .cognome(utenteDTO.cognome)
                    .email(utenteDTO.getEmail().toUpperCase(Locale.ROOT))
                    .password(passwordEncoder.encode(utenteDTO.getPassword()))
                    .ruolo(RoleEnum.valueOf("ROLE_USER")).build();

            utenteRepository.save(utente);

            return new UtenteDTO(utente);


        }
    }

    /**
     * -Metodo che effettia il cambio ruolo da USER ad ADMIN
     *
     * @param id_utente
     * @return UtenteDTO
     */
    @Override
    public UtenteDTO create_admin(Long id_utente) {
        if(!utenteRepository.existsById(id_utente)){
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }else{
            Utente utente = utenteRepository.findById(id_utente).get();
            utente.setRuolo(RoleEnum.ROLE_ADMIN);
            utenteRepository.save(utente);

            return new UtenteDTO(utente);
        }
    }

    /**
     * -Metodo che permette di modificare le credenziali di accesso
     *  dell'utente, ritornando l'utente con i parametri modificati
     *
     * @param utenteDTO
     * @return UtenteDTO
     */
    @Override
    public UtenteDTO update_utente(UtenteDTO utenteDTO) {
        if (utenteRepository.existsById(utenteDTO.id)) {

            Utente old = utenteRepository.findById(utenteDTO.id).get();

            if(utenteDTO.getPassword() != null)
                old.setPassword(passwordEncoder.encode(utenteDTO.getPassword()));
            if(utenteDTO.getEmail() != null)
                old.setEmail(utenteDTO.getEmail());

            utenteRepository.save(old);

            return new UtenteDTO(old);

        } else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }

    }

    /**
     * -Metodo che effettia il delete di un utente dal
     * -Controlla che esiste (altimenti invia exception custom
     *
     * @param id_utente
     * @return Boolean
     */
    @Override
    public Boolean elimina_utente(Long id_utente) {
        if (utenteRepository.existsById(id_utente)) {
            utenteRepository.deleteById(id_utente);
            return true;
        } else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }

    }

    /**
     * -Metodo che ritorna tutti gli utenti presenti nel DB
     *
     * @return List<UtenteDTO>
     */
    @Override
    public List<UtenteDTO> findAll() {
        if (!utenteRepository.findAll().isEmpty())
            return utenteRepository.findAll().stream().map(UtenteDTO::new).collect(Collectors.toList());
        else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTI_NE");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
    }

    /**
     * -Metodo che ritorna l'utente ricercato tramite email
     *  altrimenti invia exception custom
     *
     * @param email
     * @return UtenteDTO
     */
    @Override
    public UtenteDTO findByEmail(String email) {
        if (email != null && utenteRepository.existsByEmail(email.toUpperCase(Locale.ROOT)))
            return new UtenteDTO(utenteRepository.findByEmail(email.toUpperCase(Locale.ROOT)));
        else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
    }

    /**
     * -Metodo che ritorna una lista di utenti ricercati tramite cognome
     *  altrimenti invia exception custom
     *
     * @param cognome
     * @return List<UtenteDTO>
     */
    @Override
    public List<UtenteDTO> findByCognome(String cognome) {
        if (cognome != null && utenteRepository.existsByCognome(cognome))
            return utenteRepository.findByCognome(cognome).stream().map(UtenteDTO::new).collect(Collectors.toList());
        else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
    }
}
