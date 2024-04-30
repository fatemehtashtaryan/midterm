import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    static public ImageIcon image = new ImageIcon("src/amusement.png");
    GameGraphic boardGraphics;
    static public int turni=0;
    static public CardOnPage[] cardsOnPage = new CardOnPage[12];
    static public boolean typeGame;//true=PvE/false=PvP
    static boolean turn = true; //true=player1/false=player2
    static public player player1;
    static public player player2;
    static public boolean outOfRange = true;
    static public Color player1Color=new Color(142, 245, 169);
    static public Color player2Color=new Color(45, 181, 149);
    private final Font font = new Font("tahoma", Font.BOLD,25);
    public Main() {
        Main.this.setIconImage(image.getImage());
        setSize(600, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fum Amusement");
        setLayout(new BorderLayout());
        initPanel();
        setResizable(false);
        setVisible(true);
    }

    private void initPanel() {
        JPanel downPnl= new JPanel(new BorderLayout());
        JToggleButton personButton = new JToggleButton("PvP");
        JToggleButton computerButton = new JToggleButton("PvE");
        JPanel south = new JPanel();
        JButton guide1 = new JButton("Player1");
        JButton guide2 = new JButton("Player2");
        guide1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//player1's information
                int x = SwingUtilities.convertPoint(guide1, 0, 0, Main.this).x+400;
                int y = SwingUtilities.convertPoint(guide1, 0, 0, Main.this).y+120;
                new Setting("player1", x, y).setVisible(true);
            }
        });
        guide2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//player2's information
                int x = SwingUtilities.convertPoint(guide2, 0, 0, Main.this).x+400;
                int y = SwingUtilities.convertPoint(guide2, 0, 0, Main.this).y+120;
                new Setting("player2", x, y).setVisible(true);
            }
        });
        JButton start = new JButton("START NEW GAME");
        JLabel label= new JLabel("               !!WELCOME TO THE FUM AMUSEMENT PARK!!");
        label.setFont(new Font("Jokerman", Font.BOLD,18));
        start.setFont(new Font("Comic Sans MS", Font.BOLD,30));
        guide1.setBackground(Color.green);
        guide1.setFont(new Font("Papyrus", Font.BOLD,18));
        guide2.setBackground(Color.CYAN);
        guide2.setFont(new Font("Papyrus", Font.BOLD,18));
        start.setBackground(Color.MAGENTA);
        personButton.setFont(font);
        computerButton.setSelected(true);
        typeGame=true;
        personButton.setBackground(Color.ORANGE);
        computerButton.setBackground(Color.pink);
        computerButton.setFont(font);
        downPnl.add(computerButton);
        downPnl.add(personButton);
        personButton.setActionCommand("PvP");//with player
        computerButton.setActionCommand("PvE");//with computer
        ActionListener gameListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {//choosing type of game
                if(e.getActionCommand().equals("PvE")){
                    personButton.setSelected(!personButton.isSelected());
                    typeGame=true;
                }else{
                    computerButton.setSelected((!computerButton.isSelected()));
                    typeGame=false;
                }
            }
        };

        start.addActionListener(new ActionListener() {//starting new game
            @Override
            public void actionPerformed(ActionEvent e) {
                if(typeGame == true)player1 =new player(cards.getArrayCoin(), 0, "Computer", cardInformation.getReseve(), 0, true, 0, true);
                else player1 =new player(cards.getArrayCoin(), 0, "Player1", cardInformation.getReseve(), 0, true, 0, false);
                player2 =new player(cards.getArrayCoin(), 0, "Player2", cardInformation.getReseve(), 0, false, 0, false);
                if(turni==0) boardGraphics = new GameGraphic();
                else boardGraphics.setVisible(true);
                setVisible(false);
                turni++;
                boardGraphics.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                        GameGraphic.stopMusic();
                    }
                });
            }
        });
        personButton.addActionListener(gameListener);
        computerButton.addActionListener(gameListener);
        downPnl.setBorder(new LineBorder(Color.BLACK, 18));
        downPnl.add(personButton, BorderLayout.EAST);
        downPnl.add(computerButton, BorderLayout.WEST);
        south.setLayout(new GridLayout(1,2));
        south.add(guide1);
        south.add(guide2);
        downPnl.add(south, BorderLayout.SOUTH);
        downPnl.add(label, BorderLayout.NORTH);
        downPnl.add(start, BorderLayout.CENTER);
        add(downPnl);
    }
//JDialog for giving player information
    public class Setting extends JDialog{
        public Setting(String name, int x, int y){
            super(Main.this, "Setting", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400, 150);
            setLocation(x, y+70);
            JPanel mainPnl = new JPanel(new GridLayout(3,1,5,15));
            JPanel colors = new JPanel(new GridLayout(1,3,10,5));
            colors.setLayout(new BoxLayout(colors, BoxLayout.X_AXIS));
            JPanel textField = new JPanel();
            textField.setLayout(new BoxLayout(textField, BoxLayout.X_AXIS));
            JPanel submit = new JPanel();
            submit.setLayout(new BoxLayout(submit, BoxLayout.X_AXIS));
            JTextField nameField = new JTextField(20);
            JButton submitButton = new JButton("Submit");
            JButton color1 = new JButton("COLOR 1");
            color1.setFont(new Font("tahoma", Font.BOLD,20));
            if(name.equals("player1")) color1.setBackground(new Color(142, 245, 169));
            else color1.setBackground(new Color(45, 181, 149));
            JButton color2 = new JButton("COLOR 2");
            color2.setFont(new Font("tahoma", Font.BOLD,20));
            if(name.equals("player1")) color2.setBackground(new Color(166, 48, 134));
            else color2.setBackground(new Color(245, 113, 113));
            colors.add(Box.createRigidArea(new Dimension(55,0)));
            colors.add(color1);
            colors.add(Box.createRigidArea(new Dimension(30,0)));
            colors.add(color2);
            colors.add(Box.createRigidArea(new Dimension(55,0)));
            color1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(name.equals("player1"))player1Color=color1.getBackground();
                    else player2Color=color1.getBackground();
                }
            });
            color2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(name.equals("player1"))player1Color=color2.getBackground();
                    else player2Color=color2.getBackground();
                }
            });
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String namePlayer = nameField.getText();
                    if(name.equals("player1") && namePlayer.length()>4) player1.name=nameField.getText();
                    else if(namePlayer.length()>4) player2.name=nameField.getText();
                    dispose(); // Close the number entry dialog
                }
            });
            submitButton.setBackground(new Color(223, 171, 255));
            submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            JLabel text = new JLabel("The name of the " + name + ": ");
            text.setForeground(new Color(92, 34, 128));
            text.setFont(new Font("Papyrus", Font.BOLD, 18));
            textField.add(text);
            textField.add(Box.createRigidArea(new Dimension(20,0)));
            textField.add(nameField);
            mainPnl.setBackground(new Color(230, 215, 245));
            textField.setBackground(new Color(230, 215, 245));
            submit.setBackground(new Color(230, 215, 245));
            colors.setBackground(new Color(230, 215, 245));
            mainPnl.add(textField);
            mainPnl.add(colors);
            submit.add(Box.createRigidArea(new Dimension(150,0)));
            submit.add(submitButton);
            submit.add(Box.createRigidArea(new Dimension(150,0)));
            mainPnl.add(submit);
            getContentPane().add(mainPnl);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
