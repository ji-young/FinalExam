package FinalExam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LibraryManager {
	ArrayList<Book> book = new ArrayList<Book>();
	Scanner s = new Scanner(System.in);
	
	public void addbook(String isbn, String title, String author, int year, String publisher, int popularity, String state, long date){
		book.add(new Book(isbn, title, author, year, publisher, popularity, state, date));
	}
	
	public void loanbook(String isbn){
		for(int i=0;i<book.size();i++){
			if(book.get(i).getIsbn().equals(isbn)) book.get(i).setState("대출중");
		}
	}
	
	public void returnbook(String isbn){
		for(int i=0;i<book.size();i++){
			if(book.get(i).getIsbn().equals(isbn)) book.get(i).setState("대출가능");
		
		}
	}
	
	public void delaybook(){
		long date2 = System.currentTimeMillis();
		for(int i=0;i<book.size();i++){
			if(book.get(i).getState().equals("대출중")){
				double differ = (date2 - book.get(i).getDate()) / 1000.0;
				if (differ > 30)
					book.get(i).setState("연체");
			}
			else continue;
		}
		
	} 
	
	public void modifybook(int index, String isbn, String title, String author, int year, String publisher, int popularity, String state, long date){
		book.set(index, new Book(isbn, title, author, year, publisher, popularity, state, date));
	}
	
	public void deletebook(int num){
		book.remove(num);
	}
	
	public void addpopul(int num){
		book.get(num).addPopularity();
	}
	
	public boolean isDigit(String year){
		try{
			Integer.parseInt(year);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public void readdata(String x){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream(x);	
			ois = new ObjectInputStream(fin);
			
			ArrayList<Book> book2 = (ArrayList<Book>)ois.readObject();
			for(int i=0;i<book2.size();i++){
				book.add(book2.get(i));
			}
			System.out.println("불러왔습니다.");
			
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	public void savedata(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream("booklist.dat");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(book);
			oos.reset();
			
			System.out.println("저장되었습니다.");
			
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
			
		} //finally
	} 
	
	public ArrayList<Book> getBook() {
		return book;
	}
	
	public static void main(String[] args) {
		LibraryManager m = new LibraryManager();
	}

}
