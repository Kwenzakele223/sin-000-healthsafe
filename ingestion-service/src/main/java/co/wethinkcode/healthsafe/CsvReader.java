package co.wethinkcode.healthsafe;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

class CsvReader {

    public List<String> readLines(String filePath) throws IOException {

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
                .lines()
                .toList();
    }
}