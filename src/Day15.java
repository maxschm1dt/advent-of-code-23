import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day15 {
    public static void main(String[] args) {
        new Day15();
    }

    Day15(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day15.txt"));
            var line = br.readLine();

            var boxes = new ArrayList<Box>();

            var all = line.split(",");

            for (int i = 0; i < 256; i++) {
                boxes.add(new Box(i));
            }

            for (var x : all) {
                var label = Arrays.stream(x.split("")).takeWhile(a -> !a.equals("-") && !a.equals("=")).reduce("", String::concat);
                var boxNumber = hash(label);
                var operationIsDash = x.contains("-");
                var box = boxes.get(boxNumber);
                if(operationIsDash){
                    var z = box.labels.indexOf(label);
                    if(z != -1){
                        box.labels.remove(z);
                        box.focals.remove(z);
                    }
                }else {
                    var focal = Integer.valueOf(x.substring(x.length()-1));

                    if(box.labels.contains(label)){
                        var labelIndex = box.labels.indexOf(label);
                        box.focals.set(labelIndex, focal);

                    }else{
                        box.labels.add(label);
                        box.focals.add(focal);
                    }
                }
            }

            for (var box : boxes) {
                System.out.println(box);
            }

            var out = boxes.stream().mapToInt(Box::getFocusingPower).sum();
            System.out.println(out);



        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }


    int hash(String s){
        var res = 0;
        for (var p: s.split("")) {
            res = ((res + p.charAt(0))* 17 ) % 256;
        }
        return res;
    }
}

class Box{
    List<String> labels;
    List<Integer> focals;
    int boxNumber;

    public Box(int boxNumber){
        labels = new ArrayList<>();
        focals = new ArrayList<>();
        this.boxNumber = boxNumber;
    }

    public int getFocusingPower(){
        var focalPower = IntStream.range(0, focals.size()).map(a -> (a +1)  * focals.get(a)).sum();
        return (boxNumber +1) * focalPower;
    }

    @Override
    public String toString() {
        var out = "";
        for (int i = 0; i < labels.size(); i++) {
            var a = labels.get(i);
            var b = focals.get(i);
            out += a + " " + b +"\n";
        }
        return out;
    }
}
