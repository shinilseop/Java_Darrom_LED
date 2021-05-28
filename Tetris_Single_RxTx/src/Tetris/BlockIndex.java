package Tetris;

public class BlockIndex {
	public int x,y,idx;//idx=내가 9칸중 몇번쨰 블록인지
	public BlockIndex(int x, int y) {
		this.x=x;
		this.y=y;
		idx=(x-4)+(y*3);
	}
}
