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

/**
 * Created by salim_000 on 2015-10-02.
 */
public class twoPlayerStats {
    int twoPlayer = 0;
    int threePlayer = 0;
    int fourPlayer = 0;

    public twoPlayerStats(int threePlayer, int twoPlayer, int fourPlayer) {
        this.threePlayer = threePlayer;
        this.twoPlayer = twoPlayer;
        this.fourPlayer = fourPlayer;
    }

    public twoPlayerStats() {
    }

    public int getTwoPlayer() {
        return twoPlayer;
    }

    public void setTwoPlayer(int twoPlayer) {
        this.twoPlayer = twoPlayer;
    }

    public int getThreePlayer() {
        return threePlayer;
    }

    public void setThreePlayer(int threePlayer) {
        this.threePlayer = threePlayer;
    }

    public int getFourPlayer() {
        return fourPlayer;
    }

    public void setFourPlayer(int fourPlayer) {
        this.fourPlayer = fourPlayer;
    }

    public void addCountTwo(){
        setTwoPlayer(getTwoPlayer() + 1);
    }

    public void addCountThree() {
        setThreePlayer(getThreePlayer() + 1);
    }

    public void addCountFour() {
        setFourPlayer(getFourPlayer() + 1);
    }

    public void setAll(int twoPlayer, int threePlayer, int fourPlayer){
        this.twoPlayer = twoPlayer;
        this.threePlayer = threePlayer;
        this.fourPlayer = fourPlayer;
    }

    public void clear() {
        setTwoPlayer(0);
        setThreePlayer(0);
        setFourPlayer(0);
    }
}
