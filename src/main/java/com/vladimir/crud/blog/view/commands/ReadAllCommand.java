package com.vladimir.crud.blog.view.commands;

import com.vladimir.crud.blog.view.View;
import com.vladimir.crud.blog.view.viewFactory.ViewFactoryCreator;

public class ReadAllCommand implements Command{
    private final View view;

    public ReadAllCommand(String type) throws UnknownCommandException {
        this.view = new ViewFactoryCreator().createViewFactoryByName(type).createView();
    }

    @Override
    public void execute() {
        view.readAll();
    }
}
