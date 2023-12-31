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

import java.util.List;

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
        recordManagerService.insertMusicRecord(musicRecord);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("record", "/api/v1/record/" + musicRecord.getId());
        return new ResponseEntity<>(musicRecord, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping({"/releaseYear/{releaseYear}"})
    public ResponseEntity<List<MusicRecord>>getMusicRecordByReleaseYear(@PathVariable Integer releaseYear)
            throws RecordNotFoundException {
        List<MusicRecord> musicRecordsByReleaseYear =
            recordManagerService.getMusicRecordByReleaseYear(releaseYear);
        if (musicRecordsByReleaseYear.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(musicRecordsByReleaseYear);
    }

    @DeleteMapping({"/{recordId}"})
    public ResponseEntity<Void> deleteMusicRecordById(@PathVariable("recordId") Long recordId)
                throws RecordNotFoundException{
        boolean isDeleted = recordManagerService.deleteRecordById(recordId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{recordId}/stock")
    public ResponseEntity<Void> updateStockAmount(
            @PathVariable("recordId") Long recordId,
            @RequestParam("stock") Long stock) throws RecordNotFoundException {
        boolean isUpdated = recordManagerService.updateStockAmount(recordId, stock);

        return isUpdated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/artist/{artistName}/albums")
    public ResponseEntity<List<MusicRecord>> getAllAlbumsByArtist(@PathVariable String artistName) {
        List<MusicRecord> albums = recordManagerService.getAllAlbumsByArtist(artistName);
        if (albums.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(albums);
    }
}
