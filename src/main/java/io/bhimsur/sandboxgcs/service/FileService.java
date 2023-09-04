package io.bhimsur.sandboxgcs.service;

import io.bhimsur.sandboxgcs.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDto uploadFile(MultipartFile file) throws IOException;
    List<FileDto> listOfFile();
    void downloadFile(String fileName, HttpServletResponse httpServletResponse) throws IOException;
    void deleteFile(String fileName);
}
