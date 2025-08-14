package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "1.0"
)
public class App implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public void run() {}
}