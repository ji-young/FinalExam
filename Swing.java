package FinalExam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Swing extends JFrame {
	LibraryManager manager;
	JPanel p_north = new JPanel();
	JPanel p_center = new JPanel();
	JPanel p_south = new JPanel();
	JScrollPane scroll;
	JTable table = new JTable();
	DefaultTableModel tableModel;
	Vector<String> tableRow = new Vector<String>();
	Vector<String> tableColumn = new Vector<String>();
	String[] columnNames = { "isbn", "제목", "저자", "출판년도", "출판사", "인기도", "대출상태" };
	String f_label = "";
	JTextField[] tf = new JTextField[6];
	JTextField[] mtf = new JTextField[6];
	JTextField t_find;
	ButtonActionListener btnListener = new ButtonActionListener();
	MenuActionListener menuListener = new MenuActionListener();
	JTextField id_input;
	JPasswordField pw_input;
	JTextField c_id;
	JTextField c_id2;
	JTextField name;
	int popularity = 0;

	Swing(LibraryManager manager) {
		super("Library Manager");
		this.manager = manager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JButton bt1 = new JButton("도서추가");
		JButton bt2 = new JButton("도서수정");
		JButton bt3 = new JButton("도서삭제");
		JButton bt4 = new JButton("도서검색");
		JButton bt5 = new JButton("연체목록");
		JButton bt8 = new JButton("대출내역");
		p_north.add(bt1);
		p_north.add(bt2);
		p_north.add(bt3);
		p_north.add(bt4);
		p_north.add(bt5); 
		p_north.add(bt8);// 버튼

		JButton bt6 = new JButton("대출");
		JButton bt7 = new JButton("반납");

		bt1.addActionListener(btnListener);
		bt2.addActionListener(btnListener);
		bt3.addActionListener(btnListener);
		bt4.addActionListener(btnListener);
		bt5.addActionListener(btnListener);
		bt6.addActionListener(btnListener);
		bt7.addActionListener(btnListener);
		bt8.addActionListener(btnListener);

		tableModel = new DefaultTableModel(columnNames, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		// 테이블 수정금지설정
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		JMenu menu2 = new JMenu("관리자 설정");
		JMenuItem menuopen = new JMenuItem("Open");
		JMenuItem menusave = new JMenuItem("Save");
		JMenuItem menuexit = new JMenuItem("Exit");
		JMenuItem menuid = new JMenuItem("아이디 변경");
		JMenuItem menupw = new JMenuItem("비밀번호 변경");

		menu1.add(menuopen);
		menu1.add(menusave);
		menu1.add(menuexit);
		menu2.add(menuid);
		menu2.add(menupw);
		mb.add(menu1);
		mb.add(menu2);
		setJMenuBar(mb); // 메뉴바
		menuopen.addActionListener(menuListener);
		menusave.addActionListener(menuListener);
		menuexit.addActionListener(menuListener);
		menuid.addActionListener(menuListener);
		menupw.addActionListener(menuListener);

		this.setSize(700, 530);
		this.setVisible(false);

		new Login();

	}

	public class addFrame extends JFrame {
		JPanel p_north = new JPanel();
		JPanel p_input = new JPanel();
		JPanel p_center = new JPanel();
		JPanel p_south = new JPanel();
		String[] label = { "isbn", "제목", "저자", "출판년도", "출판사", "대출상태" };

		addFrame() {
			super("도서추가");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());

			p_north.add(new JLabel("도서정보"));
			add(p_north, BorderLayout.NORTH); // 라벨

			p_input.setLayout(new GridLayout(6, 2));
			for (int i = 0; i < label.length; i++) {
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
		String[] label = { "isbn", "제목", "저자", "출판년도", "출판사", "인기도", "대출상태" };

		modifyFrame() {
			super("도서수정");
			real_row = table.convertRowIndexToModel(row);
			real_column = table.convertColumnIndexToModel(column);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());

			p_north.add(new JLabel("도서정보"));
			add(p_north, BorderLayout.NORTH); // 라벨

			p_input.setLayout(new GridLayout(6, 2));
			for (int i = 0; i < 5; i++) {
				p_input.add(new JLabel(label[i]));
				mtf[i] = new JTextField(String.valueOf(tableModel.getValueAt(
						real_row, i)), 10);
				p_input.add(mtf[i]);
			}
			p_input.add(new JLabel(label[6]));
			mtf[5] = new JTextField(String.valueOf(tableModel.getValueAt(
					real_row, 6)), 10);
			p_input.add(mtf[5]);
			p_center.add(p_input);
			add(p_center, BorderLayout.CENTER); // 라벨, 입력텍스트필드

			JButton bt = new JButton("변경");
			p_south.add(bt);
			add(p_south, BorderLayout.SOUTH); // 추가버튼
			bt.addActionListener(btnListener);
			btnListener.setFrame(this);

			setSize(300, 300);
			setVisible(true);
		}

	}

	public class findFrame extends JFrame {
		JPanel find_north = new JPanel();
		JPanel find_center = new JPanel();
		JPanel find_south = new JPanel();

		findFrame() {
			super("도서검색");
			t_find = new JTextField("검색내용을 입력하세요.", 12);
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
			btnListener.setFrame(this);

			add(find_north, BorderLayout.NORTH);
			add(find_center, BorderLayout.CENTER);
			add(find_south, BorderLayout.SOUTH);

			setSize(320, 140);
			setVisible(true);
		}
	}

	public class Login extends JFrame {
		JPanel login_north = new JPanel();
		JPanel login_center = new JPanel();
		JPanel login_south = new JPanel();
		JPanel pan = new JPanel();
		int a = 0;

		Login() {
			super("관리자 로그인");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			login_center.setLayout(new FlowLayout());

			JLabel tmp = new JLabel(" ");
			login_north.add(tmp);
			add(login_north, BorderLayout.NORTH);

			JLabel id = new JLabel("   아이디  ", JLabel.RIGHT);
			id_input = new JTextField(10);

			login_center.add(id);
			login_center.add(id_input);

			JLabel password = new JLabel("비밀번호  ", JLabel.RIGHT);
			pw_input = new JPasswordField(10);
			login_center.add(password);
			login_center.add(pw_input);

			add(login_center, BorderLayout.CENTER);

			JLabel tt = new JLabel("");
			JButton lbt = new JButton("로그인");
			lbt.addActionListener(btnListener);
			btnListener.setFrame(this);

			login_south.add(tt);
			login_south.add(lbt);
			add(login_south, BorderLayout.SOUTH);

			setSize(239, 165);
			setVisible(true);
			setResizable(false);
		}
	}

	public class delayFrame extends JFrame {
		JScrollPane scroll;
		JTable table = new JTable();
		DefaultTableModel tableModel;

		delayFrame() {
			super("연체도서목록");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tableModel = new DefaultTableModel(columnNames, 0) {
				public boolean isCellEditable(int rowIndex, int mColIndex) {
					return false;
				}
			};
			// 테이블 수정금지설정
			setLayout(new BorderLayout());
			table = new JTable(tableModel);
			scroll = new JScrollPane(table); // 스크롤테이블
			add(scroll, BorderLayout.CENTER);

			manager.delaybook();

			ArrayList<Vector> delay = new ArrayList<Vector>();
			for (int i = 0; i < manager.getBook().size(); i++) {
				if (manager.getBook().get(i).getState().equals("연체")) {
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
			for (int i = 0; i < delay.size(); i++)
				tableModel.addRow(delay.get(i));

			setSize(400, 400);
			setVisible(true);
		}
	}

	public static class loginFrame extends JFrame {
		JPanel login_north = new JPanel();
		JPanel login_center = new JPanel();
		JPanel login_south = new JPanel();

		loginFrame() {
			super("관리자 로그인");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
			JLabel id = new JLabel("아이디");
			JTextField id_input = new JTextField(10);
			login_north.add(id);
			login_north.add(id_input);
			add(login_north, BorderLayout.NORTH);

			JLabel password = new JLabel("비밀번호");
			JPasswordField pw_input = new JPasswordField();
			login_center.add(password);
			login_center.add(pw_input);
			add(login_center, BorderLayout.CENTER);

			JButton lbt = new JButton("확인");
			login_south.add(lbt);
			add(login_south, BorderLayout.SOUTH);

			setSize(200, 200);
			setVisible(true);

		}

	}

	public class change_login extends JFrame {
		JPanel n = new JPanel();
		JPanel c = new JPanel();
		JPanel s = new JPanel();

		change_login() {
			setTitle("관리자 아이디 변경");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
			JLabel l2 = new JLabel("");
			JLabel l = new JLabel("아이디");
			c_id = new JTextField(10);
			JButton b = new JButton("OK");
			n.add(l2);
			c.add(l);
			c.add(c_id);
			s.add(b);
			b.addActionListener(btnListener);
			btnListener.setFrame(this);

			add(n, BorderLayout.NORTH);
			add(c, BorderLayout.CENTER);
			add(s, BorderLayout.SOUTH);

			setSize(200, 100);
			setVisible(true);
		}
	}

	public class change_login2 extends JFrame {
		JPanel n = new JPanel();
		JPanel c = new JPanel();
		JPanel s = new JPanel();

		change_login2() {
			setTitle("관리자 비밀번호 변경");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
			JLabel l2 = new JLabel("");
			JLabel l = new JLabel("비밀번호");
			c_id2 = new JTextField(10);
			JButton b2 = new JButton(" OK ");
			n.add(l2);
			c.add(l);
			c.add(c_id2);
			s.add(b2);
			b2.addActionListener(btnListener);
			btnListener.setFrame(this);

			add(n, BorderLayout.NORTH);
			add(c, BorderLayout.CENTER);
			add(s, BorderLayout.SOUTH);

			setSize(200, 100);
			setVisible(true);
		}
	}

	public class nameFrame extends JFrame {
		JPanel n = new JPanel();
		JPanel c = new JPanel();
		JPanel s = new JPanel();

		nameFrame(String b) {
			setTitle("회원이름");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
			JLabel l2 = new JLabel("");
			JLabel l = new JLabel("이름");
			name = new JTextField(10);
			JButton b_loan = new JButton(b);
			n.add(l2);
			c.add(l);
			c.add(name);
			s.add(b_loan);
			b_loan.addActionListener(btnListener);
			btnListener.setFrame(this);

			add(n, BorderLayout.NORTH);
			add(c, BorderLayout.CENTER);
			add(s, BorderLayout.SOUTH);
			btnListener.setFrame(this);

			setSize(200, 100);
			setVisible(true);
		}
	}
	
	public class listFrame extends JFrame{
		JScrollPane scroll;
		JTable table = new JTable();
		DefaultTableModel tableModel;
		String[] columnNames = { "isbn", "제목", "저자", "대여횟수", "빌린이", "날짜"};
		listFrame() {
			super("대출내역");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tableModel = new DefaultTableModel(columnNames, 0) {
				public boolean isCellEditable(int rowIndex, int mColIndex) {
					return false;
				}
			};
			// 테이블 수정금지설정
			setLayout(new BorderLayout());
			table = new JTable(tableModel);
			scroll = new JScrollPane(table); // 스크롤테이블
			add(scroll, BorderLayout.CENTER);

			manager.delaybook();

			ArrayList<Vector> loan = new ArrayList<Vector>();
			for (int i = 0; i < manager.getLoan().size(); i++) {
				
					Vector tmp = new Vector();
					tmp.addElement(manager.getLoan().get(i).getIsbn());
					tmp.addElement(manager.getLoan().get(i).getTitle());
					tmp.addElement(manager.getLoan().get(i).getAuthor());
					tmp.addElement(manager.getLoan().get(i).getNum());
					tmp.addElement(manager.getLoan().get(i).getName());
					tmp.addElement(manager.getLoan().get(i).getDate());
					loan.add(tmp);
				
			}
			tableModel.setNumRows(0);
			for (int i = 0; i < loan.size(); i++)
				tableModel.addRow(loan.get(i));

			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setPreferredWidth(20);
			table.getColumnModel().getColumn(2).setPreferredWidth(20);
			table.getColumnModel().getColumn(3).setPreferredWidth(20);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.getColumnModel().getColumn(5).setPreferredWidth(50);
			
			setSize(400, 400);
			setVisible(true);
		}
	}

	class ButtonActionListener implements ActionListener {
		private JFrame frame;

		public void setFrame(JFrame frame) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			Vector[] list = null;
			int selection = table.getSelectedRow();
			int real_selec = 0;
			JButton btn = (JButton) e.getSource();
			if (btn.getText() == "도서추가") {
				manager.delaybook();
				new addFrame();
			} else if (btn.getText() == "OK") {
				manager.getLogin().setId(c_id.getText());
				manager.saveid();
				frame.dispose();
			} else if (btn.getText() == " OK ") {
				manager.getLogin().setPw(c_id2.getText());
				manager.saveid();
				frame.dispose();
			} else if (btn.getText() == "도서수정") {
				manager.delaybook();
				if (selection == -1) {
					JOptionPane.showMessageDialog(null, "수정할 도서를 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
					manager.delaybook();
				} else {
					manager.delaybook();
					real_selec = table.convertRowIndexToModel(selection);
					new modifyFrame();
				}
			} else if (btn.getText() == "도서삭제") {
				manager.delaybook();
				if (selection == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 도서를 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
					manager.delaybook();
				} else {
					manager.delaybook();
					real_selec = table.convertRowIndexToModel(selection);
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					int real_row = table.convertRowIndexToModel(row);
					manager.deletebook(real_row);
					tableModel.removeRow(real_selec);
				}
			} else if (btn.getText() == "도서검색") {
				manager.delaybook();
				if (selection == -1)
					JOptionPane.showMessageDialog(null, "검색할 목록을 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
				else {
					manager.delaybook();
					new findFrame();
				}
			} else if (btn.getText() == "연체목록") {
				new delayFrame();
			}
			else if (btn.getText() == "대출내역") {
				new listFrame();
			}
			else if (btn.getText() == "대출") {
				manager.delaybook();
				new nameFrame("대출완료");
			} 
				
			else if (btn.getText() == "대출완료"){
				String book_state = "대출중";
				selection = table.getSelectedRow();
				if (!(selection == -1)) {
					real_selec = table.convertRowIndexToModel(selection);
					if (manager.getBook().get(real_selec).getState()
							.equals("대출중")
							|| manager.getBook().get(real_selec).getState()
									.equals("연체")) {
						manager.delaybook();
						JOptionPane.showMessageDialog(null, "대출가능한 도서를 선택하세요!",
								"", JOptionPane.ERROR_MESSAGE);
					} else {
						long date = System.currentTimeMillis();
						real_selec = table.convertRowIndexToModel(selection);
						manager.getBook().get(real_selec).setState(book_state);
						manager.getBook().get(real_selec).setDate(date);
						System.out.println("대출이 완료되었습니다.");
						manager.delaybook();
						manager.addpopul(real_selec);
						
						String isbn = manager.getBook().get(real_selec).getIsbn();
						String title = manager.getBook().get(real_selec).getTitle();
						String author = manager.getBook().get(real_selec).getAuthor();
						int num = manager.getBook().get(real_selec).getPopularity();
						String getname = name.getText();
						SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm분", Locale.KOREA);
						String today = d.format(new Date()) + " <대출>";
						
						manager.getLoan().add(new Loan(isbn, title, author, num, getname, today));
						manager.saveloan();
						
						tableModel.setNumRows(0);
						for (int i = 0; i < manager.getBook().size(); i++) {
							tableModel.addRow(manager.getBook().get(i).print());
						}
						frame.dispose();
					}
				} else {
					manager.delaybook();
					JOptionPane.showMessageDialog(null, "대출가능한 도서를 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}else if (btn.getText() == "반납") {
				String book_state = "대출가능";
				selection = table.getSelectedRow();
				String getname = "";
				if (!(selection == -1)) {
					real_selec = table.convertRowIndexToModel(selection);
					if (manager.getBook().get(real_selec).getState()
							.equals("대출중")
							|| manager.getBook().get(real_selec).getState()
									.equals("연체")) {
						manager.getBook().get(real_selec).setState(book_state);
						System.out.println("반납이 완료되었습니다.");
						manager.delaybook();
						
						for(int i=0;i<manager.getLoan().size();i++){
							if(manager.getBook().get(real_selec).getIsbn().equals(manager.getLoan().get(i).getIsbn())) {
								getname = manager.getLoan().get(i).getName();
							}
						}
						
						String isbn = manager.getBook().get(real_selec).getIsbn();
						String title = manager.getBook().get(real_selec).getTitle();
						String author = manager.getBook().get(real_selec).getAuthor();
						int num = manager.getBook().get(real_selec).getPopularity();
						
						SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm분", Locale.KOREA);
						String today = d.format(new Date()) + " <반납>";
						
						manager.getLoan().add(new Loan(isbn, title, author, num, getname, today));
						manager.saveloan();
						
						tableModel.setNumRows(0);
						for (int i = 0; i < manager.getBook().size(); i++) {
							tableModel.addRow(manager.getBook().get(i).print());
						}
					} else {
						manager.delaybook();
						JOptionPane.showMessageDialog(null, "반납가능한 도서를 선택하세요!",
								"", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					manager.delaybook();
					JOptionPane.showMessageDialog(null, "반납가능한 도서를 선택하세요!", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (btn.getText() == "검색") {
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
						if (manager.getBook().get(i).getIsbn()
								.contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
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
						if (manager.getBook().get(i).getTitle()
								.contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
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
						if (manager.getBook().get(i).getAuthor()
								.contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
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
						if (manager.isDigit(findbook2)
								&& manager.getBook().get(i).getYear() == Integer
										.parseInt(findbook2)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
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
						if (manager.getBook().get(i).getPublish()
								.contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
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
						if (manager.getBook().get(i).getState()
								.contains(findbook)) {
							top.add(tmp);
							count++;
						} else
							bottom.add(tmp);
					}
					if (count == 0)
						JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "",
								JOptionPane.ERROR_MESSAGE);
				}

				frame.dispose();
				tableModel.setNumRows(0);
				for (int i = 0; i < top.size(); i++)
					tableModel.addRow(top.get(i));
				for (int i = 0; i < bottom.size(); i++)
					tableModel.addRow(bottom.get(i));
			}

			else if (btn.getText() == "추가") {
				manager.delaybook();
				String isbn = tf[0].getText();
				String title = tf[1].getText();
				String author = tf[2].getText();
				String year = tf[3].getText();
				String publisher = tf[4].getText();
				String state = tf[5].getText(); // 텍스트필드 값 받아오기
				long date = 0;

				if (manager.isDigit(year)) {
					Object[] booktable = { isbn, title, author, year,
							publisher, popularity, state };
					manager.addbook(isbn, title, author,
							Integer.parseInt(year), publisher, popularity,
							state, date);
					tableModel.addRow(booktable);

					tableModel.setNumRows(0);
					for (int i = 0; i < manager.getBook().size(); i++) {
						tableModel.addRow(manager.getBook().get(i).print());
					}
				} else
					JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.", "",
							JOptionPane.ERROR_MESSAGE);
			} else if (btn.getText() == "변경") {
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
				if (manager.isDigit(year)) {
					manager.modifybook(real_selec, isbn, title, author,
							Integer.parseInt(year), publisher, popularity,
							state, date);
					tableModel.setValueAt(isbn, real_selec, 0);
					tableModel.setValueAt(title, real_selec, 1);
					tableModel.setValueAt(author, real_selec, 2);
					tableModel.setValueAt(year, real_selec, 3);
					tableModel.setValueAt(publisher, real_selec, 4);
					tableModel.setValueAt(popularity, real_selec, 5);
					tableModel.setValueAt(state, real_selec, 6);
					frame.dispose();

					tableModel.setNumRows(0);
					for (int i = 0; i < manager.getBook().size(); i++) {
						tableModel.addRow(manager.getBook().get(i).print());
					}
				} else
					JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.", "",
							JOptionPane.ERROR_MESSAGE);
			} else if (btn.getText().equals("로그인")) {
				String id = manager.getLogin().getId();
				String pw = manager.getLogin().getPw();
				if (id_input.getText().equals(id)
						&& pw_input.getText().equals(pw)) {
					setVisible(true);
					frame.dispose();
				} else if (!id_input.getText().equals("id")) {
					JOptionPane.showMessageDialog(null, "관리자 계정 아이디가 아닙니다.",
							"", JOptionPane.ERROR_MESSAGE);
					id_input.setText("");
					pw_input.setText("");
				} else if (!pw_input.getText().equals("pw")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 잘못 입력하셨습니다.",
							"", JOptionPane.ERROR_MESSAGE);
					pw_input.setText("");
				}
			}
		}
	}

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			JFileChooser jfc = new JFileChooser(
					"/Users/Jiyoung/Documents/workspace/pbang");
			if (cmd.equals("Open")) {
				int result = jfc.showOpenDialog(rootPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					String x = jfc.getSelectedFile().getPath();
					manager.readdata(x);
					manager.delaybook();
					tableModel.setNumRows(0);
					for (int i = 0; i < manager.getBook().size(); i++) {
						tableModel.addRow(manager.getBook().get(i).print());
					}
				}
			} else if (cmd.equals("Save")) {
				manager.delaybook();
				manager.savedata();
				manager.saveid();
			} else if (cmd.equals("Exit")) {
				System.exit(0);
			} else if (cmd.equals("아이디 변경"))
				new change_login();
			else if (cmd.equals("비밀번호 변경"))
				new change_login2();
		}
	}

	public static void main(String[] args) {
		LibraryManager manager = new LibraryManager();
		new Swing(manager);
	}
}
