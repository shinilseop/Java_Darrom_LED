import java.util.Scanner;

public class Test1 {
	static int[][] tetris=new int[22][10];
	public static void main(String args[]) {
		packetSend();
	}
	
	public static void packetSend() {
		for(int i=2;i<tetris.length;i++) {
			for(int j=0;j<tetris[0].length;j++) {
				int y=i-2;
				int x=j;
				String index="";
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
				System.out.println(index);
			}
		}
	}
}
