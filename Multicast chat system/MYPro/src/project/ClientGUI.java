package project;

import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/*Client GUI*/
public class ClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	// to hold the Username and later on the messages
	private JTextField tf;
	// to hold the server address an the port number
	private JTextField tSrvr, tfPort, tClient;
	
	// to hold client password
	private JPasswordField clientpaswd;
	// to Logout and get the list of the users
	private JButton login, logout;
	// for the chat room
	private JTextArea ta;
	
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int Server_Port_number;
	private String svr_Name;
	private DefaultListModel<String> multicastListModel;
	private DefaultListModel<String> UnicastChat;
	public JTree multicastTree = new JTree();
	public JList uni_cst_Lst;
	public JTextField targetTF;
	private JButton sendButton;
	private JButton gPButton;
	
	public JPanel northP;
	public JPanel lstPanel; 
	public JButton statusButton;

	//initiatin g constructor
	
	ClientGUI(String host_server, int spt,String c_Name,String clientPassword,int clientPort) {
        
		super("CLIENT CHAT " + c_Name);
		AuthenticationUtil authenticationUtil = new AuthenticationUtil();
		Server_Port_number = spt;
		svr_Name = host_server;
	
		 northP = new JPanel(new GridLayout(5,1));
	
		
		// PORT OF SERVER AND SERVER NAME
		JPanel sAP = new JPanel(new GridLayout(1,4));
		
		tSrvr = new JTextField(svr_Name);
		tfPort = new JTextField(Server_Port_number+"");
		
		// PLACING SERVER HOST LABEL 
		JLabel shLabel = new JLabel("SERVER HOST:");
		shLabel.setHorizontalAlignment(JLabel.CENTER);
		sAP.add(shLabel);
		sAP.add(tSrvr);
		
		JLabel sPLabel = new JLabel("PORT :");
		sPLabel.setHorizontalAlignment(JLabel.CENTER);
		sAP.add(sPLabel);
		sAP.add(tfPort);
		
		northP.add(sAP);

		// creating client name and password panel
		JPanel client_passwd = new JPanel(new GridLayout(1,4));
		
		tClient = new JTextField(c_Name);
		clientpaswd = new JPasswordField(clientPassword);
		
		
		JLabel cName_Label = new JLabel("CLIENT:");
		cName_Label.setHorizontalAlignment(JLabel.CENTER);
		client_passwd.add(cName_Label);
		client_passwd.add(tClient);
		
		JLabel cPassLabel = new JLabel("PASSWORD:");
		cPassLabel.setHorizontalAlignment(JLabel.CENTER);
		client_passwd.add(cPassLabel);
		client_passwd.add(clientpaswd);
		
		northP.add(client_passwd);

		
		// login and logout buttons
				login = new JButton("Login");
				login.addActionListener(this);
				logout = new JButton("Logout");
				logout.addActionListener(this);
				logout.setEnabled(false);		// you have to login before being able to logout
				statusButton = new JButton("Status");
				// add action listner to do operations
				statusButton.setEnabled(false);
				
				
		JPanel inOutPanel = new JPanel(); // new GridLayout(1,2
		          inOutPanel.add(login);
		          inOutPanel.add(logout);
		          inOutPanel.add(statusButton);
		     northP.add(inOutPanel);
		          
		
		
		// the Label and the TextField
		label = new JLabel("ENTER YOUR TEXT HERE", SwingConstants.CENTER);
		//JLabel samplelabel = new JLabel( "  ", SwingConstants.CENTER);
		
		label.setForeground(Color.LIGHT_GRAY);
		tf = new JTextField();
		//tf.setPreferredSize(new Dimension(500,40));
		tf.setBackground(Color.lightGray);
		tf.setEnabled(false);
		JPanel clientTextPanel = new JPanel(new GridLayout(2,1));
		
		clientTextPanel.add(label);
		clientTextPanel.add(tf);
		
		northP.add(clientTextPanel);
		JPanel forwardMsgPanel = new JPanel(new GridLayout(2,3));
		JLabel toLabel = new JLabel("To : ",SwingConstants.CENTER);
		forwardMsgPanel.add(toLabel);
		targetTF = new JTextField("ALL");
		forwardMsgPanel.add(targetTF);
		sendButton = new JButton("send");
		forwardMsgPanel.add(sendButton);
		gPButton = new JButton("Group Operation");
		
        
		
     MouseListener grpButtonListen = new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
        	 
        if(!connected){return;}	 
        if (e.getClickCount() == 1) {
            
        	String [] grpArray = {"Create New Group","Join Group","Leave Group"};
            
            
           String selectedOption = (String) JOptionPane.showInputDialog(null, 
                    "Select a Group Operation",
                    "Group Operation",
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, 
                    grpArray, 
                    grpArray[0]);
          System.out.println("user selecte group option = "+selectedOption);

          // perform create , join or leave operations
          switch(selectedOption){
          
          case "Create New Group" :
        	  String newGroupName = JOptionPane.showInputDialog(
        		        null, 
        		        "Enter new Group Name", 
        		        "Create Group", 
        		        JOptionPane.INFORMATION_MESSAGE
        		    );
        	 
        	   
        	   	// creating new group msg
			ChatMessage createGroupMsg = new ChatMessage();
			createGroupMsg.setClient(tClient.getText());
			createGroupMsg.setType(ChatMessage.MESSAGE);
			createGroupMsg.setCastType("CG");
			createGroupMsg.setGrp(newGroupName);
			client.sendMessage(createGroupMsg);				
			tf.setText("");
			//return; 
        	    
        	  
        	  
        	  
        	  System.out.println("create new Group : "+newGroupName);
        	  
        	  
        	  break;
         
          case "Join Group" :
        	  
        	  String joinGroupName = JOptionPane.showInputDialog(
      		        null, 
      		        "Enter Group Name to Join", 
      		        "Join Group", 
      		        JOptionPane.INFORMATION_MESSAGE
      		    );
        	  
        	
       	   
      	   	// join group msg
			ChatMessage joinGroupMsg = new ChatMessage();
			joinGroupMsg.setClient(tClient.getText());
			joinGroupMsg.setType(ChatMessage.MESSAGE);
			joinGroupMsg.setCastType("JG");
			joinGroupMsg.setGrp(joinGroupName);
			client.sendMessage(joinGroupMsg);				
			tf.setText("ALL");
			//return; 
      	    
      	  ///////
      	  System.out.println("join Group : "+joinGroupName);
        	  
        	  break;
          
          case "Leave Group" :
        	  
        	  String leaveGroupName = JOptionPane.showInputDialog(
        		        null, 
        		        "Enter Group Name to Leave from it", 
        		        "Leave Group", 
        		        JOptionPane.INFORMATION_MESSAGE
        		    );
        	  
        	  //do something
        	//////
          	   
        	   	// join group msg
  			ChatMessage leaveGroupMsg = new ChatMessage();
  			leaveGroupMsg.setClient(tClient.getText());
  			leaveGroupMsg.setType(ChatMessage.MESSAGE);
  			leaveGroupMsg.setCastType("LG");
  			leaveGroupMsg.setGrp(leaveGroupName);
  			client.sendMessage(leaveGroupMsg);				
  			tf.setText("ALL");
  			//return; 
        	    
        	  ///////
        	  
        	  System.out.println("Leave Group : "+leaveGroupName);
        	  
        	  break;
          }
          

         }
    }
};
gPButton.addMouseListener(grpButtonListen);
forwardMsgPanel.add(gPButton);
northP.add(forwardMsgPanel);
add(northP, BorderLayout.NORTH);

