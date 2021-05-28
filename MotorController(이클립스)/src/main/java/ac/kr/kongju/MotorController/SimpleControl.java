package ac.kr.kongju.MotorController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SimpleControl extends JFrame {

	private final JButton forwardDirBtn = new JButton("시계방향");
	private final JButton reverseDirBtn = new JButton("시계반대방향");
	private final JButton backBtn = new JButton("뒤로가기");
	private final JTextField timeText = new JTextField("1");
	
	public SimpleControl() {
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(500, 60);
		setResizable(false);
		setLayout(new GridLayout(1, 3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
		
		forwardDirBtn.addActionListener(buttonEvent);
		reverseDirBtn.addActionListener(buttonEvent);
		backBtn.addActionListener(buttonEvent);
		
		add(forwardDirBtn);
		add(timeText);
		add(reverseDirBtn);
		add(backBtn);
	}
	
	ActionListener buttonEvent = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == forwardDirBtn) {
				String cmd = "0 " + timeText.getText();
				SerialUtil.write(cmd.getBytes());
			} else if(e.getSource() == reverseDirBtn) {
				String cmd = "1 " + timeText.getText();
				SerialUtil.write(cmd.getBytes());
			} else if(e.getSource() == backBtn) {
				dispose();
				Starter.frame.setVisible(true);
			}
		}
	};
	
}
