package com.ut.gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JTextArea;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.client.Console;
import com.ut.common.util.IOManager;
import com.ut.common.util.JTextAreaWriter;

public class ExecutorScriptService {
    private ConnectionAndExecutorManager caeManager;
    private File file;
    private JTextArea jTextArea;
    public ExecutorScriptService(ConnectionAndExecutorManager caeManager, File file, JTextArea jTextArea) {
        this.caeManager = caeManager;
        this.file = file;
        this.jTextArea = jTextArea;
    }

    public void executeScript() {
        PrintStream jTextAreaWriter = new JTextAreaWriter(System.out, jTextArea);
        IOManager ioManager = new IOManager(System.in, jTextAreaWriter, "");
        ioManager.turnOnFileMode(file.getAbsolutePath());
        Console console = new Console(ioManager, caeManager.getReceiveManager(), caeManager.getSendManager(), caeManager.getMessage());
        try {
            console.executeScript();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
