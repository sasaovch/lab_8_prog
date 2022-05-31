package com.ut.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.ut.common.util.Message;

public class SendManager {
    private final InetAddress address;
    private final DatagramSocket socket;
    private final Integer port;
    private byte[] buff;
    private byte[] buffsize;
    private DatagramPacket outPacket;
    private final int defaultSleepTime = 500;

    public SendManager(InetAddress address, DatagramSocket socket, Integer port) {
        this.address = address;
        this.socket = socket;
        this.port = port;
    }

    public void sendMessage(Message mess) throws IOException {
        buff = serialize(mess);
        outPacket = new DatagramPacket(buff, buff.length, address, port);
        buffsize = serialize(buff.length);
        socket.send(new DatagramPacket(serialize(buff.length), buffsize.length, address, port)); //81
        socket.send(outPacket);
        try {
            Thread.sleep(defaultSleepTime);
        } catch (InterruptedException e) {
            return;
        }
    }

    public byte[] serialize(Serializable mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }
}
