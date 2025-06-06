package org.poo.checker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Checker to verify the coding style
 */
public final class Checkstyle {
    private Checkstyle() {
        //constructor for checkstyle
    }
    /**
     * DO NOT MODIFY
     */
    public static int testCheckstyle() {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar",
                CheckerConstants.CHECKER_PATH + "/checkstyle-8.36.2-all.jar", "-c",
                CheckerConstants.CHECKER_PATH + "/poo_checks.xml", "./");

        processBuilder.redirectErrorStream(true);
        File log = new File("checkstyle.txt");
        processBuilder.redirectOutput(log);

        try {
            Process process = processBuilder.start();
            process.waitFor();

            Path path = Paths.get("checkstyle.txt");
            long lineCount = Files.lines(path).count();

            long errors = 0;
            if (lineCount > 2) {
                errors = lineCount - CheckerConstants.BIG_TEST_POINTS;
            }
            System.out.println("-----------------------------------------------------");
            System.out.println("Checkstyle: "
                    + ((errors <= CheckerConstants.MAXIMUM_ERROR_CHECKSTYLE) ? "Ok" : "Failed"));
            System.out.println("Checkstyle errors: " + errors);
            System.out.println("-----------------------------------------------------");
            System.out
                    .println("Checkstyle score: "
                            + ((errors <= CheckerConstants.MAXIMUM_ERROR_CHECKSTYLE)
                            ? "10/10" : "0/10"));
            System.out
                    .println("-----------------------------------------------------");
            return (errors <= CheckerConstants.MAXIMUM_ERROR_CHECKSTYLE)
                    ? CheckerConstants.CHECKSTYLE_POINTS : 0;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
