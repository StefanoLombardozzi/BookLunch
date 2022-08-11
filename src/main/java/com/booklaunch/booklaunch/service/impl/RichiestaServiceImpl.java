package com.booklaunch.booklaunch.service.impl;

import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.exception.enums.PrenotazioneEnum;
import com.booklaunch.booklaunch.exception.enums.RichiestaEnum;
import com.booklaunch.booklaunch.exception.enums.UtenteEnum;
import com.booklaunch.booklaunch.exception.handler.ApiRequestException;
import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.model.Richiesta;
import com.booklaunch.booklaunch.repository.PrenotazioneRepository;
import com.booklaunch.booklaunch.repository.RichiestaRepository;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.RichiestaService;
import com.booklaunch.booklaunch.service.UtenteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RichiestaServiceImpl implements RichiestaService {

    private final RichiestaRepository richiestaRepository;
    private static RichiestaEnum richiestaEnum;

    private static PrenotazioneEnum prenotazioneEnum;

    private final PrenotazioneRepository prenotazioneRepository;

    private final UtenteRepository utenteRepository;


    @Override
    public RichiestaDTO create_richiesta(RichiestaDTO richiestaDTO) {
        if (prenotazioneRepository.existsById(richiestaDTO.getId_prenotazione())) {
            Prenotazione prenotazione = prenotazioneRepository.findById(richiestaDTO.getId_prenotazione()).get();
            Richiesta richiesta = new Richiesta(richiestaDTO);
            richiesta.setUtente(utenteRepository.findById(richiestaDTO.getId_utente()).get());
            richiesta.setPrenotazione(prenotazione);
            /*richiesta.setPost_pranzo(false);  //false di default per far funzionare gli if al di sotto di qui
            richiesta.setPost_cena(false);*/

            if ((richiestaRepository.findByPrenotazione_IdAndStato_richiesta(prenotazione.getId())) == 0) {
                prenotazione.setCheck_richiesta(Boolean.TRUE);
                prenotazioneRepository.save(prenotazione);
                richiestaRepository.save(richiesta);
                return new RichiestaDTO(richiesta);
            } else {
                richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_AE");
                throw new ApiRequestException(richiestaEnum.getMessage());
            }
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_IDNE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public Boolean delete_richiesta(Long id_richiesta) {
        if (richiestaRepository.existsById(id_richiesta)) {
            richiestaRepository.deleteById(id_richiesta);
            return true;
        } else {
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_IDNE");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<RichiestaDTO> findAll() {
        if (!richiestaRepository.findAll().isEmpty())
            return richiestaRepository.findAll().stream().map(RichiestaDTO::new).collect(Collectors.toList());
        else {
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_NE");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    @Override
    public List<RichiestaDTO> getRichiesteUtente(Long id_utente) {
        if (id_utente != null && utenteRepository.existsById(id_utente)) {
            return richiestaRepository.getRichiesteUtente(id_utente).stream().map(RichiestaDTO::new).collect(Collectors.toList());
        } else {
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_UN");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    @Override
    public List<RichiestaDTO> getRichiesteAdmin(Long id_admin) {
        if (id_admin != null && utenteRepository.existsById(id_admin)) {
            return richiestaRepository.getRichiesteAdmin().stream().map(RichiestaDTO::new).collect(Collectors.toList());
        } else {
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_UN");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    @Override
    public List<RichiestaDTO> getRichiesteByUtente(Long id_utente) {
        if (id_utente != null && utenteRepository.existsById(id_utente)) {
            return richiestaRepository.getRichiesteByUtente(id_utente).stream().map(RichiestaDTO::new).collect(Collectors.toList());
        } else {
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_UN");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    @Override
    public Boolean accetta_richiesta(Long id_richiesta) {
        if (richiestaRepository.existsById(id_richiesta)) {
            Richiesta richiesta = richiestaRepository.findById(id_richiesta).get();
            if(richiesta.getStato_richiesta() == null) {
                richiesta.setStato_richiesta(Boolean.TRUE);
                Prenotazione prenotazione = prenotazioneRepository.findById(richiesta.getPrenotazione().getId()).get();
                prenotazione.setCheck_richiesta(Boolean.FALSE);
                richiestaRepository.save(richiesta);
                prenotazioneRepository.save(prenotazione);
                return true;
            }else{
                richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_AT");
                throw new ApiRequestException(richiestaEnum.getMessage());
            }
        }else{
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_NF");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }

    @Override
    public Boolean rifiuta_richiesta(Long id_richiesta) {
        if (richiestaRepository.existsById(id_richiesta)) {
            Richiesta richiesta = richiestaRepository.findById(id_richiesta).get();
            if(richiesta.getStato_richiesta() == null) {
                richiesta.setStato_richiesta(Boolean.FALSE);
                Prenotazione prenotazione = prenotazioneRepository.findById(richiesta.getPrenotazione().getId()).get();
                prenotazione.setCheck_richiesta(Boolean.FALSE);
                richiestaRepository.save(richiesta);
                prenotazioneRepository.save(prenotazione);
                return true;
            }else{
                richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_AT");
                throw new ApiRequestException(richiestaEnum.getMessage());
            }
        }else{
            richiestaEnum = RichiestaEnum.getRichiestaEnumByMessageCode("RIC_NF");
            throw new ApiRequestException(richiestaEnum.getMessage());
        }
    }
}
