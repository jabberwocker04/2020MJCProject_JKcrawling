package jobkorea;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class test extends JFrame {
	public static void main(String[] args) {
		new test();
	}
public test(){
        
        String []a = {"a","b","c"};
        String [][]b = {{"a1","a2","a3"},
                        {"b1","b2","b3"},
                        {"c1","c2","c3"}};
        
        //1. �𵨰� �����͸� ����
        DefaultTableModel model = new DefaultTableModel(b,a);
        
        //2. Model�� �Ű������� ����, new JTable(b,a)�� ���������� 
        //���� ������ �ϱ� ���� �ش� ����� ����մϴ�
        JTable table = new JTable(model); //
        
        //3. ��������δ� JScrollPane�� �߰��մϴ�.
        JScrollPane sc = new JScrollPane(table);
        
        //4. ������Ʈ��  Table �߰�
        add(sc);
        
        //���̺� ������ �߰��ϱ�
        //���������͸� �ǵ��� �ʰ� table�� �Ű������� model�� �ִ� �����͸� �����մϴ�
        DefaultTableModel m =
    (DefaultTableModel)table.getModel();
        //�𵨿� ������ �߰� , 1��° �⿡ ���ο� �����͸� �߰��մϴ�
        m.insertRow(1, new Object[]{"d1","d2","d3"});
        //�߰��� ��ġ�� ������ ������ �˸��ϴ�.
        table.updateUI();
    
        
        
        //------- �� �� �޼ҵ�� ----------
        
        //���̺��� �����͸� �������� �޼ҵ�
        System.out.println(table.getValueAt(1,1));
        //���̺��� �����͸� �ٲٴ� �޼ҵ�
        table.setValueAt("picachu",2,2);
        //���̺� row ���� ��������
        System.out.println(table.getRowCount());
        //���̺� colum ���� ��������
        System.out.println(table.getColumnCount());
        //���̺� Colum �̸� ��������
        System.out.println(table.getColumnName(0));
        
        setBounds(0, 0, 300, 150);
        setVisible(true);
        
    }
}