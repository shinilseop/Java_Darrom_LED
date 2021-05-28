package ac.kr.kongju.MotorController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TrakingMode extends JFrame {

	private final JTextField startTimeTf = new JTextField("2017 2 25 6 48");
	private final JTextField endTimeTf = new JTextField("2017 2 25 18 30");
	private final JButton excuteBtn = new JButton("EXCUTE");
	private final JButton backBtn = new JButton("BACK");
	
	final int DIV = 180;
	int Thread_Count = 0;
	private Timer[] timer = new Timer[DIV];
	
	public TrakingMode() {
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(450, 100);
		setResizable(false);
		setLayout(new GridLayout(2, 3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
		
		excuteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						excute();
					}
				}).start();
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < timer.length; i++) {
					if(timer[i] != null) {
						timer[i].cancel();
					}
				}
				dispose();
				Starter.frame.setVisible(true);
			}
		});
		
		add(new JLabel("START TIME"));
		add(new JLabel("END TIME"));
		add(excuteBtn);
		add(startTimeTf);
		add(endTimeTf);
		add(backBtn);
	}
	
	private void excute() {
		String startTime = startTimeTf.getText();
		String endTime = endTimeTf.getText();
		Calendar startCal = Calendar.getInstance();
		startCal.set(
				Integer.parseInt(startTime.split(" ")[0]),	// year
				Integer.parseInt(startTime.split(" ")[1]),	// month
				Integer.parseInt(startTime.split(" ")[2]),	// day
				Integer.parseInt(startTime.split(" ")[3]),	// hour
				Integer.parseInt(startTime.split(" ")[4]),	// minute
				00);	// second
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(
				Integer.parseInt(endTime.split(" ")[0]),	// year
				Integer.parseInt(endTime.split(" ")[1]),	// month
				Integer.parseInt(endTime.split(" ")[2]),	// day
				Integer.parseInt(endTime.split(" ")[3]),	// hour
				Integer.parseInt(endTime.split(" ")[4]),	// minute
				00);	// second
		
		int count = 0;
		while(endCal.after(startCal)) {
			startCal.add(Calendar.MINUTE, 1);
			count++;
		}
		
		
		double interval = ((double) count)/DIV;
		for (int i = 0; i < DIV; i++) {
			startCal.set(
					Integer.parseInt(startTime.split(" ")[0]),	// year
					Integer.parseInt(startTime.split(" ")[1])-1,	// month
					Integer.parseInt(startTime.split(" ")[2]),	// day
					Integer.parseInt(startTime.split(" ")[3]),	// hour
					Integer.parseInt(startTime.split(" ")[4]),	// minute
					00);	// second
			startCal.add(Calendar.MINUTE, (int)(i*interval));
			Thread_Count++;
			timer[i] = new Timer(true);
			timer[i].schedule(new TimerTask() {
				@Override
				public void run() {
					SerialUtil.write("1 1".getBytes());
					Thread_Count--;
					excuteBtn.setText(Thread_Count + " 번 남음");
					if(Thread_Count == 0) {
						excuteBtn.setText("EXCUTE");
						excuteBtn.setEnabled(false);
					}
				}
			}, startCal.getTime());
			System.out.println(startCal.getTime());
		}
		excuteBtn.setEnabled(false);
		excuteBtn.setText(Thread_Count + " 번 남음");
	}
}
