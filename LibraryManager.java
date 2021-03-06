package FinalExam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManager {
	ArrayList<Book> book = new ArrayList<Book>();
	ArrayList<Loan> loan = new ArrayList<Loan>();
	Login login = new Login("1", "1");
	Scanner s = new Scanner(System.in);
	
	public LibraryManager() {
		try {
			new FileInputStream("login.dat");
		} catch (Exception e) {
			return;
		}
		readid();
		try {
			new FileInputStream("loanlist.dat");
		} catch (Exception e) {
			return;
		}
		readloan();
	}
	
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
			System.out.println(book.size());
			
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
	
	public void readid(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream("login.dat");	
			ois = new ObjectInputStream(fin);
			
			this.login = (Login)ois.readObject();
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	public void saveid(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream("login.dat");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(login);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
			
		} //finally
	}
	
	public void readloan(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream("loanlist.dat");	
			ois = new ObjectInputStream(fin);
			
			this.loan = (ArrayList<Loan>)ois.readObject();
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	public void saveloan(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream("loanlist.dat");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(loan);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
			
		} //finally
	}
	
	
	    public String encrypt(String planText) {
	        try{
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            md.update(planText.getBytes());
	            byte byteData[] = md.digest();

	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < byteData.length; i++) {
	                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	            }

	            StringBuffer hexString = new StringBuffer();
	            for (int i=0;i<byteData.length;i++) {
	                String hex=Integer.toHexString(0xff & byteData[i]);
	                if(hex.length()==1){
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }

	            return hexString.toString();
	        }catch(Exception e){
	            e.printStackTrace();
	            throw new RuntimeException();
	        }
	    }
	
	
	
	public ArrayList<Book> getBook() {
		return book;
	}
	
	public Login getLogin() {
		return login;
	}
	
	public ArrayList<Loan> getLoan(){
		return loan;
	}
}
