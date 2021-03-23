package com.vladimir.crud.blog.view.commands.commandFactory;

import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.UnknownCommandException;

public interface CommandFactory {
    Command createCommand() throws UnknownCommandException;
}
