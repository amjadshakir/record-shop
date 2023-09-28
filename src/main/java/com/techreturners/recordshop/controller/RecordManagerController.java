package com.techreturners.recordshop.controller;

import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.exception.RecordNotFoundException;
import com.techreturners.recordshop.model.MusicGenre;
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

    @GetMapping({"/{recordId}"})
    public ResponseEntity<MusicRecord> getMusicRecord(@PathVariable("recordId") Long recordId)
            throws RecordNotFoundException {
        return ResponseEntity.ok(recordManagerService.getMusicRecordById(recordId));
    }

    @GetMapping({"/all"})
    public ResponseEntity<List<MusicRecord>> getMusicRecord()
            throws RecordNotFoundException {
        return ResponseEntity.ok(recordManagerService.getAllRecords());
    }

    @GetMapping("/artist/{artistName}/albums")
    public ResponseEntity<List<MusicRecord>> getAllAlbumsByArtist(@PathVariable String artistName) {
        List<MusicRecord> albums = recordManagerService.getAllAlbumsByArtist(artistName);
        if (albums.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/stock")
    public ResponseEntity<List<MusicRecord>> getAllRecordsInStock() {
        List<MusicRecord> recordsInStock = recordManagerService.getAllRecordsInStock();
        return new ResponseEntity<>(recordsInStock, HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MusicRecord>> getAllRecordsByGenre(@PathVariable MusicGenre genre) {
        List<MusicRecord> recordsByGenre = recordManagerService.getAllRecordsByGenre(genre);
        return new ResponseEntity<>(recordsByGenre, HttpStatus.OK);
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
    public ResponseEntity<List<MusicRecord>> getMusicRecordByReleaseYear(@PathVariable Integer releaseYear)
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
            throws RecordNotFoundException {
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

    @PutMapping("/{id}")
    public ResponseEntity<MusicRecord> updateMusicRecord(@PathVariable Long id, @RequestBody MusicRecord record) {
        MusicRecord updatedRecord = recordManagerService.updateRecord(id, record);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);

    }
}
