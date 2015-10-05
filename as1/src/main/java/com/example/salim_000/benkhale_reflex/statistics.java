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



package com.example.salim_000.benkhale_reflex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class statistics extends AppCompatActivity {

    playerManager playerManage = new playerManager();
    reactionManager reactionManage = new reactionManager();
    private static final String FILENAME = "singleFile.sav";
    private static final String FILENAME1 = "multiFile.sav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        loadFromSingleFile();
        loadFromMultiFile();
        if (reactionManage.getReactionList() == null) {
            throw new RuntimeException();
        }
        setSinglePlayerStats();
        setMultiplayerStats();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromSingleFile();
        loadFromMultiFile();
        if (reactionManage.getReactionList() == null) {
            throw new RuntimeException();
        }
        setSinglePlayerStats();
        setMultiplayerStats();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    public void clear(View view){
        playerManage.clear();
        reactionManage.setReactionList(new ArrayList<Integer>());
        saveInSingleFile();
        saveMultiFile();
        setSinglePlayerStats();
        setMultiplayerStats();
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
            reactionManage.setReactionList(reactionList);

        } catch (FileNotFoundException e) {
            reactionManage.setReactionList(new ArrayList<Integer>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInSingleFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(reactionManage.getReactionList(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromMultiFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME1);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> countList;
            countList = gson.fromJson(in, listType);
            playerManage.setStatus(countList);

        } catch (FileNotFoundException e) {
            playerManage.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveMultiFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME1,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(playerManage.getStatus(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSinglePlayerStats(){
        TextView max10Text = (TextView) findViewById(R.id.max10);
        max10Text.setText(reactionManage.getMax10().toString());
        max10Text.invalidate();

        TextView max100Text = (TextView) findViewById(R.id.max100);
        max100Text.setText(reactionManage.getMax100().toString());
        max100Text.invalidate();

        TextView min10Text = (TextView) findViewById(R.id.min10);
        min10Text.setText(reactionManage.getMin10().toString());
        min10Text.invalidate();

        TextView min100Text = (TextView) findViewById(R.id.min100);
        min100Text.setText(reactionManage.getMin100().toString());
        min100Text.invalidate();

        TextView ave10Text = (TextView) findViewById(R.id.ave10);
        ave10Text.setText(String.valueOf(reactionManage.getMean10()));
        ave10Text.invalidate();

        TextView ave100Text = (TextView) findViewById(R.id.ave100);
        ave100Text.setText(String.valueOf(reactionManage.getMean100()));
        ave100Text.invalidate();

        TextView median10Text = (TextView) findViewById(R.id.median10);
        median10Text.setText(reactionManage.getMedian10().toString());
        median10Text.invalidate();

        TextView median100Text = (TextView) findViewById(R.id.median100);
        median100Text.setText(reactionManage.getMedian100().toString());
        median100Text.invalidate();
    }

    public void setMultiplayerStats() {
        ArrayList<Integer> count = playerManage.getStatus();

        TextView player1Game2Text = (TextView) findViewById(R.id.player1Game2);
        player1Game2Text.setText(count.get(0).toString());
        player1Game2Text.invalidate();

        TextView player1Game3Text = (TextView) findViewById(R.id.player1Game3);
        player1Game3Text.setText(count.get(1).toString());
        player1Game3Text.invalidate();

        TextView player1Game4Text = (TextView) findViewById(R.id.player1Game4);
        player1Game4Text.setText(count.get(2).toString());
        player1Game4Text.invalidate();

        TextView player2Game2Text = (TextView) findViewById(R.id.player2Game2);
        player2Game2Text.setText(count.get(3).toString());
        player2Game2Text.invalidate();

        TextView player2Game3Text = (TextView) findViewById(R.id.player2Game3);
        player2Game3Text.setText(count.get(4).toString());
        player2Game3Text.invalidate();

        TextView player2Game4Text = (TextView) findViewById(R.id.player2Game4);
        player2Game4Text.setText(count.get(5).toString());
        player2Game4Text.invalidate();

        TextView player3Game3Text = (TextView) findViewById(R.id.player3Game3);
        player3Game3Text.setText(count.get(6).toString());
        player3Game3Text.invalidate();

        TextView player3Game4Text = (TextView) findViewById(R.id.player3Game4);
        player3Game4Text.setText(count.get(7).toString());
        player3Game4Text.invalidate();

        TextView player4Game4Text = (TextView) findViewById(R.id.player4Game4);
        player4Game4Text.setText(count.get(8).toString());
        player4Game4Text.invalidate();
    }
}
