package com.example.offcodercyberquest.Beans;

public class TestCase {
    private  String input, expectedOutput;

    public TestCase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                '}';
    }
}
