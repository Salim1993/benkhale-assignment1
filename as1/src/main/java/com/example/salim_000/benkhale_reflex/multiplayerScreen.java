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

//I took the some code form the solution section in the website http://stackoverflow.com/questions/27680275/dynamic-number-of-buttons-in-linearlayout
//Which was written by the user ToYonos

package com.example.salim_000.benkhale_reflex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

public class multiplayerScreen extends AppCompatActivity {

    final playerManager players = new playerManager();
    private static final String FILENAME1 = "multiFile.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_screen);
        int buttonsNumber = 2;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            buttonsNumber = extras.getInt("EXTRA_NUMBER_BUTTONS");
        }

        LinearLayout multiplayerGrid = (LinearLayout) findViewById(R.id.multiplayerGrid);

        for(int i = 0; i < buttonsNumber; i++)
        {
            //http://stackoverflow.com/questions/27680275/dynamic-number-of-buttons-in-linearlayout  by the user ToYonos
            Button newButton = new Button(this);
            newButton.setId(View.generateViewId());
            newButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            final int finalButtonsNumber = buttonsNumber;
            final int finalI = i;
            newButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    players.updatePlayers(finalI +1, finalButtonsNumber);
                    AlertDialog.Builder endBuilder = new AlertDialog.Builder(multiplayerScreen.this);
                    endBuilder.setMessage("The winner is Player " + (finalI+1));
                    endBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    Dialog endDialog = endBuilder.create();
                    endDialog.show();
                }
            });
            newButton.setText("Player " + (finalI + 1));
            multiplayerGrid.addView(newButton);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromMultiFile();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMultiFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer_screen, menu);
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

    private void loadFromMultiFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME1);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> countList;
            countList = gson.fromJson(in, listType);
            System.out.println("The size list is wrong it is" + countList.size());
            players.setStatus(countList);

        } catch (FileNotFoundException e) {
            players.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            players.clear();
        }
    }

    private void saveMultiFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME1,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(players.getStatus(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