JPanel panelCenter = new JPanel(new GridLayout(1,1));
		
		 lstPanel =  new JPanel(new GridLayout(1,2));
		UnicastChat = new DefaultListModel<String>();
		
		uni_cst_Lst = new JList(UnicastChat);
		//////////////////
		
		MouseListener mouseLstnr = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {


		           String targetUnicastClient = (String) uni_cst_Lst.getSelectedValue();
		           // add selectedItem to your second list.
		           targetTF.setText(targetUnicastClient);
		          

		         }
		    }
		};
		uni_cst_Lst.addMouseListener(mouseLstnr);
		///////////////////
		
		uni_cst_Lst.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		uni_cst_Lst.setSelectedIndex(0);
        uni_cst_Lst.setVisibleRowCount(3);
        //uni_cst_Lst.setSize(220, 200);
		lstPanel.add(new JScrollPane(uni_cst_Lst));
		
		DefaultMutableTreeNode multicastGroups = new DefaultMutableTreeNode("Multicast Groups");

		multicastTree = new JTree(multicastGroups);
		multicastTree.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		//////////////////////////
		MouseListener myMouseTreeListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

                    Object treenode =  multicastTree.getLastSelectedPathComponent();
                    String selectedGroupName = treenode.toString();
		           //String targetUnicastClient = (String) unicastL.getSelectedValue();
		           // add selectedItem to your second list.
		           targetTF.setText(selectedGroupName);
		          

		         }
		    }
		};
		
		multicastTree.addMouseListener(myMouseTreeListener);
		///////////////
		 
		 
		lstPanel.add(new JScrollPane(multicastTree));
		
		panelCenter.add(lstPanel);
		
		
		add(panelCenter, BorderLayout.CENTER);  
		
		//@@@@@@@@@@@ south panel @@@@@@@@@@@@@@@@@@@@@
		JPanel southPanel = new JPanel(new GridLayout(1,1));
		// The panelCenter which is the chat room
		ta = new JTextArea("Welcome to the Chat room\n", 10, 80);
		
		southPanel.add(new JScrollPane(ta));
		ta.setEditable(false);
		add(southPanel, BorderLayout.SOUTH);

			
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 600);
		setVisible(true);
		tf.requestFocus();

	}

	// called by the Client to append text in the TextArea 
	void append(String str) {
		ta.append(str);
		ta.setCaretPosition(ta.getText().length() - 1);
	}
	// called by the GUI is the connection failed
	// we reset our buttons, label, textfield
	void connectionFailed() 
	{
		login.setEnabled(true);
		logout.setEnabled(false);
		statusButton.setEnabled(false);
		//whoIsIn.setEnabled(false);
		label.setForeground(Color.LIGHT_GRAY);
		//label.setText("Enter your username below");
		tf.setText(" ");
		tf.setBackground(Color.lightGray);
		tf.setEnabled(false);
		
		// reset port number and host name as a construction time
		tfPort.setText("" + Server_Port_number);
		tSrvr.setText(svr_Name);
		// let the user change them
		tSrvr.setEditable(false);
		tfPort.setEditable(false);
		// don't react to a <CR> after the username
		tf.removeActionListener(this);
		connected = false;
	}
		
	/*
	* Button or JTextField clicked
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
	
		
            if(o == logout) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "Client is disconnected"));
			return;
		}

		
		// ok it is coming from the JTextField
		if(connected) {
			// creating message
			ChatMessage msg = new ChatMessage();
			msg.setClient(tClient.getText());
			msg.setType(ChatMessage.MESSAGE);
			String tarString = targetTF.getText().trim();
			if(tarString == null) {return;}
			String castString = tarString.charAt(0)+"";
			if(castString.equalsIgnoreCase("c")) {
			msg.setCastType("U");
			}else if(castString.equalsIgnoreCase("g")) {
				msg.setCastType("M");
		     }else if(castString.equalsIgnoreCase("a")) {
			msg.setCastType("B");
	          } else {return;}
			
			msg.setTarget(tarString); // this value specifies the unicast, multi cast, broadcast
			msg.setMessage(tf.getText());
			client.sendMessage(msg);				
			tf.setText("");
			return;
		}
		
		
		if(o == login) {
			// updating the user information to authmap
			System.out.println("Size of map is = "+AuthenticationUtil.Validate.size());
			//AuthenticationUtil.updateAuthMap();
			// ok it is a connection request
			String username = tClient.getText().trim();
			this.setTitle(username);
			// empty username ignore it
			if(username.length() == 0)
				return;
			// empty serverAddress ignore it
			String server = tSrvr.getText().trim();
			if(server.length() == 0)
				return;
			// empty or invalid port numer, ignore it
			String portNumber = tfPort.getText().trim();
			
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   // nothing I can do if port number is not valid
			}
			
			String clientpassword = clientpaswd.getText().trim();
			if((clientpassword.length())==0) {return;}

			// try creating a new Client with GUI
			client = new Client(server, port, username,clientpassword, this);
			// test if we can start the Client
			
			String clientValidationResult = AuthenticationUtil.validateUser(username, clientpassword);
			
			if(clientValidationResult.equals("user_duplicate")){ // if user is already running
				JOptionPane.showMessageDialog(null, "Client "+username+" is already running");
			}else if(clientValidationResult.equals("valid_exist")){ // in case of existed non duplicate user
				JOptionPane.showMessageDialog(null, "Client "+username+" Logged in Successfully");
				
				if(!client.start()) {
					return;
					}
				
				
				label.setForeground(Color.BLACK);
				tf.setBackground(Color.white);
				tf.setEnabled(true);
				tf.setText("");
				label.setText("ENTER YOUR TEXT BELOW");
				connected = true;
				
				// disable login button
				login.setEnabled(false);
				// enable the 2 buttons
				logout.setEnabled(true);
				statusButton.setText("Set Offline");
				/***************/
				statusButton.addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
					    JButton tempButton = (JButton) e.getSource();
					    if(tempButton.getText().equalsIgnoreCase("Set Offline")){
					    	// update the client status in all windows
					    	
					    	ChatMessage msg = new ChatMessage();
							msg.setClient(tClient.getText());
							msg.setType(ChatMessage.OFFLINE);
							client.sendMessage(msg);				
							//tf.setText("");
							///return;
					    	// change the text value to "Set Online"
					    	tempButton.setText("Set Online");
							
					    }else {
					    	// implement this for to deal with set online status
					    	
					    	ChatMessage msg = new ChatMessage();
							msg.setClient(tClient.getText());
							msg.setType(ChatMessage.ONLINE);
							client.sendMessage(msg);				
							//tf.setText("");
							///return;
					    	// change the text value to "Set Online"
					    	tempButton.setText("Set Offline");
							
					    	
					    	// change text
					    }
					  }
					});
				/*********************/
				
				statusButton.setEnabled(true);
				//whoIsIn.setEnabled(true);
				// disable the Server and Port JTextField
				tSrvr.setEditable(false);
				tfPort.setEditable(false);
				tClient.setEditable(false);
				clientpaswd.setEditable(false);
				// Action listener for when the user enter a message
				//tf.addActionListener(this);
				  sendButton.addActionListener(this);
			
			} else if(clientValidationResult.equals("valid_new")){ // in case of new user new account creation
				JOptionPane.showMessageDialog(null, "Client "+username+" Registered Successfully \n Please login Now");
			} else {return;}
			
			
		}

	}

	// to start the whole thing the server
	public static void main(String[] args) {
		
		
		// validating the cmdArgs for port number
				if(args.length == 5){
					
					String sHost = args[0];
					String sPort = args[1];
					String cName = args[2];
					String cPassword = args[3];
					String cPort = args[4];
					try{
					int srvrPort = Integer.parseInt(sPort);
					int cntPort = Integer.parseInt(cPort);
		
				// calling constructor 
					new ClientGUI(sHost.trim(),srvrPort,cName.trim(),cPassword.trim(),cntPort);
					}catch(NumberFormatException nfe){
						System.err.println("Please enter valid port number");
						System.exit(ERROR);
					}
				}else
				
				{
	System.err.println(" Please enter the arguments in order i.e )serverhostname serverportnum clientName clientpassword clientport");
				}
				

		
		
	}

}


