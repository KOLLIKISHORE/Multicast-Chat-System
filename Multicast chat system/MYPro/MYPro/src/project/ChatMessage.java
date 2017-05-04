package project;

import java.io.*;

public class ChatMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, ONLINE = 3, OFFLINE = 4;
	private int type;
	private String msg;
	private String Client;
	private String target;
	private String ct;
	private String gn;
	
	
	public ChatMessage() {
		// TODO Auto-generated constructor stub
	}
	// constructor
	ChatMessage(int type,String msg) {
		this.type = type;
		this.msg = msg;
	}
	
	
	
	public String getGrp() {
		return gn;
	}
	public void setGrp(String gn) {
		this.gn = gn;
	}
	
	
	public String getCastType() {
		return ct;
	}
	public void setCastType(String ct) {
		this.ct = ct;
	}
	
	
	public String getClient() {
		return Client;
	}
	public void setClient(String Client) {
		this.Client = Client;
	}
	
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static int getWhoisin() {
		return WHOISIN;
	}
	public static int getLogout() {
		return LOGOUT;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setMessage(String msg) {
		this.msg = msg;
	}
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return msg;
	}
}
