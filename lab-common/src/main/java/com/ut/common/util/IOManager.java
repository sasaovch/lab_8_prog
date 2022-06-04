package com.ut.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Stack;

public class IOManager {
    private BufferedReader reader;
    private PrintStream writer;
    private String prompter;
    private boolean fileMode = false;
    private final Stack<BufferedReader> previosReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public IOManager() {
        this(System.in, System.out, "$");
    }

    public IOManager(InputStream input, PrintStream output, String promter) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.writer = output;
        this.prompter = promter;
    }

    public  void setBufferReader(BufferedReader buf) {
        reader = buf;
    }

    public  void setPrintWriter(PrintStream wr) {
        writer = wr;
    }

    public  BufferedReader getBufferedReader() {
        return reader;
    }

    public  PrintStream getPrintWriter() {
        return writer;
    }

    public void turnOnFileMode(String filename) {
        try {
            File file = new File(filename);
            if (file.exists() && !currentFiles.contains(file)) {
                    BufferedReader newReader = new BufferedReader(new FileReader(file));
                    println("Started to execute script: " + file.getName());
                    println("------------------------------------------");
                    currentFiles.push(file);
                    previosReaders.push(getBufferedReader());
                    setBufferReader(newReader);
                    fileMode = true;
            } else if (!file.exists()) {
                    printerr("File doesn't exist.");
            } else if (currentFiles.contains(file)) {
                    printerr("The file was not executed due to recursion.");
                    turnOffFileMode();
                }
        } catch (FileNotFoundException e) {
            printerr("Invalid file access rights.");
        }
    }

    public void turnOffFileMode() {
        File file = currentFiles.pop();
        setBufferReader(previosReaders.pop());
        if (currentFiles.isEmpty()) {
            fileMode = false;
        }
        println("------------------------------------------");
        println("Finished to execute script: " + file.getName());
    }

    public boolean getFileMode() {
        return fileMode;
    }

    public  String readLine() throws IOException {
        return reader.readLine();
    }

    public String readPassword() throws IOException {
        return new String(System.console().readPassword());
    }

    public String readfile(File file) throws FileNotFoundException, IOException {
        StringBuilder strData = new StringBuilder();
        String line;
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                while ((line = bufferedReader.readLine()) != null) {
                    strData.append(line);
                }
            }
        }
        return strData.toString();
    }

    public  void close() throws IOException {
        reader.close();
        writer.close();
    }

    public void prompt() {
            writer.print(prompter);
            writer.flush();
    }

    public  void print(Object o) {
            writer.print(o.toString());
            writer.flush();
    }

    public  void println(Object o) {
            writer.println(o.toString());
            writer.flush();
    }

    public  void printerr(Object o) {
            writer.print("Error: ");
            writer.println(o.toString());
            writer.flush();
    }
}
