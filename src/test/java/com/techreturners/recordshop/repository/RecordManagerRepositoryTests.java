package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RecordManagerRepositoryTests {

    @Autowired
    private RecordManagerRepository recordManagerRepository;

    @Test
    public void testCreatesAndFindMusicRecordByIdReturnsMusicRecord() {
        MusicRecord musicRecord =
                new MusicRecord(101L, "Album One nought One",
                        "Artist One nought One", 2000,10L, MusicGenre.Metal);
        recordManagerRepository.save(musicRecord);

        var musicRecordId = recordManagerRepository.findById(musicRecord.getId());
        assertThat(musicRecordId).isNotNull();
    }

    @Test
    public void testFindRecordsByReleaseYear(){
        MusicRecord musicRecord =
                new MusicRecord(101L, "Album One nought One",
                            "Artist One nought One",
                        2000,10L, MusicGenre.Metal);
        var musicRecordByReleaseYear = recordManagerRepository.findByReleaseYear(
                musicRecord.getReleaseYear());
        assertThat(musicRecordByReleaseYear).isNotNull();
    }

    @Test
    public void testDeleteByRecordId(){
        MusicRecord musicRecord =
                new MusicRecord(101L, "Album One nought One",
                        "Artist One nought One",
                        2000,10L, MusicGenre.Metal);
        recordManagerRepository.save(musicRecord);

        var recordId = recordManagerRepository.findById(musicRecord.getId());
        assertThat(recordId).isNotNull();

        recordManagerRepository.deleteById(musicRecord.getId());
        var recordAfterDeletion = recordManagerRepository.findById(musicRecord.getId());
        assertThat(recordAfterDeletion).isEmpty();

    }
    @Test
    public void testFindAllRecordsInStock() {
        List<MusicRecord> records = new ArrayList<>();
        records.add(new MusicRecord(1L,
                "Album 1", "Artist 1", 2000,5L, MusicGenre.Jazz));
                records.add(new MusicRecord(2L,
                        "Album 2", "Artist 12", 2000,0L, MusicGenre.Rock));
                        recordManagerRepository.saveAll(records);
        Iterable<MusicRecord> result = recordManagerRepository.findAll();
        assertThat(result).hasSize(2);
    }
    @Test
    public void testFindAllRecordsByGenre() {
        List<MusicRecord> records = new ArrayList<>();
        records.add(new MusicRecord(1L,
                "Album 1", "Artist 1", 2000,5L, MusicGenre.Jazz));
                records.add(new MusicRecord(2L,
                        "Album 2", "Artist 2", 2000,0L, MusicGenre.Rock));
                        records.add(new MusicRecord(2L,
                                "Album 2", "Artist 2", 2000,0L, MusicGenre.Jazz));
                                recordManagerRepository.saveAll(records);
        Iterable<MusicRecord> result = recordManagerRepository.findByGenre(MusicGenre.Jazz);
        assertThat(result).hasSize(2);
    }

    @Test
    public void testGetAllByArtistName(){
        MusicRecord musicRecordA =
                new MusicRecord(101L, "Album One",
                        "Artist One",
                        2000,10L, MusicGenre.Metal);
        MusicRecord musicRecordB =
                new MusicRecord(102L, "Album Two",
                        "Artist One",
                        2001,20L, MusicGenre.Metal);
        recordManagerRepository.save(musicRecordA);
        recordManagerRepository.save(musicRecordB);

        Optional<List<MusicRecord>> optionalRecords =  recordManagerRepository
                .findAllByArtist("Artist One");
        assertTrue(optionalRecords.isPresent());

        List<MusicRecord> records = optionalRecords.get();
        assertThat(records).isNotNull();
        assertThat(records.size()).isEqualTo(2);

    }

}