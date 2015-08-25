package com.vac.player.player;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MyApplication extends ApplicationTestCase<Application> {
    private MyApplication myApplication;
    private MyApplication() {
        super(Application.class);
    }

    public MyApplication getMyApplication() {
        return myApplication;
    }
}