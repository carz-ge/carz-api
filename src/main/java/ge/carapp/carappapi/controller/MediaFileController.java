package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.schema.MediaFileSchema;
import ge.carapp.carappapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
//@CrossOrigin(origins = "", allowedHeaders = "")
//@SecurityRequirement(name = "api-auth-bearer")
public class MediaFileController {
    private final MediaService mediaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<MediaFileSchema> uploadFile(@RequestPart(name = "file") MultipartFile inputFile) {
        MediaFileSchema filePath = this.mediaService.uploadFile(inputFile);
        return new ResponseEntity<>(filePath, HttpStatus.CREATED);
    }


}
