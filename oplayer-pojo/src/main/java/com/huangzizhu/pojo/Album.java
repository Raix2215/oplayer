package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 专辑实体类
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
   private Integer albumId;
   private String albumName;
   private String artist;

   @Override
   public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      Album album = (Album) o;
      return Objects.equals(albumName, album.albumName) && Objects.equals(artist, album.artist);
   }

   @Override
   public int hashCode() {
      return Objects.hash(albumName, artist);
   }
}
