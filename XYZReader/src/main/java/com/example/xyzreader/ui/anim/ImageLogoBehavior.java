package com.example.xyzreader.ui.anim;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.xyzreader.R;

import static java.lang.Math.abs;

public class ImageLogoBehavior extends CoordinatorLayout.Behavior<ImageView> {
    public ImageLogoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        if (!isConfigSet) {
            isConfigSet = true;
            configXDimen(parent, dependency, child);
            configYDimen(parent, dependency, child);
        }

        child.setX(initImageX - (abs(initMainX - dependency.getY()) * rangeImageX / rangeMainX));
        child.setY(initImageY - (abs(initMainY - dependency.getY()) * rangeImageY / rangeMainY));

        return true;
    }

    private void configXDimen(CoordinatorLayout parent, View main, View child) {
        float minMainX = abs(main.getY());
        float maxMainX = abs(((AppBarLayout)main).getTotalScrollRange());

        float minImageX = parent.getResources().getDimension(R.dimen.appbar_title_left_padding);
        float maxImageX = abs(child.getX());

        rangeMainX = abs(maxMainX - minMainX);
        rangeImageX = abs(maxImageX - minImageX);

        initMainX = minMainX;
        initImageX = maxImageX;
    }

    private void configYDimen(CoordinatorLayout parent, View main, View child) {
        float minMain = abs(main.getY());
        float maxMain = abs(((AppBarLayout)main).getTotalScrollRange());

        float height = parent.getResources().getDimension(R.dimen.toolbar_height);
        float minChild = abs(main.getY()) + height / 2.0f;
        float maxChild = abs(child.getY());

        rangeMainY = abs(maxMain - minMain);
        rangeImageY = abs(maxChild - minChild);

        initMainY = minMain;
        initImageY = maxChild;
    }

    private boolean isConfigSet = false;

    private float initMainX;
    private float initImageX;
    private float rangeMainX;
    private float rangeImageX;

    private float initMainY;
    private float initImageY;
    private float rangeMainY;
    private float rangeImageY;
}
