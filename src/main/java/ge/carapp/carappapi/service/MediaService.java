package ge.carapp.carappapi.service;

import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.filestorage.S3FileStorage;
import ge.carapp.carappapi.schema.MediaFileSchema;
import ge.carapp.carappapi.repository.filestorage.FileStorage;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MediaService {
    private final FileStorage fileStorage;
    private final String cloudfrontDistribution;

    public MediaService(S3Client s3Client,
                        @Qualifier("mediaFilesBucket") String s3MediaBucketName,
                        @Qualifier("cloudfrontDistribution") String cloudfrontDistribution) {
        this.fileStorage = new S3FileStorage(s3MediaBucketName, s3Client);
        this.cloudfrontDistribution = cloudfrontDistribution;
    }


    public MediaFileSchema uploadFile(@NotEmpty MultipartFile inputFile) {
        String originalFilename = inputFile.getOriginalFilename();
        String contentType = inputFile.getContentType(); // TODO Check file type
        String newFileName = UUID.randomUUID() + getFileExtension(originalFilename);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("originalFilename", originalFilename);

        try {
            fileStorage.uploadFileStream(
                    newFileName,
                    contentType,
                    inputFile.getSize(),
                    inputFile.getInputStream(),
                    metadata);

            return new MediaFileSchema(cloudfrontDistribution + "/" + newFileName);
        } catch (IOException e) {
            throw new GeneralException("could not upload file");
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0) {
            return filename.substring(dotIndex);
        }
        return "";
    }

}
