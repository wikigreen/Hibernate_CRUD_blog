package com.vladimir.crud.blog.view.commands;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String error){
        super(error);
    }
}
