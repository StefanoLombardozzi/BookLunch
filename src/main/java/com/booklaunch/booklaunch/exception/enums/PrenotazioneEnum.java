package com.booklaunch.booklaunch.exception.enums;

public enum PrenotazioneEnum {

    PRENOTAZIONE_NOT_FOUND("PRE_NF", "La prenotazione che stai cercando non è stata trovata"),
    PRENOTAZIONE_ALREADY_EXISTS("PRE_AE", "Parametri già presenti nel sistema"),
    PRENOTAZIONE_NOT_EXISTS("PRE_NE", "Nel db non sono presenti prenotazioni"),
    PRENOTAZIONE_DELETE_ERROR("PRE_DLE", "Errore durante l'eliminazione della prenotazione"),
    PRENOTAZIONE_ID_NOT_EXIST("PRE_IDNE", "La prenotazione che stai cercando non esiste"),
    PRENOTAZIONE_EMPTY_FIELD("PRE_EF", "Alcuni campi sono vuoti");

    private final String messageCode;
    private final String message;

    PrenotazioneEnum(final String messageCode, final String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessage() {
        return message;
    }

    public static PrenotazioneEnum getPrenotazioneEnumByMessageCode(final String messageCode) {
        for (final PrenotazioneEnum prenotazioneEnum : PrenotazioneEnum.values()) {
            if (prenotazioneEnum.getMessageCode().equals(messageCode)) {
                return prenotazioneEnum;
            }
        }
        return null;
    }
}
