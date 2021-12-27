package com.example.offcodercyberquest.utils;

import com.example.offcodercyberquest.Beans.Code;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeFileHandler {
    private final String root = "C:\\OffCoder\\";
    private final String baseFileName = "Codeforce";
    private Code code;

    public CodeFileHandler(Code code) {
        this.code = code;
    }

    // return file with appropriate extension and with code in it.
    public File createFile() throws IOException {
        String codeFileName = baseFileName + getLangExtension();
        File parent = getRootFile();
        File codeFile = new File(parent, codeFileName);
        codeFile.createNewFile();
        writeCode(codeFile);
        return codeFile;
    }

    // writes code to the created file
    private boolean writeCode(File codeFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile));
            writer.write(code.getCode());
            writer.flush();

            Logger.getGlobal().log(Level.INFO, "Code was written to file", code);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // creates 'temp' name directory in root folder.
    private File getRootFile() {
        File parent = new File(root + "temp");
        parent.mkdirs();
        return parent;
    }

    // returns appropriate extension based on the language of Code.
    private String getLangExtension() {
        switch (code.getLanguage()) {
            case CPP -> {
                return ".cpp";
            }
            case JAVA -> {
                return ".java";
            }
            case PYTHON -> {
                return ".py";
            }
            case JAVASCRIPT -> {
                return ".js";
            }
            default -> {
                System.out.println("No Extension");
                return "NO_EXTENSION";
            }
        }
    }
}
