package ncku.firesimplemail;

import java.io.*;
import java.net.*;
import org.json.*;
import java.util.ArrayList;

public class Client {
	private int port;
	private String serverIP;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private String session;
	/****************************************************************
	 * Constructor
	 ***************************************************************/
	public Client( String serverIP, int port ) {
		this.serverIP = serverIP;
		this.port = port;
	}
	/****************************************************************
	 * Build connection
	 ***************************************************************/
	private Client connect() {
		try {
			System.out.println(
				"Client is request to server " +
				this.serverIP +
				":" +
				this.port +
				", waiting response..."
			);
			this.clientSocket = new Socket( this.serverIP, this.port );
			this.in = new DataInputStream( this.clientSocket.getInputStream() );
			this.out = new DataOutputStream( this.clientSocket.getOutputStream() );
			System.out.println(
				"Successfully connect to server" +
				this.serverIP +
				":" +
				this.port
			);
		}
		catch ( IOException e ) {
			System.out.println( "Failed to connect." );
			e.printStackTrace();
			this.close();
		}
		return this;
	}
	/****************************************************************
	 * Close connection
	 ***************************************************************/
	private Client close() {
		try {
			this.in.close();
			this.out.close();
			this.clientSocket.close();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		finally {
			System.out.println(
				"Close connection with server " +
				this.serverIP +
				":" +
				this.port
			);
		}
		return this;
	}
	/****************************************************************
	 * Send text to server
	 ***************************************************************/
	private Client sendText( String text ) {
		try {
			this.out.writeUTF( text );
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		return this;
	}
	/****************************************************************
	 * Get text to server
	 ***************************************************************/
	private String receiveText() {
		String text = "";
		try {
			text = this.in.readUTF();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		return text;
	}
	/****************************************************************
	 * User registration
	 ***************************************************************/
	public boolean regist( String account, String password ) {
		// Create registration request data.
		JSONObject request = null;
		try {
			request = new JSONObject()
                .put( "event", "regist")
                .put( "account", account )
                .put( "password", password );

			// Send registration request data.
			this.connect().sendText( request.toString() );

			// Receive registration response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully register.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				this.session = response.getString( "session" );
				return true;
			}

			// Failed to register.
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;

	}
	/****************************************************************
	 * User authentication
	 ***************************************************************/
	public boolean authenticate( String account, String password ) {
		try{
			// Create authentication request data.
			JSONObject request = new JSONObject()
				.put( "event", "authenticate")
				.put( "account", account )
				.put( "password", password );

			// Send authentication request data.
			this.connect().sendText( request.toString() );

			// Receive authentication response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully authenticated.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				this.session = response.getString( "session" );
				return true;
			}

			// Failed to authenticate.
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;
	}
	/****************************************************************
	 * Get all mail header
	 ***************************************************************/
	public MailHead[] getAllMail() {
		try{
			// Create get all mail request data.
			JSONObject request = new JSONObject()
				.put( "event", "get all mail")
				.put( "session", this.session );

			// Send get all mail request data.
			this.connect().sendText( request.toString() );

			// Receive get all mail response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully get all mail response data.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				ArrayList<MailHead> temp = new ArrayList<MailHead>();
				JSONArray mails = response.getJSONArray( "mails" );
				for( int index = 0; index < mails.length(); ++index ) {
					JSONObject mailHead = mails.getJSONObject( index );
					temp.add(
						new MailHead(
							mailHead.getString( "id" ),
							mailHead.getString( "from" ),
							mailHead.getString( "to" ),
							mailHead.getString( "title" )
						)
					);
				}
				// No mail available yet.
				if( temp.size() == 0 )
					return null;
				// Return all available mail.
				return temp.toArray( new MailHead[ temp.size() ] );
			}
			// Failed to get all mail.
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}
	/****************************************************************
	 * Get mail by id
	 ***************************************************************/
	public Mail getMail( String id ) {
		try{
			// Create get mail request data.
			JSONObject request = new JSONObject()
				.put( "event", "get mail")
				.put( "session", this.session )
				.put( "id", id );

			// Send get mail request data.
			this.connect().sendText( request.toString() );

			// Receive get mail response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully get mail response data.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				return new Mail(
						response.getString( "from" ),
						response.getString( "to" ),
						response.getString( "title" ),
						response.getString( "body" )
				);
			}

			// Failed to get mail.
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
	/****************************************************************
	 * Send mail
	 ***************************************************************/
	public boolean sendMail( Mail mail ) {
		try{
			// Create send mail request data.
			JSONObject request = new JSONObject()
				.put( "event", "send mail")
				.put( "session", this.session )
				.put( "from", mail.getFrom() )
				.put( "to", mail.getTo() )
				.put( "title", mail.getTitle() )
				.put( "body", mail.getBody() );

			// Send send mail request data.
			this.connect().sendText( request.toString() );

			// Receive send mail response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully send mail response data.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				return true;
			}

			// Failed to send mail.
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;

	}
	/****************************************************************
	 * Get all task header
	 ***************************************************************/
	public TaskHead[] getAllTask() {
		try{
			// Create get all task request data.
			JSONObject request = new JSONObject()
				.put( "event", "get all task")
				.put( "session", this.session );

			// Send get all task request data.
			this.connect().sendText( request.toString() );

			// Receive get all task response data.
			JSONObject response = new JSONObject( this.receiveText() );

			// Close server connection.
			this.close();

			// Successfully get all task response data.
			if( response.getString( "auth" ).equals( "yes" ) ) {
				ArrayList<TaskHead> temp = new ArrayList<TaskHead>();
				JSONArray mails = response.getJSONArray( "mails" );
				for( int index = 0; index < mails.length(); ++index ) {
					JSONObject mailHead = mails.getJSONObject( index );
					temp.add(
						new TaskHead(
							mailHead.getString( "id" ),
							mailHead.getString( "from" ),
							mailHead.getString( "to" ),
							mailHead.getString( "title" )
						)
					);
				}
				// No mail available yet.
				if( temp.size() == 0 )
					return null;
				// Return all available mail.
				return temp.toArray( new TaskHead[ temp.size() ] );
			}

			// Failed to get all mail.
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}
	public Task getTask( String id ) {
		return new Task("from", "to", "title");
	}
	public boolean createTask( Task task ) {
		return true;
	}
	public boolean updateTask( Task task ) {
		return true;
	}
	public boolean deleteTask( Task task ) {
		return true;
	}
}
