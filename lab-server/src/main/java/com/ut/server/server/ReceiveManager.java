package com.ut.server.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import com.ut.common.util.Message;

@Slf4j
public class ReceiveManager {
    private final int defaultBufferSize = 256;
    private final int defaultSleepTime = 500;
    private DatagramChannel channel;
    private SocketAddress client;

    public ReceiveManager(DatagramChannel channel, SocketAddress client) {
        this.channel = channel;
        this.client = client;
    }

    public Serializable deserialize(byte[] data) throws IOException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            Serializable mess = (Serializable) is.readObject();
            in.close();
            is.close();
            return mess;
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // never throws
            return null;
        }
    }

    public Message receiveMessage() throws IOException {
        byte[] bufReceiveSize = new byte[defaultBufferSize];
        ByteBuffer receiveBufferSize = ByteBuffer.wrap(bufReceiveSize);
        client = channel.receive(receiveBufferSize);
        Message mess;
        if (Objects.nonNull(client)) {
            Serializable receiveMess = deserialize(bufReceiveSize);
            if (receiveMess.getClass().equals(Integer.class)) {
                int size = (int) receiveMess;
                try {
                    Thread.sleep(defaultSleepTime);
                } catch (InterruptedException e) {
                    return null;
                }
                byte[] bufr = new byte[size];
                ByteBuffer receiveBuffer = ByteBuffer.wrap(bufr);
                channel.receive(receiveBuffer);
                receiveMess = deserialize(bufr);
                mess = (Message) receiveMess;
                log.info("Received message: " + "\n-----------------------\n" + mess.getCommand() + "\n-----------------------");
            } else {
                mess = (Message) receiveMess;
            }
            return mess;
        } else {
            return null;
        }
    }

    public SocketAddress getClient() {
        return client;
    }
}
