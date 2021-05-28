package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TitleUI extends UI implements Runnable {
	Font font;

	ImageIcon iiBack;
	JLabel jlPress, jlBack;

	boolean isStart;

	public TitleUI(UIChanger uic) {
		super(uic);
		font = new Font("굴림", Font.BOLD, 15);
		iiBack = new ImageIcon("image/TetrisTitle.png");

		jlPress = new JLabel("Press Any Key");
		jlPress.setFont(font);
		jlPress.setForeground(Color.white);
		jlPress.setBounds(200, 330, 200, 50);
		jlBack = new JLabel(iiBack);
		jlBack.setBounds(0, 0, 512, 388);

		Container ct = getContentPane();
		ct.setLayout(null);
		ct.add(jlPress);
		ct.add(jlBack);

		addKeyListener(this);
		setFocusable(true);
		setTitle("Title");
		setSize(518, 417);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		isStart = true;

		new Thread(this).start();
	}

	public void run() {
		boolean pressVisible = true;
		int timer = 0;
		while (true) {
			if (isStart) {
				if (timer == 500) {
					jlPress.setVisible(!pressVisible);
					pressVisible = !pressVisible;
					timer = 0;
				} else {
					timer++;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		isStart = false;
		uic.goLogin();
	}
}
