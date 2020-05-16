import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;

public class Login extends JFrame{
    public static JFrame myFrame;
    public LoginPanel loginPanel;

    public Login() throws IOException
    {
        initilize();
    }

    public void initilize()throws IOException {
        BufferedImage backgroundImage = new BufferedImage(400,200,BufferedImage.TYPE_INT_RGB);
        myFrame = new JFrame("Message");
        myFrame.setLayout(new BorderLayout());

        loginPanel = new LoginPanel(backgroundImage);

        //Panel
        Container c = myFrame.getContentPane();

        c.add(loginPanel, BorderLayout.WEST);
        //myFrame.setSize(300, 150);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);
       
    } 

}

class LoginPanel extends JPanel implements ActionListener {
    public JLabel user_no=null;
    public JLabel password=null;
    public JButton btn_login = null;
    public JButton btn_newUser = null;
    public JTextField usernameField=null;
    public JPasswordField passwordField=null;
    public static String userNo;
    public ArrayList msgList = new ArrayList();

    private BufferedImage backgroundImage;

    public LoginPanel(BufferedImage backgroundImage)
    {
        this.backgroundImage = backgroundImage;
        initilize();
        initConnection();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void initilize() {

        Dimension size = getPreferredSize();
        size.width = 285;
        size.height = 150;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder(null, "Login Details", TitledBorder.CENTER, TitledBorder.TOP));

        user_no = new JLabel("User No : ");
        password = new JLabel("Password : ");
        usernameField = new JTextField(14);
        passwordField = new JPasswordField(14);
        btn_login = new JButton("Login");
        btn_newUser = new JButton("New User");
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //// First column /////////////////////////

        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;
        add(user_no, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(password, gc);

        //// Second column
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 1;
        gc.gridy = 0;
        add(usernameField, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(passwordField, gc);

        // Final row
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(btn_login, gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(btn_newUser, gc);

    }

    private void initConnection() {
        btn_login.addActionListener(this);
        btn_newUser.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(btn_login))
        {
            userNo = usernameField.getText();
            String userPwd = passwordField.getText();
            System.out.println(userNo+" "+userPwd);
            Connection con = null;

            try
            {
                PreparedStatement pstmt;
                String sql = "SELECT sender_no,pwd FROM tb_login where sender_no ='"+userNo+"'"; 
                pstmt= con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next())
                {
                    if(userNo.equalsIgnoreCase(rs.getString(1))&&userPwd.equalsIgnoreCase(rs.getString(2)))
                    {
                        System.out.println("Successfull login");
                        String sql2 = "Select msg from tb_msg where sender_no='"+userNo+"'";
                        pstmt= con.prepareStatement(sql2);
                        ResultSet rs2 = pstmt.executeQuery();

                        while(rs2.next())
                        {
                            msgList.add(rs2.getString(1));
                        }
                        System.out.println("msgList = "+msgList.size());

                        Login.myFrame.dispose();
                        //new AddMessage(userNo,msgList);
                    }
                }
            }
            catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        if(e.getSource().equals(btn_newUser))
        {
            Login.myFrame.dispose();
            //new NewUser();
        }
    }
}