import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day5 {
    public static void main(String[] args) {
        new Day5();
    }

    Day5(){
        star2();
    }

    public void star1(){
        try {
            var seeds = Arrays.stream("950527520 85181200 546703948 123777711 63627802 279111951 1141059215 246466925 1655973293 98210926 3948361820 92804510 2424412143 247735408 4140139679 82572647 2009732824 325159757 3575518161 370114248"
                    .split(" "))
                    .mapToLong(Long::valueOf)
                    .toArray();

            var seedstest = Arrays.stream("79 14 55 13"
                    .split(" "))
                    .mapToLong(Long::valueOf)
                    .toArray();

            var maps = new ArrayList<Map>();

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day5maps.txt"));
            String line = br.readLine();

            var mapValue = "";

            while (line != null){
                if(!line.endsWith(":")){
                    mapValue += line +"\n" ;
                }else{
                    if(!mapValue.isEmpty()){
                        maps.add(new Map(mapValue));
                        mapValue = "";
                    }
                }
                line = br.readLine();
            }

            maps.add(new Map(mapValue));

            var allConverted = new ArrayList<Long>();

            for (var seed : seeds) {
                var res = seed;
                for (var map : maps) {
                    //System.out.println(res);
                    res = map.convert(res);
                }
                System.out.println(res);
                allConverted.add(res);
            }

            System.out.println("star1: " + allConverted.stream().min(Long::compare).get());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void star2(){

        try{
            var seedsInput = Arrays.stream("950527520 85181200 546703948 123777711 63627802 279111951 1141059215 246466925 1655973293 98210926 3948361820 92804510 2424412143 247735408 4140139679 82572647 2009732824 325159757 3575518161 370114248"
                            .split(" "))
                    .mapToLong(Long::valueOf)
                    .toArray();

            var seedsMinMax = new ArrayList<Long>();

            for (int i = 0; i < seedsInput.length; i++) {
                if(i % 2 == 0){
                    seedsMinMax.add(seedsInput[i]);
                    seedsMinMax.add(seedsInput[i] + seedsInput[i+1]);
                }
            }

            System.out.println(seedsMinMax.toString());


            var maps = new ArrayList<Map>();

            BufferedReader br = new BufferedReader(new FileReader("src/txt/day5maps.txt"));
            String line = br.readLine();

            var mapValue = "";

            while (line != null){
                if(!line.endsWith(":")){
                    mapValue += line +"\n" ;
                }else{
                    if(!mapValue.isEmpty()){
                        maps.add(new Map(mapValue));
                        mapValue = "";
                    }
                }
                line = br.readLine();
            }

            maps.add(new Map(mapValue));
            var res = 0L;

            A: for (long i = 0; i < 100000000000000L; i++) {
                long convert = i;
                for (int j = maps.size()-1; j >= 0; j--) {
                    convert = maps.get(j).convertBack(convert);
                }
                for (int j = 0; j < seedsMinMax.size(); j = j+2) {
                    if(convert >= seedsMinMax.get(j) && convert <= seedsMinMax.get(j + 1) ){
                        System.out.println("seed:  " + convert + " location number " + i);
                        System.out.println("und er ist in " + seedsMinMax.get(j) + " " + j);
                        res = convert;
                        break A;
                    }
                }
            }

            System.out.println(superConvert(maps, res));

        }catch (IOException e){
            throw  new RuntimeException(e);
        }



    }

    public long superConvert(List<Map> maps, long seed){
        var res = seed;
        for (var map : maps) {
            //System.out.println(res);
            res = map.convert(res);
        }
        return res;
    }
}

class Map{
    List<PartMap> partMaps;
    public Map(String value){
        partMaps = new ArrayList<>();
        var v = value.split("\n");
        for (var x: v) {
            partMaps.add(new PartMap(x));
        }
    }

    public long convert(long x){
        var out = x;
        for (var partMap: partMaps) {
            if(x >= partMap.sourceRangeStart && x <= partMap.sourceRangeStart + partMap.range){
                out  = partMap.destinationRangeStart + x - partMap.sourceRangeStart;
            }
        }
        return out;
    }

    public long convertBack(long x){
        var out = x;
        for (var partMap : partMaps) {
            if(x >= partMap.destinationRangeStart && x <= partMap.destinationRangeStart + partMap.range){
                out = partMap.sourceRangeStart + x - partMap.destinationRangeStart;
            }
        }
        return out;
    }

}

class PartMap{
    long destinationRangeStart;
    long sourceRangeStart;
    long range;
    public PartMap(String values){
        var z = values.split(" ");
        destinationRangeStart = Long.parseLong(z[0]);
        sourceRangeStart = Long.parseLong(z[1]);
        range =  Long.parseLong(z[2]);
    }


}
