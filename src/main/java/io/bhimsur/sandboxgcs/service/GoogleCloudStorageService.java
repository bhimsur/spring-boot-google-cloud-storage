package io.bhimsur.sandboxgcs.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import io.bhimsur.sandboxgcs.dto.FileDto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service(GoogleCloudStorageService.SERVICE_NAME)
public class GoogleCloudStorageService implements FileService {
    public static final String SERVICE_NAME = "gcsFileService";
    @Autowired
    private Storage storage;

    @Value("${gcs.bucket.name}")
    private String bucketName;

    @Override
    public FileDto uploadFile(MultipartFile file) throws IOException {
        if (null == file) {
            throw new RuntimeException();
        }
        BlobId id = BlobId.of(bucketName, Objects.toString(file.getOriginalFilename(), ""));
        BlobInfo blob = BlobInfo.newBuilder(id).setContentType(file.getContentType()).build();
        Blob result = storage.create(blob, file.getBytes());
        return new FileDto(result.getName(), result.getMediaLink());
    }

    @Override
    public List<FileDto> listOfFile() {
        ArrayList<FileDto> result = new ArrayList<>();
        Page<Blob> list = storage.list(bucketName);
        for (Blob blob : list.iterateAll()) {
            URL url = blob.signUrl(15, TimeUnit.MINUTES);
            result.add(new FileDto(blob.getName(), url.toString()));
        }
        return result;
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse httpServletResponse) throws IOException {
        Blob blob = storage.get(bucketName, fileName);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(blob.getContent());
        httpServletResponse.setContentType(blob.getContentType());
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + blob.getName());
        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        inputStream.close();
        httpServletResponse.flushBuffer();
    }

    @Override
    public void deleteFile(String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        blob.delete();
    }
}
