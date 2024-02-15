package com.epam.controller;

import com.epam.service.Mp3FileService;
import com.epam.dto.ResourceOutputDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/resources")
public class Mp3FileController {

  private final Mp3FileService mp3FileService;

  @PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, "audio/mpeg"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResourceOutputDto uploadFile(@RequestBody byte[] mp3File) {
    return mp3FileService.uploadFile(mp3File);
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getSongFile(@PathVariable("id") Integer id) {
    try {
      byte[] file = mp3FileService.getFile(id);
      return file == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(file);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping
  public ResponseEntity<Map<String, List<Integer>>> deleteResources(@RequestParam("id") String id) {
    if (id.length() > 200) {
      return ResponseEntity.badRequest().build();
    }
    List<Integer> ids = Arrays.stream(id.split(","))
        .map(Integer::parseInt)
        .toList();
    mp3FileService.deleteResources(ids);
    Map<String, List<Integer>> response = new HashMap<>();
    response.put("ids", ids);
    return ResponseEntity.ok(response);
  }
}
