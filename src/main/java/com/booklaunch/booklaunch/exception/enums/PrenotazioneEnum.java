package com.booklaunch.booklaunch.exception.enums;

/**
 * In prenotazioneEnum vengono gestite tutte le custom exception riguardanti le prenotazioni
 */
public enum PrenotazioneEnum {

    PRENOTAZIONE_NOT_FOUND("PRE_NF", "La prenotazione che stai cercando non è stata trovata"),
    PRENOTAZIONE_ALREADY_EXISTS("PRE_AE", "Parametri già presenti nel sistema"),
    PRENOTAZIONE_NOT_EXISTS("PRE_NE", "Nel db non sono presenti prenotazioni"),
    PRENOTAZIONE_DELETE_ERROR("PRE_DLE", "Errore durante l'eliminazione della prenotazione"),
    PRENOTAZIONE_ID_NOT_EXIST("PRE_IDNE", "La prenotazione che stai cercando non esiste"),
    PRENOTAZIONE_EMPTY_FIELD("PRE_EF", "Alcuni campi sono vuoti"),
    PRENOTAZIONE_SACCHETTO_ERROR("PRE_SE","Non è possibile inserire i sacchetti se si è prenotati a pranzo o a cena"),
    PRENOTAZIONE_ERROR("PRE_E","E' possibile prenotarsi solo fino al giorno prima"),
    PRENOTAZIONE_DELETE_MODIFY("PRE_DM","Impossibile modificare o eliminare la prenotazione se la richiesta non è stata elaborata");

    private final String messageCode;
    private final String message;

    PrenotazioneEnum(final String messageCode, final String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    /**
     *-Ritorna il codice del messaggio di errore
     *
     * @return String
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * -Ritorna il messaggio di errore
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * -Ritorna PrenotazioneEnum associata al codice di errore
     *
     * @param messageCode
     * @return PrenotazioneEnum
     */
    public static PrenotazioneEnum getPrenotazioneEnumByMessageCode(final String messageCode) {
        for (final PrenotazioneEnum prenotazioneEnum : PrenotazioneEnum.values()) {
            if (prenotazioneEnum.getMessageCode().equals(messageCode)) {
                return prenotazioneEnum;
            }
        }
        return null;
    }
}
