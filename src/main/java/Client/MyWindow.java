package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Window for client to chat
 */
public class MyWindow extends JFrame{
    private JTextField jtf;
    private JTextArea jta;
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket sock;
    private DataInputStream in;
    private DataOutputStream out;

    public MyWindow() {
        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jta = new JTextArea();                              //Создаем текстАреа
        jta.setEditable(false);                             //НЕ редактируемая
        jta.setLineWrap(true);                              //
        JScrollPane jsp = new JScrollPane(jta);             //Добавляем созданный текстовый ареа в прокручивающ. панель
        add(jsp, BorderLayout.CENTER);                      //добавляем на общую панель

        JPanel authPanel = new JPanel(new GridLayout());    //Панель авторизации
        add(authPanel, BorderLayout.NORTH);
        JTextField jtfLogin = new JTextField ("Login");
        authPanel.add(jtfLogin);
        JTextField jtfPass = new JTextField ("Password");
        authPanel.add(jtfPass);
        JButton jbAuth = new JButton("Auth");
        authPanel.add(jbAuth);
        jbAuth.addActionListener(e -> {
            connect("auth\t" + jtfLogin.getText() + "\t" + jtfPass.getText()); // формируем строку для авторизации
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());    //Панель нижняя
        add(bottomPanel, BorderLayout.SOUTH);
        jtf = new JTextField();                                 //text field
        bottomPanel.add(jtf, BorderLayout.CENTER);
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        JButton jbSend = new JButton("SEND");               //button send
        bottomPanel.add(jbSend, BorderLayout.EAST);
        jbSend.addActionListener(e -> {
            if (!jtf.getText().trim().isEmpty()) { //Если не пустое
                sendMsg();
                jtf.grabFocus();
            }
        });

        addWindowListener(new WindowAdapter() { //Слушатель окна
            @Override
            public void windowClosed(WindowEvent e) { //Назначаем действие на закрытие
                super.windowClosed(e);
                try {
                    out.writeUTF("end");
                    out.flush();
                    out.close();
                    in.close();
                } catch (IOException exc) {
                } finally {
                    try {
                        sock.close();
                    } catch (IOException ex) {
                    }
                }
            }
        });
        setVisible(true);
    }

    public void connect(String cmd) {
        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(cmd);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String w = in.readUTF();
                        if (w != null) {
                            if (w.equalsIgnoreCase("end session")) break;
                            jta.append(w);
                            jta.append("\n");
                            jta.setCaretPosition(jta.getDocument().getLength());
                        }
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void sendMsg() {
        try {
            String a = jtf.getText();
            out.writeUTF(a);
            out.flush();
            jtf.setText("");
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }
}
