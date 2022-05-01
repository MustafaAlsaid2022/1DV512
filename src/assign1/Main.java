package assign1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<Process> p = new ArrayList<>();

        /*p.add(new Process(1, 0, 18));
        p.add(new Process(2, 2, 5));
        p.add(new Process(3, 4, 7));*/

        p.add(new Process(1, 0, 18));
        p.add(new Process(2, 3, 2));
        p.add(new Process(3, 25, 5));
        p.add(new Process(4, 29, 2));
        p.add(new Process(5, 33, 7));



        FCFS f = new FCFS(p);

        f.run(); //Run the FCFS algorithm
        f.printTable(); //Print a table of the results
        f.printGanttChart(); //Print Gantt chart


    }
}
