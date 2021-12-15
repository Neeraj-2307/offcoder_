package com.example.offcodercyberquest.environments;

import com.example.offcodercyberquest.Beans.TestCase;

import java.io.*;
import java.util.List;

public abstract class Environment {

    // decided root path for temporary files
    private final String root = "C:\\OffCoder\\temp\\";

    // must implement these to
    public abstract String compile(File file) throws InterruptedException, IOException;
    public abstract String run(File file, TestCase testCase, long milliseconds) throws IOException, InterruptedException, TimeLimitExceededException;



    /*
        Return error message from the error stream of the passed process.
    */
    protected String getErrorMessage(Process process) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    // Returns output from the input stream of the passed process.
    protected String getOutput(Process process) throws IOException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }


    // creates and sets up a 'input.txt' in the 'temp' folder.
    protected File setUpInput(String path, TestCase testCase) throws IOException {
        File inp = new File(path, "input.txt");

        inp.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(inp));
        writer.write(testCase.getInput());
        writer.flush();

        return inp;
    }

    // returns a Process object by passed command list.
    protected Process executeCommand(List<String> command, File infile) throws IOException {
        return new ProcessBuilder(command).redirectInput(infile)
                .directory(new File(root))
                .redirectErrorStream(true)
                .start();
    }
    protected Process executeCommand(List<String> command) throws IOException {
        return new ProcessBuilder(command)
                .directory(new File(root))
                .redirectErrorStream(true)
                .start();
    }

}
