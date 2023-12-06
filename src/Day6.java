import java.util.ArrayList;
import java.util.Arrays;

public class Day6 {

    public static void main(String[] args) {
        new Day6();
    }

    Day6(){
        star2();
    }

    void star1(){
        var times = Arrays.stream("62 64 91 90".split(" ")).mapToInt(Integer::valueOf).toArray();
        var distaces = Arrays.stream("553 1010 1473 1074".split(" ")).mapToInt(Integer::valueOf).toArray();

        var options = new ArrayList<Integer>();

        for (int i = 0; i < times.length; i++) {
            //this is a race
            var raceOptions = new ArrayList<Integer>();
            //from 1 to time - 1
            for (int j = 1; j < times[i]; j++) {
                var racresult = getDistanceFromMils(j, times[i]);
                if(racresult > distaces[i]){
                    raceOptions.add((int)racresult);
                }
            }
            options.add(raceOptions.size());
        }

        System.out.println(options.stream().reduce(1, (a, b) -> a * b));

    }

    void star2(){
        var time = 62649190L;
        var distance =  553101014731074L;

        long options = 0L;

        for (long i = 1; i < time; i++) {
            var raceresult = getDistanceFromMils(i, time);
            if(raceresult > distance){
                options++;
            }
        }

        System.out.println(options);

    }

    public long getDistanceFromMils(long mils, long time){
        var speed = mils;
        var racingTime = time - mils;
        if(racingTime > 0){
            return racingTime * speed;
        }else{
            return 0;
        }
    }
}
