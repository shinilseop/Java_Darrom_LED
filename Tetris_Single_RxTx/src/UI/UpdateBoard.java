package UI;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import DBMS.DBMS;
import Main.Main;
import Serial.Serial;
import Tetris.Tetris;

public class UpdateBoard extends Thread {
	String id;
	JLabel jlBlock[][];
	JLabel jlNext;
	JLabel jlScore;
	JLabel jlTimer;
	UIChanger uic;

	ImageIcon iiBlock[];
	ImageIcon iiBlockFull[];
	LineBorder lbGuide[];
	Tetris t;

	Serial led;

	UpdateBoard(Serial led, String id, JLabel jlBlock[][], JLabel jlNext, JLabel jlScore, JLabel jlTimer, Tetris t,
			UIChanger uic) {
		this.led = led;
		this.id = id;
		this.jlBlock = jlBlock;
		this.jlNext = jlNext;
		this.jlScore = jlScore;
		this.jlTimer = jlTimer;
		this.t = t;
		this.uic = uic;
		iiBlock = new ImageIcon[9];
		iiBlockFull = new ImageIcon[8];
		iiBlock[0] = scaledImage("image/black_block.png", 1, 1);
		iiBlock[1] = scaledImage("image/blue_block.png", 1, 1);
		iiBlock[2] = scaledImage("image/green_block.png", 1, 1);
		iiBlock[3] = scaledImage("image/orange_block.png", 1, 1);
		iiBlock[4] = scaledImage("image/purple_block.png", 1, 1);
		iiBlock[5] = scaledImage("image/red_block.png", 1, 1);
		iiBlock[6] = scaledImage("image/sky_block.png", 1, 1);
		iiBlock[7] = scaledImage("image/yellow_block.png", 1, 1);
		iiBlock[8] = scaledImage("image/grey_block.png", 1, 1);
		iiBlockFull[0] = null;
		iiBlockFull[1] = scaledImage("image/blue_block_full.png", 3, 2);
		iiBlockFull[2] = scaledImage("image/green_block_full.png", 3, 2);
		iiBlockFull[3] = scaledImage("image/orange_block_full.png", 3, 2);
		iiBlockFull[4] = scaledImage("image/purple_block_full.png", 3, 2);
		iiBlockFull[5] = scaledImage("image/red_block_full.png", 3, 2);
		iiBlockFull[6] = scaledImage("image/sky_block_full.png", 4, 1);
		iiBlockFull[7] = scaledImage("image/yellow_block_full.png", 2, 2);
		lbGuide = new LineBorder[8];
		lbGuide[0] = null;
		lbGuide[1] = new LineBorder(Color.blue, 1);
		lbGuide[2] = new LineBorder(Color.green, 1);
		lbGuide[3] = new LineBorder(Color.orange, 1);
		lbGuide[4] = new LineBorder(Color.magenta, 1);
		lbGuide[5] = new LineBorder(Color.red, 1);
		lbGuide[6] = new LineBorder(Color.cyan, 1);
		lbGuide[7] = new LineBorder(Color.yellow, 1);
		Main.isRun = true;
	}

	public ImageIcon scaledImage(String File, int x, int y) {
		Image iTmp = new ImageIcon(File).getImage().getScaledInstance(40 * x, 40 * y, Image.SCALE_SMOOTH);
		return new ImageIcon(iTmp);
	}

	public void packetSend() {
		for (int i = 21; i >= 0; i--) {
			for (int j = 0; j < t.tetris[0].length; j++) {
				int y = t.tetris.length - i - 1;
				int x = j + 5;
				String index = "";
				if (y >= 16) {
					index += "1" + (y - 16);
				} else if (y >= 10) {
					char ch = (char) (y + 55);
					index += "0" + ch;
				} else {
					index += "0" + y;
				}

				if (x >= 16) {
					index += "1" + (x - 16);
				} else if (x >= 10) {
					char ch = (char) (x + 55);
					index += "0" + ch;
				} else {
					index += "0" + x;
				}
				if (t.tetris[i][j] > 0) {
					led.GameOver(index);
				}
			}
		}
	}

	public void run() {
		while (Main.isRun) {
			if (t.now.move) {
				for (int i = 2; i < t.tetris.length; i++) {
					for (int j = 0; j < t.tetris[0].length; j++) {
						jlBlock[i - 2][j].setIcon(iiBlock[t.tetris[i][j]]);
						jlBlock[i - 2][j].setBorder(null);
					}
				}
				for (int i = 0; i < t.now.guide.length; i++) {
					if (t.now.guide[i].y >= 2)
						jlBlock[t.now.guide[i].y - 2][t.now.guide[i].x].setBorder(lbGuide[t.now.block_num]);
				}
				for (int i = 0; i < t.now.bi.length; i++) {
					if (t.now.bi[i].y >= 2)
						jlBlock[t.now.bi[i].y - 2][t.now.bi[i].x].setIcon(iiBlock[t.now.block_num]);
				}
				jlNext.setIcon(iiBlockFull[t.next]);
				jlScore.setText("Score : " + t.score);
				t.now.move = false;
			}
			jlTimer.setText("time : " + t.timer.time);

			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 게임오버이후 정렬(회색)
		for (int i = 2; i < t.tetris.length; i++) {
			for (int j = 0; j < t.tetris[0].length; j++) {
				if (t.tetris[i][j] > 0)
					t.tetris[i][j] = 8;
			}
		}
		for (int i = 2; i < t.tetris.length; i++) {
			for (int j = 0; j < t.tetris[0].length; j++) {
				if (t.tetris[i][j] > 0) {
					jlBlock[i - 2][j].setIcon(iiBlock[t.tetris[i][j]]);
				}
			}
		}
		packetSend();

		jlTimer.setText("time : " + t.timer.time);
		jlScore.setText("Score : " + t.score);

		if (new DBMS().record(id, t.score) == 0) {
			JOptionPane.showMessageDialog(null, t.score + " 점 입니다!", "게임 오버!", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, t.score + " 점 입니다!", "최고 기록 갱신!", JOptionPane.INFORMATION_MESSAGE);
		}

		uic.goMenu(0);
	}
}
