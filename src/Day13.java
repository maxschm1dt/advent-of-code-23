import javax.naming.CompositeName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day13 {
    public static void main(String[] args) {
        new Day13();

    }

    Day13(){
        star(1);
    }

    void star(int nSmudges){

        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day13.txt"));
            var maps = new ArrayList<String[][]>();
            var line = br.readLine();


            var map = new ArrayList<String[]>();
            while (line != null){
                if(line.isEmpty()){
                    maps.add(map.toArray(String[][]::new));
                    map.clear();
                }else{
                    map.add(line.split(""));
                }
                line = br.readLine();
            }
            maps.add(map.toArray(String[][]::new));


            var outputs = new ArrayList<Integer>();


            for (var mapi : maps) {

                var reflect = findPerfectReflection(mapi, nSmudges);
                var output = (reflect +1) * 100 ;

                if(reflect == -1){
                    var swapped = swapRowsAndColums(mapi);
                    reflect = findPerfectReflection(swapped, nSmudges);
                    output = reflect +1;
                }


                outputs.add(output);
                System.out.println(output);

                for (var linei : mapi) {
                    //System.out.println(Arrays.toString(linei));
                }
            }

            System.out.println(outputs.stream().reduce(0, Integer::sum));


        }catch (IOException e){
            throw  new RuntimeException(e);
        }

    }

    public int findPerfectReflection(String[][] map, int smudges){
        var maxI = -1;
        String[] lastRow = null;
        for (int i = 0; i < map.length; i++) {
            if(lastRow != null){
                var smudgesLeft = arrayEquals(lastRow, map[i], smudges);
                if(smudgesLeft >= 0){
                    if(checkReflection(map, i-1, smudgesLeft)){
                        //return i -1;
                        maxI = i-1;
                    }
                }

            }
            lastRow = map[i];
        }
        return maxI;
    }

    public boolean checkReflection(String[][] map, int start, int smuges){
        for (int i = 1; i < map.length; i++) {
            var below = start + 1 + i ;
            var ontop = start - i;

            if(ontop >= 0 && below < map.length){
                smuges = arrayEquals(map[ontop], map[below], smuges);
                if(smuges < 0 ){
                    return false;
                }
            }else{
                break;
            }
        }
        if(smuges == 0){
            return true;
        }
        return false;
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


    public int arrayEquals(String[]a, String[]b, int errorsAllowed){
        var errors = 0;
        for (int i = 0; i < a.length; i++) {
            if(!a[i].equals(b[i])){
                errors++;
            }
        }
        return  errorsAllowed-errors;
    }

}
