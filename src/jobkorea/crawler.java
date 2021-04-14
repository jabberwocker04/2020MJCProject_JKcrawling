package jobkorea;

import java.io.IOException;
import java.lang.Thread;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

import java.util.Scanner;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

	
public class crawler extends JFrame {
	int idx = 0;
	 
	final static String URL = "http://www.jobkorea.co.kr/Search/"; // 사용할 URL
		
					////////////////////////////////////GUI////////////////////////////// 
	
	public JTextField tf = new JTextField(20); // 구직정보
	public JTextField tf2 = new JTextField(10); // 지역
	public JTextArea ta = new JTextArea(10, 20);
	
	JButton nextbtn = new JButton(">>"); // next다음버튼
	JButton prevbtn = new JButton("<<"); // prev이전버튼
	JButton inputbtn = new JButton("입력");
	JButton resetbtn = new JButton("Reset");

	Panel north1 = new Panel(); // 검색창라벨과 검색창텍스트필드를 함께 배치하기 위한 패널
	
	
	String local;
	String localNumber = "&tabType=recruit";
	String KEY_WORD = "";
	int PAGE = 1;
	
	public crawler() throws IOException { //GUI
		
		String []a = {"a","b","c","d","e"};
        String [][]b = {{"서울","경기","인천","대전","세종"},
                        {"충남","충북","광주","전남","전북"},
                        {"대구","경북","부산","울산","경남"},
                        {"강원","제주","전국","지역정보창"}};
        DefaultTableModel model = new DefaultTableModel(b,a);
        JTable table = new JTable(model);
        north1.add(table);
	     //JFrame으로 간단한 지역조회
        
        
		setTitle("JobKorea crawling Program"); // 프로그램 이름
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫으면 메모리에서 완전히 제거
		
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout(30,20)); //전체 레이아웃은 Border레이아웃으로 설정
		JLabel search = new JLabel("구직정보");
		JLabel search2 = new JLabel("지역");
		//북쪽 레이아웃
		//north1에 검색창과 검색할 텍스트필드 넣기
		
		north1.add(search2);
		north1.add(tf2);
		
		north1.add(search);
		north1.add(tf); // 패널에 검색창과 검색필드 붙여줌
		north1.add(inputbtn);
		north1.add(resetbtn);
		
	
		
		
		//북쪽 레이아웃에 검색창, 텍스트필드, 버튼을 붙여줄 패널을 북쪽에 배치
		c.add(north1, BorderLayout.NORTH);
		
		
		
		//중앙 레이아웃은 텍스트필드로 크롤링 결과 출력
		c.add(new JScrollPane(ta), BorderLayout.CENTER);
		
		//남쪽 레이아웃은 설명(엔터키를 입력하도록 유도)	
		c.add(new JLabel("상단의 검색창에 관련직종을 작성하고 <ENTER>를 누르세요. / 지역란에 지역을 선택하여 입력하세요. "), BorderLayout.SOUTH);
		
		
		
