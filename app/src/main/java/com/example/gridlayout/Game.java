package com.example.gridlayout;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Game {
    private boolean[][] bombs;
    private boolean[][] revealed;


    public Game(){
        this.bombs = new boolean[12][10];
        this.revealed = new boolean[12][10];
    }

    protected void plantMines(){
        Random random = new Random();
        for(int i = 0; i < 4; i++){
            int numOne = -1;
            int numTwo = -1;
            boolean found = false;
            while(!found) {
                numOne = random.nextInt(12);
                numTwo = random.nextInt(10);
                if (!bombs[numOne][numTwo]) {
                    bombs[numOne][numTwo] = true;
                    found = true;
                }
            }
        }
    }

    protected void initialReveal(int x, int y){
        Queue<String> queue = new LinkedList<>();

        

    }


}
