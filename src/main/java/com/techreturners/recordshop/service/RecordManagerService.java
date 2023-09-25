package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicRecord;

public interface RecordManagerService {
    MusicRecord insertMusicRecord(MusicRecord musicRecord);
    MusicRecord getMusicRecordByReleaseYear(Integer releaseYear);
    boolean deleteRecordById(Long recordId);
}
