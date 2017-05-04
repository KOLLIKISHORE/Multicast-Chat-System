package project;

import java.awt.DisplayMode;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import project.Server.ClientThread;

//read and write operations on passwd.txt file 
public class AuthenticationUtil {
	public static HashMap  <String , String> Validate = new HashMap<String , String> ();
	static{
		readfile();
		System.out.println("Reading the credentails from file"+ Validate.size());
	}
	
	
	public static void readfile(){
		
		BufferedReader buffer = null;
		try{
			
			String file=null;
		 buffer = new BufferedReader(new FileReader(".\\passwd.txt"));
			
			
			while((file =buffer.readLine())!=null){
				
				if(file.length()==0)
					continue;
				
				String args [] = file.trim().split("#");
				Validate.put(args[0],args[1]);  //populating the authentication map 
				System.out.println(args[0]+" "+args[1]);
			}
			
			
		}catch(FileNotFoundException exe){
			exe.printStackTrace();
		}
		catch (IOException x) {
	    x.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				buffer.close();
			}catch(IOException ie){
				ie.printStackTrace();				
			}
		}
		
	} 
	
	
	/* ------------------------------       ------------------------------------------------------*/
	
	private static void writeData(String hostName, String password){
		
		String writableStr = hostName.trim()+"#"+password.trim();
		BufferedWriter bw = null;
		try{
		
		bw = new BufferedWriter(new FileWriter(".\\passwd.txt",true));
		bw.write("\n"+writableStr);
		bw.flush();
		
		// calling read again to update the authentication map
		
		}catch(IOException ioe){ioe.printStackTrace();}
		catch(Exception e){ e.printStackTrace();}
		finally{
			try{
				bw.close();
			}catch(IOException ioe){
				ioe.printStackTrace();				
			}
		}
		
	}
	
	/* ------------------------------       ------------------------------------------------------*/
	    public static void updateUserdetails(){
	    	readfile();
	    }
	/* ------------------------------       ------------------------------------------------------*/
	    
	   public static void showMap(){
		   System.out.println(Validate);
	   }
    /* ------------------------------       ------------------------------------------------------*/  
            public static String validateUser(String username, String password){
            	if(Validate.size()!=0){
            	if(Validate.containsKey(username)&& checkClient(username)){
            		return "user_duplicate";
            	} else if(Validate.containsKey(username)&&((Validate.get(username)).equals(password)) ){
            		return "valid_exist";
            	}else{
            		writeData(username, password);  // writing user info on file
            		updateUserdetails(); // updating dynamic object
            		return "valid_new";
            	}
            	}else{
            		
            		System.out.println("auth Map size = "+Validate.size());
            		return "-1";
            	}
            	
            }	
	/* ------------------------------       ------------------------------------------------------*/  
            public static boolean checkClient(String username){
            	System.out.println(" Live Client size is =  "+Server.liveClientList.size());
            	for(ClientThread cthread:Server.liveClientList){
            		
            		if((cthread.username).equals(username)) {return true;}
            	}
            	return false;
            }
            /* ------------------------------       ------------------------------------------------------*/        
	
	public static void main(String[] args) {
		
		showMap();
		
	}
	
	/* ------------------------------       ------------------------------------------------------*/
	
	
}
