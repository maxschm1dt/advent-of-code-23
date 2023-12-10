import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) {
        new Day9();
    }

    Day9(){
        star1and2();
    }


    void star1and2(){
        try{

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day9.txt"));
            String line = br.readLine();

            var dataSets = new ArrayList<int[]>();
            var backExtrapolatesValues = new ArrayList<Integer>();
            var frontExtrapolatesValues = new ArrayList<Integer>();

            while (line != null){
                var dataSet = Arrays.stream(line.split(" ")).mapToInt(Integer::valueOf).toArray();
                dataSets.add(dataSet);
                line = br.readLine();
            }

            for (var dataSet : dataSets) {
                var increasingLists = new ArrayList<int[]>();
                int[] lastIncreasingList = dataSet;
                increasingLists.add(dataSet);
                while (!isZeroList(lastIncreasingList)){
                    lastIncreasingList = getIncreasingList(lastIncreasingList);
                    increasingLists.add(lastIncreasingList);
                }

                for (var incList :
                        increasingLists) {
                    System.out.println(Arrays.toString(incList));
                }

                int last = 0;
                int first = 0;
                for (int i = increasingLists.size()-1; i >= 0 ; i--) {
                    var incList  = increasingLists.get(i);
                    last = last + incList[incList.length-1];
                    first = incList[0] - first;
                }

                backExtrapolatesValues.add(last);
                frontExtrapolatesValues.add(first);

            }
            for (var sum :
                    backExtrapolatesValues) {
                System.out.println(sum);
            }

            System.out.println("star1: " + backExtrapolatesValues.stream().reduce(0, Integer::sum));
            System.out.println("star2: " + frontExtrapolatesValues.stream().reduce(0, Integer::sum));

        }catch (IOException e ){
            throw  new RuntimeException(e);
        }
    }

    public int[] getIncreasingList(int[] input){
        var output = new int[input.length-1];
        for (int i = 0; i < output.length ; i++)
            output[i] = input[i+1] -input[i];
        return output;
    }

    public boolean isZeroList(int[] list){
        boolean isZeroList = true;
        for (int j : list)
            if (j != 0) {
                isZeroList = false;
                break;
            }
        return  isZeroList;
    }
}
