package com.vladimir.crud.blog.view;

public interface View {
    void create();
    void readAll();
    void read(Long id);
    void update(Long id);
    void delete(Long id);
}
