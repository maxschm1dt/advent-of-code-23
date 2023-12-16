import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day16 {

    record Vector2(int x, int y){
        @Override
        public String toString() {
            return "x: " + x + " y: " + y;
        }
    }
    public static void main(String[] args) {
        new Day16();
    }



    Day16(){

        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day16.txt"));
            var map = br.lines().map(a -> a.split("")).toArray(String[][]::new);


            var results = new ArrayList<Integer>();

            for (int i = 0; i < map.length; i++) {
                var dirMap = new boolean[map.length][map[0].length][4];
                var energizedMap = new boolean[map.length][map[0].length];
                makeBeam(map, dirMap, energizedMap, 1, new Vector2(i, 0));
                results.add(countEnergized(energizedMap));
            }

            for (int i = 0; i < map.length; i++) {
                var dirMap = new boolean[map.length][map[0].length][4];
                var energizedMap = new boolean[map.length][map[0].length];
                makeBeam(map, dirMap, energizedMap, 3, new Vector2(i, map[0].length-1));
                results.add(countEnergized(energizedMap));
            }

            for (int i = 0; i < map[0].length; i++) {

                var dirMap = new boolean[map.length][map[0].length][4];
                var energizedMap = new boolean[map.length][map[0].length];
                makeBeam(map, dirMap, energizedMap, 1, new Vector2(0, i));
                results.add(countEnergized(energizedMap));
            }

            for (int i = 0; i < map[0].length; i++) {

                var dirMap = new boolean[map.length][map[0].length][4];
                var energizedMap = new boolean[map.length][map[0].length];
                makeBeam(map, dirMap, energizedMap, 3, new Vector2(map.length-1, i));
                results.add(countEnergized(energizedMap));
            }

            System.out.println(results.stream().max(Integer::compare));

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public  int countEnergized(boolean[][] energizedMap){
        var res = 0;
        for (var row : energizedMap) {

            for (int i = 0; i < row.length; i++) {
                if(row[i]){
                    //System.out.print("#");
                    res++;
                }else {
                    //System.out.print(".");
                }
            }
            //System.out.println();
        }
        return res;
    }

    public int[] getDir(String s, int dir){
        return switch (s){
            case "-" -> dir == 0 || dir == 2 ? new int[]{1, 3} : new int[]{dir};
            case "|" -> dir == 1 || dir == 3 ? new int[]{0, 2} : new int[]{dir};
            case "\\" -> dir == 0 || dir == 2 ? new int[]{(dir + 3) % 4} : new int[]{(dir + 1) % 4};
            case "/" -> dir == 0 || dir == 2 ? new int[]{(dir + 1) % 4} : new int[]{(dir + 3) % 4};
            default -> new int[]{dir};
        };
    }
    public Vector2 getNewPositionFromDir(Vector2 position, int dir){
        return switch (dir){
            case 0 -> new Vector2(position.x - 1, position.y);
            case 1 -> new Vector2(position.x , position.y + 1);
            case 2 -> new Vector2(position.x + 1, position.y);
            case 3 -> new Vector2(position.x , position.y - 1);
            default -> new Vector2(position.x, position.y);
        };
    }

    public boolean isOutOfBounds(Vector2 position, String[][] map){
        var maxX = map[0].length;
        var maxY = map.length;
        if(position.x < 0 || position.x >= maxX)
            return false;
        return position.y >= 0 && position.y < maxY;
    }

    public void makeBeam(String[][] map, boolean[][][] dirMap, boolean[][] energizedMap, int dir, Vector2 position){
        if(!isOutOfBounds(position, map)){
            return;
        }
        if(dirMap[position.x][position.y][dir]){
            return;
        }
        dirMap[position.x][position.y][dir] = true;
        energizedMap[position.x][position.y] = true;
        var dirs = getDir(map[position.x][position.y], dir);

        for (var dirx : dirs) {
           makeBeam(map, dirMap, energizedMap, dirx, getNewPositionFromDir(position, dirx));
        }
    }
}
