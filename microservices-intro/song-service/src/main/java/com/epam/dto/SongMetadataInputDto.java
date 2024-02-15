package com.epam.dto;

import com.epam.model.SongMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SongMetadataInputDto {

  private String name;
  private String artist;
  private String album;
  private String length;
  private Integer resourceId;
  private String year;

  public SongMetadata toEntity() {
    return SongMetadata.builder()
        .name(name)
        .artist(artist)
        .album(album)
        .length(length)
        .resourceId(resourceId)
        .year(year)
        .build();
  }
}
