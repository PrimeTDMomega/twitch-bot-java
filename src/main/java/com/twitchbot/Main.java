package com.twitchbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.twitchbot.command.CommandHandler;
import com.twitchbot.command.GitHubCommand;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            String token = prop.getProperty("token");
            String username = prop.getProperty("username");
            String host = prop.getProperty("host");
            int port = Integer.parseInt(prop.getProperty("port"));
            String prefix = prop.getProperty("prefix");
            if(token == null || token.isEmpty() || username == null || username.isEmpty() || host == null || host.isEmpty() || port <= 0 || prefix == null || prefix.isEmpty()){
              LOG.error("config.properties file is missing required fields");
              return;
            }
            PircBotX bot = new PircBotX();
            bot.setName(username);
            bot.setServer(host, port);
            bot.setLogin(username);
            bot.setAutoNickChange(true);
            bot.setVerbose(true);
            AtomicBoolean reconnect = new AtomicBoolean(true);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            CommandHandler commandHandler = new CommandHandler(bot, prefix);
            commandHandler.addCommand(new GitHubCommand());
            executor.submit(commandHandler);

            while (reconnect.get()) {
                try {
                    bot.connect();
                    bot.identify(token);
                    bot.joinChannel("#" + username);
                    reconnect.set(false);
                } catch (NickAlreadyInUseException e) {
                    LOG.error("Nickname already in use. Retrying with different nickname.");
                    bot.changeNick(username + "_");
} catch (IOException | IrcException e) {
LOG.error("Error connecting to Twitch chat server: {}", e.getMessage());
LOG.info("Retrying connection in 30 seconds...");
try {
Thread.sleep(30000);
} catch (InterruptedException e1) {
LOG.error("Thread sleep interrupted: {}", e1.getMessage());
}
}
}
} catch (IOException ex) {
LOG.error("Error reading config.properties file: {}", ex.getMessage());
} finally {
if (input != null) {
try {
input.close();
} catch (IOException e) {
LOG.error("Error closing config.properties file: {}", e.getMessage());
}
}
executor.shutdown();
}
}
}
