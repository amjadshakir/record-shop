package com.techreturners.recordshop.controller;

import com.techreturners.recordshop.exception.RecordAlreadyExistsException;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.service.RecordManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/record")
public class RecordManagerController {

    @Autowired
    RecordManagerService recordManagerService;
    @PostMapping
    public ResponseEntity<MusicRecord> addMusicRecord(@RequestBody MusicRecord musicRecord)
            throws RecordAlreadyExistsException {
        MusicRecord newMusicRecord =
                recordManagerService.insertMusicRecord(musicRecord);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("record", "/api/v1/record/" + musicRecord.getId());
        return new ResponseEntity<>(musicRecord, httpHeaders, HttpStatus.CREATED);
    }
}
