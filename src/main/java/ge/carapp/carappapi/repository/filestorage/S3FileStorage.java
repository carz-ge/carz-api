package ge.carapp.carappapi.repository.filestorage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class S3FileStorage implements FileStorage {
    private final String s3BucketName;
    private final S3Client s3Client;

    public void uploadFileStream(@NotEmpty String filePath,
                                 @NotNull String contentType,
                                 @Positive long size,
                                 @NotNull InputStream inputStream,
                                 Map<String, String> additionalMetadata) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(this.s3BucketName)
                .key(filePath)
                .contentType(contentType)
                .contentLength(size)
                .metadata(additionalMetadata)
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(inputStream, size);
        try {
            this.s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            log.error("Error during file stream uploading; {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }


    public void deleteFile(@NotEmpty String fileName) {
        this.s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(s3BucketName)
                .key(fileName)
                .build());
    }
}