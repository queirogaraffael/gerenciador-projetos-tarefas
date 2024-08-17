package org.unifacisa.commons.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ManipulaData {

    private ManipulaData() {
    }

    public static final String FORMATO_DATA = "dd/MM/yyyy";
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern(FORMATO_DATA);

    public static boolean verificaFormatoDataEstaCorreto(String stringData) {
        try {
            LocalDate.parse(stringData, formato);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate retornaLocalDate(String data) {
        return LocalDate.parse(data, formato);
    }
}
