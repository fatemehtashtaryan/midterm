import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class player {
    cards.cardsArray[] coin;
    int goldCoin;
    String name;
    cardInformation[] reserveCard;
    int score;
    boolean turn;
    int numberReserve;
    public player(cards.cardsArray[] coin, int goldCoin, String name, cardInformation[] reserveCard, int score, boolean turn, int numberReserve){
        this.coin = coin;
        this.goldCoin = goldCoin;
        this.name = name;
        this.reserveCard = reserveCard;
        this.score = score;
        this.turn = turn;
        this.numberReserve=numberReserve;
    }

    public static class dialogPlayer extends JDialog {
        public dialogPlayer(int x, int y, player player, GameGraphic GameGraphic){
            super(GameGraphic, player.name + " Information!", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(500, 500);
            setLocation(x, y);
            JPanel mainPnl = new JPanel();
            mainPnl.setLayout(new GridLayout(1,3,15,10));
            JPanel first = new JPanel(new GridLayout(6,1,0,10));
            JPanel two = new JPanel(new GridLayout(5,1,20,10));
            JPanel three = new JPanel(new GridLayout(7,1,0,5));
            JPanel two1 = new JPanel();
            JPanel two2 = new JPanel();
            JPanel two3= new JPanel();
            JPanel[] panels = {two1, two2, two3};
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
                GameGraphic.addMouseListenerToCoinPlayer(label, player, i);
                first.add(label);
            }
            first.setBorder(new LineBorder(Color.BLACK, 5));
            mainPnl.add(first);
            JLabel score = new JLabel("  Score = " + player.score);
            JLabel reserveCard = new JLabel("Reserved Cards");
            score.setForeground(Color.red);
            score.setFont(new Font("Jokerman", Font.BOLD, 25));
            reserveCard.setFont(new Font("Stencil", Font.BOLD,16));
            JPanel text = new JPanel(new GridLayout(2,1, 0, 8));
            text.add(score);
            text.add(reserveCard);
            two.add(text);

            for(int j=0; j<player.numberReserve; j++){
                panels[j].setLayout(new BoxLayout(panels[j], BoxLayout.X_AXIS));
                panels[j].add(Box.createRigidArea(new Dimension(15,0)));
                panels[j].add(player.reserveCard[j].panel);
                panels[j].add(Box.createRigidArea(new Dimension(15,0)));
                two.add(panels[j]);
            }

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
}