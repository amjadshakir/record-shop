package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.exception.RecordNotFoundException;
import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.repository.RecordManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class RecordManagerServiceTests {

    @Mock
    private RecordManagerRepository mockRecordManagerRepository;

    @InjectMocks
    private RecordManagerServiceImpl recordManagerServiceImpl;


    @Test
    public void testInsertMusicRecordValid() {
        var musicRecord = new MusicRecord(100L, "Album Hundred",
                "Artist 100", 2000, 20L, MusicGenre.Instrumental);
        when(mockRecordManagerRepository.save(musicRecord)).thenReturn(musicRecord);
        MusicRecord actualResult =
                recordManagerServiceImpl.insertMusicRecord(musicRecord);
        assertThat(actualResult).isEqualTo(musicRecord);
    }

    @Test
    public void testInsertMusicRecordInvalidYear() {
        var invalidMusicRecord = new MusicRecord(100L, "Album Hundred",
                "Artist 100", 2025, 20L, MusicGenre.Instrumental);

        InvalidRecordInputException thrownException = assertThrows(InvalidRecordInputException.class, () -> {
            recordManagerServiceImpl.insertMusicRecord(invalidMusicRecord);
        });
        assertThat(thrownException.getMessage()).isEqualTo(
                "Invalid input for Release Year. Please enter valid value for year in the past in format YYYY");
    }

    @Test
    public void testInsertMusicRecordInvalidStock() {
        var invalidMusicRecord = new MusicRecord(100L, "Album Hundred",
                "Artist 100", 2023, -20L, MusicGenre.Instrumental);

        InvalidRecordInputException thrownException = assertThrows(InvalidRecordInputException.class, () -> {
            recordManagerServiceImpl.insertMusicRecord(invalidMusicRecord);
        });
        assertThat(thrownException.getMessage()).isEqualTo(
                "Invalid input for Stock. Please enter valid positive integer value for stock");
    }

    @Test
    public void testGetMusicRecordByReleaseYear() {

        Integer releaseYear = 2020;
        var musicRecord = new MusicRecord(105L,
                "Album 105", "Artist 105", 2020,50L, MusicGenre.Jazz);

        when(mockRecordManagerRepository.findByReleaseYear(releaseYear)).thenReturn(Optional.of(musicRecord));

        MusicRecord actualResult = recordManagerServiceImpl.getMusicRecordByReleaseYear(releaseYear);

        assertThat(actualResult).isEqualTo(musicRecord);
    }

    @Test
    public void testGetMusicRecordNotFoundByReleaseYear(){
        Integer releaseYear = 2020;
        when(mockRecordManagerRepository.findByReleaseYear(releaseYear)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> {
            recordManagerServiceImpl.getMusicRecordByReleaseYear(releaseYear);
        });
    }

    @Test
    public void testDeleteMusicRecordById(){
        Long recordId = 105L;
        var musicRecord = new MusicRecord(105L,
                "Album 105", "Artist 105", 2020,50L, MusicGenre.Jazz);

        when(mockRecordManagerRepository.findById(recordId)).thenReturn(Optional.of(musicRecord));
        mockRecordManagerRepository.deleteById(recordId);

        boolean isDeleted = recordManagerServiceImpl.deleteRecordById(recordId);
        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteMusicRecordByIdNotFound(){
        Long recordId = 105L;
        when(mockRecordManagerRepository.findById(recordId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            recordManagerServiceImpl.deleteRecordById(recordId);
        });
    }

}