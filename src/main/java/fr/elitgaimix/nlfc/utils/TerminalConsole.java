package fr.elitgaimix.nlfc.utils;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class TerminalConsole implements AutoCloseable {

    private ProcessHandle process;
    private PrintStream stream;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    public PrintStream open() throws IOException {
        if (stream != null)
            return stream;

        Path log = Files.createTempFile("nlfc-", ".log");

        // Échapper les apostrophes pour PowerShell.
        String path = log.toAbsolutePath().toString().replace("'", "''");

        String command =
                "$Host.UI.RawUI.WindowTitle = 'NLFC - Logs'; " +
                        "Get-Content -LiteralPath '" + path + "' -Encoding UTF8 -Wait";

        String encoded = Base64.getEncoder().encodeToString(
                command.getBytes(StandardCharsets.UTF_16LE)
        );

        Process launcher = new ProcessBuilder(
                "powershell.exe", "-NoProfile", "-Command",
                "$ErrorActionPreference = 'Stop'; " +
                        "$terminal = Start-Process powershell.exe -PassThru -ArgumentList " +
                        "'-NoProfile', '-NoExit', '-EncodedCommand', '" + encoded + "'; $terminal.Id"
        ).redirectErrorStream(true).start();

        String output;
        try (var input = launcher.getInputStream()) {
            output = new String(input.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
        try {
            long pid = Long.parseLong(output);
            process = ProcessHandle.of(pid).orElseThrow(
                    () -> new IOException("Terminal closed before initialization completed."));
        } catch (NumberFormatException e) {
            throw new IOException("Could not start terminal: " + output, e);
        }

        try {
            stream = new PrintStream(
                    Files.newOutputStream(log, StandardOpenOption.APPEND),
                    true,
                    StandardCharsets.UTF_8
            );
            return stream;
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    @Override
    public void close() {
        if (stream != null) {
            if (System.out == stream)
                System.setOut(originalOut);
            if (System.err == stream)
                System.setErr(originalErr);
            stream.close();
            stream = null;
        }
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
