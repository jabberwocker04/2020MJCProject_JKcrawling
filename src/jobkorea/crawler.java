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
	 
	final static String URL = "http://www.jobkorea.co.kr/Search/"; // ����� URL
		
					////////////////////////////////////GUI////////////////////////////// 
	
	public JTextField tf = new JTextField(20); // ��������
	public JTextField tf2 = new JTextField(10); // ����
	public JTextArea ta = new JTextArea(10, 20);
	
	JButton nextbtn = new JButton(">>"); // next������ư
	JButton prevbtn = new JButton("<<"); // prev������ư
	JButton inputbtn = new JButton("�Է�");
	JButton resetbtn = new JButton("Reset");

	Panel north1 = new Panel(); // �˻�â�󺧰� �˻�â�ؽ�Ʈ�ʵ带 �Բ� ��ġ�ϱ� ���� �г�
	
	
	String local;
	String localNumber = "&tabType=recruit";
	String KEY_WORD = "";
	int PAGE = 1;
	
	public crawler() throws IOException { //GUI
		
		String []a = {"a","b","c","d","e"};
        String [][]b = {{"����","���","��õ","����","����"},
                        {"�泲","���","����","����","����"},
                        {"�뱸","���","�λ�","���","�泲"},
                        {"����","����","����","��������â"}};
        DefaultTableModel model = new DefaultTableModel(b,a);
        JTable table = new JTable(model);
        north1.add(table);
	     //JFrame���� ������ ������ȸ
        
        
		setTitle("JobKorea crawling Program"); // ���α׷� �̸�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������� ������ �޸𸮿��� ������ ����
		
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout(30,20)); //��ü ���̾ƿ��� Border���̾ƿ����� ����
		JLabel search = new JLabel("��������");
		JLabel search2 = new JLabel("����");
		//���� ���̾ƿ�
		//north1�� �˻�â�� �˻��� �ؽ�Ʈ�ʵ� �ֱ�
		
		north1.add(search2);
		north1.add(tf2);
		
		north1.add(search);
		north1.add(tf); // �гο� �˻�â�� �˻��ʵ� �ٿ���
		north1.add(inputbtn);
		north1.add(resetbtn);
		
	
		
		
		//���� ���̾ƿ��� �˻�â, �ؽ�Ʈ�ʵ�, ��ư�� �ٿ��� �г��� ���ʿ� ��ġ
		c.add(north1, BorderLayout.NORTH);
		
		
		
		//�߾� ���̾ƿ��� �ؽ�Ʈ�ʵ�� ũ�Ѹ� ��� ���
		c.add(new JScrollPane(ta), BorderLayout.CENTER);
		
		//���� ���̾ƿ��� ����(����Ű�� �Է��ϵ��� ����)	
		c.add(new JLabel("����� �˻�â�� ���������� �ۼ��ϰ� <ENTER>�� ��������. / �������� ������ �����Ͽ� �Է��ϼ���. "), BorderLayout.SOUTH);
		
		
		
		//���� ���̾ƿ��� ȭ��ǥ(1��->2��->3�� ��)
		c.add(nextbtn, BorderLayout.EAST);
		c.add(prevbtn, BorderLayout.WEST);
		nextbtn.addActionListener(new EventAction()); // >>��ư������ �׼Ǹ����ʸ޼ҵ� �۵�
		prevbtn.addActionListener(new EventAction()); // <<��ư������ �׼Ǹ����ʸ޼ҵ� �۵�
		
		
		//GUI ���� �������κ�

			
		//��ǲ�׼Ǹ����ʸ޼ҵ� 	
		inputbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					local = tf2.getText();	
					KEY_WORD = tf.getText(); // KEY_WORD�� textField�� �ִ� ������ ��(������)
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
		
		//������ư�׼Ǹ����ʸ޼ҵ�
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
		//������ư�׼Ǹ����ʸ޼ҵ�
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
		//�׼Ǹ����ʳ�
		
		setSize(1000,1000); // ������ ũ��
		setVisible(true);
		
		//thread0.start(); // ������ ����
	} // GUI �޼ҵ�
	
	
	Document doc = Jsoup.connect(URL + getParameter(KEY_WORD, PAGE, local)).get(); // URL �ҷ����� (doc ��ü���� �Ľ��� HTML �ڵ尡 ��) , parse�� XML�� �Ľ��� �� ���
	
	
	//�Է� ��ư�� ������ ũ�Ѹ� ������ �� �ְ� �ϴ� �޼ҵ�
	private void submitAction() throws IOException {
					local = tf2.getText();	
					KEY_WORD = tf.getText(); // KEY_WORD�� textField�� �ִ� ������ ��(������)
					
					if(local.equals("")) { // ���� �������� ����
						localNumber = "&tabType=recruit";}
					if(local.equals("����") ) {
						localNumber = "&local=I000";	}
					if(local.equals("���") ) { 
						localNumber = "&local=B000";	}	
					if(local.equals("��õ")) {
						localNumber = "&local=K000";}
					if(local.equals("����") ) {
						localNumber = "&local=G000";}	
					if(local.equals("����") ) {
						localNumber = "&local=1000";}
					if(local.equals("�泲") ) {
						localNumber = "&local=O000";}	
					if(local.equals("���")) {
						localNumber = "&local=P000";}
					if(local.equals("����")) {
						localNumber = "&local=E000";}
					if(local.equals("����")) {
						localNumber = "&local=L000";}
					if(local.equals("����")) {
						localNumber = "&local=M000";}
					if(local.equals("�뱸")) {
						localNumber = "&local=F000";}
					if(local.equals("���")) {
						localNumber = "&local=D000";}
					if(local.equals("�λ�")) {
						localNumber = "&local=H000";}
					if(local.equals("���")) {
						localNumber = "&local=J000";}
					if(local.equals("�泲")) {
						localNumber = "&local=C000";}
					if(local.equals("����")) {
						localNumber = "&local=A000";}
					if(local.equals("����")) {
						localNumber = "&local=N000";}
					if(local.equals("����")) {
					    localNumber = "&local=Q000";}
					
			       
			       
			       Document doc = Jsoup.connect(URL + realgetParameter(KEY_WORD, PAGE, localNumber)).get(); // URL �ҷ����� (doc ��ü���� �Ľ��� HTML �ڵ尡 ��)
					
					// ����� �����´�.
					Elements elements = doc.select(".list-post"); //���� Ŭ���� : . , ID : #
						
					
					
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
	
	//URL�� �ҷ���
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
	

	// next class, ���� ��ư
	
	
	
	//GUI �����ϴ� ���θ޼ҵ�
	public static void main(String[] args) throws IOException {
		
		new crawler();
		
		
		}
	}
