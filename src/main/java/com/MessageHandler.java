package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MessageHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);
    private PircBotX bot;
    private BlockingQueue<String> messageQueue;

    public MessageHandler(PircBotX bot) {
        this.bot = bot;
        messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    messageQueue.put(line);
                }
            } catch (IOException e) {
                LOG.error("Error reading user input: {}", e.getMessage());
            } catch (InterruptedException e) {
                LOG.error("Interrupted while waiting for message: {}", e.getMessage());
            }
        }
    }

    public void sendMessages() {
        while (true) {
            try {
                String message = messageQueue.take();
                bot.sendMessage("#" + bot.getNick(), message);
            } catch (InterruptedException e) {
                LOG.error("Interrupted while waiting for message: {}", e.getMessage());
            } catch (SocketException e) {
                LOG.error("Error sending message: {}", e.getMessage());
            }
        }
    }
}

