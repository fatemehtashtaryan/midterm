import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GameGraphic extends JFrame {
    char s = '❤';
    static Clip clip;
    JPanel firstPnl = new JPanel();
    JPanel secondPnl = new JPanel();
    JPanel thirdPnl = new JPanel();
    JPanel[] cardPanel ={firstPnl, secondPnl, thirdPnl};
    JPanel rightPnl = new JPanel();
    public static JPanel left = new JPanel();
    public static JPanel goldPnl = new JPanel(new BorderLayout());
    JPanel centerPnl = new JPanel();
    public static JPanel warning = new JPanel();
    public static JLabel warn;
    private boolean isPlaying = false;
    public static cardInformation cardOne;
    public static cardInformation cardTwo;
    public static cardInformation cardThree;


    public GameGraphic(){
        setSize(1220, 725);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Amusement park");
        setLayout(new BorderLayout(8, 6));
        getContentPane().setBackground(Color.pink);
        if(!Main.turn){
            getRootPane().setBorder(BorderFactory.createMatteBorder(10,4,10,4,Main.player2Color));
            getContentPane().setBackground(Main.player2Color);
        }
        if(Main.turn){
            getRootPane().setBorder(BorderFactory.createMatteBorder(10,4,10,4,Main.player1Color));
            getContentPane().setBackground(Main.player1Color);
        }
        setResizable(false);
        GameGraphic.this.setIconImage(Main.image.getImage());
        initLeftPnl();
        initTopPnl();
        initCenterPnl();
        add(centerPnl, BorderLayout.CENTER);
        initRightPnl();
        goldPnl.setBackground(Color.WHITE);
        Container conten =getContentPane();
        for(Component comp : conten.getComponents()){
            if(comp instanceof JPanel){
                comp.setBackground(Color.white);
            }
        }
        setVisible(true);
    }

    private void initRightPnl() {
        rightPnl.setLayout(new BoxLayout(rightPnl, BoxLayout.Y_AXIS));
        JPanel image = new JPanel();
        JPanel one = new JPanel();
        one.setLayout(new BoxLayout(one, BoxLayout.X_AXIS));
        JPanel two = new JPanel();
        two.setLayout(new BoxLayout(two, BoxLayout.X_AXIS));
        JPanel three = new JPanel();
        three.setLayout(new BoxLayout(three, BoxLayout.X_AXIS));
        image.setLayout(new FlowLayout());
        ImageIcon slotIcon = new ImageIcon("src/prize.jpg");
        Image img = slotIcon.getImage();
        ImageIcon newImage = new ImageIcon(img.getScaledInstance(140,120,Image.SCALE_SMOOTH));
        JLabel prize = new JLabel(newImage);
        image.setPreferredSize(new Dimension(140,100));
        image.add(prize);
        rightPnl.add(image);
        rightPnl.add(Box.createRigidArea(new Dimension(50,15)));
        one.add(Box.createRigidArea(new Dimension(20, 10)));
        cards card1 = new cards(8, 12);
        cardOne = card1.firstLevel(card1,"src/prize1.png", rightPnl, true, cards.numberOfCardsPrize, 1, false);
        one.add(cardOne.panel);
        one.add(Box.createRigidArea(new Dimension(20, 10)));
        rightPnl.add(one);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        two.add(Box.createRigidArea(new Dimension(20, 10)));
        cardTwo = card1.firstLevel(card1,"src/prize2.png", rightPnl, true, cards.numberOfCardsPrize, 2, false);
        two.add(cardTwo.panel);
        two.add(Box.createRigidArea(new Dimension(20, 10)));
        rightPnl.add(two);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        three.add(Box.createRigidArea(new Dimension(20, 10)));
        cardThree = card1.firstLevel(card1,"src/prize3.png", rightPnl, true, cards.numberOfCardsPrize, 3, false);
        three.add(cardThree.panel);
        three.add(Box.createRigidArea(new Dimension(20, 10)));
        one.setBackground(Color.WHITE);
        two.setBackground(Color.WHITE);
        three.setBackground(Color.WHITE);
        rightPnl.add(three);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        rightPnl.setBorder(new LineBorder(Color.black, 5));
        rightPnl.setPreferredSize(new Dimension(160,190));
        add(rightPnl, BorderLayout.EAST);
    }

    private void initLeftPnl() {
        left.setLayout(new GridLayout(7,1));
        ImageIcon slotIcon = new ImageIcon("src/slot.png");
        Image img = slotIcon.getImage();
       // ImageIcon newImage = new ImageIcon(img.getScaledInstance(100,100,Image.SCALE_SMOOTH));
        JLabel slot = new JLabel(slotIcon);
        left.add(slot);
        goldPnl.add(coins.gold, BorderLayout.CENTER);
        left.add(goldPnl);
        for(int i=0; i<5; i++){
            coins.SquareImageButton coin = coins.coin[i].coinCreate();
            addMouseListenerToCoin( coins.coin[i], coin);
            left.add(coin);
        }
        left.setBorder(new LineBorder(Color.black, 5));
        left.setPreferredSize(new Dimension(120, 190));
        add(left, BorderLayout.WEST);
    }

    private void initTopPnl() {
        JPanel top = new JPanel();
        top.setBackground(new Color(212, 113, 245));
        ImageIcon musicIcon = new ImageIcon("src/music.png");
        Image image = musicIcon.getImage();
        ImageButton musicButton = new ImageButton(image);
        musicButton.setText("                      ");
        musicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPlaying) {
                    stopMusic();
                    musicButton.setText("                      ");
                } else {
                    playMusic();
                    musicButton.setText("                      ");
                }
                isPlaying = !isPlaying;
            }
        });
        JButton player1= new JButton(STR."!\{Main.player1.name}!");
        player1.setBackground(Main.player1Color);
        player1.setFont(new Font("Papyrus", Font.BOLD, 30));
        addMouseListenerToPlayers(player1, true);
        JButton player2= new JButton(STR."!\{Main.player2.name}!");
        player2.setFont(new Font("Papyrus", Font.BOLD, 30));
        player2.setBackground(Main.player2Color);
        addMouseListenerToPlayers(player2, false);
        JButton howToPlay = new JButton("Refresh Cards");
        howToPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = getComponentZOrder(centerPnl);
                warning.removeAll();
                firstPnl.removeAll();
                secondPnl.removeAll();
                thirdPnl.removeAll();
                centerPnl.removeAll();
                remove(centerPnl);
                initCenterPnl();
                add(centerPnl, index);
                revalidate();
                repaint();
            }
        });
        howToPlay.setBackground(Color.pink);
        JLabel target = new JLabel("    TARGET = 15");
        target.setFont(new Font("Chiller", Font.BOLD, 28));
        howToPlay.setFont(new Font("Curlz MT", Font.BOLD, 20));
        top.setBorder(new LineBorder(Color.black, 3, true));
        top.setBackground(Color.white);
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(Box.createRigidArea(new Dimension(75,0)));
        top.add(musicButton);
        top.add(Box.createRigidArea(new Dimension(110,0)));
        top.add(player1);
        top.add(Box.createRigidArea(new Dimension(55,0)));
        top.add(howToPlay);
        top.add(Box.createRigidArea(new Dimension(55,0)));
        top.add(player2);
        top.add(Box.createRigidArea(new Dimension(110,0)));
        top.add(target);
        top.setPreferredSize(new Dimension(52, 50));
        add(top, BorderLayout.NORTH);
    }
    public static class ImageButton extends JButton {
        private final Image backgroundImage;

        public ImageButton(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!getModel().isPressed()) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.ORANGE);
            }
        }
    }
    private void playMusic() {
        try {
            File file = new File("src/songi.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    public void initCenterPnl(){
        cards card1;
        warn = new JLabel(Main.player1.name+" is Your turn!");
        warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
        warning.add(warn);
        warning.setBorder(new LineBorder(Main.player1Color, 5));
        centerPnl.add(warning);
        centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
        centerPnl.add(Box.createRigidArea(new Dimension(0,40)));
        for(int i=0; i<cardPanel.length; i++){
            cardPanel[i].setBackground(Color.WHITE);
            JPanel currentPnl = cardPanel[i];
            int startScor, endScore;
            int numberCards;
            currentPnl.setLayout(new BoxLayout(currentPnl, BoxLayout.X_AXIS));
            currentPnl.add(Box.createRigidArea(new Dimension(30,0)));
            if(i==0) {startScor=0; endScore=1; numberCards=cards.numberOfCards1;}
            else if(i==1) {startScor=2; endScore=4; numberCards=cards.numberOfCards2;}
            else{startScor=3; endScore=5; numberCards=cards.numberOfCards3;}

            for(int j=0; j<4; j++){
                int imagePath = (i+1)*10+j+1;
                if(numberCards>=j+1){
                    card1 = new cards(startScor, endScore);
                    cardInformation card = card1.firstLevel(card1, STR."src/\{imagePath}.png", currentPnl, false, numberCards, j+1, false);
                    currentPnl.add(card.panel);
                    addMouseListenerToCard(card.panel, card1, card.imagePath, currentPnl, false, numberCards, j+1, card);
                }
                if(j!=3) currentPnl.add(Box.createRigidArea(new Dimension(50, 0)));
            }

            currentPnl.add(Box.createRigidArea(new Dimension(30, 0)));
            centerPnl.add(currentPnl);
            centerPnl.add(Box.createRigidArea(new Dimension(0,30)));
        }

        centerPnl.setBorder(new LineBorder(Color.black, 5));
    }

    public void addMouseListenerToCard(JPanel cardPnl, cards card1, String imagePath, JPanel panel,
                                       boolean giftCard, int numberOfCards, int bounds, cardInformation card){
        cardPnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(cardPnl, 0 ,0, GameGraphic.this).x;
                int y =SwingUtilities.convertPoint(cardPnl, 0 ,0, GameGraphic.this).y;
                String title;
                if(Main.turn) title = Main.player1.name;
                else title = Main.player2.name;
                cardInformation.dialogCard dialogCard = new cardInformation.dialogCard(GameGraphic.this, title, cardPnl, x, y, panel, card1, imagePath, giftCard, numberOfCards, bounds, card, GameGraphic.this);
                dialogCard.setVisible(true);
            }
        });
    }

    public void addMouseListenerToCoin(coins coin, coins.SquareImageButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).y-50;
                new coins.dialogCoin(coin, x, y, GameGraphic.this).setVisible(true);
            }
        });
    }

    public void addMouseListenerToPlayers(JButton button, boolean turn){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).y-50;
                if(turn) new player.dialogPlayer(x, y, Main.player1, GameGraphic.this).setVisible(true);
                else new player.dialogPlayer(x, y, Main.player2, GameGraphic.this).setVisible(true);
            }
        });
    }

    public void addMouseListenerToCoinPlayer(JLabel label, player player, int i){
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(label, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(label, 0, 0, GameGraphic.this).y-50;
                new coins.dialogReduceCoin(player, i, x, y, GameGraphic.this).setVisible(true);
            }
        });
    }
}