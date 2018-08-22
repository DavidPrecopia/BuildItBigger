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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    private EndpointsAsyncTask asyncTask;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.joke = new MutableLiveData<>();
        this.asyncTask = new EndpointsAsyncTask(joke);
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
        setUpAdView();
    }

    private void setUpOnClickListener() {
        binding.buttonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarVisibility(View.VISIBLE);
                asyncTask.execute();
            }
        });
    }

    private void setUpAdView() {
        AdView mAdView = binding.getRoot().findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
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