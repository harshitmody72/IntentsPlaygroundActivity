package com.harshitmody72.intentsplaygroundactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.harshitmody72.intentsplaygroundactivity.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private int qty = 0;
    private ActivityMainBinding b;
    private int minVal , maxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
        getInitialCount();

    }

    //Receiving Data
    private void getInitialCount() {
        //GET data from intent
        Bundle bundle = getIntent().getExtras();
        qty = bundle.getInt(Constants.INITIAL_COUNT_KEY,0);
        minVal = bundle.getInt(Constants.MIN_VALUE,Integer.MIN_VALUE);
        maxVal = bundle.getInt(Constants.MAX_VALUE,Integer.MAX_VALUE);
        b.qty.setText(String.valueOf(qty));

        if(qty !=0){
            b.sendBackBtn.setVisibility(View.VISIBLE);
        }
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

    public void sendCount(View view) {
        //Validate count
        if(qty >= minVal && qty <= maxVal){

            //Send the data
            Intent intent = new Intent();
            intent.putExtra(Constants.FINAL_COUNT,qty);
            setResult(RESULT_OK, intent);

            //Close the activity
            finish();
        }
        //NOT IN RANGE
        else {

            Toast.makeText(this, "NOT IN RANGE", Toast.LENGTH_SHORT).show();
        }

    }
}