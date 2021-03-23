package com.vladimir.crud.blog.view;

import com.vladimir.crud.blog.service.hibernate.HibernateConnection;
import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.UnknownCommandException;
import com.vladimir.crud.blog.view.commands.commandFactory.CommandFactoryCreator;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;

public class
MainMenu {
    private boolean isRunning = true;
    private static final MainMenu MAIN_MENU = new MainMenu();
    private final Scanner SCANNER = new Scanner(System.in);

    private MainMenu(){}

    public static MainMenu getInstance(){
        return MAIN_MENU;
    }

    public void run(){
//        try {
//            System.setErr(new PrintStream("src/main/resources/hibernateLogs.txt"));
//        } catch (FileNotFoundException e) {
//            System.out.println("Something wrong with logs...");
//        }
        System.out.println("Type 'help' to see all commands.");
        while(isRunning){
            System.out.print("Type command:");
            executeCommand();
            System.out.println();
        }
        System.out.println("See you!!!");
    }

    private void executeCommand() {
        String stringCommand = SCANNER.nextLine().trim();
        if("".equals(stringCommand))
            return;
        try {
            Command command = new CommandFactoryCreator()
                    .createCommandFactory(stringCommand)
                    .createCommand();
            command.execute();
        } catch (UnknownCommandException e) {
            System.out.println(e.getMessage());
        }
    }      

    public void turnOff() {
        isRunning = false;
        HibernateConnection.getInstance().close();
    }
}
