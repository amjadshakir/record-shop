package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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