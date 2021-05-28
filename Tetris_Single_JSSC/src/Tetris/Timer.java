package Tetris;

public class Timer extends Thread {
	public int time;
	SetStep ss;

	public Timer() {
		time = 0;
	}

	public void run() {
		while (true) {
			time++;
			if (time == 30) {
				ss.setStep(900);
			} else if (time == 60) {
				ss.setStep(800);
			} else if (time == 90) {
				ss.setStep(700);
			} else if (time == 120) {
				ss.setStep(600);
			} else if (time == 160) {
				ss.setStep(500);
			}
			try {
				sleep(1000);
			} catch (Exception e) {
				System.out.println("Timer Error");
			}
		}
	}

	public void callback(SetStep ss) {
		this.ss = ss;
	}
}
