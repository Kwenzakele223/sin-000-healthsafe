package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CsvReaderTest {

    @Test
    void shouldReadCsvFile() throws Exception
    {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("wards-outdated.csv");

        assertFalse(inputStream == null);

        CsvReader reader = new CsvReader();

        List<String> lines = reader.readLines("wards-outdated.csv");

        assertFalse(lines.isEmpty());
    }
}