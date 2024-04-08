package co.istad.mbanking.features.media;

import co.istad.mbanking.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file) {
        return mediaService.uploadSingle(file,"IMAGE");
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files,"IMAGE");
    }

    @GetMapping("/{mediaName}")
    MediaResponse loadFileByName(@PathVariable String mediaName) {
        return mediaService.loadMediaByName(mediaName,"IMAGE");
    }
    @DeleteMapping("/{mediaName}")
    MediaResponse deleteFileByName(@PathVariable String mediaName) {
        return mediaService.deleteMediaByName(mediaName,"IMAGE");
    }
    @GetMapping()
    List<MediaResponse> listAllMedia() {
        return mediaService.listAllMedia();
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = {"/{mediaName}/download"},
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<Resource> downloadMediaByName(@PathVariable String mediaName) {
        Resource resource = mediaService.loadMediaResource(mediaName,"IMAGE");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +mediaName)
                .body(resource);
    }
}
