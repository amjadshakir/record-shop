package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicRecord;

import java.util.List;

public interface RecordManagerService {
    MusicRecord insertMusicRecord(MusicRecord musicRecord);
    MusicRecord getMusicRecordByReleaseYear(Integer releaseYear);
    boolean deleteRecordById(Long recordId);
    List<MusicRecord> getAllRecordsInStock();
}
