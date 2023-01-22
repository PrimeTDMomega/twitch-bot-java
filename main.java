import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String HOST = "irc.chat.twitch.tv";
    private static final int PORT = 6667;

    public static void main(String[] args) {
        // replace TOKEN with your bot's token
        String token = TOKEN;
        // replace USERNAME with your bot's username
        String username = USERNAME;

        PircBotX bot = new PircBotX();
        bot.setName(username);
        bot.setServer(HOST, PORT);
        bot.setLogin(username);
        bot.setAutoNickChange(true);
        bot.setVerbose(true);
        AtomicBoolean reconnect = new AtomicBoolean(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new MessageHandler(bot));

        while (reconnect.get()) {
            try {
                bot.connect();
                bot.identify(token);
                bot.joinChannel("#" + username);
                reconnect.set(false);
            } catch (NickAlreadyInUseException e) {
                LOG.error("Nickname already in use. Retrying with different nickname.");
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
        executor.shutdown();
    }
}

class MessageHandler implements Runnable {
    private PircBotX bot;

    public MessageHandler(PircBotX bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    bot.sendMessage("#" + bot.getNick(), line);
                }
            } catch (IOException e) {
                LOG.error("Error reading user input: {}", e.getMessage());
            } catch (SocketException e) {
                LOG.error("Error sending message                } catch (SocketException e) {
                    LOG.error("Error sending message: {}", e.getMessage());
                }
            }
        }
    }
}

