package assign1;

import java.util.ArrayList;


public class FCFS {

	// The list of processes to be scheduled
	public ArrayList<Process> processes;
	private int CT = 0;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		this.processes = processes;
	}

	public void run() {
		// TODO Implement the FCFS algorithm here

		processes.sort((Process p1, Process p2) -> p1.getArrivalTime() - p2.getArrivalTime()); // sort processes based on arrival time

		processes.forEach((p) -> {      // loop through each process 
			completedTimeCalculation(p);
			turnAroundTimeCalculation(p);
			waitingTimeCalculation(p);
		});

	}

	

	// METHOD TO CALCULATE WAITING TIME taking in parameter the class Process
	public int waitingTimeCalculation(Process process) {
		// WT indicates how much time a task spent on waiting on the queue before it was
		// allocated
		int WT = process.getTurnaroundTime() - process.getBurstTime();

		process.setWaitingTime(WT);

		return WT;
	}

	// METHOD TO CALCULATE TURN AROUND TIME with Process as parameter
	public int turnAroundTimeCalculation(Process process) {
		// TAT indicates the total time a task has spent since it has arrived until it
		// has finished
		int TAT = process.getCompletedTime() - process.getArrivalTime();

		process.setTurnaroundTime(TAT);

		return TAT;
	}

	// METHOD TO CALCULATE THE COMPLETED TIME with Process as parameter
	public int completedTimeCalculation(Process process) {
		// CT indicates the timeframe since first task has arrived until the specific
		// task has been completed
		// Completed Time is Burst Time PROCESS NUMBER 1 + BURST TIME PROCESS 2
		// CT = BT(x) + BT(x+1)

		// If the Next Arrival time is greater than the current Completed Time(the one
		// before the arrival time)
		// Then we add the BT and AT together to make the new Completed Time
		// Otherwise, we just adding the BT together to have the CT.
		if (process.getArrivalTime() > CT) {
			// CTC +=process.getBurstTime() + 1;

			CT = process.getBurstTime() + process.getArrivalTime();

			// System.out.print("CTC1 : "+CTC);

		} else {
			CT += process.getBurstTime();
		}
		// System.out.print("CTC 1 : "+CTC);

		process.setCompletedTime(CT);
		return CT;

	}
	
	public void printTable() {
		// TODO Print the list of processes in form of a table here

		System.out.println("\n----------------------------------------------------------------------------------");
		System.out.println("PID\t\tAT\t\tBT\t\tCT\t\tTAT\t\tWT");
		processes.forEach((p) -> {      // loop through each process 
			System.out.println("" + p.getProcessId() + "\t\t" + p.getArrivalTime()
					+ "\t\t" + p.getBurstTime() + "\t\t" + p.getCompletedTime() + "\t\t"
					+ p.getTurnaroundTime() + "\t\t" + p.getWaitingTime());
		});


		System.out.print("-----------------------------------------------------------------------------------\n");

	}

	public void printGanttChart() {
		// TODO Print the demonstration of the scheduling algorithm using Gantt Chart
		System.out.println("\n%%%%%%%%%%% GRANTT CHART %%%%%%%%%%\n");

		String equalRow = "";
		String middleRow = "";
		String lastRow = "0";

		for (int i = 0; i < processes.size(); i++) {
			// int helpWTC = processes.get(i).getWaitingTime();
			int helpBT = processes.get(i).getBurstTime();
			int helpCT = processes.get(i).getCompletedTime();

			if (i == 0) {
				// FOR FIRST PID

				middleRow += "|";
				middleRow += "P" + processes.get(i).getProcessId();
				while (helpBT != 0) {
					equalRow += "=";
					lastRow += " ";
					middleRow += " ";
					helpBT--;
					if (helpBT == 0) {
						equalRow += "====";
						middleRow += "|";
						lastRow += " ";
					}
				}
				lastRow += "" + processes.get(i).getCompletedTime();

			} else if (i == processes.size() - 1) {
				// FOR LAST PID

				middleRow += "|";
				middleRow += "P" + processes.get(i).getProcessId();
				while (helpBT != 0) {
					equalRow += "=";
					lastRow += " ";
					middleRow += " ";
					helpBT--;
					if (helpBT == 0) {
						equalRow += "====";
						middleRow += "|";
						lastRow += " ";
					}
				}
				lastRow += "" + processes.get(i).getCompletedTime();

			} else {
				// FOR EVERYTHING ELSE BETWEEN THE FIRST AND LAST PID

				if (helpCT < processes.get(i + 1).getArrivalTime()) {
					// PRINT FOR WAITING TIME HERE
					middleRow += "|";
					middleRow += "P" + processes.get(i).getProcessId();

					while (helpBT != 0) {
						equalRow += "=";
						lastRow += " ";
						middleRow += " ";
						helpBT--;
						if (helpBT == 0) {
							equalRow += "====";
							middleRow += "|";
							lastRow += " ";
						}
					}
					lastRow += " " + processes.get(i).getCompletedTime(); // TO print CT in the correct position

					int calc = processes.get(i + 1).getArrivalTime() - processes.get(i).getCompletedTime(); // get
																											// difference
					if (calc < 0) {
						calc = -(processes.get(i + 1).getArrivalTime() - processes.get(i).getCompletedTime());
					}
					// WAITING TIME STARTS HERE
					middleRow += "|";

					while (calc != 0) {
						equalRow += "=";
						lastRow += " ";
						middleRow += " ";
						calc--;
						if (calc == 0) {
							equalRow += "==";
							middleRow += "|";
							lastRow += " ";
						}
					}

				} else {
					middleRow += "|";
					middleRow += "P" + processes.get(i).getProcessId();

					while (helpBT != 0) {
						equalRow += "=";
						lastRow += " ";
						middleRow += " ";
						helpBT--;
						if (helpBT == 0) {
							equalRow += "====";
							middleRow += "|";
							lastRow += " ";
						}
					}

				}

				if (helpCT > processes.get(i + 1).getArrivalTime()) {
					// Check if Completed is greater than arrival time to the correct last row
					// And to not mess up with arrival time and waiting time
					// when we get an arrival time of 4 and a CT of 23
					lastRow += processes.get(i).getCompletedTime();
					continue;
				}

				lastRow += "" + processes.get(i + 1).getArrivalTime();
			}

		}

		System.out.println(equalRow);
		System.out.println(middleRow);
		System.out.println(equalRow);
		System.out.println(lastRow);

		

	}

}
