import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day11 {
    public static void main(String[] args) {
        new Day11();

    }

    Day11(){
        System.out.println("star1: " + star(1));
        System.out.println("star2: "+ star(1000000-1));

    }

    long star(int amountCosmicExpansion){
        try{

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day11.txt"));
            var map = br.lines().map(a -> a.split("")).toArray(String[][]::new);
            var galaxies = new ArrayList<Galaxy>();

            //stores the amount of cosmic expansion depending on [x/y]
            int[] expansionX = new int[map.length];
            int[] expansionY = new int[map[0].length];

            var expansionXCount = 0;
            for (int x = 0; x < map.length; x++) {
                if(!columnHasGalaxy(map, x))
                    expansionXCount += amountCosmicExpansion;
                expansionX[x] = expansionXCount;
            }

            var expansionYCount = 0;
            for (int y = 0; y < map[0].length; y++) {
                if(!rowHasGalaxy(map, y)){
                    expansionYCount += amountCosmicExpansion;
                }
                expansionY[y] = expansionYCount;
            }

            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map[x].length; y++) {
                    if(map[x][y].equals("#")){
                        galaxies.add(new Galaxy(x + expansionY[x], y + expansionX[y]));
                    }

                }
            }

            var distances = new ArrayList<Long>();
            var vergleiche = 0;

            for (int i = 0; i < galaxies.size(); i++) {
                for (int j = 0; j < galaxies.size(); j++) {
                    var g1 = galaxies.get(i);
                    var g2 = galaxies.get(j);
                    var dist = g1.getDistanceTo(g2);
                    //System.out.println((i + 1) + " -> "+  (j +1)  + " = " + dist);
                    distances.add(dist);
                    vergleiche++;
                }
            }

            var sum = distances.stream().reduce(0L, Long::sum);

            return sum/2;



        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public boolean rowHasGalaxy(String[][] input, int row){
        for (int column = 0; column < input[row].length; column++) {
            if(input[row][column].equals("#"))
                return true;
        }
        return false;
    }

    public boolean columnHasGalaxy(String[][] input, int column){
        for (int row = 0; row < input.length; row++) {
            if(input[row][column].equals("#"))
                return true;
        }
        return false;
    }
}

record Galaxy(int x, int y){
    public long getDistanceTo(Galaxy galaxy){
        return Math.abs(x - galaxy.x) + Math.abs(y - galaxy.y);
    }
    @Override
    public String toString() {
        return "Galaxy{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

