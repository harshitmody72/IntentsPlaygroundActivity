package com.harshitmody72.intentsplaygroundactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.harshitmody72.intentsplaygroundactivity.databinding.ActivityMain2Binding;
import com.harshitmody72.intentsplaygroundactivity.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    private int qty = 0;
    private ActivityMain2Binding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
    }
    private void setupEventHandlers() {
        b.incBtn.setOnClickListener(v -> incQty());
        b.decBtn.setOnClickListener(v -> decQty());
    }

    public void decQty() {
        b.qty.setText(String.valueOf(--qty));
    }

    public void incQty() {
        b.qty.setText(String.valueOf(++qty));
    }
}