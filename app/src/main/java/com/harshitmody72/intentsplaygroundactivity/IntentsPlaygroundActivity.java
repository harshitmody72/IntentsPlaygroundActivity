package com.harshitmody72.intentsplaygroundactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.harshitmody72.intentsplaygroundactivity.databinding.ActivityIntentsPlaygroundBinding;

public class IntentsPlaygroundActivity extends AppCompatActivity {

    private static final int REQUEST_COUNT = 0 ;
    private ActivityIntentsPlaygroundBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        setupHideErrorForEditText();

    }

    //Initial Setups
    private void setupLayout() {
        b = ActivityIntentsPlaygroundBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setTitle("Intents Playground");
    }

    private void setupHideErrorForEditText() {
        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hideError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        b.data.getEditText().addTextChangedListener(myTextWatcher);
        b.initialCounterEt.getEditText().addTextChangedListener(myTextWatcher);
    }

    //Event Handlers

    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void sendImplicitIntent(View view) {
        //Validate Data Input
        String input = b.data.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            b.data.setError("Please Enter Something!");
            return;
        }

        //Validate intent Type
        int type = b.intentTypeRGrp.getCheckedRadioButtonId();

        //Handle Implicit intent Cases
        if (type == R.id.openWebpageRBtn) {
            openWebPage(input);
        } else if (type == R.id.dialNumberRBtn) {
            dialNumber(input);
        } else if (type == R.id.shareTextRBtn) {
            shareText(input);
        } else {
            Toast.makeText(this, "Please Select an intent type!", Toast.LENGTH_SHORT).show();
        }

    }

    //Implicit Intent Sender

    private void shareText(String text) {
        Intent intent = new Intent(); intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text );
        startActivity(Intent.createChooser(intent, "Share text via"));
    }

    private void dialNumber(String number) {
        //Check if input is Number
        if(!number.matches("\\d{10}$")){
            b.data.setError("Invalid Mobile NUMBER!");
            return;
        }

        Uri uri = Uri.parse("tel:"+ number);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(intent);
        hideError();
    }

    private void openWebPage(String url) {
        //Check if input is url
        if(!url.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
            b.data.setError("Invalid URL!");
            return;
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
        hideError();
    }

    //Utils

    private void hideError(){
        b.data.setError(null);
    }

    public void sendData(View view) {
        //Validate Data Input
        String input = b.initialCounterEt.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            b.initialCounterEt.setError("Please Enter Something!");
            return;
        }

        //Get Count
        int initialCount = Integer.parseInt(input);

        //Create intent
        Intent intent = new Intent(this,MainActivity.class);

        //Create Bundle
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INITIAL_COUNT_KEY,initialCount);
        bundle.putInt(Constants.MIN_VALUE, -100);
        bundle.putInt(Constants.MAX_VALUE, 100);


        //passing bundle
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_COUNT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_COUNT && resultCode == RESULT_OK){
            //GET DATA
            int count = data.getIntExtra(Constants.FINAL_COUNT,Integer.MIN_VALUE);

            //Show DATA
            b.result.setText("Final Count Received : " + count);
            b.result.setVisibility(View.VISIBLE);

        }
    }


}