import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {

    public static void main(String[] args) {
        new Day3();
    }

    Day3(){
        star1();
        star2();
    }

    public void star1(){

        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day3.txt"));
            String line = br.readLine();
            var partNumbers = new ArrayList<PartNumber>();
            //achtung das ist sehr sehr hässlich bitte beim lesen nicht sterben
            String[][] map = new String[140][];
            int lineCount = 0;

            while (line != null){
                map[lineCount] = line.split("");
                String number = "";
                int x = 0, y = 0;
                for (int i = 0; i < line.length(); i++) {

                    if(line.charAt(i) > 47 && line.charAt(i) < 58){
                        if(number.isEmpty()){
                            x = lineCount;
                            y = i;
                        }
                        //is a digit
                        number += line.charAt(i);
                    }else if(!number.isEmpty()){
                        partNumbers.add(new PartNumber(x, y, Integer.parseInt(number), map));
                        number = "";
                    }
                }

                if(!number.isEmpty()){
                    partNumbers.add(new PartNumber(x, y, Integer.parseInt(number), map));
                }

                lineCount++;
                line = br.readLine();
            }


            var adjacentPartNumbers = partNumbers.stream()
                    .filter(PartNumber::isAdjacentToSymbol)
                    .toList();

            var sum = adjacentPartNumbers.stream()
                    .mapToInt(PartNumber::getNumber)
                    .sum();

            System.out.println("star1: " + sum);

        }catch (IOException e){
            System.out.println("aua" + e);
        }

    }


    public void star2(){

        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day3.txt"));
            String line = br.readLine();
            var partNumbers = new ArrayList<PartNumber>();
            //achtung das ist sehr sehr hässlich bitte beim lesen nicht sterben
            String[][] map = new String[140][];
            int lineCount = 0;


            while (line != null){
                map[lineCount] = line.split("");
                String number = "";
                int x = 0, y = 0;
                for (int i = 0; i < line.length(); i++) {

                    if(line.charAt(i) > 47 && line.charAt(i) < 58){
                        if(number.isEmpty()){
                            x = lineCount;
                            y = i;
                        }
                        //is a digit
                        number += line.charAt(i);
                    }else if(!number.isEmpty()){
                        partNumbers.add(new PartNumber(x, y, Integer.parseInt(number), map));
                        number = "";
                    }
                }

                if(!number.isEmpty()){
                    partNumbers.add(new PartNumber(x, y, Integer.parseInt(number), map));
                }

                lineCount++;
                line = br.readLine();
            }
            
            partNumbers.forEach(PartNumber::addAdjacentGearsToSet);

            var goodGears = Gear.gears.stream()
                    .filter(a -> a.adjs == 2)
                    .toList();

            var ratios = goodGears.stream()
                    .mapToInt(Gear::getRatio)
                    .sum();

            System.out.println("star2: " + ratios);

        }catch (IOException e){
            System.out.println("aua" + e);
        }

    }


}

class PartNumber{
    public int x;
    public int y;
    public int number;
    String[][] map;

    PartNumber(int x, int y, int number, String[][] map){
        this.x = x;
        this.y = y;
        this.number = number;
        this.map = map;
    }

    public void addAdjacentGearsToSet(){
        int numberLength = String.valueOf(number).length();
        //die reihe drüber checken
        if(x > 0){
            for (int i = y - 1; i <= y + numberLength; i++) {
                if(i < map[x].length && i > 0){
                    if(checkForGear(map[x-1][i])){
                        Gear.addGear(x-1, i, number);
                    }
                }
            }
        }

        //die reihe selber checken
        if(y > 0){
            if(checkForGear(map[x][y-1])){
                Gear.addGear(x, y-1, number);
            }
        }
        if(y + numberLength < map[x].length){
            if(checkForGear(map[x][y+numberLength])){
                Gear.addGear(x, y+numberLength, number);
            }
        }


        //die reihe drunter checken
        if(x < map.length -1){
            for (int i = y - 1; i <= y + numberLength; i++) {
                if(i > 0 && i < map[x].length){
                    if(checkForGear(map[x+1][i])){
                        Gear.addGear(x+1, i, number);
                    }
                }
            }
        }
    }

    public boolean isAdjacentToSymbol(){
        int numberLength = String.valueOf(number).length();

        //die reihe drüber checken
        if(x > 0){
            for (int i = y - 1; i <= y + numberLength; i++) {
                if(i < map[x].length && i > 0){
                    if(checkSymbol(map[x-1][i])){
                        return true;
                    }
                }
            }
        }

        //die reihe selber checken
        if(y > 0){
            if(checkSymbol(map[x][y-1])){
                return true;
            }
        }
        if(y + numberLength < map[x].length){
            if(checkSymbol(map[x][y+numberLength])){
                return true;
            }
        }


        //die reihe drunter checken
        if(x < map.length -1){
            for (int i = y - 1; i <= y + numberLength; i++) {
                if(i > 0 && i < map[x].length){
                    if(checkSymbol(map[x+1][i])){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkSymbol(String symbol){
        return !isDigit(symbol) && symbol.charAt(0) != 46;
    }

    private boolean checkForGear(String symbol){
        return symbol.charAt(0) == 42;
    }

    private boolean isDigit(String a){
        return a.charAt(0) < 58 && a.charAt(0) > 47;
    }

    @Override
    public String toString() {
        return "PartNumber{" +
                "x=" + x +
                ", y=" + y +
                ", number=" + number +
                '}';
    }

    public int getNumber() {
        return number;
    }
}
class Gear{
    static Set<Gear> gears = new HashSet<>();
    int x;
    int y;
    int adjs;
    public List<Integer> numbers;

    private Gear(int x, int y, int number) {
        this.x = x;
        this.y = y;
        adjs = 1;
        numbers = new ArrayList<>();
        numbers.add(number);
    }

    public int getRatio(){
        return numbers.get(0) * numbers.get(1);
    }

    public static void addGear(int x, int y, int number){
        var isInGears = false;
        for (var gear : gears) {
            if(gear.x == x && gear.y == y){
                isInGears = true;
                gear.adjs++;
                gear.numbers.add(number);
            }
        }

        if(!isInGears){
            gears.add(new Gear(x, y, number));
        }
    }

}
