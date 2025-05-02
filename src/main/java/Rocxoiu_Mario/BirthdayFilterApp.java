package Rocxoiu_Mario;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BirthdayFilterApp {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java BirthdayFilterApp <inputFile> <targetMonth> <outputFile>");
            return;
        }

        String inputFile = args[0];
        int targetMonth = Integer.parseInt(args[1]);
        String outputFile = args[2];

        List<String> inputLines = Files.readAllLines(Path.of(inputFile));
        List<String> outputLines = filterAndSortPeople(inputLines, targetMonth);
        Files.write(Path.of(outputFile), outputLines);
    }

    public static List<String> filterAndSortPeople(List<String> lines, int targetMonth) {
        return lines.stream()
                .map(BirthdayFilterApp::parseLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(person -> person.getDateOfBirth().getMonthValue() == targetMonth)
                .sorted(Comparator.comparing(Person::getFirstName)
                        .thenComparing(Person::getLastName))
                .map(person -> person.getFirstName() + "," + person.getLastName())
                .collect(Collectors.toList());
    }

    private static Optional<Person> parseLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 3) return Optional.empty();
            String firstName = parts[0].trim();
            String lastName = parts[1].trim();
            LocalDate dob = LocalDate.parse(parts[2].trim(), FORMATTER);
            return Optional.of(new Person(firstName, lastName, dob));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
