import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        star1();
        star2();
    }

    public static void star1(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day2.txt"));
            String line = br.readLine();
            var sumOfIds = 0;
            var thisID = 1;

            while (line != null){

                var sets = line.replace(" ", "").split(":")[1].split(";");
                boolean setIsPossible = true;

                for (String set : sets) {
                    var splits = set.split(",");
                    for (var split : splits) {
                        var color = split.chars()
                                .dropWhile(Character::isDigit)
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.joining());
                        var count = Integer.parseInt(split.chars()
                                .takeWhile(Character::isDigit)
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.joining()));

                        if (color.equals("red")) {
                            if (count > 12) {
                                setIsPossible = false;
                            }

                        }
                        if (color.equals("green")) {
                            if (count > 13) {
                                setIsPossible = false;
                            }

                        }
                        if (color.equals("blue")) {
                            if (count > 14) {
                                setIsPossible = false;
                            }
                        }
                    }
                }

                if(setIsPossible){
                    sumOfIds += thisID;
                }


                line = br.readLine();
                thisID++;
            }

            System.out.println("star1: " + sumOfIds);



        }catch (Exception e){
            System.out.println("aua" + e );
        }
    }

    public static void star2(){
        try{

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day2.txt"));
            String line = br.readLine();

            var sumOfPower = 0;

            while (line != null){

                var sets = line.replace(" ", "").split(":")[1].split(";");

                var maxBlue = 0;
                var maxGreen = 0;
                var maxRed = 0;

                for (String set : sets) {
                    var splits = set.split(",");

                    for (var split : splits) {

                        var color = split.chars()
                                .dropWhile(Character::isDigit)
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.joining());
                        var count = Integer.parseInt(split.chars()
                                .takeWhile(Character::isDigit)
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.joining()));

                        if (color.equals("red")) {
                            if (maxRed < count) {
                                maxRed = count;
                            }

                        }
                        if (color.equals("green")) {
                            if (maxGreen < count) {
                                maxGreen = count;
                            }

                        }
                        if (color.equals("blue")) {
                            if (maxBlue < count) {
                                maxBlue = count;
                            }

                        }
                    }
                }

                var power = maxRed * maxBlue * maxGreen;
                sumOfPower += power;

                line = br.readLine();
            }

            System.out.println("star2: "+ sumOfPower);

        }catch (IOException e){
            System.out.println("aua" + e);
        }
    }
}

