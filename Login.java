package FinalExam;

import java.io.Serializable;

public class Login implements Serializable {
	private String id;
	private String password;
	
	public Login(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String getId(){
		return id;
	}
	
	public String getPw(){
		return String.valueOf(password);
	}

	public void setId(String newid){
		this.id = newid;
	}
	
	public void setPw(String password){
		this.password = password;
	}
}
