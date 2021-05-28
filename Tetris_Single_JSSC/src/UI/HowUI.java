package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HowUI extends UI implements KeyListener {
	Font fTitle, fEx;

	JPanel jpContent;
	JLabel jlTitle, jlEx;

	Container ct;
	
	public HowUI(UIChanger uic) {
		super(uic);
		fTitle=new Font("굴림", Font.BOLD, 30);
		fEx=new Font("굴림", Font.BOLD, 13);
		
		jlTitle=new JLabel("게임 설명");
		jlTitle.setFont(fTitle);
		
		jpContent=new JPanel(new GridLayout(13,1));
		String content[]= {" 위에서 블록들이 내려올때 화살표와 ",
				" 스페이스바를 이용하여 밑까지 잘 도달하세요.",
				" 쭉 블록을 쌓다가 한줄을 채우게 되면",
				" 그 줄은 사라지고 위에 블록들은 한줄 내려옵니다.",
				" 점수는 블록이 쌓을때마다 진행된 시간에 따라 점수를 지급합니다.", 
				" 높은 점수를 획득해 보세요 !",
				"",
				"                                 조작키",
				" (← = 왼쪽으로 이동, → = 오른쪽으로 이동, ↑ = 블록 회전, ",
				" ↓ = 한칸 아래로 이동, SPACE BAR = 맨 아래로 이동)",
				"",
				"",
				" 다시 메뉴로 돌아가시려면 <- 키를 눌러주세요."};
		for(int i=0;i<content.length;i++) {
			JLabel tmp=new JLabel(content[i]);
			tmp.setFont(fEx);
			jpContent.add(tmp);
		}
		

		ct=getContentPane();
		ct.setLayout(new BorderLayout());
		ct.add(jlTitle, BorderLayout.NORTH);
		ct.add(jpContent, BorderLayout.CENTER);

		addKeyListener(this);
		setFocusable(true);
		setTitle("Select");
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
			uic.goMenu(1);
		}
	}
}
