package com.gojek.consumers;

import com.gojek.service.ParkingLot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileConsumer {
    private final File file;
    private CommandHandler commandHandler;

    public FileConsumer(File file, ParkingLot parkingLot) {
        this.file = file;
        this.commandHandler = new CommandHandler(parkingLot);
    }

    public void execute() {
        final String[] commands = getCommands();
        for (String command : commands) {
            final String[] cmd = command.split(" ");
            commandHandler.handleCommand(cmd[0], cmd);
        }
    }

    public String[] getCommands() {
        String[] commands = new String[getLineCount()];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            for (int i = 0; (currentLine = bufferedReader.readLine()) != null; i++) {
                commands[i] = currentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }

    private int getLineCount() {
        int lineCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

}
