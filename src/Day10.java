import javax.management.relation.RelationNotFoundException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day10 {

    public static void main(String[] args) {
        new Day10();
    }


    Day10(){
        star2();
    }

    void star1(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day10.txt"));
            FileOutputStream fileOutputStream = new FileOutputStream("src/txt/day10out");
            var lines = br.lines().map(String::new).toList();

            var nodes = lines.stream()
                    .map(a -> a.split(""))
                    .toArray(String[][]::new);
            var steps = new int[nodes.length][nodes[0].length];

            int x = 0, y = 0;
            for (int i = 0; i < nodes.length; i++) {
                for (int j = 0; j < nodes[i].length; j++) {
                    if(nodes[i][j].equals("S")){
                        x = i;
                        y = j;
                    }
                }
            }

            followPathIterative(nodes, steps, x + 1, y, 'S');
            followPathIterative(nodes, steps, x , y+1, 'E');

            for (var stepline : steps) {
                fileOutputStream.write((Arrays.toString(stepline) + "\n").getBytes());
            }


        }catch (IOException e){
            throw  new RuntimeException(e);
        }

    }

    public void followPathIterative(String[][] nodes, int[][]steps, int x, int y, char startDir){
        var dir = startDir;
        var nSteps = 1;
        while (!nodes[x][y].equals("S")){
            dir = changeDir(nodes[x][y], dir);
            switch (dir) {
                case 'N' -> x -= 1;
                case 'E' -> y += 1;
                case 'S' -> x += 1;
                case 'W' -> y-= 1;
            }
            if(nSteps < steps[x][y] || steps[x][y] == 0){
                steps[x][y] = nSteps++;
            }else{

                System.out.println("star1 : " +( nSteps + 1));
                break;
            }
        }
    }

    public void followPathIterativeAndMark(String[][] nodes, int x, int y, char startDir, String[][] marked){
        var dir = startDir;
        while (!nodes[x][y].equals("S")){
            var oldDIr = dir;
            dir = changeDir(nodes[x][y], dir);
            var oldX = x;
            var oldY = y;
            switch (dir) {
                case 'N' -> x -= 1;
                case 'E' -> y += 1;
                case 'S' -> x += 1;
                case 'W' -> y-= 1;
            }
            marked[oldX][oldY] = nodes[oldX][oldY];
        }
    }

    public String getMarkFromDir(char dir){
        return switch (dir){
            case 'N' -> "^";
            case 'E' -> ">";
            case 'S' -> "v";
            case 'W' -> "<";
            default -> ">";
        };
    }

    public char changeDir(String s, char dir){
        return  switch (s){
            case "|" -> dir;
            case "-" -> dir;
            case "L" -> dir == 'S' ? 'E' : 'N';
            case "J" -> dir == 'E' ? 'N' : 'W';
            case "7" -> dir == 'E' ? 'S' : 'W';
            case "F" -> dir == 'W' ? 'S' : 'E';
            default -> dir;
        };
    }

    public void followPath(String[][] nodes, int[][]steps, int x, int y, int nextStep){
        System.out.println(nextStep);
        var node = nodes[x][y];
        var step = steps[x][y];
        if (nextStep < step || step == 0) {
            steps[x][y] = nextStep;
            step = nextStep;
            if (!node.equals("S")){
                switch (node){
                    case "|" -> {
                        followPath(nodes, steps, x + 1, y, step + 1);
                        followPath(nodes, steps, x - 1, y, step + 1);
                    }
                    case "-" -> {
                        followPath(nodes, steps, x, y + 1, step + 1);
                        followPath(nodes, steps, x, y - 1, step + 1);
                    }
                    case "L" -> {
                        followPath(nodes, steps, x -1, y, step + 1);
                        followPath(nodes, steps, x, y + 1, step + 1);
                    }
                    case "J" -> {
                        followPath(nodes, steps, x - 1, y, step + 1);
                        followPath(nodes, steps, x, y - 1, step + 1);
                    }
                    case "7" -> {
                        followPath(nodes, steps, x, y-1, step + 1);
                        followPath(nodes, steps, x + 1, y, step + 1);
                    }
                    case "F" -> {
                        followPath(nodes, steps, x, y + 1, step + 1);
                        followPath(nodes, steps, x + 1, y, step + 1);
                    }
                }
            }
        }
    }

    void star2(){

        try{
            BufferedReader br = new BufferedReader(new FileReader("src/txt/day10.txt"));
            FileOutputStream fileOutputStream = new FileOutputStream("src/txt/day10out2");
            FileOutputStream fileOutputStream2 = new FileOutputStream("src/txt/day10out3");
            var lines = br.lines().map(String::new).toList();

            var nodes = lines.stream()
                    .map(a -> a.split(""))
                    .toArray(String[][]::new);
            var steps = new int[nodes.length][nodes[0].length];

            int x = 0, y = 0;
            for (int i = 0; i < nodes.length; i++) {
                for (int j = 0; j < nodes[i].length; j++) {
                    if(nodes[i][j].equals("S")){
                        x = i;
                        y = j;
                    }
                }
            }

            var marked = new String[nodes.length][nodes[0].length];
            for (int i = 0; i < marked.length; i++) {
                for (int m = 0; m < marked[i].length; m++) {
                    marked[i][m] = " ";
                }
            }

            marked[x][y] = "O";

            followPathIterativeAndMark(nodes, x + 1, y, 'S', marked);
            followPathIterativeAndMark(nodes, x , y +1, 'E', marked);



            
            for (var nodeline : marked) {
                int first = -1;
                int last = -1;
                var count = 0;
                for (int i = 0; i < nodeline.length; i++) {
                    if(first == -1 && nodeline[i] == "$"){
                        first = i;
                    }
                    if(nodeline[i].equals("$")){
                        last = i;
                    }
                }

                if(first != -1 && last != -1){
                for (int i = first; i <= last ; i++) {
                    if(!nodeline[i].equals("$")){
                        nodeline[i] = "I";
                    }
                }}
            }

            for (int i = 0; i < marked[0].length; i++) {
                var first  = -1;
                var last = -1;
                for (int j = 0; j < marked.length; j++) {
                    if(marked[j][i].equals("$") && first == -1){
                        first = j;
                    }
                    if(marked[j][i].equals("$")){
                        last = j;
                    }
                }
                if(first != -1 && last != -1){
                    for (int j = 0; j < first; j++) {
                        //nodes[j][i] = "O";
                        //outNode.all.add(new outNode(j, i, nodes));
                    }
                    for (int j = last; j < nodes.length; j++) {
                        //nodes[j][i] = "O";
                        //outNode.all.add(new outNode(j, i, nodes));
                    }
                }
            }


            for (int i = 0; i < marked[0].length; i++) {
                var start = new outNode(i,0,marked);
                start.spread();
            }
            for (int i = 0; i < marked[0].length; i++) {
                var start = new outNode(i,marked[0].length-1,marked);
                start.spread();
            }
            for (int i = 0; i < marked.length; i++) {
                if(marked[0][i] != "$"){
                    //var start = new outNode(0,i,marked);
                    //start.spread();
                }
            }

            var c = 0;

            for (int i = 0; i < marked.length; i++) {
                for (int j = 0; j < marked[0].length; j++) {
                    if(marked[i][j].equals(" ")){
                        if(isActuallyActuallyInside(i, j, marked)){
                            c++;
                            marked[i][j] = "I";
                        }else{
                            marked[i][j] = "O";
                        }
                    }
                }
            }

            System.out.println("star2: " + c);

            for (var ndoeline : marked) {
                fileOutputStream.write((Arrays.toString(ndoeline) + "\n").getBytes());
            }

            for (var ndoeline : marked) {
                var out = "";
                for (var z :
                        ndoeline) {
                    out+= z;
                }
                var newOUt = out.replace("O", " ")
                        .replace("F", "╭")
                        .replace("7", "╮")
                        .replace("-", "─")
                        .replace("J", "╯")
                        .replace("L", "╰")
                        .replace("I", "o");
                fileOutputStream2.write((newOUt + "\n").getBytes());
            }


        }catch (IOException e){
            throw  new RuntimeException(e);
        }

    }

    public boolean isActuallyActuallyInside(int x, int y,String[][] nodes){
        var countToEdge = 0;
        String wallEnter = "";
        for (int i = x -1; i >= 0; i--){

            //nich so easy man schneidet eine wand -> man muss rausfinden ob man die wand schneidet oder tangiert

            //wenn eine wand betreten wird, wird gespeichert ob die vonlinks oder rechts kam
            if(nodes[i][y].equals("L"))
                wallEnter = "R";
            if(nodes[i][y].equals("J"))
                wallEnter = "L";


            //wenn eine wand verlassen wir wird geschaut ob die wand in die geliche richtung verschwindet wie sie kam
            if(nodes[i][y].equals("F") && wallEnter.equals("L"))
                countToEdge++;

            if(nodes[i][y].equals("7") && wallEnter.equals("R"))
                countToEdge++;

            //easy, da ist einfach ne wand
            if(nodes[i][y].equals("-")){
                countToEdge++;
            }

        }
        System.out.println("crossing path: " + countToEdge);

        return countToEdge % 2 != 0;
    }

}

class outNode{
    static Set<outNode> all = new HashSet<>();
    int x, y;
    int[][] allDir;
    String[][] nodes;
    outNode(int x, int y, String[][] nodes){
        this.x = x;
        this.y = y;
        this.nodes = nodes;
        nodes[x][y]  = "O";
        allDir = new int[][]{{x+1, y},{x, y+1}, {x-1, y}, {x, y-1}, {x+1, y+1}, {x-1, y-1}, {x-1, y+1},{x+1, y-1}};
        all.add(this);
    }

    void spread(){
        for (var dir : allDir) {
            if(dir[0] >= 0 && dir[1] >= 0 && dir[0] < nodes.length && dir[1] < nodes[0].length){
                var x = nodes[dir[0]][dir[1]];
                if(!x.equals("|") && !x.equals("-") && !x.equals("L") && !x.equals("J") && !x.equals("O") && !x.equals("7") && !x.equals("F")){
                    var theNew = new outNode(dir[0], dir[1], nodes);
                    theNew.spread();
                }
            }
        }

    }

}

