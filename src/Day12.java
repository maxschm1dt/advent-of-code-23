import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day12 {
    public int aufrufe;
    public static void main(String[] args) {
        new Day12();
    }

    Day12(){
        aufrufe = 0;
        star2();
    }

    void star1(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day12.txt"));
            var lines = br.lines().toList();
            var rows = new ArrayList<String[]>();
            var numbers = new ArrayList<Integer[]>();
            var res = new ArrayList<Integer>();

            for (var line : lines) {
                rows.add(Arrays.stream(line.split("")).takeWhile(a -> !a.equals(" ")).toArray(String[]::new));
                //numbers.add((Arrays.stream(line.split("")).dropWhile(a -> !a.equals(" ")).skip(1)).filter(a -> !a.equals(",")).map(Integer::valueOf).toArray(Integer[]::new));
                numbers.add( Arrays.stream(line.split(" ")[1].split(",")).map(Integer::valueOf).toArray(Integer[]::new));
            }

            for (int i = 0; i < rows.size(); i++) {
                var row = rows.get(i);
                var number = numbers.get(i);

                //var rowS = Arrays.stream(row).reduce("", String::concat);
                //var numberS = Arrays.stream(number).map(String::valueOf).reduce("" , (a, b) -> a + b + ",");

                res.add(treeify(row, number));
            }

            var sum = res.stream().reduce(0, Integer::sum);
            System.out.println(sum);

        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    void star2(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day12test.txt"));
            var lines = br.lines().toList();
            var rows = new ArrayList<String[]>();
            var numbers = new ArrayList<Integer[]>();

            var res = new ArrayList<Integer>();

            for (var line : lines) {

                var split = line.split(" ");
                var rowsS = split[0];//+ "?" + split[0] + "?" + split[0] + "?" + split[0]  + "?" + split[0];
                var numbersS = split[1];//+ "," + split[1] +"," + split[1] +"," +split[1] +"," +split[1];

                rows.add(Arrays.stream(rowsS.split("")).takeWhile(a -> !a.equals(" ")).toArray(String[]::new));
                numbers.add( Arrays.stream(numbersS.split(",")).map(Integer::valueOf).toArray(Integer[]::new));
            }



            for (int i = 1; i < 2; i++) {
                var row = rows.get(i);
                var number = numbers.get(i);

                //System.out.print(Arrays.toString(row));
                System.out.println(Arrays.toString(number));

                var out = smartTreeify(row, number);
                res.add(out);
                System.out.println(out);
                System.out.println("aufrufe: " + aufrufe);
            }

            var sum = res.stream().reduce(0, Integer::sum);
            System.out.println(sum);

        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }


    public boolean check(String[] row, Integer[] numbers){
        var numbersInRow = new ArrayList<Integer>();
        var c = 0;
        boolean in = false;

        for (int i = 0; i < row.length; i++) {
            if(row[i].equals("#")){
                if(in){
                    c++;
                }else {
                    in = true;
                    c= 1;
                }
            }else{
                if(in){
                    in = false;
                    numbersInRow.add(c);
                }
            }
        }
        if(in){
            numbersInRow.add(c);
        }

        var numbersInRowArray = numbersInRow.toArray(Integer[]::new);

        if(arrayEquals(numbers, numbersInRowArray)){
            return true;
        }

        return false;
    }

    public int smartCheck(String[] row, Integer[] numbers){
        var numbersInRow = new ArrayList<Integer>();
        var c = 0;
        boolean in = false;

        for (int i = 0; i < row.length; i++) {
            if(row[i].equals("#")){
                if(in){
                    c++;
                }else {
                    in = true;
                    c= 1;
                }
            }else{
                if(in){
                    in = false;
                    numbersInRow.add(c);
                }
            }
        }
        if(in){
            numbersInRow.add(c);
        }

        var numbersInRowArray = numbersInRow.toArray(Integer[]::new);

        var sameUntil = arrayEqualsUnitl(numbers, numbersInRowArray);

        if(sameUntil == numbers.length){
            return 1;
        }else{
            return 0;
        }

    }

    public int treeify(String[] row, Integer[] numbers){
        var resCheck = check(row, numbers);

        if(resCheck == true){
            return 1;
        }


        if(resCheck == false){
            var last = ".";
            for (int i = 0; i < row.length; i++) {
                if(row[i].equals("?")){
                    var rowa = row.clone();
                    var rowb = row.clone();
                    rowa[i] = "#";
                    rowb[i] = ".";
                    return treeify(rowa, numbers) + treeify(rowb, numbers);
                }
                last = row[i];
            }
        }

        return 0;

    }

    public boolean arrayEquals(Integer[] a, Integer[] b){
        if(a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++) {
            if(!a[i].equals(b[i]))
                return false;
        }
        return true;
    }

    public int arrayEqualsUnitl(Integer[]a, Integer[] b){
        if(a.length != b.length)
            return 0;
        for (int i = 0; i < a.length; i++) {
            if(!a[i].equals(b[i]))
                return i;
        }
        return a.length;
    }

    public int smartTreeify(String[] row, Integer[] numbers){

        return smartTreeify(row, numbers, 0, 0, 0);
    }

    public int smartTreeify(String[] row, Integer[] numbers,int index, int streak, int numberIndex){
        System.out.println(Arrays.toString(row));
        aufrufe++;


        if(check(row, numbers)){
            System.out.println("das isses");
            return 1;
        }


        for (int i = index; i < row.length ; i++) {
            index = i;
            if(row[i].equals("#")){
                streak++;
            }
            if(row[i].equals("?")){
                break;
            }
        }
        if(index >= row.length-1){
            System.out.println("bin am ende");
            return 0;
        }


        if(streak > numbers[numberIndex]){
            System.out.println("streak("+ streak + ")zu groß" + numbers[numberIndex]);
            return 0;
        }


        //am anfang einer streak
        if(streak == 0){
            var rowa = row.clone();
            var rowb = row.clone();
            rowa[index] = "#";
            rowb[index] = ".";
            System.out.println("beide starten für index " + index);
            return smartTreeify(rowa, numbers, index+1, 1,  numberIndex)
                    + smartTreeify(rowb, numbers, index+1, 0,  numberIndex);
        }

        //achtung hier bin ich mit noch nicht sicher
        if(numberIndex >= numbers.length){
            return 0;
        }

        if(streak == numbers[numberIndex]){
            //streak beenden
            row[index] = ".";
            System.out.println("streak beenden ");
            return smartTreeify(row, numbers, index, 0, numberIndex+1);
        }else {
            //streak erhalten
            System.out.println("streak erhalten");
            row[index] = "#";
            return smartTreeify(row, numbers, index, streak+1, numberIndex);
        }
    }
}
