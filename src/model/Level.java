package model;

/**
 * Created by Maria on 15.09.2017.
 */
class Level {

    int countOfCars, countOfPumps, goal, currentLevel, timer;
    String description;

    Level(){
        currentLevel = 1;
        changeState();
    }

    void increaseLevel(){
        currentLevel++;
        changeState();
    }

    private void changeState(){
        switch(currentLevel){
            case 1:
                countOfCars = 10;
                countOfPumps = 1;
                goal = 200;
                description = "";
                timer = 0;
                break;
            case 2:
                countOfCars = 15;
                countOfPumps = 1;
                goal = 400;
                description = "";
                timer = 0;
                break;
            case 3:
                countOfCars = 20;
                countOfPumps = 2;
                goal = 600;
                description = "";
                timer = 0;
                break;
            case 4:
                countOfCars = 30;
                countOfPumps = 3;
                goal = 800;
                description = "";
                timer = 0;
                break;
            case 5:
                countOfCars = 45;
                countOfPumps = 4;
                goal = 1000;
                description = "";
                timer = 0;
                break;
        }
    }
}
