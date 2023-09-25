package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exception.InvalidRecordInputException;
import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.repository.RecordManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}