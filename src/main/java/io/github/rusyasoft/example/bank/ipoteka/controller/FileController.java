package io.github.rusyasoft.example.bank.ipoteka.controller;

import io.github.rusyasoft.example.bank.ipoteka.model.FileUploadStatusResponse;
import io.github.rusyasoft.example.bank.ipoteka.service.FileStorageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@Api(value="storage", description = "csv file upload")
@RequestMapping("/input/")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

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

}