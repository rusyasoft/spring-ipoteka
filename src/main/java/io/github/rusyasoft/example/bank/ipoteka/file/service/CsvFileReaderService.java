package io.github.rusyasoft.example.bank.ipoteka.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class CsvFileReaderService {

    @Value("${java.io.tmpdir}")
    public String tmpDirPath;

    String readFile(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            String data = readFromInputStream(inputStream);
            log.info("data: " + data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String readResourceFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        try {
            String data = readFromInputStream(inputStream);
            System.out.println("data: " + data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            // it was good experience. found from -> https://stackoverflow.com/questions/17405165/first-character-of-the-reading-from-the-text-file-%C3%AF
            br.mark(1);
            if (br.read() != 0xFEFF)
                br.reset();
            /////////////////

            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
