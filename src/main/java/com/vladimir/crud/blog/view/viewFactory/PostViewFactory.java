package com.vladimir.crud.blog.view.viewFactory;

import com.vladimir.crud.blog.view.PostView;
import com.vladimir.crud.blog.view.View;

public class PostViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new PostView();
    }
}
