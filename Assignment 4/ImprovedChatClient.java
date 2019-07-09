/* CS 1501
   Primitive chat client. 
   This client connects to a server so that messages can be typed and forwarded
   to all other clients.  Try it out in conjunction with ImprovedChatServer.java.
   You will need to modify / update this program to incorporate the secure elements
   as specified in the Assignment sheet.  Note that the PORT used below is not the
   one required in the assignment -- for your SecureChatClient be sure to 
   change the port that so that it matches the port specified for the secure
   server.
*/
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.math.*;

public class ImprovedChatClient extends JFrame implements Runnable, ActionListener {

    public static final int PORT = 8765; //5678
    ObjectOutputStream myWriter;
    ObjectInputStream myReader;
    JTextArea outputArea;
    JLabel prompt;
    JTextField inputField;
    String myName, serverName;
	Socket connection;
    SymCipher cipher;

    public ImprovedChatClient ()
    {
        try {

        myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
        serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
        InetAddress addr =
                InetAddress.getByName(serverName);
        connection = new Socket(addr, PORT);   // Connect to server with new
                                               // Socket

        myWriter =new ObjectOutputStream(connection.getOutputStream());
        myWriter.flush();
        myReader = new ObjectInputStream(connection.getInputStream());

        BigInteger E = (BigInteger)myReader.readObject();
        BigInteger N = (BigInteger)myReader.readObject();
        String encType = (String)myReader.readObject();

        if (encType.equals("Add")){
            System.out.println("Chosen Cipher is Add128");
            cipher = new Add128();
        }
        else{
            System.out.println("Chosen Cipher is Substitute");
            cipher = new Substitute();
        }

        byte[] bytes = cipher.getKey();
        BigInteger key = new BigInteger(bytes);
        key = key.abs();
        key = key.modPow(E, N);
        myWriter.writeObject(key);
        myWriter.writeObject(cipher.encode(myName));

        this.setTitle(myName);      // Set title to identify chatter

        Box b = Box.createHorizontalBox();  // Set up graphical environment for
        outputArea = new JTextArea(8, 30);  // user
        outputArea.setEditable(false);
        b.add(new JScrollPane(outputArea));

        outputArea.append("Welcome to the Chat Group, " + myName + "\n");

        inputField = new JTextField("");  // This is where user will type input
        inputField.addActionListener(this);

        prompt = new JLabel("Type your messages below:");
        Container c = getContentPane();

        c.add(b, BorderLayout.NORTH);
        c.add(prompt, BorderLayout.CENTER);
        c.add(inputField, BorderLayout.SOUTH);

        Thread outputThread = new Thread(this);  // Thread is to receive strings
        outputThread.start();                    // from Server

		addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    { 
                        try{
                            myWriter.writeObject(cipher.encode("CLIENT CLOSING"));
                            System.exit(0);
                        } catch(Exception e3){
                            e3.printStackTrace();
                        }
                     }
                }
            );

        setSize(500, 200);
        setVisible(true);

        }
        catch (Exception e)
        {
            System.out.println("Problem starting client!");
        }
    }

    public void run()
    {
        while (true)
        {
             try {
                byte[] currMsg = (byte[])myReader.readObject();
                String msg = new String(SecureChatServer.cipher.decode(currMsg));
			    outputArea.append(msg+"\n");
             }
             catch (Exception e)
             {
                e.printStackTrace();
                break;
             }
        }
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e)
    {
        String currMsg = e.getActionCommand();      // Get input value
        inputField.setText("");
        try{
            myWriter.writeObject(cipher.encode(myName + ":" + currMsg));
        } catch(Exception e4){
            e4.printStackTrace();
           // Add name and send it
        }
    }                                               // to Server

    public static void main(String [] args)
    {
         ImprovedChatClient JR = new ImprovedChatClient();
         JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}


