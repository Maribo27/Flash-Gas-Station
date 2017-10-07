package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 15.09.2017.
 */
class Level {

    int countOfCars;
    int countOfPumps;
    int goal;
    int currentLevel;
    int balance = 0;
    int speed;
    int coefficient;
    List<Pump> pumps;

    private void changeState(){
        switch(currentLevel){
            case 1:
                countOfCars = 5;
                countOfPumps = 1;
                goal = 200;
                speed = 10;
                coefficient = 2;
                break;
            case 2:
                countOfCars = 10;
                countOfPumps = 2;
                goal = 400;
                speed = 7;
                coefficient = 4;
                break;
            case 3:
                countOfCars = 15;
                countOfPumps = 2;
                goal = 500;
                speed = 7;
                coefficient = 6;
                break;
            case 4:
                countOfCars = 20;
                countOfPumps = 3;
                goal = 700;
                speed = 5;
                coefficient = 8;
                break;
            case 5:
                countOfCars = 30;
                countOfPumps = 4;
                goal = 800;
                speed = 5;
                coefficient = 10;
                break;
        }

        pumps = new ArrayList<>();

        for (int pump = 0; pump < countOfPumps; pump++){
            pumps.add(new Pump());
        }
    }

    void setLevel(int level){
        currentLevel = level;
        changeState();
    }
}
