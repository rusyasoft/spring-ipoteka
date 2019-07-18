package io.github.rusyasoft.example.bank.ipoteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadStatusResponse {
    private String fileName;
    private String status;
}