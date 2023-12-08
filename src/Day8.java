import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day8 {
    public static void main(String[] args) {
        new Day8();
    }

    Day8(){
        star1();
        star2();
    }

    void star1(){
        try{

            int sequencePointer = 0;
            int steps = 0;

            var sequence = ("LRRRLRRLRRLRLRRLRRRLLRRLLRRLRRRLRLRRLLRRLRRLRLRRRLRRRLRLRLRLRRRLRRLRRRLRLRRLLLRLRLLRLRRRLRLRRRLRRRLLLRRLRLRRLRRRLLRRLRRLRRLRRRLRRLRRLRRLRLRRLRLRLRLRLRLRRRLRRLRLLLRRRLRLRRRLRRRLLRRLRRRLRRLRRRLRRRLRLRRRLRRLRLLRRLLRLRRLRLRLLRRLLRRLLRRLRRLRRRLRLRRLRLRRRLRRRLLRLRRLLLLRRRLLRLLLRRLRRRLRRRLRRRLRLRRRLRRRLRRRLRLRRRR"
                    .split(""));
            var testSequence = "RL".split("");

            var instructions = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day8.txt"));
            String line = br.readLine();
            while (line != null){
                instructions.add(line);
                line = br.readLine();
            }


            var currentStep = find("AAA", instructions);
            while (!currentStep.startsWith("ZZZ")){
                var direction = sequence[sequencePointer];
                sequencePointer = (sequencePointer + 1) % sequence.length;
                String nextStep = direction.equals("L") ? currentStep.substring(7, 10) : currentStep.substring(12, 15);
                //System.out.println(steps + ": " + nextStep);


                currentStep = find(nextStep, instructions);
                steps++;
            }

            System.out.println("star1: " +steps);

        }catch (IOException e ){
            throw new RuntimeException(e);
        }
    }


    String find(String start, List<String> instructions){

        var opCurrentStep = instructions.stream().filter(a -> a.startsWith(start)).findFirst();

        if(opCurrentStep.isEmpty()){
            System.out.println("da bassd wos ned");
        }

        return opCurrentStep.get();
    }


    List<String> findAll(String start, List<String> instructions){
        return  instructions.stream().filter(a -> a.substring(2, 3).equals(start)).toList();
    }



    void star2(){

        try{

            int sequencePointer = 0;
            long steps = 0;

            var sequence = ("LRRRLRRLRRLRLRRLRRRLLRRLLRRLRRRLRLRRLLRRLRRLRLRRRLRRRLRLRLRLRRRLRRLRRRLRLRRLLLRLRLLRLRRRLRLRRRLRRRLLLRRLRLRRLRRRLLRRLRRLRRLRRRLRRLRRLRRLRLRRLRLRLRLRLRLRRRLRRLRLLLRRRLRLRRRLRRRLLRRLRRRLRRLRRRLRRRLRLRRRLRRLRLLRRLLRLRRLRLRLLRRLLRRLLRRLRRLRRRLRLRRLRLRRRLRRRLLRLRRLLLLRRRLLRLLLRRLRRRLRRRLRRRLRLRRRLRRRLRRRLRLRRRR"
                    .split(""));
            var testSequence = "RL".split("");

            var instructions = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day8.txt"));
            String line = br.readLine();
            while (line != null){
                instructions.add(line);
                line = br.readLine();
            }

            var startSteps = new ArrayList<>(findAll("A", instructions));

            var allSteps = new ArrayList<Long>();

            for (var currentStep :
                    startSteps) {
                steps = 0;
                while (currentStep.charAt(2) != 'Z'){
                    var direction = sequence[sequencePointer];
                    sequencePointer = (sequencePointer + 1) % sequence.length;
                    String nextStep = direction.equals("L") ? currentStep.substring(7, 10) : currentStep.substring(12, 15);
                    //System.out.println(steps + ": " + nextStep);


                    currentStep = find(nextStep, instructions);
                    steps++;
                }

                allSteps.add(steps);
            }

            ///System.out.println(allSteps.toString());
            //used https://www.calculatorsoup.com/calculators/math/lcm.php to calculate the LCM

            System.out.println("star2: " +LCM(allSteps));

        }catch (IOException e ){
            throw new RuntimeException(e);
        }

    }

    public static long LCM (List<Long> values){
        var x = values.get(0);
        for (var value :
                values) {
            x = LCM(x, value);
        }

        return x;
    }


    public static long LCM(long a, long b){
        return (a * b) / EuklidischerAlgo(a, b);
    }

    public static long EuklidischerAlgo(long a, long b){
        if(b == 0){
            return a;
        }else{
            return EuklidischerAlgo(b, (a % b));
        }
    }
}
