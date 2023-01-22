package com.twitchbot.command;

import org.pircbotx.PircBotX;

public class GitHubCommand implements Command {
    private static final String COMMAND = "github";
    private static final String GITHUB_LINK = "https://github.com/WitheredKnights/";

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public void execute(PircBotX bot, String channel, String sender, String[] args) {
        bot.send().message(channel, GITHUB_LINK);
    }
}
