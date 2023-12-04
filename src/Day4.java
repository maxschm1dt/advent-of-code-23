import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day4 {
    public static void main(String[] args) {
        new Day4();
    }


    Day4(){
        star1();
        star2();
    }

    public void star1(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day4.txt"));
            String line = br.readLine();
            var scoreScore = 0;

            while (line != null){
                var score = 0;

                var spiltLine = line.split(" ");
                var winingLine = Arrays.stream(spiltLine).
                        dropWhile(a -> !Objects.equals(a, "|"))
                        .skip(1)
                        .filter(a -> !a.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

                var numbersIHave = Arrays.stream(spiltLine)
                        .filter(a -> !a.isEmpty())
                        .dropWhile(a -> a.endsWith(":"))
                        .skip(1)
                        .takeWhile(a -> !Objects.equals(a, "|"))
                        .skip(1)
                        .map(Integer::valueOf)
                        .toList();

                for (var number : numbersIHave) {
                    if(winingLine.contains(number))
                        score = score == 0 ? 1 : score * 2;
                }

                line = br.readLine();
                scoreScore += score;
            }

            System.out.println("star1: " + scoreScore);

        }catch (IOException e){
            System.out.println("aua" + e);
        }

    }

    public void star2(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day4.txt"));
            String line = br.readLine();

            var allWinningNumbers = new ArrayList<Integer>();

            while (line != null){
                var winningNumbers = 0;

                var spiltLine = line.split(" ");
                var winingLine = Arrays.stream(spiltLine).
                        dropWhile(a -> !Objects.equals(a, "|"))
                        .skip(1)
                        .filter(a -> !a.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

                var numbersIHave = Arrays.stream(spiltLine)
                        .filter(a -> !a.isEmpty())
                        .dropWhile(a -> a.endsWith(":"))
                        .skip(1)
                        .takeWhile(a -> !Objects.equals(a, "|"))
                        .skip(1)
                        .map(Integer::valueOf)
                        .toList();


                for (var number : numbersIHave) {
                    if(winingLine.contains(number))
                        winningNumbers++;
                }

                allWinningNumbers.add(winningNumbers);

                line = br.readLine();
            }

            var winningNumbersArray = allWinningNumbers.stream().mapToInt(Integer::intValue).toArray();

            var nCards = new int[winningNumbersArray.length];

            for (int i = 0; i < winningNumbersArray.length; i++) {
                nCards[i] += 1;
                //ich hab schon nCards[i] copys sooo
                for (int j = 1; j < winningNumbersArray[i] +1; j++) {
                    nCards[i+j] += nCards[i];
                }
            }

            var sum = Arrays.stream(nCards).sum();
            System.out.println("star2: "  + sum);

        }catch (IOException e){
            System.out.println("aua" + e);
        }
    }
}


