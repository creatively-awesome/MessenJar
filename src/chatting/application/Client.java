package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client extends JFrame implements ActionListener{
    //All the J-Declaration will be global to be used outside constructor
    //JPanel is like div in WebDev. It creates division
    JPanel p1;
    //Text field to type messages
    JTextField textField;
    JButton sendButton;
    //Text Area to display sent messages
    static JTextArea sentTextArea;
    //Text Area to display received messages
    static JTextArea receivedTextArea;

    //creating socket
    static Socket socket;

    //Tracking input and output of data
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    Client() {
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
                    socket.close();
                } catch (Exception e) {

                }
                System.exit(0);
            }
        });

        //adding profile picture icon
        ImageIcon user2DP = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/user2DPIcon.png"));
        Image scaledUser2DP = user2DP.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon user2DPIcon = new ImageIcon(scaledUser2DP);
        JLabel user2DPIconLabel = new JLabel(user2DPIcon);
        user2DPIconLabel.setBounds(40, 7, 45, 45);
        p1.add(user2DPIconLabel);

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
        JLabel user2Name = new JLabel("Gaitonde");
        user2Name.setFont(new Font("SAN_SARIF", Font.BOLD, 16));
        user2Name.setForeground(Color.WHITE);
        user2Name.setBounds(100, 7, 100, 30);
        p1.add(user2Name);

        //adding user status to frame
        JLabel user2Status = new JLabel("Active Now");
        user2Status.setFont(new Font("SAN_SARIF", Font.PLAIN, 14));
        user2Status.setForeground(Color.white);
        user2Status.setBounds(100, 24, 100, 30);
        p1.add(user2Status);

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
        setLocation(1100, 150);
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
                textField.setText("");
                receivedTextArea.setText(receivedTextArea.getText() + "\n");
                message += "\n";
                dataOutputStream.writeUTF(message);
            } catch (Exception e) {

            }
    }

    public static void main(String[] args) {
        new Client().setVisible(true);
        String messageInput = "";
        try {
            socket = new Socket("127.0.0.1", 6001);
            while(true) {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                messageInput = dataInputStream.readUTF();
                receivedTextArea.setText(receivedTextArea.getText() + "\n" + messageInput);
                sentTextArea.setText(sentTextArea.getText() + "\n");
            }

        } catch (Exception e){

        }
    }
}
