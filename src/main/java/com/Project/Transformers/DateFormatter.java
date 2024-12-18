package com.Project.Transformers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    public static String convertDate(String date) {
        // Correct the format to "dd/MM/yyyy" to match input like "17/12/2024"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            return parsedDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + date);
            throw e; // Re-throw the exception for handling upstream
        }
    }
}
