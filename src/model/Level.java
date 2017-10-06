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
    int currentLevel = 0;
    int currentGoal = 0;
    List<Pump> pumps;

    void changeState(int currentLevel){
        this.currentLevel = currentLevel;
        switch(currentLevel){
            case 1:
                countOfCars = 10;
                countOfPumps = 1;
                goal = 200;
                break;
            case 2:
                countOfCars = 15;
                countOfPumps = 1;
                goal = 400;
                break;
            case 3:
                countOfCars = 20;
                countOfPumps = 2;
                goal = 600;
                break;
            case 4:
                countOfCars = 30;
                countOfPumps = 3;
                goal = 800;
                break;
            case 5:
                countOfCars = 45;
                countOfPumps = 4;
                goal = 1000;
                break;
        }

        pumps = new ArrayList<>();

        for (int pump = 0; pump < countOfPumps; pump++){
            pumps.add(new Pump());
        }
    }
}
