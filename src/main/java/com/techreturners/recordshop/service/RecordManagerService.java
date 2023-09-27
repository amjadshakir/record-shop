package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;

import java.util.List;

public interface RecordManagerService {
    MusicRecord insertMusicRecord(MusicRecord musicRecord);

    List<MusicRecord> getMusicRecordByReleaseYear(Integer releaseYear);

    List<MusicRecord> getAllAlbumsByArtist(String artistName);

    boolean deleteRecordById(Long recordId);

    boolean updateStockAmount(Long recordId, Long stock);
    List<MusicRecord> getAllRecordsInStock();
    List<MusicRecord> getAllRecordsByGenre(MusicGenre genre);
    MusicRecord replaceExistingRecord(Long id, MusicRecord record);

}
