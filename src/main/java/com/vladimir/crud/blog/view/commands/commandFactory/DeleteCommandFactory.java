package com.vladimir.crud.blog.view.commands.commandFactory;

import com.vladimir.crud.blog.view.commands.Command;
import com.vladimir.crud.blog.view.commands.DeleteCommand;
import com.vladimir.crud.blog.view.commands.UnknownCommandException;

public class DeleteCommandFactory implements CommandFactory {
    private final String type;
    private final String id;

    public DeleteCommandFactory(String type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Command createCommand() throws UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'delete' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'delete region 1'");
        if(id == null)
            throw new UnknownCommandException("After command 'delete " + type + "' should be an ID" + "\n"
                    + "For example: 'delete " + type + " 1'");
        return new DeleteCommand(type, id);
    }
}
