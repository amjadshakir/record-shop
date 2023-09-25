package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.MusicRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordManagerRepository extends CrudRepository<MusicRecord, Long> {
    Optional<MusicRecord> findByAlbumName(String albumName);
}