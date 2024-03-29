package com.booklaunch.booklaunch.exception.enums;

/**
 * In RichiestaEnum vengono gestite tutte le custom exception riguardanti le richieste
 */
public enum RichiestaEnum {

    RICHIESTA_NOT_FOUND("RIC_NF", "La richiesta che stai cercando non è stata trovata"),
    RICHIESTA_ALREADY_EXISTS("RIC_AE", "Parametri già presenti nel sistema"),
    RICHIESTA_NOT_EXISTS("RIC_NE", "Nel db non sono presenti richieste"),
    RICHIESTA_DELETE_ERROR("RIC_DLE", "Errore durante l'eliminazione della richiesta"),
    RICHIESTA_ID_NOT_EXIST("RIC_IDNE", "La richiesta che stai cercando non esiste"),
    RICHIESTA_EMPTY_FIELD("RIC_EF", "Alcuni campi sono vuoti"),
    RICHIESTA_USER_UNEXIST("RIC_UN","L'utente che stai cercando non esiste"),
    RICHIESTA_ALREADY_TAKE("RIC_AT","La richiesta indicata è stata già gestita");

    private final String messageCode;
    private final String message;

    RichiestaEnum(final String messageCode, final String message) {
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
     * -Ritorna RichiestaEnum associata al codice di errore
     *
     * @param messageCode
     * @return RichiestaEnum
     */
    public static RichiestaEnum getRichiestaEnumByMessageCode(final String messageCode) {
        for (final RichiestaEnum richiestaEnum : RichiestaEnum.values()) {
            if (richiestaEnum.getMessageCode().equals(messageCode)) {
                return richiestaEnum;
            }
        }
        return null;
    }
}
