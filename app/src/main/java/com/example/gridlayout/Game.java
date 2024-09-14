package com.example.gridlayout;
import android.graphics.Color;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

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

    public void plantMines(){
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

    public void reveal(int x, int y, HashMap<List<Integer>, TextView> coordMap) {
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int[] initial = {x, y};
        queue.add(initial);

        while (!queue.isEmpty()) {
            int[] front = queue.poll();
            int i = front[0];
            int j = front[1];
            if (front == initial && bombFreqs[i][j] != 0){
                revealed[i][j] = true;
                TextView tv = coordMap.get(Arrays.asList(i, j));
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.LTGRAY);
                tv.setText(String.valueOf(bombFreqs[i][j]));
                break;
            }

            if (i >= 0 && i < 12 && j >= 0 && j < 10 && !revealed[i][j] && !visited.contains(i + "," + j)) {
                revealed[i][j] = true;
                visited.add(i + "," + j);
                TextView tv = coordMap.get(Arrays.asList(i, j));
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.LTGRAY);
                tv.setText(String.valueOf(bombFreqs[i][j]));
                if (!bombs[i][j] && bombFreqs[i][j] == 0) {
                    queue.add(new int[]{i - 1, j});
                    queue.add(new int[]{i + 1, j});
                    queue.add(new int[]{i, j - 1});
                    queue.add(new int[]{i, j + 1});
                    queue.add(new int[]{i - 1, j - 1});
                    queue.add(new int[]{i + 1, j + 1});
                    queue.add(new int[]{i + 1, j - 1});
                    queue.add(new int[]{i - 1, j + 1});
                }
            }
        }
    }


}
