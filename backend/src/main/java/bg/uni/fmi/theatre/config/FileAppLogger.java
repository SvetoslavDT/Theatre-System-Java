package bg.uni.fmi.theatre.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Profile("prod")
public class FileAppLogger implements AppLogger {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LogLevel configuredLevel;
    private final String logFilePath;

    public FileAppLogger(TheatreProperties theatreProperties) {

        this.configuredLevel = theatreProperties.getLogLevel();
        this.logFilePath = theatreProperties.getLogFile();
        // new File(logFilePath).getParentFile().mkdirs();
    }

    @Override
    public void trace(String message) {
        if (LogLevel.TRACE.isEnabled(configuredLevel)) {
            write("Trace", message);
        }
    }

    @Override
    public void debug(String message) {
        if (LogLevel.DEBUG.isEnabled(configuredLevel)) {
            write("DEBUG", message);
        }
    }

    @Override
    public void info(String message) {
        if (LogLevel.INFO.isEnabled(configuredLevel)) {
            write("INFO", message);
        }
    }

    @Override
    public void error(String message) {
        write("ERROR", message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        write("ERROR", message +  " - " + throwable.getMessage());
    }

    private void write(String level, String message) {
        String line = String.format("[%s] [%s] %s%n", LocalDateTime.now().format(FMT), level, message);
        try (PrintWriter out = new PrintWriter(new FileWriter(logFilePath, true))) {
            out.print(line);
        } catch (IOException e) {
            System.err.println("Log write failed: " + e.getMessage());
        }
    }
}
