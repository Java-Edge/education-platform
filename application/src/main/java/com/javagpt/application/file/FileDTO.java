package com.javagpt.application.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.util.Optional;

@Data
public class FileDTO {


    @ApiModelProperty("文件id")
    private Long id;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String name;


    /**
     * 文件后缀,如pdf，word
     */
    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("文件媒体类型 也称MIME Type、Content Type")
    private String mediaType;


    public String getMediaType() {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(suffix)) {
            Optional<MediaType> typeOptional = MediaTypeFactory.getMediaType(name + "." + suffix);
            return typeOptional.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        }
        return null;
    }

}
