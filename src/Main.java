import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    static public ImageIcon image = new ImageIcon("src/amusement.png");
    private boolean typeGame;//true=PvE/false=PvP
    static boolean turn = true; //true=player1/false=player2
    static public player player1 =new player(cards.getArrayCoin(), 0, "Player1", cardInformation.getReseve(), 1, true, 0);
    static public player player2 =new player(cards.getArrayCoin(), 0, "Player2", cardInformation.getReseve(), 1, false, 0);

    private final Font font = new Font("tahoma", Font.BOLD,25);
    public Main() {
        Main.this.setIconImage(image.getImage());
        setSize(600, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fum Amusement");
        setLayout(new BorderLayout());
        initDownPanel();
        setResizable(false);
        setVisible(true);
    }

    private void initDownPanel() {
        JPanel downPnl= new JPanel(new BorderLayout());
        JToggleButton personButton = new JToggleButton("PvP");
        JToggleButton computerButton = new JToggleButton("PvE");
        JButton guide = new JButton("HOW TO PLAY?");
        JButton start = new JButton("START NEW GAME");
        JLabel label= new JLabel("               !!WELCOME TO THE FUM AMUSEMENT PARK!!");
        label.setFont(new Font("Jokerman", Font.BOLD,18));
        start.setFont(new Font("Comic Sans MS", Font.BOLD,30));
        guide.setBackground(Color.yellow);
        guide.setFont(new Font("Consolas", Font.BOLD,18));
        start.setBackground(Color.MAGENTA);
        personButton.setFont(font);
        computerButton.setSelected(true);
        typeGame=true;
        personButton.setBackground(Color.ORANGE);
        computerButton.setBackground(Color.pink);
        computerButton.setFont(font);
        downPnl.add(computerButton);
        downPnl.add(personButton);
        personButton.setActionCommand("PvP");
        computerButton.setActionCommand("PvE");
        ActionListener gameListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("PvE")){
                    personButton.setSelected(!personButton.isSelected());
                    typeGame=true;
                }else{
                    computerButton.setSelected((!computerButton.isSelected()));
                    typeGame=false;
                }
            }
        };
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameGraphic boardGraphics = new GameGraphic();
                setVisible(false);
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
        downPnl.add(guide, BorderLayout.SOUTH);
        downPnl.add(label, BorderLayout.NORTH);
        downPnl.add(start, BorderLayout.CENTER);
        add(downPnl);
    }

    public static void main(String[] args) {
        new Main();
    }
}
