package com.techreturners.recordshop.validator;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.model.MusicRecord;

public class MusicRecordValidator {

    public static boolean validateStock(Long stock) {
        if (stock != null){
            if (stock instanceof Long &&
                    stock >= 0) {
                return true;
            }else{
                throw new InvalidRecordInputException("Invalid input for Stock. "+
                        "Please enter valid positive integer value for stock");
            }
        }
        return false;
    }

    public static boolean validateReleaseYear(Integer releaseYear) {
        if (releaseYear != null) {
            if (releaseYear instanceof Integer &&
                    releaseYear.toString().length() == 4 &&
                    releaseYear.intValue() > 1000 && releaseYear.intValue() < 9999 ) {
                return true;
            } else {
                throw new InvalidRecordInputException("Invalid input for Release Year. " +
                        "Please enter valid value for year in format YYYY");
            }
        }
        return false;
    }
}
