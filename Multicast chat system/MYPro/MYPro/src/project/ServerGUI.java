package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*SERVER GUI*/

public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	// the stop and start buttons
	private JButton stopStart;
	
	// The port number
	private JTextField portnum;
	// my server
	private Server server;
	
// JTextArea for the chat room and the events
		private JTextArea chat, event;
	
	 ServerGUI(int port) {
		super("Server");
		server = null;
		// SERVER PORT NUMBER AND IP ADDRESS
		JPanel jp = new JPanel(new GridLayout(3,1));
		jp.add(new JLabel("SERVER IP/HOST:"));
		JTextField hostName = new JTextField("localhost");
		hostName.setEditable(false);
		jp.add(hostName);
		jp.add(new JLabel("PORTNUMBER:"));
		//System.out.println("");
		portnum = new JTextField(" " + port);
		jp.add(portnum);
		// to stop or start the server, we start with "Start"
		stopStart = new JButton("START SERVER");
		stopStart.addActionListener(this);
		jp.add(stopStart);
		add(jp, BorderLayout.NORTH);
		
		// the event and chat room
		JPanel center = new JPanel(new GridLayout(2,1));
		chat = new JTextArea(80,80);
		chat.setEditable(false);
		appendRoom("LET'S CHAT\n");
		center.add(new JScrollPane(chat));
		event = new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("EVENT DESCRIPTION\n");
		center.add(new JScrollPane(event));	
		add(center);
		
		// need to be informed when the user click the close button on the frame
		addWindowListener(this);
		setSize(400, 600);
		setResizable(false);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end
	void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);
		
	}
	
	// Server Start or Stop options
	public void actionPerformed(ActionEvent e) {
		// if running we have to stop
		if(server != null) {
			server.stop();
			server = null;  //--
			portnum.setEditable(true);
			stopStart.setText("Start Server");
			return;
		}
      	// OK start the server	
		int port;
		try {
			port = Integer.parseInt(portnum.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Invalid port number");
			return;
		}
		// ceate a new Server and passing the port number and GUI values
		server = new Server(port, this);
		// and start it as a thread
		new RunServer().start();
		stopStart.setText("Stop Server");
		portnum.setEditable(false);
	}

	/* --------------------------------------------         ------------------------------------------------------------------------ */
	// This main method starts only ServerGUI but not server
	// to start server we have to click on start/stop button
	// This UI executes and display if user entered the port number
	public static void main(String[] args) {
		// validating the args for port number
		if(args.length == 1){
			
			String pNS= args[0];
			try{
			int port = Integer.parseInt(pNS);
			new ServerGUI(port);
			}catch(NumberFormatException nfe){
				System.err.println("Invalid port number Please Enter proper port number");
				System.exit(ERROR);
			}
		}else {
			System.err.println("Please Enter  list of arguments in specified order ");
		}
		
		
	}
	

	public void windowClosing(WindowEvent e) {
		// if my Server exist
		if(server != null) {
			try {
				server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}
	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Server
	 */
	class RunServer extends Thread {
		public void run() {
			server.start();         // should execute until if fails
			// the server failed
			stopStart.setText("Start Server");
			portnum.setEditable(true);
			appendEvent("Server Stopped\n");
			server = null;
		}
	}

}
