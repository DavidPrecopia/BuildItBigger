package com.example.displayjoke;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.displayjoke.databinding.ActivityDisplayJokeBinding;

public class DisplayJokeActivity extends AppCompatActivity {

    private ActivityDisplayJokeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_joke);
        setUpTextView();
    }

    private void setUpTextView() {
        String joke = getIntent().getStringExtra(DisplayJokeActivity.class.getSimpleName());
        binding.tvJoke.setText(joke);
    }
}
