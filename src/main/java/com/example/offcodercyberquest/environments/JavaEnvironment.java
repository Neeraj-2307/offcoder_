package com.example.offcodercyberquest.environments;

import com.example.offcodercyberquest.Beans.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaEnvironment extends Environment {
    private final String root = "C:\\OffCoder\\temp\\";
    private final String baseCompileCommand = "javac";
    private final String baseRunCommand = "java";

    @Override
    public String compile(File codeFile) throws IOException {

        List<String> compileCommand = Arrays.asList(
                baseCompileCommand ,
                root + codeFile.getName());
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
    public String run(File codeFile, TestCase testcase, long milliseconds) throws IOException, TimeLimitExceededException, InterruptedException {
        String op = compile(codeFile);
        if(!op.equals("COMPILED SUCCESSFULLY"))
            return op;

        File inputFile = setUpInput(root, testcase);

        List<String> command = Arrays.asList(
                baseRunCommand,
                getMainClass(codeFile)
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

    private String getMainClass(File codeFile) throws FileNotFoundException {
        //TODO logic for searching class-name with main method;
        return codeFile.getName().split("\\.")[0];
    }
}
