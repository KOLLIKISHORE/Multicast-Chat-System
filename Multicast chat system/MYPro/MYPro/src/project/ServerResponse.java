package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ServerResponse implements Serializable {

	protected static final long serialVersionUID = 1112122201L;

	private ArrayList<String>  ClientUpdate;
	private HashMap<String, HashSet<String>> groupUpdate;
	private String respType;
	private String message;
	public ServerResponse() {
		// TODO Auto-generated constructor stub
	}
  public ArrayList<String> getClientUpdate() {
		return ClientUpdate;
	}

 public void setClientUpdate(ArrayList<String> ClientUpdate) {
	 this.ClientUpdate = ClientUpdate;
	}

public HashMap<String, HashSet<String>> getGroupUpdate() {
		return groupUpdate;
	}

public void setGroupUpdate(HashMap<String, HashSet<String>> groupUpdate) {
		this.groupUpdate = groupUpdate;
	}

public String getRespType() {
		return respType;
	}

public void setRespType(String respType) {
		this.respType = respType;
	}

public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	}
