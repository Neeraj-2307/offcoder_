package com.example.offcodercyberquest.environments;

import com.example.offcodercyberquest.Beans.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PythonEnvironment extends Environment {
    String root = "C:\\OffCoder\\temp\\";
    String baseRunCommand = "python";

    ProcessBuilder processBuilder;

    @Override
    public String compile(File file) throws InterruptedException, IOException {
        return "";
    }

    //TODO
    @Override
    public String run(File codeFile, TestCase testcases, long milliseconds) throws IOException, InterruptedException, TimeLimitExceededException {

        File infile = setUpInput(root, testcases);
        List<String> command = Arrays.asList(
                baseRunCommand,
                codeFile.getName()

        );
        Logger.getGlobal().log(Level.INFO, "Executing " + command);
        Process runProcess = this.executeCommand(command, infile);

        boolean ranInTime = runProcess.waitFor(milliseconds, TimeUnit.MILLISECONDS);

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
}
