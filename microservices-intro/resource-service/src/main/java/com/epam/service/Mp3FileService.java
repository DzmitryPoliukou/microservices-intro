package com.epam.service;

import com.epam.dto.ResourceOutputDto;
import com.epam.model.Mp3FileEntity;
import com.epam.repository.Mp3FileRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

@Slf4j
@RequiredArgsConstructor
@Service
public class Mp3FileService {

  private final Mp3FileRepository mp3FileRepository;
  private final RestTemplate restTemplate;

  @SneakyThrows
  public ResourceOutputDto uploadFile(byte[] mp3File) {
    Mp3FileEntity fileEntity = new Mp3FileEntity();
    fileEntity.setFile(mp3File);
    Mp3FileEntity savedFile = mp3FileRepository.save(fileEntity);

    Map<String, Object> metadata = retrieveMetadata(mp3File, savedFile.getId());
    saveSongMetadata(metadata);

    return ResourceOutputDto.builder()
        .id(savedFile.getId())
        .build();
  }

  public byte[] getFile(Integer id) {
    return mp3FileRepository.findById(id).map(Mp3FileEntity::getFile).orElse(null);
  }

  public void deleteResources(List<Integer> ids) {
    mp3FileRepository.deleteAllById(ids);
  }

  private Map<String, Object> retrieveMetadata(byte[] file, Integer resourceId) throws IOException, TikaException, SAXException {
    ContentHandler handler = new BodyContentHandler();
    Metadata metadata = new Metadata();
    ParseContext parseCtx = new ParseContext();

    try (InputStream input = new ByteArrayInputStream(file)) {
      Mp3Parser parser = new Mp3Parser();
      parser.parse(input, handler, metadata, parseCtx);
    }

    return prepareMetadata(metadata, resourceId);
  }

  private Map<String, Object> saveSongMetadata(Map<String, Object> songMetadata) {
    return restTemplate.postForObject("http://SONG-SERVICE/songs", songMetadata, Map.class);
  }

  private Map<String, Object> prepareMetadata(Metadata metadata, Integer resourceId) {
    HashMap<String, Object> preparedMetadata = new HashMap<>();
    preparedMetadata.put("name", metadata.get("title"));
    preparedMetadata.put("artist", metadata.get("xmpDM:artist"));
    preparedMetadata.put("album", metadata.get("xmpDM:album"));
    preparedMetadata.put("length", metadata.get("xmpDM:duration"));
    preparedMetadata.put("resourceId", resourceId);
    preparedMetadata.put("year", metadata.get("xmpDM:releaseDate"));

    return preparedMetadata;
  }
}
