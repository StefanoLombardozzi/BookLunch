package com.booklaunch.booklaunch.service.impl;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;
import com.booklaunch.booklaunch.exception.enums.PrenotazioneEnum;
import com.booklaunch.booklaunch.exception.enums.UtenteEnum;
import com.booklaunch.booklaunch.exception.handler.ApiRequestException;
import com.booklaunch.booklaunch.model.Prenotazione;
import com.booklaunch.booklaunch.repository.PrenotazioneRepository;
import com.booklaunch.booklaunch.repository.UtenteRepository;
import com.booklaunch.booklaunch.service.PrenotazioneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    /**
     * -Metodo che controlla se ci sono prenotazioni.
     * Se la risposta è positiva,ritorna tutte le prenotazioni presenti nel DB
     *
     * @return List<PrenotazioneDTO>
     */
    @Override
    public List<PrenotazioneDTO> findAll() {
        if (!prenotazioneRepository.findAll().isEmpty())
            return prenotazioneRepository.findAll().stream().map(PrenotazioneDTO::new).collect(Collectors.toList());
        else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che effettua l'insert della prenotazione da parte di un utente
     * -Prima di tutto viene controllato se l'utente esiste (altrimenti invio exception custom)
     * -Controllo che l'utente non abbia effettuato una prenotazione nella stessa data (altrimenti invio exception custom)
     * -Controllo che l'utente abbia selezionato una data valida (bisogna prenotarsi fino al giorno precedente)
     * -Se tutti i controlli danno esito positivo, creo la prenotazione
     *
     * @param prenotazioneDTO
     * @return PrenotazioneDTO
     */
    @Override
    public PrenotazioneDTO insertPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        LocalDate today = LocalDate.now();
        if(utenteRepository.existsById(prenotazioneDTO.getId_utente())) {

            if (prenotazioneRepository.existsByUtenteAndData(prenotazioneDTO.getId_utente(), prenotazioneDTO.getData_prenotazione()) == 0) {
                Prenotazione prenotazione = new Prenotazione.PrenotazioneBuilder()
                        .colazione(prenotazioneDTO.getColazione())
                        .pranzo(prenotazioneDTO.getPranzo())
                        .cena(prenotazioneDTO.getCena())
                        .data_prenotazione(prenotazioneDTO.getData_prenotazione()).build();

                prenotazione.setCheck_richiesta(Boolean.FALSE);
                prenotazione.setSacchetto_pranzo(false);
                prenotazione.setSacchetto_cena(false);
                prenotazione.setUtente(utenteRepository.findById(prenotazioneDTO.getId_utente()).get());

                if ((ChronoUnit.DAYS.between(today, prenotazioneDTO.getData_prenotazione()) >= 1)) {

                    if (!prenotazioneDTO.getCena()) {
                        prenotazione.setSacchetto_cena(prenotazioneDTO.getSacchetto_cena());
                    }
                    if (!prenotazioneDTO.getPranzo()) {
                        prenotazione.setSacchetto_pranzo(prenotazioneDTO.getSacchetto_pranzo());
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
        }else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
    }

    /**
     * -Metodo che controlla se la prenotazione esiste
     *  e se il campo check_richiesta in Prenotazione è false
     *  dopodicchè la elimina
     *
     * @param id_prenotazione
     * @return Boolean
     */
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

    /**
     * -Metodo che ritorna una lista di prenotazioni ricercate tramite la data
     *
     * @param data_prenotazione
     * @return List<PrenotazioneDTO>
     */
    @Override
    public List<PrenotazioneDTO> findByData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.existsByData_prenotazione(data_prenotazione) != 0) {
            return prenotazioneRepository.findByData_prenotazione(data_prenotazione).stream().map(PrenotazioneDTO::new).collect(Collectors.toList());
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che conta le prenotazioni al pranzo in una certa data
     *
     * @param data_prenotazione
     */
    @Override
    public int countByPranzoAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazionePranzo(data_prenotazione) != 0) {
            return prenotazioneRepository.prenotazionePranzo(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che conta le prenotazioni a cena in una certa data
     *
     * @param data_prenotazione
     */
    @Override
    public int countByCenaAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneCena(data_prenotazione) != 0) {
            return prenotazioneRepository.prenotazioneCena(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che conta le prenotazioni a colazione in una certa data
     *
     * @param data_prenotazione
     */
    @Override
    public int countByColazioneAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneColazione(data_prenotazione) != 0) {
            return prenotazioneRepository.prenotazioneColazione(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che conta le prenotazioni al sacchetto pranzo in una certa data
     *
     * @param data_prenotazione
     */
    @Override
    public int countBySacchettoPranzoAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneSacchettoPranzo(data_prenotazione) != 0) {
            return prenotazioneRepository.prenotazioneSacchettoPranzo(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che conta le prenotazioni al sacchetto cena in una certa data
     *
     * @param data_prenotazione
     */
    @Override
    public int countBySacchettoCenaAndData(LocalDate data_prenotazione) {
        if (prenotazioneRepository.prenotazioneSacchettoCena(data_prenotazione) != 0) {
           return prenotazioneRepository.prenotazioneSacchettoCena(data_prenotazione);
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }

    /**
     * -Metodo che ritorna la prenotazione modificata
     *  dopo aver controllato l'esistenza dell'utente e della prenotazione stessa
     *
     * @param prenotazioneDTO
     * @return PrenotazioneDTO
     */
    @Override
    public PrenotazioneDTO updatePrenotazione(PrenotazioneDTO prenotazioneDTO) {
        LocalDate today = LocalDate.now();
        if(utenteRepository.existsById(prenotazioneDTO.getId_utente())) {

            if (prenotazioneRepository.existsById(prenotazioneDTO.getId())) {
                Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneDTO.getId()).get();
                prenotazione.setSacchetto_pranzo(false);
                prenotazione.setSacchetto_cena(false);

                if (!prenotazione.getCheck_richiesta()) {
                    if ((ChronoUnit.DAYS.between(today, prenotazione.getData_prenotazione()) >= 1)) {
                        prenotazione.setColazione(prenotazioneDTO.getColazione());
                        prenotazione.setPranzo(prenotazioneDTO.getPranzo());
                        prenotazione.setCena(prenotazioneDTO.getCena());

                        if (!prenotazioneDTO.getCena())
                            prenotazione.setSacchetto_cena(prenotazioneDTO.getSacchetto_cena());

                        if (!prenotazioneDTO.getPranzo())
                            prenotazione.setSacchetto_pranzo(prenotazioneDTO.getSacchetto_pranzo());

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
            } else {
                prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_IDNE");
                throw new ApiRequestException(prenotazioneEnum.getMessage());
            }
        }else {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_NF");
            throw new ApiRequestException(utenteEnum.getMessage());
        }
    }

    /**
     * -Metodo che ritorna una lista di prenotazioni ricercate tramite l'utente
     *
     * @param id_utente
     * @return List<PrenotazioneDTO>
     */
    @Override
    public List<PrenotazioneDTO> findByUtente(Long id_utente) {
        if (utenteRepository.existsById(id_utente)) {
            return prenotazioneRepository.prenotazioneByUtente(id_utente).stream().map(PrenotazioneDTO::new).collect(Collectors.toList());

        } else if (!utenteRepository.existsById(id_utente)) {
            utenteEnum = UtenteEnum.getUtenteEnumByMessageCode("UTE_IDNE");
            throw new ApiRequestException(utenteEnum.getMessage());
        } else {
            prenotazioneEnum = PrenotazioneEnum.getPrenotazioneEnumByMessageCode("PRE_NE");
            throw new ApiRequestException(prenotazioneEnum.getMessage());
        }
    }
}
