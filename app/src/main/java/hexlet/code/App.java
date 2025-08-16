package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "1.0"
)
public final class App implements Callable<Integer> {
    @Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filepath1"
    )
    private String filePath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filepath2"
    )
    private String filePath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish",
            paramLabel = "format"
    )
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            String formattedDiff = Differ.generate(filePath1, filePath2, format);
            System.out.println(formattedDiff);
            return 0; // Успех
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 1; // Ошибка
        }
    }
}