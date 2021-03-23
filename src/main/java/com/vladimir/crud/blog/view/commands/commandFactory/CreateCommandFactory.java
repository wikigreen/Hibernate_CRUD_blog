package com.vladimir.crud.blog.view.commands.commandFactory;

import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.CreateCommand;
import com.vladimir.crud.blog.view.commands.UnknownCommandException;

public class CreateCommandFactory implements CommandFactory {
    private final String type;

    public CreateCommandFactory(String type) {
        this.type = type;
    }

    @Override
    public Command createCommand() throws UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'create' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'create' region");
        return new CreateCommand(type);
    }
}
