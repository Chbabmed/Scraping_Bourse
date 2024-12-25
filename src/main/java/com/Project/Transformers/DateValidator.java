package com.Project.Transformers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

/*      I created this class for handling insertion error of date,
        like if someone set a date with another format (not YYYY-MM-DD)
*/

    public boolean isValidDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try{
            // try to parse the string to LocalDate :
            LocalDate date1 = LocalDate.parse(date, formatter);
            return true;

        }catch(DateTimeParseException e){
            //System.out.println("Date is not valid");
            return false;
        }

    }










}
