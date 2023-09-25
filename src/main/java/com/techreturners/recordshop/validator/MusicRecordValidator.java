package com.techreturners.recordshop.validator;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import java.time.Year;

public class MusicRecordValidator {

    public static boolean validateStock(Long stock) {
        if (stock != null){
            if (stock >= 0) {
                return true;
            }else{
                throw new InvalidRecordInputException("Invalid input for Stock. "+
                        "Please enter valid positive integer value for stock");
            }
        }
        return false;
    }

    public static boolean validateReleaseYear(Integer releaseYear) {
        int currentYear = Year.now().getValue();
        if (releaseYear != null) {
            if (releaseYear.toString().length() == 4 &&
                releaseYear >= 1000 &&
                releaseYear <= currentYear ) {
                return true;
            } else {
                throw new InvalidRecordInputException("Invalid input for Release Year. " +
                        "Please enter valid value for year in the past " +
                        "in format YYYY");
            }
        }
        return false;
    }
}
