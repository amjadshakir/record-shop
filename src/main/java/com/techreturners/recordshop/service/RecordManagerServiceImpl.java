package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.repository.RecordManagerRepository;
import com.techreturners.recordshop.validator.MusicRecordValidator;
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
            Optional<MusicRecord> recordOptional =
                    musicRecordManagerRepository.findByAlbumName(albumName);
            if (recordOptional.isPresent()) {
                throw new RecordAlreadyExistsException("Album with name: " +
                        musicRecord.getAlbumName() +
                        " already exists and cannot be inserted again");
            }
        }
        boolean isValidReleaseYear = MusicRecordValidator.validateReleaseYear(musicRecord.getReleaseYear());
        boolean isValidStock = MusicRecordValidator.validateStock(musicRecord.getStock());
        if (isValidReleaseYear && isValidStock)
            return musicRecordManagerRepository.save(musicRecord);
        else
            throw new InvalidRecordInputException("Invalid input entered. Please enter" +
                    "only integers");
    }
}
