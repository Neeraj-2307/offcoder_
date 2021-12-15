package com.example.offcodercyberquest.environments;

import com.example.offcodercyberquest.Beans.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CppEnvironment extends Environment {

    private final String root = "C:\\OffCoder\\temp\\";
    private final String baseCompileCommand = "g++.exe -std=gnu++17 %s.cpp -o %s.exe";
    private final String baseRunCommand = ".\\OffCoder\\temp\\";
    Runtime runtime;

    public CppEnvironment() {
        runtime = Runtime.getRuntime();
    }

    @Override
    public String compile(File codeFile) throws IOException {
        List<String> compileCommand = Arrays.asList(
                "g++",
                "-std=gnu++17",
                codeFile.getName(),
                "-o",
                getBase(codeFile) + ".exe"
        );

        Logger.getGlobal().log(Level.INFO, "Executing " + compileCommand);

        Process compileProcess = executeCommand(compileCommand);

        String errorMessage = getErrorMessage(compileProcess);
        String output = getOutput(compileProcess);

        if(errorMessage.isEmpty()){
            if(output.isEmpty()){
                return "COMPILED SUCCESSFULLY";
            }
            return output;
        }
        return errorMessage;
    }

    @Override
    public String run(File codeFile, TestCase testcases, long milliseconds) throws IOException, InterruptedException, TimeLimitExceededException {
        String op = compile(codeFile);
        if(!op.equals("COMPILED SUCCESSFULLY"))
            return op;

        File inputFile = setUpInput(root,testcases);

        List<String> command = Arrays.asList(
                "./" + getBase(codeFile) + ".exe"
        );


        Logger.getGlobal().log(Level.INFO, "Executing " + (command));
        Process runProcess = this.executeCommand(command, inputFile);

        boolean ranInTime = runProcess.waitFor(milliseconds, TimeUnit.SECONDS);

        if(ranInTime){
            String errorMessage = getErrorMessage(runProcess);
            String output = getOutput(runProcess);
            if(errorMessage.isEmpty()){
                if(output.isEmpty()){
                    return "RAN SUCCESSFULLY\n\n";
                }
                return output;
            }
            return errorMessage;
        }else{
            throw new TimeLimitExceededException("Took more than 10 sec to run");
        }
    }

    private String getBase(File file) {
        return getBase(file.getName());
    }

    private String getBase(String fileName) {
        return fileName.split("\\.")[0];
    }

}
