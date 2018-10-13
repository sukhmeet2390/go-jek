package com.gojek.consumers;

import com.gojek.service.ParkingLot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TerminalConsumer {
    private CommandHandler commandHandler;

    public TerminalConsumer(ParkingLot parkingLot) {
        this.commandHandler = new CommandHandler(parkingLot);
    }

    public void execute() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = null;
        try {
            while ((command = reader.readLine()) != null) {
                final String[] split = command.split(" ");
                commandHandler.handleCommand(split[0], split);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
