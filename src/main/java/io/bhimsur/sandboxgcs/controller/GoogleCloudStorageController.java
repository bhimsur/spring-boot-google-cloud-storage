package io.bhimsur.sandboxgcs.controller;

import io.bhimsur.sandboxgcs.dto.FileDto;
import io.bhimsur.sandboxgcs.service.FileService;
import io.bhimsur.sandboxgcs.service.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/gcs")
@RestController
public class GoogleCloudStorageController implements FileController {
    @Autowired
    @Qualifier(GoogleCloudStorageService.SERVICE_NAME)
    private FileService fileService;

    @Override
    public ResponseEntity<List<FileDto>> listOfFile() {
        return new ResponseEntity<>(fileService.listOfFile(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> downloadFile(String fileName, HttpServletResponse httpServletResponse) throws IOException {
        fileService.downloadFile(fileName, httpServletResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FileDto> uploadFile(MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteFile(String fileName) {
        fileService.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
