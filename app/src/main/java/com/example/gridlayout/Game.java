package com.example.gridlayout;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Game {
    public boolean[][] bombs;
    public boolean[][] revealed;
    public int[][] bombFreqs;


    public Game(){
        this.bombs = new boolean[12][10];
        this.revealed = new boolean[12][10];
        this.bombFreqs = new int[12][10];
        plantMines();
    }

    protected void plantMines(){
        int[][] directions = {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1}
        };
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
                    for(int j = 0; j < 8; j++){
                        if (numOne + directions[j][0] >= 0 && numOne + directions[j][0] < 12){
                            if (numTwo + directions[j][1] >= 0 && numTwo + directions[j][1] < 10){
                                bombFreqs[numOne + directions[j][0]][numTwo + directions[j][1]] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    protected void initialReveal(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        int[] initial = {x, y};
        queue.add(initial);
        int cellsRevealed = 0;

        while (!queue.isEmpty() && cellsRevealed <= 65) {
            int[] front = queue.poll();
            int i = front[0];
            int j = front[1];

            // Check bounds and whether the cell is already revealed
            if (i >= 0 && i < 12 && j >= 0 && j < 10 && !revealed[i][j]) {
                if (!bombs[i][j]) {
                    cellsRevealed++;
                    revealed[i][j] = true;

                    // Add neighboring cells to the queue
                    queue.add(new int[]{i - 1, j});
                    queue.add(new int[]{i + 1, j});
                    queue.add(new int[]{i, j - 1});
                    queue.add(new int[]{i, j + 1});
                }
            }
        }
    }


}
