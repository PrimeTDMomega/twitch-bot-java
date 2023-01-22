## Note - This is not a file you will actually use, I'll format it into a Twitch Bot you can use in a bit. Also SHOUTOUT to EvelynGosselin for helping with the original Java code !

## Overview

This is a Twitch chat bot that connects to a Twitch channel and responds to messages sent by users. The bot is built using the PircBotX library, which provides a more robust and feature-rich API for creating a Twitch bot.

## Getting Started

### Prerequisites

- A Twitch account with a bot account created and OAuth token obtained
- Java 8 or later
- Maven (for building and managing dependencies)

### Installation

1. Clone the repository to your local machine: `git clone https://github.com/<your_username>/TwitchChatBot.git`
2. Navigate to the project directory: `cd TwitchChatBot`
3. Replace `TOKEN` and `USERNAME` in the `Main.java` file with your bot's token and username, respectively
4. Build the project using Maven: `mvn clean install`
5. Run the project: `java -jar target/TwitchChatBot-1.0-SNAPSHOT.jar`

### Usage

Once the bot is running, it will connect to the Twitch channel specified in the `Main.java` file and start listening for messages. To send a message through the bot, simply type the message in the console and press enter.

## Dependencies

- PircBotX 3.8.1: A powerful and flexible Java-based IRC library that provides a rich set of features for building a Twitch chat bot
- SLF4J 1.7.30: A logging facade for Java that allows for the use of various logging frameworks

## How it works

The `Main` class starts by creating an instance of the `PircBotX` class and configuring it with the bot's username, server, and token. It then creates an `ExecutorService` to handle the message handling in a separate thread and joins the specified channel.

The `MessageHandler` class implements the `Runnable` interface and runs in a separate thread. It uses a `BufferedReader` to read messages from the console and sends them to the Twitch channel using the `bot.sendMessage()` method.

The `AtomicBoolean` reconnect variable is used to check if the bot should reconnect and the if statement in the while loop makes sure that the bot keeps trying to reconnect if the connection is lost.

In case of a `NickAlreadyIn UseException` the bot will try again with a different nickname, and in case of other IO or Irc exceptions it will wait for 30 seconds before trying to reconnect again.

# Credit
[Prime](https://github.com/PrimeTDMomega/) - For overusing IntelliSense
<br>
[Evelyn Gosselin](https://github.com/evelyn-gosselin) - Having Skill

