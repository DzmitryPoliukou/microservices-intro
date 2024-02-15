package com.epam.repository;

import com.epam.model.Mp3FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Mp3FileRepository extends JpaRepository<Mp3FileEntity, Integer> {

}
