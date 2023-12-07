import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day7 {
    public static void main(String[] args) {
        new Day7();
    }
    Day7(){
        star1();
        star2();
    }

    void star1(){
        var hands = new ArrayList<Hand>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day7.txt"));
            String line = br.readLine();
            while (line != null){
                var spiltLine = line.split(" ");
                hands.add(new Hand(spiltLine[0], Integer.valueOf(spiltLine[1])));
                line = br.readLine();
            }

            hands.sort(Hand::compare);

            var counter = 1;
            long res = 0;
            for (var hand  : hands) {
                res += counter * hand.bid;
                counter++;
            }

            System.out.println("star1: " +  res);



        }catch(IOException e){
            throw  new RuntimeException(e);
        }
    }

    void star2(){
        var jHnd = new JokerHand("KTJJT", 0);

        var hands = new ArrayList<JokerHand>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day7.txt"));
            String line = br.readLine();
            while (line != null){
                var spiltLine = line.split(" ");
                hands.add(new JokerHand(spiltLine[0], Integer.valueOf(spiltLine[1])));
                line = br.readLine();
            }

            hands.sort(JokerHand::compare);

            var counter = 1;
            long res = 0;
            for (var hand  : hands) {
                res += counter * hand.bid;
                counter++;
            }

            System.out.println("star2: "  + res);



        }catch(IOException e){
            throw  new RuntimeException(e);
        }
    }


}

class Hand{
    static String[] cardValues = new String[]{"A","K", "Q", "J", "T", "9", "8", "7", "6","5", "4", "3", "2"};

    int[] numberOfEachCard;

    String[] cards;
    int strongnessType;
    int bid;

    Hand(String cards,int bid){
        this.cards = Arrays.stream(cards.split("")).toArray(String[]::new);
        numberOfEachCard = new int[cardValues.length];
        strongnessType = computeStrongnessType();
        this.bid = bid;
    }

    private int computeStrongnessType(){
        for (var card : cards) {
            for (int i = 0; i < cardValues.length; i++){
                if(Objects.equals(card, cardValues[i])){
                    numberOfEachCard[i] ++;
                }
            }
        }


        int max = 0;
        var  opMax = Arrays.stream(numberOfEachCard).max();

        if(opMax.isPresent()){
            max = opMax.getAsInt();
        }

        if(max == 5){
            return 6;
        }
        if(max == 4){
            return 5;
        }

        var sorted = Arrays.stream(numberOfEachCard).sorted().toArray();
        if(sorted[sorted.length-1] == 3 && sorted[sorted.length-2] == 2){
            return 4;
        }

        if(max == 3){
            return 3;
        }

        if(sorted[sorted.length-1] == 2 && sorted[sorted.length-2] == 2){
            return 2;
        }

        if(max == 2){
            return 1;
        }

        return 0;
    }

    public int getIntFromCard(String card){
        for (int i = 0; i < cardValues.length; i++){
            if(Objects.equals(card, cardValues[i])){
                return i;
            }
        }
        return 0;
    }

    public int compare(Hand hand){
        if(this.strongnessType > hand.strongnessType){
            return 1;
        }
        if(this.strongnessType < hand.strongnessType){
            return -1;
        }
        //vergleiche die ersten elemente
        for (int i = 0; i < cards.length; i++) {
            if(getIntFromCard(this.cards[i]) < getIntFromCard(hand.cards[i])){
                return 1;
            }
            if(getIntFromCard(this.cards[i]) > getIntFromCard(hand.cards[i])){
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(cards) + " " + strongnessType +"   ";
    }
}
class JokerHand{
    static String[] cardValues = new String[]{"A","K", "Q", "T", "9", "8", "7", "6","5", "4", "3", "2", "J"};

    int[] numberOfEachCard;

    String[] cards;
    int strongnessType;
    int bid;

    JokerHand(String cards,int bid){
        this.cards = Arrays.stream(cards.split("")).toArray(String[]::new);
        numberOfEachCard = new int[cardValues.length];
        strongnessType = computeStrongnessType();
        this.bid = bid;
    }

    private int computeStrongnessType(){
        for (var card : cards) {
            for (int i = 0; i < cardValues.length; i++){
                if(Objects.equals(card, cardValues[i])){
                    numberOfEachCard[i] ++;
                }
            }
        }

        var nJokers = numberOfEachCard[numberOfEachCard.length-1];
        numberOfEachCard[numberOfEachCard.length-1] = 0;

        int max = 0;
        var  opMax = Arrays.stream(numberOfEachCard).max();

        if(opMax.isPresent()){
            max = opMax.getAsInt();
        }

        if(max + nJokers >= 5){
            return 6;
        }
        if(max + nJokers == 4){
            return 5;
        }

        var sorted = Arrays.stream(numberOfEachCard).sorted().toArray();
        if(sorted[sorted.length-1] == 3 && sorted[sorted.length-2] == 2){
            return 4;
        }

        if(sorted[sorted.length-1] == 2 && sorted[sorted.length-2] == 2 && nJokers >= 1){
            return 4;
        }


        if(max + nJokers == 3){
            return 3;
        }

        if(sorted[sorted.length-1] == 2 && sorted[sorted.length-2] == 2){
            return 2;
        }

        if(max + nJokers == 2){
            return 1;
        }

        return 0;
    }

    public int getIntFromCard(String card){
        for (int i = 0; i < cardValues.length; i++){
            if(Objects.equals(card, cardValues[i])){
                return i;
            }
        }
        return 0;
    }

    public int compare(JokerHand hand){
        if(this.strongnessType > hand.strongnessType){
            return 1;
        }
        if(this.strongnessType < hand.strongnessType){
            return -1;
        }

        for (int i = 0; i < cards.length; i++) {
            if(getIntFromCard(this.cards[i]) < getIntFromCard(hand.cards[i])){
                return 1;
            }
            if(getIntFromCard(this.cards[i]) > getIntFromCard(hand.cards[i])){
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(cards) + " " + strongnessType +"   ";
    }
}