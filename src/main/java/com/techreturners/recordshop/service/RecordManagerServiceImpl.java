package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.repository.RecordManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordManagerServiceImpl implements RecordManagerService {

    @Autowired
    RecordManagerRepository musicRecordManagerRepository;

    @Override
    public MusicRecord insertMusicRecord(MusicRecord musicRecord) {
        String albumName = musicRecord.getAlbumName();
        if (albumName != null) {
            Optional<MusicRecord> bookOptional =
                    musicRecordManagerRepository.findByAlbumName(albumName);
            if (bookOptional.isPresent()) {
                throw new RecordAlreadyExistsException("Album with name: " +
                        musicRecord.getAlbumName() +
                        " already exists and cannot be inserted again");
            }
            validateInputValues(musicRecord);
        }
        return musicRecordManagerRepository.save(musicRecord);
    }

    private boolean validateInputValues(MusicRecord musicRecord){
        Integer releaseYear = musicRecord.getReleaseYear();
        Long stock = musicRecord.getStock();
        if (validateReleaseYear(releaseYear) && validateStock(stock))
            return true;
        return false;
    }

    private static boolean validateStock(Long stock) {
        if (stock != null){
            if (stock instanceof Long &&
                stock >= 0) {
                return true;
            }else{
                throw new NumberFormatException("Invalid input for Stock. "+
                        "Please enter valid positive integer value for stock");
            }
        }
        return false;
    }

    private static boolean validateReleaseYear(Integer releaseYear) {
        if (releaseYear != null) {
            if (releaseYear instanceof Integer &&
                releaseYear.toString().length() == 4 &&
                releaseYear.intValue() > 1000 && releaseYear.intValue() < 9999 ) {
                return true;
            } else {
                throw new NumberFormatException("Invalid input for Release Year. " +
                        "Please enter valid value for year in format YYYY");
            }
        }
        return false;
    }
}
