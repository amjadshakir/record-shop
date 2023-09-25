package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecordManagerRepositoryTests {

    @Autowired
    private RecordManagerRepository recordManagerRepository;

    @Test
    public void testCreatesAndFindBookByIdReturnsBook() {
        MusicRecord musicRecord =
                new MusicRecord(101L, "Album One nought One",
                        "Artist One nought One", 2000,10L, MusicGenre.Metal);
        recordManagerRepository.save(musicRecord);

        var musicRecordId = recordManagerRepository.findById(musicRecord.getId());
        assertThat(musicRecordId).isNotNull();
    }

}