		//동서 레이아웃은 화살표(1탭->2탭->3탭 등)
		c.add(nextbtn, BorderLayout.EAST);
		c.add(prevbtn, BorderLayout.WEST);
		nextbtn.addActionListener(new EventAction()); // >>버튼누를시 액션리스너메소드 작동
		prevbtn.addActionListener(new EventAction()); // <<버튼누를시 액션리스너메소드 작동
		
		
		//GUI 구조 마지막부분

			
		//인풋액션리스너메소드 	
		inputbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					local = tf2.getText();	
					KEY_WORD = tf.getText(); // KEY_WORD를 textField에 있는 것으로 함(가져옴)
					submitAction();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
		});
		
		resetbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.setText(null);
				}
		});
		
		//다음버튼액션리스너메소드
		nextbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
					
				try {
					++PAGE;
					submitAction();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
			
		});
		//이전버튼액션리스너메소드
		prevbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
					
				try {
					--PAGE;
					submitAction();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
			
		});
		//액션리스너끝
		
		setSize(1000,1000); // 프레임 크기
		setVisible(true);
		
		//thread0.start(); // 쓰레드 시작
	} // GUI 메소드
	
	
	Document doc = Jsoup.connect(URL + getParameter(KEY_WORD, PAGE, local)).get(); // URL 불러오기 (doc 객체에는 파싱한 HTML 코드가 들어감) , parse는 XML을 파싱할 때 사용
	
	
	//입력 버튼을 누르면 크롤링 실행할 수 있게 하는 메소드
	private void submitAction() throws IOException {
					local = tf2.getText();	
					KEY_WORD = tf.getText(); // KEY_WORD를 textField에 있는 것으로 함(가져옴)
					
					if(local.equals("")) { // 지역 선택하지 않음
						localNumber = "&tabType=recruit";}
					if(local.equals("서울") ) {
						localNumber = "&local=I000";	}
					if(local.equals("경기") ) { 
						localNumber = "&local=B000";	}	
					if(local.equals("인천")) {
						localNumber = "&local=K000";}
					if(local.equals("대전") ) {
						localNumber = "&local=G000";}	
					if(local.equals("세종") ) {
						localNumber = "&local=1000";}
					if(local.equals("충남") ) {
						localNumber = "&local=O000";}	
					if(local.equals("충북")) {
						localNumber = "&local=P000";}
					if(local.equals("광주")) {
						localNumber = "&local=E000";}
					if(local.equals("전남")) {
						localNumber = "&local=L000";}
					if(local.equals("전북")) {
						localNumber = "&local=M000";}
					if(local.equals("대구")) {
						localNumber = "&local=F000";}
					if(local.equals("경북")) {
						localNumber = "&local=D000";}
					if(local.equals("부산")) {
						localNumber = "&local=H000";}
					if(local.equals("울산")) {
						localNumber = "&local=J000";}
					if(local.equals("경남")) {
						localNumber = "&local=C000";}
					if(local.equals("강원")) {
						localNumber = "&local=A000";}
					if(local.equals("제주")) {
						localNumber = "&local=N000";}
					if(local.equals("전국")) {
					    localNumber = "&local=Q000";}
					
			       
			       
			       Document doc = Jsoup.connect(URL + realgetParameter(KEY_WORD, PAGE, localNumber)).get(); // URL 불러오기 (doc 객체에는 파싱한 HTML 코드가 들어감)
					
					// 목록을 가져온다.
					Elements elements = doc.select(".list-post"); //사용법 클래스 : . , ID : #
						
					
					
						for(Element element : elements) {
							ta.append(++idx+ " : " + element.text()+ "\n");
							ta.append("==========================================================\n\n");
							}
							ta.append(("URL :: " +  URL + getParameter(KEY_WORD, PAGE, localNumber) + "\n\n"));
						
							System.out.println(KEY_WORD);
						       System.out.println(localNumber);
						       System.out.println("URL :: " +  URL + getParameter(KEY_WORD, PAGE, localNumber));
						       System.out.println("realURL :: " +  URL + realgetParameter(KEY_WORD, PAGE, localNumber));
			}
	
	//URL을 불러옴
	public static String getParameter(String KEY_WORD, int PAGE, String localNumber) {
		String params = "?stext=" 
				+ KEY_WORD
				+ localNumber 
				+ "&Page_No="
				+ PAGE;
		return params;
	}
	
	public static String realgetParameter(String KEY_WORD, int PAGE, String localNumber) {
		String params = "RecruitList?stext=" 
				+ KEY_WORD 
				+ localNumber
				+ "&Page_No="
				+ PAGE;
		return params;
	}
	
	
	//nextbtn, prevbtn
	

	// next class, 다음 버튼
	
	
	
	//GUI 실행하는 메인메소드
	public static void main(String[] args) throws IOException {
		
		new crawler();
		
		
		}
	}
