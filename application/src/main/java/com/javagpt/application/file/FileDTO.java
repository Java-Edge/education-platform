package com.javagpt.application.file;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.util.Optional;

@Data
public class FileDTO {


    private Long id;


    private String name;


    /**
     * 文件后缀,如pdf，word
     */
    private String suffix;

    private String mediaType;


    public String getMediaType() {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(suffix)) {
            Optional<MediaType> typeOptional = MediaTypeFactory.getMediaType(name + "." + suffix);
            return typeOptional.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        }
        return null;
    }

}
