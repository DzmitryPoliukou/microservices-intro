package com.epam.repository;

import com.epam.model.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongMetadataRepository extends JpaRepository<SongMetadata, Integer> {

}
