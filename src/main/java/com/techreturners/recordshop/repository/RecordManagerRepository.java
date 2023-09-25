package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.MusicRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordManagerRepository extends CrudRepository<MusicRecord, Long> {
}