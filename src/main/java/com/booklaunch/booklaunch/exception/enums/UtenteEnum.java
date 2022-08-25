package com.booklaunch.booklaunch.exception.enums;

/**
 * In UtenteEnum vengono gestite tutte le custom exception riguardanti gli utenti
 */
public enum UtenteEnum {

    UTENTE_NOT_FOUND("UTE_NF", "l'utente che stai cercando non è stato trovato"),
    UTENTE_ALREADY_EXISTS("UTE_AE", "Parametri già presenti nel sistema"),
    UTENTI_NOT_EXISTS("UTI_NE", "Nel db non sono presenti utenti"),
    UTENTE_DELETE_ERROR("UTE_DLE", "Errore durante l'eliminazione dell'utente"),
    UTENTE_ID_NOT_EXIST("UTE_IDNE", "l'utente che stai cercando non esiste"),
    UTENTE_EMPTY_FIELD("UTE_EF", "Alcuni campi sono vuoti");


    private final String messageCode;
    private final String message;

    UtenteEnum(final String messageCode, final String message) {
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
     * -Ritorna UtenteEnum associata al codice di errore
     *
     * @param messageCode
     * @return UtenteEnum
     */
    public static UtenteEnum getUtenteEnumByMessageCode(final String messageCode) {
        for (final UtenteEnum utenteEnum : UtenteEnum.values()) {
            if (utenteEnum.getMessageCode().equals(messageCode)) {
                return utenteEnum;
            }
        }
        return null;
    }
}

