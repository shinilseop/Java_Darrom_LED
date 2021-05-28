package ac.kr.kongju.MotorController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AutoControl extends JFrame {

	final int ITEM_COUNT = 5;

	public static JLabel topLable = new JLabel("STOP", JLabel.CENTER);
	JPanel centerPanel = new JPanel(new GridLayout(ITEM_COUNT, 1));
	JPanel bottomPanel = new JPanel(new FlowLayout());

	List<TimeItem> items = new ArrayList<TimeItem>();
	public static JButton runButton = new JButton("RUN");
	public static JButton stopButton = new JButton("STOP");
	public static JButton backButton = new JButton("Back");

	public AutoControl() {
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setSize(new Dimension(250, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
		
		topLable.setFont(new Font("Serial", Font.BOLD, 25));
		topLable.setForeground(Color.RED);
		topLable.setPreferredSize(new Dimension(0, 70));
		
		for (int i = 0; i < ITEM_COUNT; i++) {
			TimeItem item = new TimeItem(false, "00:00:00", "0 10");
			item.setEnabled(true);
			items.add(item);
			centerPanel.add(item);
		}
		
		runButton.addActionListener(buttonEvent);
		stopButton.addActionListener(buttonEvent);
		backButton.addActionListener(buttonEvent);
		bottomPanel.add(runButton);
		bottomPanel.add(stopButton);
		bottomPanel.add(backButton);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topLable, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		runButton.setEnabled(true);
		stopButton.setEnabled(false);
		backButton.setEnabled(true);
	}

	ActionListener buttonEvent = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == runButton) {
				for (TimeItem item : items) {
					item.setEnabled(false);
					if(item.isCheck()) {
						item.start();
						TimeItem.count++;
					}
				}
				topLable.setText("RUNNING");
				topLable.setForeground(Color.GREEN);
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				backButton.setEnabled(false);
			} else if (e.getSource() == stopButton) {
				for (TimeItem item : items) {
					item.setEnabled(true);
					if(item.isCheck()) {
						item.cancel();
					}
				}
				TimeItem.count = 0;
				topLable.setText("STOP");
				topLable.setForeground(Color.RED);
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				backButton.setEnabled(true);
			} else if (e.getSource() == backButton) {
				stopButton.doClick();
				dispose();
				Starter.frame.setVisible(true);
			}
		}
	};

}

@SuppressWarnings("serial")
class TimeItem extends JPanel {

	public static int count = 0;
	
	private JCheckBox checkbox = new JCheckBox("");
	private JTextField timefield = new JTextField("");
	private JTextField cmdfield = new JTextField("");
	private Timer timer;

	public TimeItem(boolean check, String time, String cmd) {
		this.check(check);
		this.timefield.setText(time);
		this.cmdfield.setText(cmd);

		timefield.setPreferredSize(new Dimension(80, 30));
		cmdfield.setPreferredSize(new Dimension(80, 30));
		setLayout(new FlowLayout());
		add(checkbox);
		add(timefield);
		add(cmdfield);
	}

	public void check(boolean check) {
		checkbox.setSelected(check);
	}
	
	public boolean isCheck() {
		return checkbox.isSelected();
	}
	
	public void setEnabled(boolean enable) {
		checkbox.setEnabled(enable);
		timefield.setEnabled(enable);
		cmdfield.setEnabled(enable);
	}

	public void start() {
		String[] time = timefield.getText().split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.set(
				calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DATE), 
				Integer.parseInt(time[0]), 
				Integer.parseInt(time[1]),
				Integer.parseInt(time[2]));
		timer = new Timer();
		timer.schedule(
			new TimerTask() {
				@Override
				public void run() {
					TimeItem.count--;
					if(TimeItem.count == 0) {
						AutoControl.stopButton.doClick();
					}
					SerialUtil.write(cmdfield.getText().getBytes());
				}
			}, calendar.getTime());
	}
	
	public void cancel() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}

}
