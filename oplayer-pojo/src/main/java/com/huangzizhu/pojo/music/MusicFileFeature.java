package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicFileFeature {
    private String name;
    private String path;
    private String md5;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MusicFileFeature that = (MusicFileFeature) o;
        return Objects.equals(md5, that.md5);
    }

    @Override
    public int hashCode() {
        return md5.hashCode();
    }
}
