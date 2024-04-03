package co.istad.mbanking.features.media.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MediaResponse(
        String name, // unique
        // check if json null not display
        @JsonInclude( JsonInclude.Include.NON_NULL)
        String contentType, // png , jpeg , mp4
        String extension,
        String uri,
        @JsonInclude( JsonInclude.Include.NON_NULL)
        Long size

) {

}
