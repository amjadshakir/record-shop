package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;

import java.util.List;

public interface RecordManagerService {
    public MusicRecord getMusicRecordById(Long recordId);

    public List<MusicRecord> getAllRecords();

    List<MusicRecord> getAllRecordsInStock();

    List<MusicRecord> getMusicRecordByReleaseYear(Integer releaseYear);

    List<MusicRecord> getAllAlbumsByArtist(String artistName);

    List<MusicRecord> getAllRecordsByGenre(MusicGenre genre);

    MusicRecord insertMusicRecord(MusicRecord musicRecord);

    boolean updateStockAmount(Long recordId, Long stock);

    MusicRecord updateRecord(Long id, MusicRecord record);

    boolean deleteRecordById(Long recordId);

}
