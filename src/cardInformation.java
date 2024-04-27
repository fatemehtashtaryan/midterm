import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class cardInformation {
    String imagePath;
    Color colorGift;
    cards.cardsArray[] array;
    int score;
    boolean isHold;
    boolean holder;
    boolean isPrizeCard;
    JPanel panel;
    int numberOfCard;
    public cardInformation(String imagePath, Color colorGift, cards.cardsArray[] array, int score,
                           boolean isPrizeCard, boolean isHold, boolean holder, JPanel panel, int numberOfCard){
        this.imagePath= imagePath;
        this.colorGift=colorGift;
        this.array=array;
        this.score=score;
        this.isPrizeCard=isPrizeCard;
        this.isHold=isHold;
        this.holder=holder;
        this.panel = panel;
        this.numberOfCard=numberOfCard;
    }
    public static cardInformation[] getReseve(){
        cardInformation[] reserve= new cardInformation[3];
        for(int i=0; i<3; i++){
            reserve[i]=new cardInformation(null, null, null, 0, false, true, true, null, 0);
        }
        return reserve;
    }

    public static class dialogCard extends JDialog{
        private final player player;
        private boolean possible=true;
        public dialogCard(JFrame frame, String title, JPanel cardPnl, int x, int y,
                          JPanel panel, cards card1, String imagePath,
                          boolean giftCard, int numberOfCards, int bounds, cardInformation card, GameGraphic GameGraphic) {
            super(frame, title, true);
            cardInformation.givePrizeCard(GameGraphic);
            int index = panel.getComponentZOrder(cardPnl);
            int goldIndex = GameGraphic.left.getComponentZOrder(GameGraphic.goldPnl);
            int warnIndex = GameGraphic.centerPnl.getComponentZOrder(GameGraphic.warning);
            final player[] playerWarn = new player[1];
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
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    panel.add(cardPnl, index);
                    panel.repaint();
                    panel.revalidate();

                }
            });
            if(Main.outOfRange && !player.computer) {
                buyLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                            for (int j = 0; j < card.array.length && possible; j++) {
                                for (int i = 0; i < 5 && possible; i++) {
                                    if (player.coin[i].color.equals(card.array[j].color)) {
                                        if (player.coin[i].price + player.coin[i + 5].price + player.goldCoin < card.array[j].price)
                                            possible = false;
                                    }
                                }
                            }
                            if (possible) {
                                Main.turn = !Main.turn;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                if (!Main.turn) playerWarn[0] = Main.player2;
                                else playerWarn[0] = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn[0].name + " is Your turn!");
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                for (int j = 0; j < card.array.length; j++) {
                                    for (int i = 0; i < 5; i++) {
                                        if (player.coin[i].color.equals(card.array[j].color)) {
                                            if (player.coin[i + 5].price >= card.array[j].price)
                                                card.array[j].price = 0;
                                            if (player.coin[i + 5].price < card.array[j].price)
                                                card.array[j].price -= player.coin[i + 5].price;
                                            if (card.array[j].price > 0) {
                                                if (player.coin[i].price < card.array[j].price) {
                                                    player.goldCoin--;
                                                    coins.goldCn++;
                                                    GameGraphic.goldPnl.removeAll();
                                                    GameGraphic.left.remove(GameGraphic.goldPnl);
                                                    coins.CircularImageButton gold = new coins.CircularImageButton("src/goldcoin.jpg", coins.goldCn);
                                                    GameGraphic.goldPnl.add(gold, BorderLayout.CENTER);
                                                    GameGraphic.left.add(GameGraphic.goldPnl, goldIndex);
                                                    GameGraphic.left.revalidate();
                                                    GameGraphic.left.repaint();
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
                                for (int i = 5; i < 10; i++) {
                                    if (player.coin[i].color.equals(card.colorGift)) player.coin[i].price++;
                                }
                                player.score += card.score;
                                panel.remove(cardPnl);
                                if (numberOfCards >= bounds) {
                                    cardInformation cards = card1.firstLevel(card1, imagePath, panel, giftCard, numberOfCards, bounds, false, card.numberOfCard);
                                    panel.add(cards.panel, index);
                                    Main.cardsOnPage[card.numberOfCard]=new CardOnPage(cards, index, panel, card1);
                                    GameGraphic.addMouseListenerToCard(cards.panel, card1, card.imagePath, panel, false, numberOfCards, bounds, cards);
                                    panel.repaint();
                                    panel.revalidate();
                                }
                                if (card.isHold) {
                                    card.holder = false;
                                }
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (!Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
                                    GameGraphic.getContentPane().setBackground(Main.player2Color);
                                }
                                if (Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player1Color));
                                    GameGraphic.getContentPane().setBackground(Main.player1Color);
                                    if(Main.player1.computer) CardOnPage.computerGame(GameGraphic);
                                }
                                dispose();
                            } else {
                                Object[] option = {"OK"};
                                JOptionPane.showOptionDialog(buyLabel.getParent(), "Your coin is not enough to buy this card!", "Oops!!",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                                panel.add(cardPnl, index);
                                panel.repaint();
                                panel.revalidate();
                                dispose();
                            }
                    }
                });
                if (!card.isHold)
                    holdLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            Object[] option = {"OK"};
                            if (player.numberReserve < 3) {
                                Main.turn = !Main.turn;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                if (!Main.turn) playerWarn[0] = Main.player2;
                                else playerWarn[0] = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn[0].name + " is Your turn!");
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                if (coins.goldCn > 0) {
                                    player.goldCoin++;
                                    coins.goldCn--;
                                    GameGraphic.goldPnl.removeAll();
                                    GameGraphic.left.remove(GameGraphic.goldPnl);
                                    coins.CircularImageButton gold = new coins.CircularImageButton("src/goldcoin.jpg", coins.goldCn);
                                    GameGraphic.goldPnl.add(gold, BorderLayout.CENTER);
                                    GameGraphic.left.add(GameGraphic.goldPnl, goldIndex);
                                    GameGraphic.left.revalidate();
                                    GameGraphic.left.repaint();
                                }
                                card.isHold = true;
                                player.reserveCard[player.numberReserve] = card;
                                player.numberReserve++;
                                panel.remove(cardPnl);
                                if (numberOfCards >= bounds) {
                                    cardInformation cards = card1.firstLevel(card1, imagePath, panel, giftCard, numberOfCards, bounds, false, card.numberOfCard);
                                    panel.add(cards.panel, index);
                                    Main.cardsOnPage[card.numberOfCard]=new CardOnPage(cards, index, panel, card1);
                                    GameGraphic.addMouseListenerToCard(cards.panel, card1, card.imagePath, panel, false, numberOfCards, bounds, cards);
                                    panel.repaint();
                                    panel.revalidate();
                                }

                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (!Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
                                    GameGraphic.getContentPane().setBackground(Main.player2Color);
                                }
                                if (Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player1Color));
                                    GameGraphic.getContentPane().setBackground(Main.player1Color);
                                    if(Main.player1.computer) CardOnPage.computerGame(GameGraphic);
                                }
                                dispose();
                            } else
                                JOptionPane.showOptionDialog(holdLabel.getParent(), "The capacity of your reservation cards has been completed!", "Oops!!",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                            dispose();
                        }
                    });
            }
            else;
            rightButton.add(buy);
            rightButton.add(Box.createRigidArea(new Dimension(0, 15)));
            rightButton.add(buyLabel);
            if(!card.isHold) leftButton.add(hold);
            leftButton.add(Box.createRigidArea(new Dimension(0, 15)));
            if(!card.isHold) leftButton.add(holdLabel);
            mainPnl.add(Box.createRigidArea(new Dimension(15,0)));
            mainPnl.add(rightButton);
            mainPnl.add(Box.createRigidArea(new Dimension(25,0)));
            mainPnl.add(n);
            mainPnl.add(Box.createRigidArea(new Dimension(25,0)));
            if(!card.isHold) mainPnl.add(leftButton);
            mainPnl.add(Box.createRigidArea(new Dimension(15,0)));
            getContentPane().add(mainPnl);
        }
    }

    public static void givePrizeCard(GameGraphic gameGraphic){
        player player;
        boolean prize1 = true;
        boolean prize2 = false;
        boolean prize3 = false;
        if(Main.turn) player = Main.player1;
        else player = Main.player2;
        for(int i =5; i<10 && prize1; i++){
            for(int j =0; j<GameGraphic.cardOne.array.length && prize1; j++) {
                if(player.coin[i].color.equals(GameGraphic.cardOne.array[j].color)){
                    if(!(player.coin[i].price==GameGraphic.cardOne.array[j].price)) prize1=false;
                }
            }
        }
        if(prize1 && GameGraphic.cardOne.isPrizeCard){
            player.score+=GameGraphic.cardOne.score;
            GameGraphic.one.removeAll();
            GameGraphic.rightPnl.remove(GameGraphic.one);
            GameGraphic.cardOne.panel.setBackground(Color.CYAN);
            GameGraphic.one.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.one.add(GameGraphic.cardOne.panel);
            GameGraphic.one.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.rightPnl.add(GameGraphic.one, GameGraphic.rightPnl.getComponentZOrder(GameGraphic.one));
            GameGraphic.rightPnl.revalidate();
            GameGraphic.rightPnl.repaint();
            GameGraphic.cardOne.isPrizeCard=false;
        }
        if(!prize1) prize2=true;
        for(int i =5; i<10 && prize2; i++){
            for(int j =0; j<GameGraphic.cardTwo.array.length && prize2; j++) {
                if(player.coin[i].color.equals(GameGraphic.cardTwo.array[j].color)){
                    if(!(player.coin[i].price==GameGraphic.cardTwo.array[j].price)) prize2=false;
                }
            }
        }
        if(prize2 && GameGraphic.cardTwo.isPrizeCard){
            player.score+=GameGraphic.cardTwo.score;
            GameGraphic.two.removeAll();
            GameGraphic.rightPnl.remove(GameGraphic.two);
            GameGraphic.cardTwo.panel.setBackground(Color.CYAN);
            GameGraphic.two.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.two.add(GameGraphic.cardTwo.panel);
            GameGraphic.two.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.rightPnl.add(GameGraphic.two, GameGraphic.rightPnl.getComponentZOrder(GameGraphic.two));
            GameGraphic.rightPnl.revalidate();
            GameGraphic.rightPnl.repaint();
            GameGraphic.cardTwo.isPrizeCard=false;
        }
        if(!prize1 && !prize2) prize3=true;
        for(int i =5; i<10 && prize3; i++){
            for(int j =0; j<GameGraphic.cardThree.array.length && prize3; j++) {
                if(player.coin[i].color.equals(GameGraphic.cardThree.array[j].color)){
                    if(!(player.coin[i].price==GameGraphic.cardThree.array[j].price)) prize3=false;
                }
            }
        }
        if(prize3 && GameGraphic.cardThree.isPrizeCard){
            player.score+=GameGraphic.cardThree.score;
            GameGraphic.three.removeAll();
            GameGraphic.rightPnl.remove(GameGraphic.three);
            GameGraphic.cardThree.panel.setBackground(Color.CYAN);
            GameGraphic.three.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.three.add(GameGraphic.cardThree.panel);
            GameGraphic.three.add(Box.createRigidArea(new Dimension(20, 10)));
            GameGraphic.rightPnl.add(GameGraphic.three, GameGraphic.rightPnl.getComponentZOrder(GameGraphic.three));
            GameGraphic.rightPnl.revalidate();
            GameGraphic.rightPnl.repaint();
            GameGraphic.cardThree.isPrizeCard=false;
        }
    }
}