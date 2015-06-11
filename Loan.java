package FinalExam;

import java.io.Serializable;

import java.util.Date;

public class Loan implements Serializable {
	
	private String isbn;
	private String title;
	private String author;
	private int num;
	private String name;
	private String date;
	

	Loan(String isbn, String title, String author, int num, String name, String date) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.num = num;
		this.name = name;
		this.date = date;

	}
	
	public String getIsbn(){
		return isbn;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public int getNum(){
		return num;
	}
	
	public String getName(){
		return name;
	}

	public String getDate(){
		return date;
	}
}
