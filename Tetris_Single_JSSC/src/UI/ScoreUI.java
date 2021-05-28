package UI;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import DBMS.DBMS;
import DBMS.Score;

public class ScoreUI extends UI {
	Font fontTitle, fontContent;

	JLabel jlRanking;
	DBMS dbms;
	ArrayList<Score> scoreList;
	JLabel jlRank[], jlName[], jlScore[];
	JLabel jlMyBest;
	
	int y, myScore;


	ScoreUI(UIChanger uic) {
		super(uic);
		fontTitle = new Font("굴림", Font.BOLD, 20);
		fontContent = new Font("굴림", Font.BOLD, 15);

		//init_Title
		jlRanking = new JLabel("랭킹");
		jlRanking.setHorizontalAlignment(JLabel.CENTER);
		jlRanking.setFont(fontTitle);
		
		//init_Content
		dbms = new DBMS();
		scoreList=dbms.selectScore();
		myScore=dbms.getScore(uic.getId());
		jlRank=new JLabel[scoreList.size()+1];
		jlName=new JLabel[scoreList.size()+1];
		jlScore=new JLabel[scoreList.size()+1];
		
		jlRank[0]=new JLabel("순위");
		jlRank[0].setHorizontalAlignment(JLabel.CENTER);
		jlRank[0].setFont(fontContent);
		jlName[0]=new JLabel("이름");
		jlName[0].setHorizontalAlignment(JLabel.CENTER);
		jlName[0].setFont(fontContent);
		jlScore[0]=new JLabel("점수");
		jlScore[0].setHorizontalAlignment(JLabel.CENTER);
		jlScore[0].setFont(fontContent);
		
		for(int i=1;i<=scoreList.size();i++) {
			jlRank[i]=new JLabel((i)+"");
			jlRank[i].setHorizontalAlignment(JLabel.CENTER);
			jlRank[i].setFont(fontContent);
			jlName[i]=new JLabel(scoreList.get(i-1).getId());
			jlName[i].setHorizontalAlignment(JLabel.CENTER);
			jlName[i].setFont(fontContent);
			jlScore[i]=new JLabel(scoreList.get(i-1).getScore()+"");
			jlScore[i].setHorizontalAlignment(JLabel.CENTER);
			jlScore[i].setFont(fontContent);
		}
		jlMyBest=new JLabel("나의 최고 점수 : "+myScore);
		jlMyBest.setHorizontalAlignment(JLabel.CENTER);
		
		y=40;
		
		Container ct=getContentPane();
		ct.setLayout(null);
		ct.add(jlRanking);
		jlRanking.setBounds(0, 10, 230, 20);
		for(int i=0;i<=scoreList.size();i++) {
			jlRank[i].setBounds(10, y, 30, 25);
			jlName[i].setBounds(50, y, 130, 25);
			jlScore[i].setBounds(190 , y, 60, 25);
			y+=25;
			ct.add(jlRank[i]);
			ct.add(jlName[i]);
			ct.add(jlScore[i]);
		}
		jlMyBest.setBounds(0, 320, 230, 25);
		ct.add(jlMyBest);
		
		addKeyListener(this);
		setFocusable(true);
		setTitle("랭킹");
		setSize(270, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			uic.goMenu(2);
		}   
	}
}
