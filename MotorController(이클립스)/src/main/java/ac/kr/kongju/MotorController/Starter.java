package ac.kr.kongju.MotorController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jssc.SerialPortList;

public class Starter {

	final static JLabel serialMSG = new JLabel("Com Port 연결해주세요~", JLabel.CENTER); 
	final static String[] buttons = { "Simple Packet Test", "Continuous Test", "Tracking Mode", "Exit" };
	final static JButton[] button = new JButton[buttons.length];
	
	public final static JFrame frame = new JFrame("실행 형식 선택");
	
	/**
	 * Program Starter (Main 함수)
	 * 프로그램에서 사용하는 Spectrum 그래프, DataViewer Panel, DataOption Panel,
	 * DisplayPanel, LightViewer Panel, Clibration 를 메모리에 Load함
	 * 보드와의 데이터 송수신을 수행하는 RXTXThread 객체를 run
	 */
	public static void main(String[] args) {
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.add(serialMSG);
		frame.add(new ComportPanel());
		
		for (int i = 0; i < button.length; i++) {
			button[i] = new JButton(buttons[i]);
			frame.add(button[i]);
			button[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (buttons[0].equals(event.getActionCommand())) {
						new SimpleControl().setVisible(true);
						frame.setVisible(false);
					} else if (buttons[1].equals(event.getActionCommand())) {
						new AutoControl().setVisible(true);
						frame.setVisible(false);
					} else if (buttons[2].equals(event.getActionCommand())) {
						new TrakingMode().setVisible(true);
						frame.setVisible(false);
					} else if (buttons[3].equals(event.getActionCommand())) {
						System.exit(0);
					}
				}
			});
		}
		enableButton(false);

		frame.setLayout(new GridLayout(6, 1));
		frame.setSize(300, 240);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
	}
	
	/**
	 * 보드와의 Connection 상태를 출력 (외부 호출 가능)
	 */
	public static void setMSG(String msg) {
		serialMSG.setText(msg);
	}

	/**
	 * 보드와의 Connection 상태에 따라 SimpleFrame, ContinousFrame을 활성/비활성화 처리함
	 */
	public static void enableButton(boolean what) {
		button[0].setEnabled(what);
		button[1].setEnabled(what);
		button[2].setEnabled(what);
	}
	
	/**
	 * 보드와의 Serial Connect/Disconnect의 사용자인터페이스를 제공 (JPanel)
	 */
	@SuppressWarnings("serial")
	static class ComportPanel extends JPanel {
		JComboBox<String> combo;
		JButton openBtn;

		/**
		 * Serial 통신 사용자 인터페이스에서 사용되는 Swing Component들을 설정
		 */
		public ComportPanel() {
			setLayout(new BorderLayout());
			combo = new JComboBox<String>(SerialPortList.getPortNames());
			openBtn = new JButton("Connect");
			openBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String cmd = event.getActionCommand();
					if(cmd.equals("Connect")) {
						if(!SerialUtil.isConnected()) {
							if(!SerialUtil.open(combo.getSelectedItem().toString())) {
								return;
							}
							button[0].setEnabled(true);
							button[1].setEnabled(true);
							openBtn.setText("Disconnect");
						}
					} else if(cmd.equals("Disconnect")) {
						if(SerialUtil.isConnected()) {
							SerialUtil.close();
							button[0].setEnabled(false);
							button[1].setEnabled(false);
							openBtn.setText("Connect");
						}
					}
					
				}
			});
			add(combo, BorderLayout.CENTER);
			add(openBtn, BorderLayout.EAST);
		}
	}
}