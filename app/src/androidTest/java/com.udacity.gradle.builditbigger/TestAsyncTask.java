package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class TestAsyncTask {
    private CountDownLatch countDownLatch;

    private MutableLiveData<String> asyncTaskResult;

    @Before
    public void initialize() {
        this.countDownLatch = new CountDownLatch(1);
        this.asyncTaskResult = new MutableLiveData<>();
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String joke) {
                countDownLatch.countDown();
            }
        };

        asyncTaskResult.observeForever(observer);
    }

    @Test
    public void validReturnFromAsyncTask() throws InterruptedException {
        new EndpointsAsyncTask(asyncTaskResult).execute();
        countDownLatch.await();
    }
}
