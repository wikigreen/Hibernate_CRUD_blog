package com.vladimir.crud.blog.view.commands.commandFactory;

import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.ExitCommand;

public class ExitCommandFactory implements CommandFactory{
    public ExitCommandFactory() {
    }

    @Override
    public Command createCommand() {
        return new ExitCommand();
    }
}
