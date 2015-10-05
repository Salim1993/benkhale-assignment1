/*
        Copyright 2015 Mohamed Salim Ben Khaled
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

//I took the some code form the handler section in the website http://codetheory.in/android-handlers-runnables-loopers-messagequeue-handlerthread/
//Which was authored by Rishabh

package com.example.salim_000.benkhale_reflex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Singleplayer extends AppCompatActivity {

    Handler timeHandler;
    String reactionStart = "not done!";
    reactionManager currentReactions = new reactionManager();
    Date startTime;
    Date clickTime;
    Boolean startStatus = false;
    private static final String FILENAME = "singleFile.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        changeButtonColor(findViewById(R.id.reactionButton), 0xFFD5D6D6);

        AlertDialog.Builder singleplayerBuilder = new AlertDialog.Builder(this);
        singleplayerBuilder.setMessage(R.string.dialogInstructions);
        singleplayerBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startStatus = true;
                onResume();
            }
        });

        Dialog singleplayerDialog = singleplayerBuilder.create();
        singleplayerDialog.show();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromSingleFile();
    }

    @Override
    protected void onResume(){
        super.onResume();

        final View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create text alert and send calculate
                clickTime = new Date();
                currentReactions.addTime(currentReactions.calculateTime(clickTime, startTime));
                System.out.println("the time is");
                System.out.println(currentReactions.calculateTime(clickTime, startTime));

                AlertDialog.Builder endBuilder = new AlertDialog.Builder(Singleplayer.this);
                endBuilder.setMessage("Your time was " + currentReactions.calculateTime(clickTime, startTime).toString() + " ms");
                endBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onResume();
                    }
                });
                Dialog endDialog = endBuilder.create();
                endDialog.show();
            }
        };

        changeButtonColor(findViewById(R.id.reactionButton), 0xFFD5D6D6);


        timeHandler = new Handler() {
            //from http://codetheory.in/android-handlers-runnables-loopers-messagequeue-handlerthread/ by Rishabh
            @Override
            public void handleMessage(Message msg) {
                reactionStart = (String) msg.obj;
                startTime = new Date();
                System.out.println(startTime.toString());
                changeButtonColor(findViewById(R.id.reactionButton),0xFF00FF00);
                Button reactionButton = (Button) findViewById(R.id.reactionButton);
                reactionButton.setOnClickListener(buttonListener);
            }
        };
        Thread t = new Thread(new timeThread());
        t.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveInSingleFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_singleplayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeButtonColor (View button, int color){
        button.setBackgroundColor(color);
        button.invalidate();
    }

    class timeThread implements Runnable {
    //from http://codetheory.in/android-handlers-runnables-loopers-messagequeue-handlerthread/ by Rishabh
        startTimer timerStart = new startTimer();
        @Override
        public void run() {
            timerStart.startCountDown();
            while((!timerStart.getStatus().equals("done!")) && startStatus){}
            Message msg = Message.obtain();
            msg.obj = timerStart.getStatus();

            timeHandler.sendMessage(msg);
        }
    }

    private void loadFromSingleFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> reactionList;
            reactionList = gson.fromJson(in, listType);
            currentReactions.setReactionList(reactionList);

        } catch (FileNotFoundException e) {
            currentReactions.setReactionList(new ArrayList<Integer>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            currentReactions.setReactionList(new ArrayList<Integer>());
        }
    }

    private void saveInSingleFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(currentReactions.getReactionList(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


