package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;

import java.util.List;

public interface RecordManagerService {
    MusicRecord getMusicRecordById(Long recordId);

    List<MusicRecord> getAllRecords();

    List<MusicRecord> getAllRecordsInStock();

    List<MusicRecord> getMusicRecordByReleaseYear(Integer releaseYear);

    List<MusicRecord> getAllAlbumsByArtist(String artistName);

    List<MusicRecord> getAllRecordsByGenre(MusicGenre genre);

    List<MusicRecord> getMusicRecordsByAlbumName(String albumName);

    MusicRecord insertMusicRecord(MusicRecord musicRecord);

    boolean updateStockAmount(Long recordId, Long stock);

    MusicRecord updateRecord(Long id, MusicRecord record);

    boolean deleteRecordById(Long recordId);

}
