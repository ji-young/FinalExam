package FinalExam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Swing extends JFrame{
	LibraryManager manager = new LibraryManager();
	JPanel p_north = new JPanel();
	JPanel p_center = new JPanel();
	JPanel p_south = new JPanel();
	JScrollPane scroll;
	JTable table = new JTable();
	DefaultTableModel tableModel;
	Vector<String> tableRow = new Vector<String>();
	Vector<String> tableColumn = new Vector<String>();
	String[] columnNames = {"isbn","제목","저자","출판년도","출판사","인기도","대출상태"};
	String f_label = "";
	JTextField[] tf = new JTextField[6];
	JTextField[] mtf = new JTextField[6];
	JTextField t_find;
	ButtonActionListener btnListener = new ButtonActionListener();
	MenuActionListener menuListener = new MenuActionListener();
	int popularity = 0;
	Swing(){
		super("Library Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JButton bt1 = new JButton("도서추가");
		JButton bt2 = new JButton("도서수정");
		JButton bt3 = new JButton("도서삭제");
		JButton bt4 = new JButton("도서검색");
		JButton bt5 = new JButton("연체목록");
		p_north.add(bt1);
		p_north.add(bt2);
		p_north.add(bt3);
		p_north.add(bt4);
		p_north.add(bt5); // 버튼
		
		JButton bt6 = new JButton("대출");
		JButton bt7 = new JButton("반납");
		
		bt1.addActionListener(btnListener);
		bt2.addActionListener(btnListener);
		bt3.addActionListener(btnListener);
		bt4.addActionListener(btnListener);
		bt5.addActionListener(btnListener);
		bt6.addActionListener(btnListener);
		bt7.addActionListener(btnListener);
		
	
		tableModel = new DefaultTableModel(columnNames, 0){public boolean isCellEditable(int rowIndex, int mColIndex) {return false;}};
		// 테이블 수정금지설정
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
		
		scroll = new JScrollPane(table); // 스크롤테이블
		scroll.setPreferredSize(new Dimension(600, 400));
		p_center.add(scroll);
		table.setRowSorter(new TableRowSorter(tableModel)); 
		
		p_south.setLayout(new FlowLayout());
		p_south.add(bt6);
		p_south.add(bt7);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH); // 패널추가
		
		
		
		JMenuBar mb = new JMenuBar();
		JMenu menu1 = new JMenu("파일");
		JMenuItem menuopen = new JMenuItem("Open");
		JMenuItem menusave = new JMenuItem("Save");
		JMenuItem menuexit = new JMenuItem("Exit");
		menu1.add(menuopen);
		menu1.add(menusave);
		menu1.add(menuexit);
		mb.add(menu1);
		setJMenuBar(mb); // 메뉴바
		menuopen.addActionListener(menuListener);
		menusave.addActionListener(menuListener);
		menuexit.addActionListener(menuListener);
		
		this.setSize(700, 530);
		this.setVisible(true);
		
	}
	
	public class addFrame extends JFrame {
		JPanel p_north = new JPanel();
		JPanel p_input = new JPanel();
		JPanel p_center = new JPanel();
		JPanel p_south = new JPanel();
		String[] label = {"isbn","제목","저자","출판년도","출판사","대출상태"};
		addFrame() {
		super("도서추가");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		p_north.add(new JLabel("도서정보"));
		add(p_north, BorderLayout.NORTH);  // 라벨
		
		p_input.setLayout(new GridLayout(6,2));
		for(int i=0;i<label.length;i++){
			p_input.add(new JLabel(label[i]));
			tf[i] = new JTextField(10);
			p_input.add(tf[i]);
		}
		p_center.add(p_input);
		add(p_center, BorderLayout.CENTER); // 라벨, 입력텍스트필드
		
		JButton bt = new JButton("추가");
		p_south.add(bt);
		add(p_south, BorderLayout.SOUTH); // 추가버튼
		bt.addActionListener(btnListener);
		
		
		setSize(300, 275);
		setVisible(true);
		}
		
	}
	
	public class modifyFrame extends JFrame {
		JPanel p_north = new JPanel();
		JPanel p_input = new JPanel();
		JPanel p_center = new JPanel();
		JPanel p_south = new JPanel();
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		int real_row;
		int real_column;
		String[] label = {"isbn","제목","저자","출판년도","출판사","인기도","대출상태"};
		modifyFrame() {
		super("도서수정");
		real_row = table.convertRowIndexToModel(row);
		real_column = table.convertColumnIndexToModel(column);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		p_north.add(new JLabel("도서정보"));
		add(p_north, BorderLayout.NORTH);  // 라벨
		
		p_input.setLayout(new GridLayout(6,2));
		for(int i=0;i<5;i++){
			p_input.add(new JLabel(label[i]));
			mtf[i] = new JTextField(String.valueOf(tableModel.getValueAt(real_row,i)),10);
			p_input.add(mtf[i]);
		}
		p_input.add(new JLabel(label[6]));
		mtf[5] = new JTextField(String.valueOf(tableModel.getValueAt(real_row,6)),10);
		p_input.add(mtf[5]);
		p_center.add(p_input);
		add(p_center, BorderLayout.CENTER); // 라벨, 입력텍스트필드
		
		JButton bt = new JButton("변경");
		p_south.add(bt);
		add(p_south, BorderLayout.SOUTH); // 추가버튼
		bt.addActionListener(btnListener);
		
		
		setSize(300, 300);
		setVisible(true);
		}
		
	}
	
	public class findFrame extends JFrame {
		JPanel find_north = new JPanel();
		JPanel find_center = new JPanel();
		JPanel find_south = new JPanel();
		findFrame(){
		super("도서검색");
		t_find = new JTextField("검색내용을 입력하세요.",12);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		int column = table.getSelectedColumn();
		int real_column = table.convertColumnIndexToModel(column);
		f_label = table.getColumnName(real_column);
		find_north.add(new JLabel(" "));
		find_center.add(new JLabel(f_label));
		find_center.add(t_find);
		JButton fb = new JButton("검색");
		find_south.add(fb);
		fb.addActionListener(btnListener);
		
		add(find_north, BorderLayout.NORTH);
		add(find_center, BorderLayout.CENTER);
		add(find_south, BorderLayout.SOUTH);
		
		setSize(320, 140);
		setVisible(true);
		}
	}
	
	public class delayFrame extends JFrame {
		JScrollPane scroll;
		JTable table = new JTable();
		DefaultTableModel tableModel;
		delayFrame(){
			super("연체도서목록");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tableModel = new DefaultTableModel(columnNames, 0){public boolean isCellEditable(int rowIndex, int mColIndex) {return false;}};
			//테이블 수정금지설정
			setLayout(new BorderLayout());
			table = new JTable(tableModel);
			scroll = new JScrollPane(table); // 스크롤테이블
			add(scroll, BorderLayout.CENTER);
			
			manager.delaybook();
			
			ArrayList<Vector> delay = new ArrayList<Vector>();
			for(int i=0;i<manager.getBook().size();i++){ 
				if(manager.getBook().get(i).getState().equals("연체")){
					Vector tmp = new Vector();
					tmp.addElement(manager.getBook().get(i).getIsbn());
					tmp.addElement(manager.getBook().get(i).getTitle());
					tmp.addElement(manager.getBook().get(i).getAuthor());
					tmp.addElement(manager.getBook().get(i).getYear());
					tmp.addElement(manager.getBook().get(i).getPublish());
					tmp.addElement(manager.getBook().get(i).getPopularity());
					tmp.addElement(manager.getBook().get(i).getState());
					delay.add(tmp);
				}
			}
			tableModel.setNumRows(0);
			for (int i=0;i<delay.size();i++)
				tableModel.addRow(delay.get(i));
			
			setSize(400, 400);
			setVisible(true);
		}
	}

	
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Vector[] list = null;
			int selection = table.getSelectedRow();
			int real_selec = 0;
			JButton btn = (JButton)e.getSource();
			if(btn.getText() == "도서추가"){
				manager.delaybook();
				new addFrame();
			}
			else if(btn.getText() == "도서수정"){
				manager.delaybook();
				if(selection==-1) {
					JOptionPane.showMessageDialog(null, "수정할 도서를 선택하세요!", "", JOptionPane.ERROR_MESSAGE);
					manager.delaybook();
				}
				else {
					manager.delaybook();
					real_selec = table.convertRowIndexToModel(selection);
					new modifyFrame();
				}
			}
			else if(btn.getText() == "도서삭제"){
				manager.delaybook();
				if(selection==-1) {
					JOptionPane.showMessageDialog(null, "삭제할 도서를 선택하세요!", "", JOptionPane.ERROR_MESSAGE);
					manager.delaybook();
				}
				else {
					manager.delaybook();
					real_selec = table.convertRowIndexToModel(selection);
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					int real_row = table.convertRowIndexToModel(row);
					manager.deletebook(real_row);
					tableModel.removeRow(real_selec);
				}
			}
			else if (btn.getText() == "도서검색"){
				manager.delaybook();
				if(selection==-1) JOptionPane.showMessageDialog(null, "검색할 목록을 선택하세요!", "", JOptionPane.ERROR_MESSAGE);
				else {
					manager.delaybook();
					new findFrame();
				}
			}
			else if (btn.getText() == "연체목록"){
				new delayFrame();
			}
			else if(btn.getText() == "대출"){
				manager.delaybook();
				String book_state = "대출중";
				selection = table.getSelectedRow();
				if (!(selection == -1)) {
					real_selec = table.convertRowIndexToModel(selection);
					if (manager.getBook().get(real_selec).getState().equals("대출중")||manager.getBook().get(real_selec).getState().equals("연체")){
						manager.delaybook();
						JOptionPane.showMessageDialog(null, "대출가능한 도서를 선택하세요!","", JOptionPane.ERROR_MESSAGE);
					}
					else {
						long date = System.currentTimeMillis();
						real_selec = table.convertRowIndexToModel(selection);
						manager.getBook().get(real_selec).setState(book_state);
						manager.getBook().get(real_selec).setDate(date);
						System.out.println("대출이 완료되었습니다.");
						manager.delaybook();
						manager.addpopul(real_selec);
						tableModel.setNumRows(0);
						for (int i = 0; i < manager.getBook().size(); i++) {
							tableModel.addRow(manager.getBook().get(i).print());
						}
					}
				} else  {
					manager.delaybook();
					JOptionPane.showMessageDialog(null, "대출가능한 도서를 선택하세요!","", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(btn.getText() == "반납"){
				String book_state = "대출가능";
				selection = table.getSelectedRow();
				if (!(selection == -1)) {
					real_selec = table.convertRowIndexToModel(selection);
					if (manager.getBook().get(real_selec).getState()
							.equals("대출중") || manager.getBook().get(real_selec).getState()
							.equals("연체")) {
						manager.getBook().get(real_selec).setState(book_state);
						System.out.println("반납이 완료되었습니다.");
						manager.delaybook();
						tableModel.setNumRows(0);
						for (int i = 0; i < manager.getBook().size(); i++) {
							tableModel.addRow(manager.getBook().get(i).print());
						}
					} else {
						manager.delaybook();
						JOptionPane.showMessageDialog(null, "반납가능한 도서를 선택하세요!", "",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					manager.delaybook();
					JOptionPane.showMessageDialog(null, "반납가능한 도서를 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(btn.getText() == "검색"){
				String findbook = t_find.getText();
				selection = table.getSelectedColumn();
				ArrayList<Vector> top = new ArrayList<Vector>();
				ArrayList<Vector> bottom = new ArrayList<Vector>();
				if (selection == 0) {
					int count = 0;
					manager.delaybook();
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.getBook().get(i).getIsbn().contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if(count == 0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
					}
				if (selection == 1) {
					manager.delaybook();
					int count = 0;
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.getBook().get(i).getTitle().contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if(count == 0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
					}
				if (selection == 2) {
					manager.delaybook();
					int count = 0;
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.getBook().get(i).getAuthor().contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if(count == 0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
					}
				if (selection == 3) {
					manager.delaybook();
					int count = 0;
					String findbook2 = t_find.getText();
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.isDigit(findbook2) && manager.getBook().get(i).getYear() == Integer.parseInt(findbook2)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
						}
					if(count==0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
				}
				if (selection == 4) {
					manager.delaybook();
					int count = 0;
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.getBook().get(i).getPublish().contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if(count == 0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
					}
				if (selection == 6) {
					manager.delaybook();
					int count = 0;
					for (int i = 0; i < manager.getBook().size(); i++) {
						Vector tmp = new Vector();
						tmp.addElement(manager.getBook().get(i).getIsbn());
						tmp.addElement(manager.getBook().get(i).getTitle());
						tmp.addElement(manager.getBook().get(i).getAuthor());
						tmp.addElement(manager.getBook().get(i).getYear());
						tmp.addElement(manager.getBook().get(i).getPublish());
						tmp.addElement(manager.getBook().get(i).getPopularity());
						tmp.addElement(manager.getBook().get(i).getState());
						if (manager.getBook().get(i).getState().contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if(count == 0) JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","", JOptionPane.ERROR_MESSAGE);
					}
				
				
					tableModel.setNumRows(0);
					for (int i = 0; i < top.size(); i++)
						tableModel.addRow(top.get(i));
					for (int i = 0; i < bottom.size(); i++)
						tableModel.addRow(bottom.get(i));
					
					
				}
			
			
			else if(btn.getText() == "추가"){
				manager.delaybook();
				String isbn = tf[0].getText();
				String title = tf[1].getText();
				String author = tf[2].getText();
				String year = tf[3].getText();
				String publisher = tf[4].getText();
				String state = tf[5].getText(); // 텍스트필드 값 받아오기
				long date = 0;
				
				if(manager.isDigit(year)){
				Object[] booktable = {isbn, title, author, year, publisher, popularity, state}; 
				manager.addbook(isbn, title, author, Integer.parseInt(year), publisher, popularity, state, date);
				tableModel.addRow(booktable);
				
				tableModel.setNumRows(0);
				for(int i=0;i<manager.getBook().size();i++){
					tableModel.addRow(manager.getBook().get(i).print());
				}
				}
				else JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.","", JOptionPane.ERROR_MESSAGE);
			}
			else if(btn.getText() == "변경"){
				manager.delaybook();
				selection = table.getSelectedRow();
				real_selec = table.convertRowIndexToModel(selection);
				String isbn = mtf[0].getText();
				String title = mtf[1].getText();
				String author = mtf[2].getText();
				String year = mtf[3].getText();
				String publisher = mtf[4].getText();
				String state = mtf[5].getText(); // 텍스트필드 값 받아오기
				long date = manager.getBook().get(real_selec).getDate();				
				if(manager.isDigit(year)){
					manager.modifybook(real_selec, isbn, title, author, Integer.parseInt(year), publisher, popularity, state, date);
				tableModel.setValueAt(isbn, real_selec, 0);
				tableModel.setValueAt(title, real_selec, 1);
				tableModel.setValueAt(author, real_selec, 2);
				tableModel.setValueAt(year, real_selec, 3);
				tableModel.setValueAt(publisher, real_selec, 4);
				tableModel.setValueAt(popularity, real_selec, 5);
				tableModel.setValueAt(state, real_selec, 6);
				
				tableModel.setNumRows(0);
				for(int i=0;i<manager.getBook().size();i++){
					tableModel.addRow(manager.getBook().get(i).print());
				}
				}
				else JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.","", JOptionPane.ERROR_MESSAGE);
				
					
			}
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			JFileChooser jfc = new JFileChooser("/Users/Jiyoung/Documents/workspace/pbang");
			if (cmd.equals("Open")) {
				int result = jfc.showOpenDialog(rootPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					String x = jfc.getSelectedFile().getPath();
					manager.readdata(x);
					manager.delaybook();
					tableModel.setNumRows(0);
					for(int i=0;i<manager.getBook().size();i++){
						tableModel.addRow(manager.getBook().get(i).print());
					}
				}
			} 
			else if (cmd.equals("Save")) {
				manager.delaybook();
				manager.savedata();
			} 
			else if (cmd.equals("Exit")) {
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args) {
		new Swing();
	}
}
