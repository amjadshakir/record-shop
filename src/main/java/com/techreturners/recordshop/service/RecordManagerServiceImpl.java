package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exception.InvalidInputException;
import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.exception.RecordNotFoundException;
import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.repository.RecordManagerRepository;
import com.techreturners.recordshop.validator.MusicRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
            throw new InvalidRecordInputException(
                    "Invalid input entered. Please enter only integers");
    }

    @Override
    public MusicRecord getMusicRecordById(Long recordId) {
        return musicRecordManagerRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException(
                        "Record with ID: " + recordId + " not found"));
    }

    @Override
    public List<MusicRecord> getAllRecords() {
        Iterable<MusicRecord> records = musicRecordManagerRepository.findAll();
        return StreamSupport.stream(records.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicRecord> getAllRecordsInStock() {
        return StreamSupport.stream(musicRecordManagerRepository.findAll().spliterator(), false)
                .filter(record -> record.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicRecord> getMusicRecordByReleaseYear(Integer releaseYear) {

        if (releaseYear != null) {
            Optional<List<MusicRecord>> musicRecordOptionalList =
                    musicRecordManagerRepository.findByReleaseYear(releaseYear);
            if (musicRecordOptionalList.isPresent()) {
                return musicRecordOptionalList.get();
            }
        }
        throw new RecordNotFoundException("Record with release year: " +
                releaseYear + " is not found");
    }

    @Override
    public List<MusicRecord> getAllAlbumsByArtist(String artistName) {
        return musicRecordManagerRepository.findAllByArtist(artistName)
                .orElseThrow(() -> new RecordNotFoundException(
                        "Cannot find Music Records for artist: " + artistName));
    }

    @Override
    public List<MusicRecord> getAllRecordsByGenre(MusicGenre genre) {
        return musicRecordManagerRepository.findByGenre(genre);
    }

    @Override
    public List<MusicRecord> getMusicRecordsByAlbumName(String albumName) {
        return musicRecordManagerRepository.findAllByAlbumName(albumName)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No music records found for album name: " + albumName));
    }

    @Override
    public MusicRecord updateRecord(Long id, MusicRecord record) {
        return musicRecordManagerRepository.findById(id)
                .map(currentRecord -> {
                    currentRecord.setAlbumName(record.getAlbumName());
                    currentRecord.setArtist(record.getArtist());
                    currentRecord.setReleaseYear(record.getReleaseYear());
                    currentRecord.setStock(record.getStock());
                    currentRecord.setGenre(record.getGenre());
                    return musicRecordManagerRepository.save(currentRecord);
                })
                .orElseThrow(() -> new RecordNotFoundException("Product not found with id: " + id));
    }

    @Override
    public boolean updateStockAmount(Long recordId, Long stock) {
        return Optional.ofNullable(recordId)
                .flatMap(id -> musicRecordManagerRepository.findById(id))
                .map(musicRecord -> {
                    musicRecord.setStock(stock);
                    musicRecordManagerRepository.save(musicRecord);
                    return true;
                })
                .orElseThrow(() -> new RecordNotFoundException(
                        "Music Record with record Id: " + recordId + " is not found for update"));
    }

    @Override
    public boolean deleteRecordById(Long recordId) {
        if (recordId != null) {
            Optional<MusicRecord> musicRecordOptional =
                    musicRecordManagerRepository.findById(recordId);
            if (musicRecordOptional.isPresent()) {
                musicRecordManagerRepository.deleteById(recordId);
                return true;
            }
        }
        throw new RecordNotFoundException("Music Record with record Id: " +
                recordId + " is not found for delete");
    }

}
