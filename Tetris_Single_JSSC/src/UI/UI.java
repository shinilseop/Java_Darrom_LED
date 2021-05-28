package UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class UI extends JFrame implements KeyListener {
	UIChanger uic;

	UI(UIChanger uic) {
		this.uic = uic;
	}

	public void closeThis() {
		removeKeyListener(this);
		setFocusable(false);
		setVisible(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
