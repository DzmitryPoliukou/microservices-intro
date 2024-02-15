package com.epam.controller;

import com.epam.dto.SongMetadataInputDto;
import com.epam.dto.SongMetadataOutputDto;
import com.epam.service.SongMetadataService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongMetadataController {

  private final SongMetadataService songMetadataService;

  @PostMapping
  public ResponseEntity<Map<String, Integer>> createSongMetadata(@RequestBody SongMetadataInputDto songMetadataInputDto) {
    try {
      Map<String, Integer> response = songMetadataService.saveMetadata(songMetadataInputDto);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<SongMetadataOutputDto> findSongMetadata(@PathVariable("id") Integer id) {
    SongMetadataOutputDto songMetadata = songMetadataService.findSongMetadata(id);
    if (songMetadata == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(songMetadata);
  }

  @DeleteMapping
  public ResponseEntity<Map<String, List<Integer>>> deleteSongMetadata(@RequestParam("id") String id) {
    if (id.length() > 200) {
      return ResponseEntity.badRequest().build();
    }
    List<Integer> ids = Arrays.stream(id.split(","))
        .map(Integer::parseInt)
        .toList();
    List<Integer> removedIds = songMetadataService.deleteSongMetadata(ids);
    Map<String, List<Integer>> response = new HashMap<>();
    response.put("ids", removedIds);
    return ResponseEntity.ok(response);
  }
}
