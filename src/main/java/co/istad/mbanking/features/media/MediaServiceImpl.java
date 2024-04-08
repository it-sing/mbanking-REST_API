package co.istad.mbanking.features.media;

import co.istad.mbanking.features.media.dto.MediaResponse;
import co.istad.mbanking.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {

        String newName = UUID.randomUUID().toString();
        int lastDotIndex = file.getOriginalFilename()
                .lastIndexOf(".");
        String extension = file.getOriginalFilename()
                .substring(lastDotIndex + 1);
        newName = newName + "." + extension;
        Path path = Paths.get(serverPath, folderName, newName);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage()
            );
        }
        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .uri(String.format("%s/%s/%s", baseUri, folderName, newName))
                .size(file.getSize())
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
//        create empty array list , wait for add uploaded files
        List<MediaResponse> mediaResponses = new ArrayList<>();
        files.forEach(file ->{
                MediaResponse  mediaResponse = this.uploadSingle(file, folderName);
                mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName , String folderName) {
        // create absolute path
        Path path = Paths.get(serverPath, folderName, mediaName);
        try {
            Resource resource = new UrlResource(path.toUri());
            if(!resource.exists()){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media has not been file"
                );
            }
            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType(Files.probeContentType(path))
                    .extension((MediaUtil.extractExtension(mediaName)))
                    .size(resource.contentLength())
                    .uri(String.format("%s/%s/%s", baseUri, folderName,mediaName))
                    .build();
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media has not been file"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MediaResponse> listAllMedia() {
        List<MediaResponse> mediaResponses = new ArrayList<>();
        try {
            Files.walk(Paths.get(serverPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String mediaName = path.getFileName().toString();
                        mediaResponses.add(this.loadMediaByName(mediaName,"IMAGE"));
                    });
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media has not been file"
            );
        }
        return mediaResponses;

    }
    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);
        try {
            long size = Files.size(path);
            if(Files.deleteIfExists(path)){
                return MediaResponse.builder()
                        .name(mediaName)
                        .contentType(Files.probeContentType(path))
                        .extension((MediaUtil.extractExtension(mediaName)))
                        .size(size)
                        .uri(String.format("%s/%s/%s", baseUri, folderName,mediaName))
                        .build();
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Media has not been file"
            );
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media has not been file"
            );
        }
    }

    @Override
    public Resource loadMediaResource(String mediaName ,String  folderName) {
        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Media has not been found!");
        }

    }

}
