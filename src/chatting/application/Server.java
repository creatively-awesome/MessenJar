package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Server extends JFrame implements ActionListener{
    //All the J-Declaration will be global to be used outside constructor
    //JPanel is like div in WebDev. It creates division
    JPanel p1;
    //Text field to type messages
    JTextField textField;
    JButton sendButton;
    //Text Area to display sent messages
    JTextArea sentTextArea;
    //Text Area to display received messages
    static JTextArea receivedTextArea;

    //Connecting server and client using server-socket and socket
    static ServerSocket serverSocket;
    static Socket socket;

    //Tracking input and output of data
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    Server() {
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(67, 151, 136));
        p1.setBounds(0, 0, 500, 60);
        //add the element to the frame. Here Frame will add P1 so it's on the top
        add(p1);

        //Adding image icon to app
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/backIcon.png"));
        //Scale the image
        Image scaledBack = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon backIconButton = new ImageIcon(scaledBack);
        JLabel backIconLabel = new JLabel(backIconButton);
        backIconLabel.setBounds(5, 12, 30, 30);
        //P1 will add the icon so icon remains on top. It's like creating hierarchy
        p1.add(backIconLabel);

        //adding close functionality to back icon
        backIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    serverSocket.close();
                    socket.close();
                } catch (Exception e) {

                }
                System.exit(0);
            }
        });

        //adding profile picture icon
        ImageIcon user1DP = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/user1DPIcon.png"));
        Image scaledUser1DP = user1DP.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon user1DPIcon = new ImageIcon(scaledUser1DP);
        JLabel user1DPIconLabel = new JLabel(user1DPIcon);
        user1DPIconLabel.setBounds(40, 7, 45, 45);
        p1.add(user1DPIconLabel);

        //adding video icon
        ImageIcon video = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image scaledVideo = video.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon videoIcon = new ImageIcon(scaledVideo);
        JLabel videoIconLabel = new JLabel(videoIcon);
        videoIconLabel.setBounds(380, 12, 35, 35);
        p1.add(videoIconLabel);

        //adding phone icon
        ImageIcon phone = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image scaledPhone = phone.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon phoneIcon = new ImageIcon(scaledPhone);
        JLabel phoneIconLabel = new JLabel(phoneIcon);
        phoneIconLabel.setBounds(420, 12, 35, 35);
        p1.add(phoneIconLabel);

        //adding options icon
        ImageIcon options = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/options.png"));
        Image scaledOptions = options.getImage().getScaledInstance(12, 23, Image.SCALE_DEFAULT);
        ImageIcon optionsIcon = new ImageIcon(scaledOptions);
        JLabel optionsIconLabel = new JLabel(optionsIcon);
        optionsIconLabel.setBounds(455, 12, 35, 35);
        p1.add(optionsIconLabel);

        //adding user1 name to frame
        JLabel user1Name = new JLabel("Gaitonde");
        user1Name.setFont(new Font("SAN_SARIF", Font.BOLD, 16));
        user1Name.setForeground(Color.WHITE);
        user1Name.setBounds(100, 7, 100, 30);
        p1.add(user1Name);

        //adding user status to frame
        JLabel user1Status = new JLabel("Active Now");
        user1Status.setFont(new Font("SAN_SARIF", Font.PLAIN, 14));
        user1Status.setForeground(Color.white);
        user1Status.setBounds(100, 24, 100, 30);
        p1.add(user1Status);

        //Adding Text Area to display sent messages
        sentTextArea = new JTextArea();
        sentTextArea.setBounds(255, 65, 240, 680);
        sentTextArea.setBackground(Color.GREEN);
        sentTextArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sentTextArea.setEditable(false);
        sentTextArea.setAutoscrolls(true);
        sentTextArea.setLineWrap(true);
        sentTextArea.setWrapStyleWord(true);
        sentTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(sentTextArea);

        //Received Text Area
        receivedTextArea = new JTextArea();
        receivedTextArea.setBounds(5, 65, 240, 680);
        receivedTextArea.setBackground(Color.ORANGE);
        receivedTextArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        receivedTextArea.setEditable(false);
        receivedTextArea.setAutoscrolls(true);
        receivedTextArea.setLineWrap(true);
        receivedTextArea.setWrapStyleWord(true);
        add(receivedTextArea);

        //Adding text field to type messages
        textField = new JTextField();
        textField.setBounds(5, 750, 360, 40);
        textField.setFont(new Font("SAN_SARIF", Font.PLAIN, 17));
        add(textField);

        //Adding send button next to textField
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(67, 151, 136));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sendButton.setBounds(375, 750, 120, 40);
        sendButton.addActionListener(this);
        add(sendButton);

        //change the background of the fame
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        //sets the size of the frame
        setSize(500,800);
        //location of frame
        setLocation(400, 150);
        //Remove Maximise, minimise, close button header from app
        setUndecorated(true);
        //They are invisible by default. It displays the frame so should be written in the end
        setVisible(true);
    }

    //Abstract method of Action Listener, must be implemented
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String message = textField.getText();
            try {
                sentTextArea.setText(sentTextArea.getText() + "\n" + message +"\n");
                receivedTextArea.setText(receivedTextArea.getText() + "\n");
                textField.setText("");
                message += "\n";
                dataOutputStream.writeUTF(message);
            } catch (Exception e) {

            }
    }

    public static void main(String[] args) {
        new Server().setVisible(true);
        String messageInput = "";

        try {
            serverSocket = new ServerSocket(6001);
            socket = serverSocket.accept();
            while (true){
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                messageInput = dataInputStream.readUTF();
                receivedTextArea.setText(receivedTextArea.getText() + "\n" + messageInput);
            }
//            serverSocket.close();
//            socket.close();
        } catch (Exception e) {

        }
    }
}
