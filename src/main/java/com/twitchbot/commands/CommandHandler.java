package com.twitchbot.command;

import java.util.HashMap;
import java.util.Map;

import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CommandHandler.class);
    private Map<String, Command> commandMap;
    private PircBotX bot;

    public CommandHandler(PircBotX bot) {
        this.bot = bot;
        this.commandMap = new HashMap<>();
    }

    public void registerCommand(Command command) {
        commandMap.put(command.getCommand(), command);
    }

    public void handleCommand(String channel, String sender, String command, String[] args) {
        if (!commandMap.containsKey(command)) {
            LOG.warn("Unrecognized command: {}", command);
            return;
        }
        commandMap.get(command).execute(bot, channel, sender, args);
    }
}

