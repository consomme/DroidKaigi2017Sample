package com.consomme.droidkaigi2017sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.consomme.droidkaigi2017sample.adapter.SampleRecyclerAdapter;
import com.consomme.droidkaigi2017sample.databinding.ActivityMainBinding;
import com.consomme.droidkaigi2017sample.manager.Step1LayoutManager;
import com.consomme.droidkaigi2017sample.manager.Step2LayoutManager;
import com.consomme.droidkaigi2017sample.manager.Step3LayoutManager;
import com.consomme.droidkaigi2017sample.manager.Step4LayoutManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recyclerView.setAdapter(new SampleRecyclerAdapter());

        initSpinner();
    }

    private void initSpinner() {
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        break;
                    case 1:
                        binding.recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        break;
                    case 2:
                        binding.recyclerView.setLayoutManager(new Step1LayoutManager());
                        break;
                    case 3:
                        binding.recyclerView.setLayoutManager(new Step2LayoutManager());
                        break;
                    case 4:
                        binding.recyclerView.setLayoutManager(new Step3LayoutManager());
                        break;
                    case 5:
                        binding.recyclerView.setLayoutManager(new Step4LayoutManager());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
