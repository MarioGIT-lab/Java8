package Rocxoiu_Mario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayFilterAppUnitTest {

    private static final Path INPUT_FILE = Path.of("test_input.txt");
    private static final Path OUTPUT_FILE = Path.of("test_output.txt");

    @BeforeEach
    void setUp() throws IOException {
        List<String> lines = List.of(
                "Badea,Ovidiu,1992-05-10",
                "Marius,Andrei,1993-06-21",
                "Popescu,Catalin,1994-05-15",
                "Zamfir,Florin,1990-05-01",
                "Invalid,Entry,not-a-date"
        );
        Files.write(INPUT_FILE, lines);
    }

    @Test
    void testFilterByMonth() throws IOException {
        BirthdayFilterApp.main(new String[]{INPUT_FILE.toString(), "5", OUTPUT_FILE.toString()});
        List<String> output = Files.readAllLines(OUTPUT_FILE);

        List<String> expected = List.of(
                "Badea,Ovidiu",
                "Popescu,Catalin",
                "Zamfir,Florin"
        );

        assertEquals(expected, output);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(INPUT_FILE);
        Files.deleteIfExists(OUTPUT_FILE);
    }
}
