package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class TestAsyncTask {

    private MutableLiveData<String> asyncTaskResult;

    @Before
    public void initialize() {
        this.asyncTaskResult = new MutableLiveData<>();
        this.asyncTaskResult.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String joke) {
                assertFalse(TextUtils.isEmpty(joke));
            }
        });
    }

    @Test
    public void returnFromAsyncTaskShouldNotBeNullNorEmpty() {
        new EndpointsAsyncTask(asyncTaskResult).execute();
    }
}
