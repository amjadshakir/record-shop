package com.techreturners.recordshop.controller;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.exception.RecordNotFoundException;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.service.RecordManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/record")
public class RecordManagerController {

    @Autowired
    RecordManagerService recordManagerService;

    @ExceptionHandler(value = RecordAlreadyExistsException.class)
    public ResponseEntity handleRecordAlreadyExistsException(
            RecordAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity handleRecordANotFoundException(
            RecordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidRecordInputException.class)
    public ResponseEntity handleInvalidRecordInputException(
            InvalidRecordInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @PostMapping
    public ResponseEntity<MusicRecord> addMusicRecord(@RequestBody MusicRecord musicRecord)
            throws RecordAlreadyExistsException {
        MusicRecord newMusicRecord =
                recordManagerService.insertMusicRecord(musicRecord);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("record", "/api/v1/record/" + musicRecord.getId());
        return new ResponseEntity<>(musicRecord, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping({"/{releaseYear}"})
    public ResponseEntity<MusicRecord> getMusicRecordByReleaseYear(@PathVariable Integer releaseYear)
            throws RecordNotFoundException {
        return new ResponseEntity<>(recordManagerService.getMusicRecordByReleaseYear(releaseYear), HttpStatus.OK);
    }
}
