package com.booklaunch.booklaunch.service.impl;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.dto.RichiestaDTO;
import com.booklaunch.booklaunch.exception.enums.PrenotazioneEnum;
import com.booklaunch.booklaunch.exception.enums.RichiestaEnum;
import com.booklaunch.booklaunch.exception.enums.UtenteEnum;
import com.booklaunch.booklaunch.exception.handler.ApiRequestException;
import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.repository.PrenotazioneRepository;
import com.booklaunch.booklaunch.repository.RichiestaRepository;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.PrenotazioneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private static PrenotazioneEnum prenotazioneEnum;

    private final PrenotazioneRepository prenotazioneRepository;

    private final UtenteRepository utenteRepository;

    private static UtenteEnum utenteEnum;

    @Override
    public List<PrenotazioneDTO> findAll() {
        if (!prenotazioneRepository.findAll().isEmpty())
            return prenotazioneRepository.findAll().stream().map(PrenotazioneDTO::new).collect(Collectors.toList());
        else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public PrenotazioneDTO insertPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        LocalDate today = LocalDate.now();
        if (prenotazioneRepository.existsByUtenteAndData(prenotazioneDTO.getId_utente(), prenotazioneDTO.getData_prenotazione()) != 0) {
            Prenotazione prenotazione = new Prenotazione(prenotazioneDTO);
            if ((ChronoUnit.DAYS.between(today, prenotazioneDTO.getData_prenotazione()) >= 1)) {
                prenotazione.setColazione(prenotazioneDTO.getColazione());
                prenotazione.setPranzo(prenotazioneDTO.getPranzo());
                prenotazione.setCena(prenotazioneDTO.getCena());
                prenotazione.setData_prenotazione(prenotazioneDTO.getData_prenotazione());

                if (prenotazioneDTO.getCena() == null) {
                    prenotazione.setSacchetto_pranzo(prenotazioneDTO.getSacchetto_pranzo());
                } else {
                    prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_SE");
                    throw new ApiRequestException(prenotazioneEnum.getMessage());
                }

                if (prenotazioneDTO.getPranzo() == null) {
                    prenotazione.setSacchetto_cena(prenotazioneDTO.getSacchetto_cena());
                } else {
                    prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_SE");
                    throw new ApiRequestException(prenotazioneEnum.getMessage());
                }

                prenotazioneRepository.save(prenotazione);

                return new PrenotazioneDTO(prenotazione);
            } else {
                prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_E");
                throw new ApiRequestException(prenotazioneEnum.getMessage());
            }
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_AE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public Boolean deletePrenotazione(Long id_prenotazione) {
        if (prenotazioneRepository.existsById(id_prenotazione)) {
            Prenotazione prenotazione = prenotazioneRepository.findById(id_prenotazione).get();
            if (!prenotazione.getCheck_richiesta()) {
                prenotazioneRepository.deleteById(id_prenotazione);
                return true;
            } else {
                prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_DM");
                throw new ApiRequestException(prenotazioneEnum.getMessage());
            }

        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_IDNE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public List<PrenotazioneDTO> findByData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.existsByData_prenotazione(data_prenotazione) != 0) {
            return prenotazioneRepository.findByData_prenotazione(data_prenotazione).stream().map(PrenotazioneDTO::new).collect(Collectors.toList());
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public void countByPranzoAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazionePranzo(data_prenotazione) != 0) {
            prenotazioneRepository.prenotazionePranzo(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public void countByCenaAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneCena(data_prenotazione) != 0) {
            prenotazioneRepository.prenotazioneCena(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public void countByColazioneAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneColazione(data_prenotazione) != 0) {
            prenotazioneRepository.prenotazioneColazione(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public void countBySacchettoPranzoAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneSacchettoPranzo(data_prenotazione) != 0) {
            prenotazioneRepository.prenotazioneSacchettoPranzo(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public void countBySacchettoCenaAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneSacchettoCena(data_prenotazione) != 0) {
            prenotazioneRepository.prenotazioneSacchettoCena(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public PrenotazioneDTO updatePrenotazione(PrenotazioneDTO prenotazioneDTO) {
        LocalDate today = LocalDate.now();
        if (prenotazioneRepository.existsById(prenotazioneDTO.getId())) {
            Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneDTO.getId()).get();

            if (!prenotazione.getCheck_richiesta()) {
                if ((ChronoUnit.DAYS.between(today, prenotazione.getData_prenotazione()) >= 1)) {
                    prenotazione.setColazione(prenotazioneDTO.getColazione());
                    prenotazione.setPranzo(prenotazioneDTO.getPranzo());
                    prenotazione.setCena(prenotazioneDTO.getCena());

                    if (prenotazioneDTO.getCena() == null) {
                        prenotazione.setSacchetto_pranzo(prenotazioneDTO.getSacchetto_pranzo());
                    } else {
                        prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_SE");
                        throw new ApiRequestException(prenotazioneEnum.getMessage());
                    }

                    if (prenotazioneDTO.getPranzo() == null) {
                        prenotazione.setSacchetto_cena(prenotazioneDTO.getSacchetto_cena());
                    } else {
                        prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_SE");
                        throw new ApiRequestException(prenotazioneEnum.getMessage());
                    }

                    prenotazioneRepository.save(prenotazione);

                    return new PrenotazioneDTO(prenotazione);
                } else {
                    prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_E");
                    throw new ApiRequestException(prenotazioneEnum.getMessage());
                }

            } else {
                prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_DM");
                throw new ApiRequestException(prenotazioneEnum.getMessage());
            }
        }else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_IDNE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    @Override
    public List<PrenotazioneDTO> findByUtente(Long id_utente) {
        if (!utenteRepository.existsById(id_utente)) {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_IDNE");
            throw new ApiRequestException(utenteEnum.getMessage());
        } else if (utenteRepository.existsById(id_utente)) {
            return prenotazioneRepository.prenotazioneByUtente(id_utente).stream().map(PrenotazioneDTO::new).collect(Collectors.toList());
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }
}
