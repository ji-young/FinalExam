package FinalExam;
import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable{
	private static final long serialVersionUID = 6684427525956843687L;
	private String isbn;
	private String title;
	private String author;
	private int year;
	private String publisher;
	private int popularity;
	private String state;
	private long date;
	
	public Book(String isbn, String title, String author, int year, String publisher, int popularity, String state, long date){
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.year = year;
		this.publisher = publisher;
		this.popularity = popularity;
		this.state = state;
		this.date = date;
	}
	
	public String getIsbn(){return isbn;}
	
	public String getAuthor(){return author;}
	
	public String getTitle(){return title;}
	
	public int getYear(){return year;}
	
	public String getPublish(){return publisher;}
	
	public int getPopularity(){return popularity;}
	
	public String getState(){return state;}
	
	public long getDate(){return date;}
	
	public void setIsbn(String isbn){this.isbn = isbn;}
	
	public void setAuthor(String author){this.author = author;}
	
	public void setTitle(String title){this.title = title;}
	
	public void setState(String state){this.state = state;}
	
	public void setDate(long date){this.date = date;}
	
	public void addPopularity(){this.popularity ++;}
	
	public String toString(){return getIsbn() + "\t" + getTitle() + "\t" + getAuthor() + "\t"  + "\t" + getYear() + "\t" + getPublish() + "\t" + getPopularity() + "\t" + getState();}
	
	public Object[] print(){
		Object[] list =  {getIsbn(), getTitle(), getAuthor(), getYear(), getPublish(), getPopularity(),getState()};
		return list;
	}
}
