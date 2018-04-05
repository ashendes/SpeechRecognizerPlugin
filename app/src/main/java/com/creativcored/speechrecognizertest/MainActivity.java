package com.creativcored.speechrecognizertest;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creativcored.voicerecognitionplugin.VoiceRecognizer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView resultText;
    private static final int REQUEST_MIC = 0;

    private VoiceRecognizer vrecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView) findViewById(R.id.tvResult);
        vrecognizer = VoiceRecognizer.initializeVoiceRecognizer(getApplicationContext());
    }

    public void promptSpeechInput(View view){
        getPermissions();
        vrecognizer.activateListener();
    }

    public void getResults(View view){
        if(!vrecognizer.getRecognizingState()){
            resultText.setText(vrecognizer.getRecognizedText());
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        if (vrecognizer != null)
        {
            //vrecognizer = null;
        }
    }

    @TargetApi(23)
    public void getPermissions(){
        if(checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            return;
        }else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)){
                Toast.makeText(this, "Audio recording permission is needed", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_MIC);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_MIC){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onResume();
            }else{
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

}
