package com.vladimir.crud.blog.view.viewFactory;

import com.vladimir.crud.blog.view.RegionView;
import com.vladimir.crud.blog.view.View;

public class RegionViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new RegionView();
    }
}
