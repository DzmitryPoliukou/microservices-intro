package com.epam.service;

import com.epam.dto.SongMetadataInputDto;
import com.epam.dto.SongMetadataOutputDto;
import com.epam.model.SongMetadata;
import com.epam.repository.SongMetadataRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SongMetadataService {

  private final SongMetadataRepository songMetadataRepository;

  public Map<String, Integer> saveMetadata(SongMetadataInputDto songMetadataInputDto) {
    if (!isSongMetadataValid(songMetadataInputDto.toEntity())) {
      throw new IllegalArgumentException();
    }
    SongMetadata songMetadata = songMetadataInputDto.toEntity();
    SongMetadata savedSong = songMetadataRepository.save(songMetadata);
    return Map.of("id", savedSong.getId());
  }

  public SongMetadataOutputDto findSongMetadata(Integer id) {
    return songMetadataRepository.findById(id)
        .map(SongMetadata::toDto)
        .orElse(null);
  }

  public List<Integer> deleteSongMetadata(List<Integer> ids) {
    List<Integer> deletedIds = new ArrayList<>();
    ids.forEach(id -> songMetadataRepository.findById(id).ifPresent(song -> {
      songMetadataRepository.delete(song);
      deletedIds.add(id);
    }));
    return deletedIds;
  }

  private boolean isSongMetadataValid(SongMetadata songMetadata) {
    return songMetadata.getName() != null && songMetadata.getArtist() != null;
  }


}
