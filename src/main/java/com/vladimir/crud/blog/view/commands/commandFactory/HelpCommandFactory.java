package com.vladimir.crud.blog.view.commands.commandFactory;

import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.HelpCommand;

public class HelpCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new HelpCommand();
    }
}
