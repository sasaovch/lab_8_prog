package com.ut.common.util;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class JTextAreaWriter extends PrintStream {
    private JTextArea jTextArea;

    public JTextAreaWriter(OutputStream out, JTextArea jTextArea) {
        super(out);
        this.jTextArea = jTextArea;
    }

    @Override
    public void println(String str) {
        jTextArea.setText(jTextArea.getText() + str + "\n");
    }
}
