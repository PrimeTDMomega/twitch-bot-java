import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final String HOST = "irc.chat.twitch.tv";
    private static final int PORT = 6667;

    public static void main(String[] args) throws IOException {
        // replace TOKEN with your bot's token
        String token = TOKEN;
        // replace USERNAME with your bot's username
        String username = USERNAME;

        Socket socket = new Socket(HOST, PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println("PASS " + token);
        writer.println("NICK " + username);
        writer.println("JOIN #" + username);

        Scanner scanner = new Scanner(System.in);
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("PING")) {
                writer.println("PONG" + line.substring(5));
            } else {
                System.out.println(line);
            }

            if (scanner.hasNextLine()) {
                writer.println(scanner.nextLine());
            }
        }
    }
}
