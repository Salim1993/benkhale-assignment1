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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by salim_000 on 2015-09-29.
 */
public class startTimer {

    int totalTime = new Random().nextInt(4000);
    Timer timer = new Timer();

    private String status = "not done";

    public startTimer() {
    }

    public void startCountDown() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                status = "done!";
            }
        }, (totalTime + 1000));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String text) {status = text;}

}
