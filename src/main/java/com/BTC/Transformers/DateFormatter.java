package com.BTC.Transformers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatter {
    public static String convertDate(String date) {
        // Ajout du Locale.ENGLISH pour gérer les abréviations de mois en anglais
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            return parsedDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + date);
            throw e; // Re-throw l'exception pour la gestion en amont
        }
    }
}
