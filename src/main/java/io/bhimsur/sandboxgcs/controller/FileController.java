package io.bhimsur.sandboxgcs.controller;

import io.bhimsur.sandboxgcs.dto.FileDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<FileDto>> listOfFile();

    @GetMapping("/{fileName}")
    @ResponseBody
    ResponseEntity<Void> downloadFile(@PathVariable(value = "fileName") String fileName, HttpServletResponse httpServletResponse) throws IOException;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file) throws IOException;

    @DeleteMapping
    @ResponseBody
    ResponseEntity<Void> deleteFile(@RequestParam("fileName") String fileName);
}
