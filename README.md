# Twitch Bot

This code uses the java.net.Socket class to connect to the Twitch IRC (Internet Relay Chat) server and send and receive messages.

To use this code, you will need to replace USERNAME and TOKEN with your Twitch username and token, respectively. You can get your token by following the instructions in the Twitch Developer Portal. You will also need to replace YOUR_CHANNEL_NAME_HERE with the name of the channel you want to join.

 - The main method starts by setting up a connection to the Twitch chat server using a socket. It then creates a BufferedReader and a PrintWriter to read from and write to the socket.

 - The bot authenticates with the server by sending the PASS and NICK commands, followed by the JOIN command to join the specified channel's chat.

 - The bot then enters a loop to read lines from the server and print them to the console. If a line starts with "PING", the bot responds with a "PONG" message.

 - The bot also reads lines from standard input (i.e. the console) and sends them to the server as chat messages.

I'm pretty sure Twitch developers have a version in Python lol

# Credit
[Prime](https://github.com/PrimeTDMomega/) - For overusing IntelliSense
[Evelyn Gosselin](https://github.com/evelyn-gosselin) - Having Skill

