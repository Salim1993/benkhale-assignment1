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
public class threePlayerStats {
    int threePlayer = 0;
    int fourPlayer = 0;

    public threePlayerStats() {}

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

    public void addCountThree() {
        setThreePlayer(getThreePlayer() + 1);
    }

    public void addCountFour() {
        setFourPlayer(getFourPlayer() + 1);
    }

    public void clear() {
        setThreePlayer(0);
        setFourPlayer(0);
    }

    public void setAll(int threePlayer, int fourPlayer){
        this.threePlayer = threePlayer;
        this.fourPlayer = fourPlayer;
    }
}
