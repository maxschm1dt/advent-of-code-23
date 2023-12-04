import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {

    public static void main(String[] args) {

        star1();
        star2();
    }

    public static void star1(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day1.txt"));
            String line = br.readLine();
            long sum = 0;

            while (line != null){
                var all = line.split("");
                String first = null;
                String last = null;
                for (String a: all) {
                    if(a.charAt(0) < 58 && a.charAt(0) > 47){
                        if(first == null){
                            first = a;
                        }
                        last = a;
                    }
                }
                sum += Integer.parseInt(first + last);
                line = br.readLine();
            }

            System.out.println("star1: " + sum);


        } catch (IOException e) {
            System.out.println("aua" + e);
        }
    }

    public static void star2(){
        try{

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day1.txt"));
            String line = br.readLine();

            var sum = 0;

            while (line != null){
                line = toNumberString(line);
                var all = line.split("");
                String first = null;
                String last = "";

                for (String a: all) {
                    if(a.charAt(0) < 58){
                        if(first == null){
                            first = a;
                        }
                        last = a;
                    }
                }
                sum += Integer.parseInt(first + last);
                line = br.readLine();
            }

            System.out.println("star2: " + sum);



        }catch (IOException e){
            System.out.println("aua" + e);
        }
    }

    public static String toNumberString(String s){
        for (int i = 0; i < s.length(); i++) {
            s = check(s, i);
        }
        return s;
    }

    public static String check(String s, int startAt){

        var checkable = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};


        var toCheck = s.substring(startAt);
        var front = s.substring(0, startAt);

        for (int i = 0; i < checkable.length; i++) {
            if(toCheck.startsWith(checkable[i])){
                return front + (i +1) + toCheck.substring(1);
            }
        }
       return front + toCheck;
    }
}
