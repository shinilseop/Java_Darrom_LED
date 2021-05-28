package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Serial.Serial;
import Tetris.Tetris;

public class GameUI extends UI {
	Font font;

	JPanel jpTetris;
	JLabel jlBlock[][];
	
	ImageIcon iiNext;
	JPanel jpOther;
	JLabel jlNextT, jlNext, jlScore, jlTimer;

	Tetris t;
	UpdateBoard ub;
	
	Serial led;

	GameUI(UIChanger uic, Serial led) {
		super(uic);
		this.led=led;
		font = new Font("굴림", Font.BOLD, 25);

		// init_left
		jpTetris = new JPanel(new GridLayout(20, 10));
		jlBlock = new JLabel[20][10];
		for (int i = 0; i < jlBlock.length; i++) {
			for (int j = 0; j < jlBlock[0].length; j++) {
				jlBlock[i][j] = new JLabel(new ImageIcon("image/black_block.png"));
			}
		}

		// init_right
		jpOther = new JPanel(null);
		jpOther.setBackground(Color.GRAY);
		iiNext = scaledImage(null);
		jlNextT = new JLabel("NEXT");
		jlNextT.setHorizontalAlignment(JLabel.CENTER);
		jlNextT.setFont(font);
		jlNextT.setForeground(Color.white);
		jlNextT.setBounds(50, 20, 200, 30);
		jlNext = new JLabel(iiNext);
		jlNext.setBorder(new LineBorder(Color.white, 4));
		jlNext.setBounds(60, 80, 180, 150);
		jlScore = new JLabel("SCORE : 0");
		jlScore.setHorizontalAlignment(JLabel.CENTER);
		jlScore.setFont(font);
		jlScore.setForeground(Color.white);
		jlScore.setBounds(50, 350, 200, 30);
		jlTimer = new JLabel("TIME : 0 s");
		jlTimer.setHorizontalAlignment(JLabel.CENTER);
		jlTimer.setFont(font);
		jlTimer.setForeground(Color.white);
		jlTimer.setBounds(50, 500, 200, 30);

		t = new Tetris(led);
		ub = new UpdateBoard(led, uic.getId(), jlBlock, jlNext, jlScore, jlTimer, t, uic);
		
		Container ct = getContentPane();
		ct.setLayout(null);
		jpTetris.setBounds(0, 0, 400, 800);
		ct.add(jpTetris);
		jpOther.setBounds(400, 0, 282, 800);
		ct.add(jpOther);

		addKeyListener(this);
		setFocusable(true);
		setTitle("테트리스");
		setSize(679, 829);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// set_left
		for (int i = 0; i < jlBlock.length; i++) {
			for (int j = 0; j < jlBlock[0].length; j++) {
				jpTetris.add(jlBlock[i][j]);
			}
		}

		// set_right
		jpOther.add(jlNextT);
		jpOther.add(jlNext);
		jpOther.add(jlScore);
		jpOther.add(jlTimer);
		
		ub.start();
		t.start();
		t.timer.start();
	}
	
	public ImageIcon scaledImage(String File) {
		Image iTmp=new ImageIcon(File).getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
		return new ImageIcon(iTmp);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			t.now.rotate();
		} else if (key == KeyEvent.VK_LEFT) {
			t.now.left();
		} else if (key == KeyEvent.VK_RIGHT) {
			t.now.right();
		} else if (key == KeyEvent.VK_DOWN) {
			t.now.down();
		} else if (key == KeyEvent.VK_SPACE) {
			t.now.down_space();
		}
	}
}
