package com.epam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class SongMetadataOutputDto {

  private String name;
  private String artist;
  private String album;
  private String length;
  private Integer resourceId;
  private String year;
}
