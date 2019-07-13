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

    public static final int PORT = 8765;
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

        InetAddress addr =
                InetAddress.getByName(serverName);
        connection = new Socket(addr, PORT);   // Connect to server with new
                                               // Socket

        myWriter = new ObjectOutputStream(connection.getOutputStream());
        myWriter.flush();
        myReader = new ObjectInputStream(connection.getInputStream());

        BigInteger E = (BigInteger)myReader.readObject();
        System.out.println("Received E: " + E);
        BigInteger N = (BigInteger)myReader.readObject();
        System.out.println("Received N: " + N);
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
        BigInteger key = new BigInteger(1, bytes);
        key = key.modPow(E, N);
        System.out.println("Generated Key: " + key);
        myWriter.writeObject(key); myWriter.flush();

        myName = JOptionPane.showInputDialog(this, "Enter your user name: ");

        myWriter.writeObject(cipher.encode(myName)); myWriter.flush();

        serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
        
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
                            myWriter.writeObject(cipher.encode("CLIENT CLOSING")); myWriter.flush();
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

                System.out.println("Bytes Received: ");
                    for (int i = 0; i < currMsg.length; i++){
                        System.out.print(currMsg[i] + " ");
                    }
                    System.out.println();

                String msg = new String(cipher.decode(currMsg));
                byte [] decoded = msg.getBytes();

                System.out.println("Decoded Bytes: ");
                    for (int i = 0; i < decoded.length; i++){
                        System.out.print(decoded[i] + " ");
                    }
                    System.out.println();

                System.out.println("Decrypted String: " + msg);


			    outputArea.append(msg + "\n");
             }
             catch (Exception e)
             {
                e.printStackTrace();
                break;
             }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        String currMsg = e.getActionCommand();      // Get input value
        inputField.setText("");
        try{
            byte [] originalBytes = currMsg.getBytes();
            byte [] encoding = cipher.encode(currMsg);

            myWriter.writeObject(cipher.encode(myName + ":" + currMsg)); myWriter.flush();

            System.out.println("Original String Message: " + currMsg + "\n");

            System.out.println("Corresponding Array of Bytes: ");
                    for (int i = 0; i < originalBytes.length; i++){
                        System.out.print(originalBytes[i] + " ");
                    }
                    System.out.println();

            System.out.println("Encrypted Array of Bytes: ");
                    for (int i = 0; i < encoding.length; i++){
                        System.out.print(encoding[i] + " ");
                    }
                    System.out.println();



        } catch(Exception e4){
            e4.printStackTrace();
        }
    }                                               

    public static void main(String [] args)
    {
         ImprovedChatClient JR = new ImprovedChatClient();
         JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}


