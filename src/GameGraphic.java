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
    JPanel rightPnl = new JPanel();
    JPanel left = new JPanel();
    JPanel goldPnl = new JPanel(new BorderLayout());
    JPanel centerPnl = new JPanel();
    JPanel warning = new JPanel();
    JLabel warn;
    private boolean isPlaying = false;

    public GameGraphic(){
        setSize(1220, 725);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(s+"Amusement park"+s);
        setLayout(new BorderLayout(8, 6));
        getContentPane().setBackground(Color.pink);
        getRootPane().setBorder(BorderFactory.createMatteBorder(10,4,10,4,Color.BLACK));
        setResizable(false);
        GameGraphic.this.setIconImage(Main.image.getImage());
        initLeftPnl();
        initTopPnl();
        initCenterPnl();
        initRightPnl();
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
        if(cards.numberOfCardsPrize>=1){
            cardInformation card = card1.firstLevel(card1,"src/prize1.png", rightPnl, true, cards.numberOfCardsPrize, 1);
            one.add(card.panel);

        }
        one.add(Box.createRigidArea(new Dimension(20, 10)));
        rightPnl.add(one);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        two.add(Box.createRigidArea(new Dimension(20, 10)));
        if(cards.numberOfCardsPrize>=2){
            cardInformation card = card1.firstLevel(card1,"src/prize2.png", rightPnl, true, cards.numberOfCardsPrize, 2);
            two.add(card.panel);
        }
        two.add(Box.createRigidArea(new Dimension(20, 10)));
        rightPnl.add(two);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        three.add(Box.createRigidArea(new Dimension(20, 10)));
        if(cards.numberOfCardsPrize==3) {
            cardInformation card = card1.firstLevel(card1,"src/prize3.png", rightPnl, true, cards.numberOfCardsPrize, 3);
            three.add(card.panel);
        }
        three.add(Box.createRigidArea(new Dimension(20, 10)));
        rightPnl.add(three);
        rightPnl.add(Box.createRigidArea(new Dimension(10,15)));
        rightPnl.setBorder(new LineBorder(Color.black, 5));
        rightPnl.setPreferredSize(new Dimension(160,190));
        add(rightPnl, BorderLayout.EAST);
    }

    private void initLeftPnl() {
        left.setLayout(new GridLayout(7,1));
        ImageIcon slotIcon = new ImageIcon("src/coin.png");
        Image img = slotIcon.getImage();
        ImageIcon newImage = new ImageIcon(img.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        JLabel slot = new JLabel(newImage);
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
        JButton player1= new JButton("!PLAYER 1!");
        player1.setBackground(Color.cyan);
        player1.setFont(new Font("Courier New", Font.BOLD, 23));
        addMouseListenerToPlayers(player1, true);
        JButton player2= new JButton("!PLAYER 2!");
        player2.setFont(new Font("Courier New", Font.BOLD, 23));
        player2.setBackground(Color.RED);
        addMouseListenerToPlayers(player2, false);
        JButton howToPlay = new JButton("HOW TO PLAY?");
        howToPlay.setBackground(Color.pink);
        JLabel target = new JLabel("    TARGET = 15");
        target.setFont(new Font("Chiller", Font.BOLD, 28));
        howToPlay.setFont(new Font("Consolas", Font.BOLD, 15));
        top.setBorder(new LineBorder(Color.black, 3, true));
        top.setBackground(Color.white);
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(Box.createRigidArea(new Dimension(55,0)));
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
        JPanel[] cardPanel ={firstPnl, secondPnl, thirdPnl};
        cards card1;
        warn = new JLabel(Main.player1.name+" is Your turn");
        warning.add(warn);
        warning.setBorder(new LineBorder(Color.CYAN, 5));
        centerPnl.add(warning);
        centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
        centerPnl.add(Box.createRigidArea(new Dimension(0,40)));
        for(int i=0; i<cardPanel.length; i++){
            JPanel currentPnl = cardPanel[i];
            int startScor, endScore;
            int numberCards;
            currentPnl.setLayout(new BoxLayout(currentPnl, BoxLayout.X_AXIS));
            currentPnl.add(Box.createRigidArea(new Dimension(30,0)));
            if(i==0) {startScor=0; endScore=1; numberCards=cards.numberOfCards1; card1 = new cards(startScor, endScore);}
            else if(i==1) {startScor=2; endScore=4; numberCards=cards.numberOfCards2; card1 = new cards(startScor, endScore);}
            else{startScor=3; endScore=5; numberCards=cards.numberOfCards3; card1 = new cards(startScor, endScore);}

            for(int j=0; j<4; j++){
                int imagePath = (i+1)*10+j+1;
                if(numberCards>=j+1){
                    cardInformation card = card1.firstLevel(card1, "src/"+imagePath+".png", currentPnl, false, numberCards, j+1);
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
        add(centerPnl, BorderLayout.CENTER);
    }

    public void addMouseListenerToCard(JPanel cardPnl, cards card1, String imagePath, JPanel panel,
                                       boolean giftCard, int numberOfCards, int bounds, cardInformation card){
        cardPnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(cardPnl, 0 ,0, GameGraphic.this).x;
                int y =SwingUtilities.convertPoint(cardPnl, 0 ,0, GameGraphic.this).y;
                String title;
                if(Main.turn) title = "player1";
                else title = "player2";
                dialogCard dialogCard = new dialogCard(GameGraphic.this, title, cardPnl, x, y, panel, card1, imagePath, giftCard, numberOfCards, bounds, card);
                dialogCard.setVisible(true);
            }
        });
    }

    public class dialogCard extends JDialog{
        private final player player;
        private boolean possible=true;
        public dialogCard(JFrame frame, String title, JPanel cardPnl, int x, int y,
                          JPanel panel, cards card1, String imagePath,
                          boolean giftCard, int numberOfCards, int bounds, cardInformation card) {
            super(frame, title, true);
            if(Main.turn) player = Main.player1;
            else player = Main.player2;
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(350, 250);
            setLocation(x, y-50);
            JPanel mainPnl = new JPanel();
            mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.X_AXIS));
            JPanel leftButton = new JPanel();
            JPanel rightButton = new JPanel();
            leftButton.setLayout(new BoxLayout(leftButton, BoxLayout.Y_AXIS));
            rightButton.setLayout(new BoxLayout(rightButton, BoxLayout.Y_AXIS));
            JPanel n = new JPanel();
            n.setLayout(new BoxLayout(n, BoxLayout.Y_AXIS));
            ImageIcon imageIconB = new ImageIcon("src/buy.png");
            Image imageB = imageIconB.getImage();
            ImageIcon newImageB = new ImageIcon(imageB.getScaledInstance(75,75,Image.SCALE_SMOOTH));
            JLabel buyLabel = new JLabel(newImageB);
            ImageIcon imageIconH = new ImageIcon("src/hold.png");
            Image imageH = imageIconH.getImage();
            ImageIcon newImageH = new ImageIcon(imageH.getScaledInstance(75,75,Image.SCALE_SMOOTH));
            JLabel holdLabel = new JLabel(newImageH);
            JLabel buy = new JLabel("    BUY");
            buy.setFont(new Font("Jokerman", Font.BOLD,20));
            JLabel hold = new JLabel("   Hold");
            hold.setFont(new Font("Jokerman", Font.BOLD,20));
            n.add(Box.createRigidArea(new Dimension(0, 30)));
            n.add(cardPnl);
            n.add(Box.createRigidArea(new Dimension(0,30)));
            buyLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < card.array.length && possible; j++) {
                        for (int i = 0; i < 5 && possible; i++) {
                            if (player.coin[i].color.equals(card.array[j].color)) {
                                if (player.coin[i].price + player.coin[i + 5].price + player.goldCoin < card.array[j].price) possible = false;
                            }
                        }
                    }
                    if (possible) {
                        Main.turn= !Main.turn;
                        for (int j = 0; j < card.array.length; j++) {
                            for (int i = 0; i < 5; i++) {
                                if (player.coin[i].color.equals(card.array[j].color)) {
                                    if (player.coin[i + 5].price >= card.array[j].price) card.array[j].price = 0;
                                    if (player.coin[i + 5].price < card.array[j].price)
                                        card.array[j].price -= player.coin[i + 5].price;
                                    if (card.array[j].price > 0) {
                                        if (player.coin[i].price < card.array[j].price) {
                                            player.goldCoin--;
                                            coins.goldCn++;
                                            coins.coin[i].number += player.coin[i].price;
                                            player.coin[i].price = 0;
                                        }
                                        if (player.coin[i].price >= card.array[j].price) {
                                            player.coin[i].price -= card.array[j].price;
                                            coins.coin[i].number += card.array[j].price;
                                        }
                                    }
                                }
                            }
                        }
                        for(int i=5; i<10; i++){
                            if(player.coin[i].color.equals(card.colorGift)) player.coin[i].price++;
                        }
                        player.score += card.score;
                        panel.remove(cardPnl);
                        if (numberOfCards >= bounds) {
                            cardInformation card = card1.firstLevel(card1, imagePath, panel, giftCard, numberOfCards, bounds);
                            panel.add(card.panel, panel.getComponentZOrder(cardPnl));
                            addMouseListenerToCard(card.panel, card1, card.imagePath, panel, false, numberOfCards, bounds, card);
                            panel.revalidate();
                            panel.repaint();
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                        dispose();
                    }
                    else{
                        Object[] option = {"OK"};
                        JOptionPane.showOptionDialog(buyLabel.getParent(), "Your coin is not enough to buy this card!", "Oops!!",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                        dispose();
                    }
                }
            });
            holdLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] option = {"OK"};
                    if (player.numberReserve < 3) {
                        Main.turn= !Main.turn;
                        if(coins.goldCn>0) {
                            player.goldCoin++;
                            coins.goldCn--;
                            goldPnl.removeAll();
                            left.remove(goldPnl);
                            coins.CircularImageButton gold = new coins.CircularImageButton("src/goldcoin.jpg", coins.goldCn);
                            goldPnl.add(gold, BorderLayout.CENTER);
                            left.add(goldPnl, left.getComponentZOrder(goldPnl));
                            left.revalidate();
                            left.repaint();
                        }
                        player.reserveCard[player.numberReserve] = card;
                        player.numberReserve++;
                        panel.remove(cardPnl);
                        if (numberOfCards >= bounds) {
                            cardInformation card = card1.firstLevel(card1, imagePath, panel, giftCard, numberOfCards, bounds);
                            panel.add(card.panel, panel.getComponentZOrder(cardPnl));
                            addMouseListenerToCard(card.panel, card1, card.imagePath, panel, false, numberOfCards, bounds, card);
                            panel.revalidate();
                            panel.repaint();
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    }
                    else JOptionPane.showOptionDialog(holdLabel.getParent(), "The capacity of your reservation cards has been completed!", "Oops!!",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                    dispose();
                }
            });
            rightButton.add(buy);
            rightButton.add(Box.createRigidArea(new Dimension(0, 15)));
            rightButton.add(buyLabel);
            leftButton.add(hold);
            leftButton.add(Box.createRigidArea(new Dimension(0, 15)));
            leftButton.add(holdLabel);
            mainPnl.add(Box.createRigidArea(new Dimension(15,0)));
            mainPnl.add(rightButton);
            mainPnl.add(Box.createRigidArea(new Dimension(25,0)));
            mainPnl.add(n);
            mainPnl.add(Box.createRigidArea(new Dimension(25,0)));
            mainPnl.add(leftButton);
            mainPnl.add(Box.createRigidArea(new Dimension(15,0)));
            getContentPane().add(mainPnl);
        }
    }


    public void addMouseListenerToCoin(coins coin, coins.SquareImageButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).y-50;
                new dialogCoin(coin, x, y).setVisible(true);
            }
        });
    }

    public class dialogCoin extends JDialog{
        public dialogCoin(coins coin, int x, int y){
            super(GameGraphic.this, coin.name, true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(200, 150);
            setLocation(x, y);
            JPanel mainPnl = new JPanel();
            JPanel topPnl = new JPanel();
            JPanel centerPnl = new JPanel();
            centerPnl.setLayout(new BoxLayout(centerPnl,BoxLayout.X_AXIS));
            JLabel balance = new JLabel("Balance = " + coin.number );
            balance.setForeground(coin.colorCoin);
            balance.setFont(new Font("Jokerman", Font.BOLD, 18));
            topPnl.add(balance);
            ImageIcon one = new ImageIcon("src/1.png");
            Image imageOne = one.getImage();
            ImageIcon newImage = new ImageIcon(imageOne.getScaledInstance(40,40,Image.SCALE_SMOOTH));
            JLabel oneLabel = new JLabel(newImage);
            ImageIcon two = new ImageIcon("src/2.png");
            Image imageTwo = two.getImage();
            ImageIcon newImage2 = new ImageIcon(imageTwo.getScaledInstance(40,40,Image.SCALE_SMOOTH));
            JLabel twoLabel = new JLabel(newImage2);
            oneLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] option = {"OK"};
                    player player;
                    if(coin.number>0){
                        coin.number--;
                        if(Main.turn) player = Main.player1;
                        else player = Main.player2;
                        for(int i =0; i<5; i++){
                            if(player.coin[i].color.equals(coin.colorCoin)) player.coin[i].price++;
                        }
                        Main.turn = !Main.turn;
                    }
                    else JOptionPane.showOptionDialog(oneLabel.getParent(), "The balance of this bag is 0!", "Zero",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                    dispose();
                }
            });
            twoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] option = {"OK"};
                    player player;
                    if(coin.number==4){
                        coin.number = coin.number-2;
                        if(Main.turn) player = Main.player1;
                        else player = Main.player2;
                        for(int i =0; i<5; i++){
                            if(player.coin[i].color.equals(coin.colorCoin)) player.coin[i].price=player.coin[i].price+2;
                        }
                        Main.turn = !Main.turn;
                        dispose();
                    }
                    else JOptionPane.showOptionDialog(twoLabel.getParent(), "You can't buy 2 coins because the bag is not full!", "Can't Buying",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);

                }
            });
            centerPnl.add(Box.createRigidArea(new Dimension(20, 10)));
            centerPnl.add(oneLabel);
            centerPnl.add(Box.createRigidArea(new Dimension(20,10)));
            centerPnl.add(twoLabel);
            centerPnl.add(Box.createRigidArea(new Dimension(20,10)));
            mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
            mainPnl.add(Box.createRigidArea(new Dimension(0, 15)));
            mainPnl.add(topPnl);
            mainPnl.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPnl.add(centerPnl);
            mainPnl.add(Box.createRigidArea(new Dimension(0, 15)));
            getContentPane().add(mainPnl);
        }
    }

    public void addMouseListenerToPlayers(JButton button, boolean turn){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(button, 0, 0, GameGraphic.this).y-50;
                if(turn) new dialogPlayer(x, y, Main.player1).setVisible(true);
                else new dialogPlayer(x, y, Main.player2).setVisible(true);
            }
        });
    }
    public class dialogPlayer extends JDialog{
        public dialogPlayer(int x, int y, player player){
            super(GameGraphic.this, player.name + " Information!", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(500, 500);
            setLocation(x, y);
            JPanel mainPnl = new JPanel();
            mainPnl.setLayout(new GridLayout(1,3,15,10));
            JPanel first = new JPanel(new GridLayout(6,1,0,10));
            JPanel two = new JPanel(new GridLayout(5,1,0,10));
            JPanel three = new JPanel(new GridLayout(7,1,0,5));
            JLabel mainCoin = new JLabel("  Main Coins");
            mainCoin.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            first.add(mainCoin);
            for(int i=0; i<5; i++){
                String path = i + "Coin";
                ImageIcon one = new ImageIcon("src/"+path+".png");
                Image imageOne = one.getImage();
                ImageIcon newImage = new ImageIcon(imageOne.getScaledInstance(60,60,Image.SCALE_SMOOTH));
                int price = player.coin[i].price;
                JLabel label = new JLabel(newImage){
                    @Override
                    protected void paintComponent(Graphics g){
                        super.paintComponent(g);
                        int newImageWid = newImage.getIconWidth();
                        int newImageHei = newImage.getIconHeight();
                        g.setFont(new Font("Jokerman", Font.BOLD, 20));
                        g.drawString(String.valueOf(price), newImageWid/2+32, newImageHei/2+20);
                    }
                };
                addMouseListenerToCoinPlayer(label, player, i);
                first.add(label);
            }
            first.setBorder(new LineBorder(Color.BLACK, 5));
            mainPnl.add(first);
            JLabel score = new JLabel("  Score = " + player.score);
            JLabel reserveCard = new JLabel("Reserved Cards");
            score.setForeground(Color.red);
            score.setFont(new Font("Jokerman", Font.BOLD, 25));
            reserveCard.setFont(new Font("Consolas", Font.BOLD,19));
            JPanel text = new JPanel(new GridLayout(2,1, 0, 8));
            text.add(score);
            text.add(reserveCard);
            two.add(text);
            two.setBorder(new LineBorder(Color.BLACK, 5));
            JLabel giftCoin = new JLabel("  Special Coins");
            giftCoin.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            three.add(giftCoin);
            ImageIcon goldIcon = new ImageIcon("src/goldcoin.png");
            Image goldIm = goldIcon.getImage();
            ImageIcon newGold = new ImageIcon(goldIm.getScaledInstance(60,60,Image.SCALE_SMOOTH));
            JLabel goldLabel = new JLabel(newGold){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    int newImageWid = newGold.getIconWidth();
                    int newImageHei = newGold.getIconHeight();
                    g.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
                    g.drawString(String.valueOf(player.goldCoin), newImageWid/2+35, newImageHei/2+7);
                    System.out.println(player.name);
                }
            };
            three.add(goldLabel);
            for(int i=5; i<10; i++){
                String path = i + "Prize";
                ImageIcon gift = new ImageIcon("src/"+path+".png");
                Image giftIM = gift.getImage();
                ImageIcon newGift = new ImageIcon(giftIM.getScaledInstance(45,45,Image.SCALE_SMOOTH));
                String pric = String.valueOf(player.coin[i].price);
                JLabel giftLabel = new JLabel( newGift){
                    @Override
                    protected void paintComponent(Graphics g){
                        super.paintComponent(g);
                        int newImageWid = newGift.getIconWidth();
                        int newImageHei = newGift.getIconHeight();
                        g.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
                        g.drawString(pric, newImageWid/2+44, newImageHei/2+16);
                    }
                };
                three.add(giftLabel);
            }

            mainPnl.add(two);
            three.setBorder(new LineBorder(Color.BLACK, 5));
            mainPnl.add(three);
            getContentPane().add(mainPnl);
        }
    }

    public void addMouseListenerToCoinPlayer(JLabel label, player player, int i){
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = SwingUtilities.convertPoint(label, 0, 0, GameGraphic.this).x;
                int y = SwingUtilities.convertPoint(label, 0, 0, GameGraphic.this).y-50;
                new dialogReduseCoin(player, i, x, y).setVisible(true);
            }
        });
    }

    public class dialogReduseCoin extends JDialog{
        public dialogReduseCoin(player player, int i, int x, int y){
            super(GameGraphic.this, "Reduction of Coins", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(400, 100);
            setLocation(x, y);
            JPanel mainPnl = new JPanel(new FlowLayout());
            JTextField number = new JTextField(1);
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String text = number.getText();
                    Object[] option = {"OK"};
                    if(text.charAt(0)>String.valueOf(player.coin[i].price).charAt(0) || text.charAt(0)<String.valueOf(player.coin[i].price).charAt(0))
                        JOptionPane.showOptionDialog(number.getParent(), "Out Of Range!", "Oops",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                    else player.coin[i].price-= Integer.parseInt(text);
                    dispose(); // Close the number entry dialog
                }
            });
            submitButton.setBackground(player.coin[i].color);
            submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            JLabel text = new JLabel("Enter the number of coins you want to SUBTRACT: ");
            text.setForeground(player.coin[i].color);
            text.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            mainPnl.add(text);
            mainPnl.add(number);
            mainPnl.add(submitButton);
            getContentPane().add(mainPnl);
        }
    }
}