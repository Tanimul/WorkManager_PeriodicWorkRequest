package com.example.workmanager_periodicworkrequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button button, button2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(Myworker.class, 15, TimeUnit.MINUTES).build();

        button = findViewById(R.id.onetimerequest);
        button2 = findViewById(R.id.canclework);
        textView = findViewById(R.id.textview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(MainActivity.this).enqueue(request);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(MainActivity.this).cancelWorkById(request.getId());
            }
        });


        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String status = workInfo.getState().name();
                        textView.append(status + "\n");
                    }
                });

    }
}