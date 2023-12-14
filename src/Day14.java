import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day14 {

    public static void main(String[] args) {
        new Day14();
    }



    Day14(){
        star2();

    }

    void star1(){
        try{

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day14test.txt"));
            var map = br.lines().map(a -> a.split("")).toArray(String[][]::new);


            var out = getLoad(map);
            System.out.println(out);

        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    void star2(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day14.txt"));
            var map = br.lines().map(a -> a.split("")).toArray(String[][]::new);
            var circles = new ArrayList<BigInteger>();


            var circle = map;
            long count = 0;
            var start = 0;
            while (true){
                count++;
                circle = circle(circle);
                if(circles.contains(hashCircle(circle))){
                    start = circles.indexOf(hashCircle(circle));
                    System.out.println("start of loop: " + start);
                    System.out.println("end of loop: " + count);
                    break;
                }else{
                    circles.add(hashCircle(circle));
                }
            }

            var n = 1000000000L;
            var loopLength = start - count;
            var rest = (n - start) % loopLength + count - 2;

            System.out.println("rest :" + rest);

            circle = map;

            for (int i = 0; i < rest; i++) {
                circle = circle(circle);
            }

            var load = getLoadWithoutTilt(circle);
            System.out.println("load: "  + load);


        }catch (IOException e){
            throw  new RuntimeException(e);
        }

    }

    public String[][] swapRowsAndColums(String[][] map){
        var out = new String[map[0].length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                out[j][i] = map[i][j];
            }
        }
        return out;
    }

    public int sumPartLengthToI(String[] parts, int i){
        var out = 0;
        for (int j = 0; j < i; j++) {
            out+= parts[j].length();
        }
        //add the #s
        out += i;

        return out ;
    }

    public int getLoadWithoutTilt(String[][] map){
        var load = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j].equals("O"))
                    load += map.length - i;
            }
        }
        return load;
    }

    public int getLoad(String[][] map){

        var swapped = swapRowsAndColums(map);
        var columns = Arrays.stream(swapped)
                .map(a -> Arrays.stream(a).reduce("", String::concat))
                .toArray(String[]::new);


        var res = 0;

        for (var colum : columns) {

            var parts = colum.split("#");
            //System.out.println(Arrays.toString(parts));
            for (int i = 0; i < parts.length; i++) {
                var part = parts[i];
                var weightStart = colum.length() - sumPartLengthToI(parts, i);

                var os = Arrays.stream(part.split(""))
                        .filter(a -> a.equals("O"))
                        .count();

                var midRes = 0;
                for (int j = 0; j < os; j++) {
                    midRes += weightStart--;
                }
                res += midRes;
            }
        }

        return res;

    }


    public String[][] rollLeft(String[][] map, boolean rollLeft){
        var lines = Arrays.stream(map)
                .map(a -> Arrays.stream(a).reduce("", String::concat))
                .toArray(String[]::new);

        var newLines = new ArrayList<String[]>();

        for (var line : lines) {
            var partList = new ArrayList<String>();
            var s = "";
            for (var c : line.split("")){
                if(c.equals("#")){
                    partList.add(s);
                    s = "";
                }else{
                    s+=c;
                }
            }
            partList.add(s);
            var splits = partList.toArray(String[]::new);
            StringBuilder newLine = new StringBuilder();
            for (int i = 0; i < splits.length; i++) {
                var os = Arrays.stream(splits[i].split("")).filter(a -> a.equals("O")).count();
                if(rollLeft){
                    splits[i] = "O".repeat((int)os) + ".".repeat(splits[i].length() - (int)os) + "#";
                }else{
                    splits[i] =  ".".repeat(splits[i].length() - (int)os) +  "O".repeat((int)os) + "#";
                }

                newLine.append(splits[i]);
            }
            newLines.add(newLine.substring(0, line.length()).split(""));
        }

        return newLines.toArray(String[][]::new);
    }

    public String[][] circle(String[][] map){
        //roll north
        var swapped = swapRowsAndColums(map);
        var rolledNorth = rollLeft(swapped, true);
        var swappedBack = swapRowsAndColums(rolledNorth);
        var rolledWest = rollLeft(swappedBack, true);
        swapped = swapRowsAndColums(rolledWest);
        var rolledSouth = rollLeft(swapped, false);
        swappedBack = swapRowsAndColums(rolledSouth);
        var rolledEst = rollLeft(swappedBack, false);

        return rolledEst;
    }

    public BigInteger hashCircle(String[][] map){
        var out = new BigInteger("0");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j].equals("O"))
                    out = out.setBit(j);
            }
            out = out.shiftLeft(map[0].length);
        }

        return out.shiftLeft(1);
    }
}
