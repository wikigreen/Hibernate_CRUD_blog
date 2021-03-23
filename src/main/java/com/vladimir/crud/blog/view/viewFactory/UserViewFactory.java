package com.vladimir.crud.blog.view.viewFactory;

import com.vladimir.crud.blog.view.UserView;
import com.vladimir.crud.blog.view.View;

public class UserViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new UserView();
    }
}
