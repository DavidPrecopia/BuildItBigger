package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import timber.log.Timber;

public final class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService;
    private final MutableLiveData<String> joke;

    EndpointsAsyncTask(MutableLiveData<String> joke) {
        this.joke = joke;
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.0.3:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        myApiService = builder.build();
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            return myApiService.myEndpoint().sayHi().execute().getData();
        } catch (IOException e) {
            Timber.e(e);
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        joke.setValue(result);
    }
}
