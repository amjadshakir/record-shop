package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;

import java.util.List;

public interface RecordManagerService {
    MusicRecord insertMusicRecord(MusicRecord musicRecord);
    MusicRecord getMusicRecordByReleaseYear(Integer releaseYear);
    boolean deleteRecordById(Long recordId);
    List<MusicRecord> getAllRecordsInStock();
    List<MusicRecord> getAllRecordsByGenre(MusicGenre genre);
    MusicRecord replaceExistingRecord(Long id, MusicRecord record);
}
