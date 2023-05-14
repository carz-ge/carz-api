package ge.carapp.carappapi.repository.filestorage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public interface FileStorage {

    public void uploadFileStream(@NotEmpty String filePath,
                                 @NotNull String contentType,
                                 @Positive long size,
                                 @NotNull InputStream inputStream,
                                 Map<String, String> additionalMetadata) throws IOException;

    void deleteFile(@NotEmpty String fileName);

}

