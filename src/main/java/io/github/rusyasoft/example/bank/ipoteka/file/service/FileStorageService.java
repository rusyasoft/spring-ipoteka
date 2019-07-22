package io.github.rusyasoft.example.bank.ipoteka.file.service;

import io.github.rusyasoft.example.bank.ipoteka.file.model.FileUploadStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileStorageService {

    @Value("${java.io.tmpdir}")
    public String tmpDirPath;

    @Autowired
    private CsvFileReaderService csvFileReaderService;

    @Autowired
    private CsvParser csvParser;

    public List<FileUploadStatusResponse> uploadMultipleFiles(MultipartFile[] multipartFiles) {

        return Arrays.asList(multipartFiles)
                .stream()
                .map(file -> uploadSingleFile(file))
                .flatMap(file -> file.stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<FileUploadStatusResponse> uploadSingleFile(MultipartFile multipartFile) {
        List<FileUploadStatusResponse> fileStatusResponseList = new ArrayList<>();
        String fileName = multipartFile.getOriginalFilename();

        boolean isParsed = false;
        try {
            File convertedFile = convertMultiPartToFile(multipartFile);

            String rawCsvStr = csvFileReaderService.readFile(convertedFile);

            isParsed = csvParser.parseCsvAndStore(rawCsvStr);

            deleteFile(convertedFile);

        } catch (IOException e) {
            log.error("Convertion of Multipart file has failed: " + e.getMessage());
        }

        fileStatusResponseList.add(
                new FileUploadStatusResponse(
                        fileName
                        , isParsed ? "SUCCESS" : "FAILED"
                )
        );

        return fileStatusResponseList;
    }

    public File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(tmpDirPath + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }

    private void deleteFile(File file) {
        if (!Objects.isNull(file))
            if (file.delete() == false)
                log.error("Failed to delete temporary created file: " + file.getName());
    }


}
