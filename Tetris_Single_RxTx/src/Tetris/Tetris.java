package Tetris;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import Main.Main;
import Serial.Serial;

public class Tetris extends Thread {
	public int tetris[][];
	public int score;
	public int next;
	public int step;
	public Block now;
	public Timer timer;
	public BlockIndexArray bia;

	HashMap<BlockIndex, Boolean> way;
	BlockIndex[][] bi_way;
	Serial led;
	int end = 2;

	public Tetris(Serial led) {
		bia = new BlockIndexArray();
		way = new HashMap<>();
		bi_way = new BlockIndex[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				bi_way[i][j] = new BlockIndex(j, i);
				way.put(bi_way[i][j], false);
			}
		}
		step = 1000;
		tetris = new int[22][10];
		score = 0;
		timer = new Timer();
		timer.callback(new SetStep() {
			@Override
			public void setStep(int nextStep) {
				// TODO Auto-generated method stub
				step = nextStep;
			}
		});
		this.led = led;
		now = new Block(bia, step, tetris, new Random().nextInt(7) + 1, led, way, bi_way);
		next = new Random().nextInt(7) + 1;
		now.start();
	}

	public void isGameOver() {
		for (int i = 0; i < tetris[0].length; i++) {
			if (tetris[0][i] > 0) {
				Main.isRun = false;
				return;
			}
		}
		for (int i = 0; i < tetris[0].length; i++) {
			if (tetris[1][i] > 0) {
				Main.isRun = false;
				return;
			}
		}
	}

	public void isLine() {
		int start = -1;
		for (int i = 21; i >= 2; i--) {
			if (tetris[i][0] > 0) {
				for (int j = 1; j < tetris[0].length; j++) {
					if (tetris[i][j] == 0)
						break;
					if (j == tetris[0].length - 1) {
						if (start == -1) {
							getMaxLine();
							start = i;
						}
						removeLine(i);
						i++;
					}
				}
			}
		}
		if (start != -1) {
			ledOn_Line(start);
		}
	}

	public void removeLine(int line) {
		for (int i = line; i >= 2; i--) {
			for (int j = 0; j < tetris[line].length; j++) {
				tetris[i][j] = tetris[i - 1][j];
			}
		}
		now.guide_make();
	}

	public void ledOn_Line(int start) {
		for (int i = end - 1; i <= start; i++) {
			for (int j = 0; j < tetris[0].length; j++) {
				int y = tetris.length - 1 - i;
				int x = j + 5;
				if (tetris[i][j] > 0) {
					led.ledON_Color(makeIndex(y, x), tetris[i][j]);
				} else {
					led.ledOFF(makeIndex(y, x));
				}
			}
		}
	}

	public String makeIndex(int y, int x) {
		StringBuffer index = new StringBuffer();
		if (y >= 16) {
			index.append("1" + (y - 16));
		} else if (y >= 10) {
			char ch = (char) (y + 55);
			index.append("0" + ch);
		} else {
			index.append("0" + y);
		}

		if (x >= 16) {
			index.append("1" + (x - 16));
		} else if (x >= 10) {
			char ch = (char) (x + 55);
			index.append("0" + ch);
		} else {
			index.append("0" + x);
		}
		return index.toString();
	}

	public void getMaxLine() {
		for (int i = 2; i < tetris.length; i++) {
			for (int j = 0; j < tetris[0].length; j++) {
				if (tetris[i][j] > 0) {
					end = i;
					return;
				}
			}
		}
	}

	public void tetrisMaker() {
		for (int j = 0; j < now.bi.length; j++) {
			tetris[now.bi[j].y][now.bi[j].x] = now.block_num;
		}
	}

	public void setZero() {
		for (int i = 21; i >= 0; i--) {
			for (int j = 0; j < tetris[0].length; j++) {
				if (tetris[i][j] == 0) {
					int y = tetris.length - i - 1;
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
					led.ledOFF(index);
				}
			}
		}
	}

	public void setZeroWay() {
		Iterator<BlockIndex> mapIter = now.way.keySet().iterator();
		while (mapIter.hasNext()) {
			BlockIndex key = mapIter.next();
			boolean value = now.way.get(key);
			if (value) {
				way.put(key, false);
				int y = tetris.length - key.y - 1;
				int x = key.x + 5;
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
				led.ledOFF(index);
			}
		}
	}

	public void run() {
		while (Main.isRun) {
			if (now.isLand) {
				setZeroWay();
				tetrisMaker();
				now.ledOn_Block();
				isLine();
				isGameOver();
				try {
					sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				score += ((timer.time / 10) + 1) * 50;
				now = new Block(bia, step, tetris, next, led, way, bi_way);
				next = new Random().nextInt(7) + 1;
				now.start();
			}

			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
