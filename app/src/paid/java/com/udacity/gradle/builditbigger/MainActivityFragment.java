package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.displayjoke.DisplayJokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;

import java.io.IOException;

public class MainActivityFragment extends Fragment {

    private FragmentMainBinding binding;

    private MutableLiveData<String> joke;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.joke = new MutableLiveData<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        setUpOnClickListener();
        observeLiveData();
    }

    private void setUpOnClickListener() {
        binding.buttonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarVisibility(View.VISIBLE);
                new EndpointsAsyncTask(joke).execute();
            }
        });
    }

    private void observeLiveData() {
        this.joke.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String jokeString) {
                progressBarVisibility(View.GONE);
                Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
                intent.putExtra(DisplayJokeActivity.class.getSimpleName(), jokeString);
                startActivity(intent);
            }
        });
    }

    private void progressBarVisibility(int visibility) {
        binding.progressBar.setVisibility(visibility);
    }


    static final class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

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
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            joke.setValue(result);
        }
    }
}