package io.github.rusyasoft.example.bank.ipoteka.file.controller;

import io.github.rusyasoft.example.bank.ipoteka.file.model.FileUploadStatusResponse;
import io.github.rusyasoft.example.bank.ipoteka.file.service.CsvFileReaderService;
import io.github.rusyasoft.example.bank.ipoteka.file.service.CsvParser;
import io.github.rusyasoft.example.bank.ipoteka.file.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Api(value = "storage", description = "데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API")
@RequestMapping("/input/")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    CsvFileReaderService csvFileReaderService;

    @Autowired
    CsvParser csvParser;

    @RequestMapping(value = "/multipleFilesUpload", headers = ("content-type=multipart/form-data"), method = RequestMethod.POST)
    @ApiOperation(value = "데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API (upload multiple files)", response = List.class)
    public List<FileUploadStatusResponse> multipleFilesUpload(
            @RequestParam(value = "file") MultipartFile[] files) throws IOException {
        return fileStorageService.uploadMultipleFiles(files);
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ApiOperation(value = "데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API", response = String.class)
    public List<FileUploadStatusResponse> fileUpload(
            @RequestParam(value = "file") MultipartFile file) throws IOException {
        return fileStorageService.uploadSingleFile(file);
    }

    @GetMapping("/parse")
    @ApiOperation(value = "Resource 에 있는 예지즐 업로드 와 parsing 하기 (upload file from resource folder)", response = Boolean.class)
    public Boolean parseThem() {
        String totalStr = csvFileReaderService.readResourceFile("123.csv");
        return csvParser.parseCsvAndStore(totalStr);
    }

}