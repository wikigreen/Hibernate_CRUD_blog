package com.vladimir.crud.blog.view.commands;

import com.vladimir.crud.blog.view.MainMenu;

public class ExitCommand implements Command{
    @Override
    public void execute() {
        MainMenu.getInstance().turnOff();
    }
}
