package com.epam.model;

import com.epam.dto.SongMetadataOutputDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String artist;
  private String album;
  private String length;
  private Integer resourceId;
  private String year;

  public SongMetadataOutputDto toDto() {
    return SongMetadataOutputDto.builder()
        .name(name)
        .artist(artist)
        .album(album)
        .length(length)
        .resourceId(resourceId)
        .year(year)
        .build();
  }
}
