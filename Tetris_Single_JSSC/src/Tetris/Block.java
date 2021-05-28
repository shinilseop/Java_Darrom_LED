package Tetris;

import Serial.Serial;

public class Block extends Thread {
	int step;
	int[][] tetris;
	public BlockIndex bi[];
	public BlockIndex guide[];
	public int block_num;
	public BlockIndexArray bia;
	public int rotate;
	public boolean move;
	boolean isLand;
	Serial led;

	Block(BlockIndexArray bia, int step, int[][] tetris, int block_num, Serial led) {
		this.bia = bia;
		this.step = step;
		this.tetris = tetris;
		isLand = false;
		move=false;
		rotate = 0;
		this.block_num = block_num;
		bi = new BlockIndex[4];
		guide = new BlockIndex[4];
		setIndex();
		guide_make();
		this.led=led;
	}
	
	public void ledOn_Block() {
		for(int i=0;i<bi.length;i++) {
			int y=tetris.length-bi[i].y-1;
			int x=bi[i].x+5;
			led.ledON_Color(makeIndex(y,x), block_num);
		}
	}
	
	public void ledOff_Block() {
		for(int i=0;i<bi.length;i++) {
			int y=tetris.length-bi[i].y-1;
			int x=bi[i].x+5;
			led.ledOFF(makeIndex(y,x));
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

	public void setIndex() {
		for (int i = 0; i < bia.block_index[1].length; i++) {
			bi[i] = new BlockIndex(bia.block_index[block_num][i][0], bia.block_index[block_num][i][1]);
			guide[i] = new BlockIndex(bi[i].x, bi[i].y);
		}
		move=true;
	}

	public void isLand() {
		for (int i = 0; i < bi.length; i++) {
			if (bi[i].y == 21 || tetris[bi[i].y + 1][bi[i].x] > 0) {
				isLand = true;
				move=true;
				return;
			}
		}
	}

	public void run() {
		isLand();
		while (!isLand) {
			ledOff_Block();
			for (int i = 0; i < bi.length; i++) {
				bi[i].y += 1;
			}
			ledOn_Block();
			move=true;
			try {
				sleep(step);
			} catch (InterruptedException e) {
				System.out.println("Block Down Thread Error");
			}
			if (!isLand) 
				isLand();
		}
	}

	public void left() {
		if (!isLand) {
			for (int i = 0; i < bi.length; i++) {
				if (bi[i].x == 0 || tetris[bi[i].y][bi[i].x - 1] > 0) {
					return;
				}
			}
			ledOff_Block();
			for (int i = 0; i < bi.length; i++) {
				bi[i].x -= 1;
			}
			ledOn_Block();
			guide_make();
		}
	}

	public void right() {
		if (!isLand) {
			for (int i = 0; i < bi.length; i++) {
				if (bi[i].x == 9 || tetris[bi[i].y][bi[i].x + 1] > 0) {
					return;
				}
			}
			ledOff_Block();
			for (int i = 0; i < bi.length; i++) {
				bi[i].x += 1;
			}
			ledOn_Block();
			guide_make();
		}
	}

	public void rotate() {
		if (!isLand && block_num != 7) {//네모블록 제외
			if (block_num == 6) {// 막대블록인경우 회전
				int stdNum = rotate / 2 + 1;
				for (int i = 0; i < bia.sky_check[rotate].length; i++) {
					int xTmp = bi[stdNum].x + bia.sky_check[rotate][i][0];
					int yTmp = bi[stdNum].y + bia.sky_check[rotate][i][1];
					if (0 > xTmp || xTmp > 9 || yTmp < 0 || yTmp > 21 || tetris[yTmp][xTmp] > 0) {
						return;
					}
				}
				// 이동
				ledOff_Block();
				for (int i = 0; i < bi.length; i++) {
					bi[i].x += bia.sky_rotate[rotate][i][0];
					bi[i].y += bia.sky_rotate[rotate][i][1];
				}
				ledOn_Block();
			} else {//나머지 블록 회전
				for (int i = 0; i < bia.color_check[block_num][rotate].length; i++) {
					for (int j = 0; j < bi.length; j++) {
						int xTmp = bi[2].x + bia.check[bia.color_check[block_num][rotate][i]][0];
						int yTmp = bi[2].y + bia.check[bia.color_check[block_num][rotate][i]][1];
						if (0 > xTmp || xTmp > 9 || yTmp < 0 || yTmp > 21 || tetris[yTmp][xTmp] > 0) {
							if (bia.color_check[block_num][rotate][i] == 0 || bia.color_check[block_num][rotate][i] == 3
									|| bia.color_check[block_num][rotate][i] == 6) {
								for (int k = -1; k <= 1; k++) {
									if (bi[2].x + 2 == 10 || tetris[bi[2].y + k][bi[2].x + 2] > 0
											|| tetris[bi[2].y + k][bi[2].x + 1] > 0)
										break;
									if (k == 1) {
										for (int t = 0; t < bi.length; t++) {
											bi[t].x += 1;
										}
										xTmp += 1;
										break;
									}
									if (k == 1) {
										return;
									}
								}
							}
							if (bia.color_check[block_num][rotate][i] == 2 || bia.color_check[block_num][rotate][i] == 5
									|| bia.color_check[block_num][rotate][i] == 8) {
								for (int k = -1; k <= 1; k++) {
									if (bi[2].x - 2 == -1 || tetris[bi[2].y + k][bi[2].x - 2] > 0
											|| tetris[bi[2].y + k][bi[2].x - 1] > 0)
										break;
									if (k == 1) {
										for (int t = 0; t < bi.length; t++) {
											bi[t].x -= 1;
										}
										xTmp -= 1;
										break;
									}
									if (k == 1) {
										return;
									}
								}
							}
							if (0 > xTmp || xTmp > 9 || yTmp < 0 || yTmp > 21 || tetris[yTmp][xTmp] > 0) {
								return;
							}
						}
					}
				}
				// 이동
				ledOff_Block();
				for (int i = 0; i < bi.length; i++) {
					bi[i].x += bia.rotate[bi[i].idx][0];
					bi[i].y += bia.rotate[bi[i].idx][1];
					bi[i].idx = bia.after_rotate[bi[i].idx];
				}
				ledOn_Block();
			}
			rotate++;
			if (rotate == 4)
				rotate = 0;
			guide_make();
		}
	}

	public void down() {
		if (!isLand) {
			for (int i = 0; i < bi.length; i++) {
				if (bi[i].y == 21 || tetris[bi[i].y + 1][bi[i].x] > 0) {
					return;
				}
			}
			ledOff_Block();
			for (int i = 0; i < bi.length; i++) {
				bi[i].y += 1;
			}
			ledOn_Block();
			move=true;
		}
	}

	public void down_space() {
		if (!isLand) {
			boolean keep = true;
			ledOff_Block();
			while (keep) {
				for (int i = 0; i < bi.length; i++) {
					if (bi[i].y == tetris.length - 1 || tetris[bi[i].y + 1][bi[i].x] > 0) {
						keep = false;
						break;
					}
				}
				if (keep) {
					for (int i = 0; i < bi.length; i++) {
						bi[i].y += 1;
					}
				}
			}
			isLand();
		}
	}

	public void guide_make() {
		move=true;
		if (!isLand) {
			boolean keep = true;
			for (int i = 0; i < bi.length; i++) {
				guide[i].x = bi[i].x;
				guide[i].y = bi[i].y;
			}
			while (keep) {
				for (int i = 0; i < guide.length; i++) {
					if (guide[i].y == tetris.length - 1 || tetris[guide[i].y + 1][bi[i].x] > 0) {
						keep = false;
						break;
					}
				}
				if (keep) {
					for (int i = 0; i < bi.length; i++) {
						guide[i].y += 1;
					}
				}
			}
		}
	}
}
