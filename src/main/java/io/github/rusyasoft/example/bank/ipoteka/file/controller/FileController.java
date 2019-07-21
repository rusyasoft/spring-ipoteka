package io.github.rusyasoft.example.bank.ipoteka.file.controller;

import io.github.rusyasoft.example.bank.ipoteka.file.model.FileUploadStatusResponse;
import io.github.rusyasoft.example.bank.ipoteka.file.service.CsvFileReaderService;
import io.github.rusyasoft.example.bank.ipoteka.file.service.CsvParser;
import io.github.rusyasoft.example.bank.ipoteka.file.service.FileStorageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Api(value="storage", description = "csv file upload")
@RequestMapping("/input/")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    CsvFileReaderService csvFileReaderService;

    @Autowired
    CsvParser csvParser;

    @RequestMapping(value="/multipleFilesUpload" ,
            headers=("content-type=multipart/form-data"),
            method= RequestMethod.POST)
    public List<FileUploadStatusResponse> multipleFilesUpload(
            @RequestParam(value="file") MultipartFile[] files
    ) throws IOException {
        return fileStorageService.storeFiles(files);
    }

    @RequestMapping(value="/fileUpload" ,
            method= RequestMethod.POST)
    public String fileUpload(
            @RequestParam(value="file") MultipartFile file
    ) throws IOException {
        System.out.println("here");
        return "yeap";
    }

    @GetMapping("/parse")
    public Boolean parseThem() {
        String totalStr = csvFileReaderService.readResourceFile("123.csv");

        return csvParser.parseCsvAndStore(totalStr);
    }

}