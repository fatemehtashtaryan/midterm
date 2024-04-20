
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class coins {
    int number = 4;
    String name;
    Color colorCoin;
    String imagePath;
    public static int goldCn = 5;
    public static CircularImageButton gold = new CircularImageButton("src/goldcoin.jpg", coins.goldCn);
    public static coins[] coin = {
            new coins(4, "Green Coin", new Color(70, 231, 141), "src/greenBag.png"),
            new coins(4, "Pink Coin", new Color(241, 78, 125), "src/pinkBag.png"),
            new coins(4, "Blue Coin", new Color(91, 230, 249), "src/blueBag.png"),
            new coins(4, "Orange Coin", new Color(255, 156, 69), "src/orangeBag.png"),
            new coins(4, "Yellow Coin", new Color(247, 240, 40), "src/yellowBag.png")};

    public coins(int number, String name, Color colorCoin, String imagePath) {
        this.number = number;
        this.name = name;
        this.colorCoin = colorCoin;
        this.imagePath = imagePath;
    }

    public SquareImageButton coinCreate() {
        return new SquareImageButton(this);
    }

    public class SquareImageButton extends JButton {
        private final Image image;

        public SquareImageButton(coins coin) {
            ImageIcon icon = new ImageIcon(coin.imagePath);
            this.image = icon.getImage();
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            setContentAreaFilled(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Draw the square shape
            Rectangle2D.Double square = new Rectangle2D.Double(22, 15, 62, 62);
            g2d.setClip(square);

            // Draw the image
            if (!getModel().isPressed()) {
                g2d.drawImage(image, 22, 15, 62, 62, null);
                g2d.setXORMode(Color.white);
            } else {
                g2d.setColor(Color.ORANGE);
            }

            g2d.dispose();
        }
    }

    public static class CircularImageButton extends JButton {
        private final Image image;
        private String label;

        public CircularImageButton(String imagePath) {
            ImageIcon icon = new ImageIcon(imagePath);
            this.image = icon.getImage();
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            setContentAreaFilled(false);
            setBorderPainted(false);
        }

        public CircularImageButton(String imagePath, int label) {
            this(imagePath);
            this.label = String.valueOf(label);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Draw the circular shape
            Ellipse2D.Double circle = new Ellipse2D.Double(22, 15, 55, 55);
            g2d.setClip(circle);

            // Draw the image
            if (!getModel().isPressed()) {
                g2d.drawImage(image, 22, 15, 55, 55, null);
            } else {
                g2d.setColor(Color.ORANGE);
            }
            g2d.setColor(Color.BLACK); // Change color as needed
            Font font = new Font("Arial", Font.BOLD, 16);
            g2d.setFont(font);

            // Calculate text position
            FontMetrics metrics = g2d.getFontMetrics(font);
            int x = (getWidth() - metrics.stringWidth(label)) / 2 - 4;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent() + 2;

            // Draw the text
            g2d.drawString(label, x, y);

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Draw border as a circle
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.green);
            g2d.setColor(getForeground());
            Ellipse2D.Double circle = new Ellipse2D.Double(22, 15, 55, 55);
            g2d.draw(circle);
            g2d.dispose();

        }
    }

    public static class dialogCoin extends JDialog {
        public dialogCoin(coins coin, int x, int y, GameGraphic GameGraphic) {
            super(GameGraphic, coin.name, true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(200, 150);
            setLocation(x, y);
            int warnIndex = GameGraphic.centerPnl.getComponentZOrder(GameGraphic.warning);
            JPanel mainPnl = new JPanel();
            JPanel topPnl = new JPanel();
            JPanel centerPnl = new JPanel();
            centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.X_AXIS));
            JLabel balance = new JLabel("Balance = " + coin.number);
            balance.setForeground(coin.colorCoin);
            balance.setFont(new Font("Jokerman", Font.BOLD, 18));
            topPnl.add(balance);
            ImageIcon one = new ImageIcon("src/1.png");
            Image imageOne = one.getImage();
            ImageIcon newImage = new ImageIcon(imageOne.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            JLabel oneLabel = new JLabel(newImage);
            ImageIcon two = new ImageIcon("src/2.png");
            Image imageTwo = two.getImage();
            ImageIcon newImage2 = new ImageIcon(imageTwo.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            JLabel twoLabel = new JLabel(newImage2);
            if(Main.outOfRange) {
                oneLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Object[] option = {"OK"};
                        player player;
                        int sumCoins = 0;
                        if (coin.number > 0) {
                            coin.number--;
                            if (Main.turn) player = Main.player1;
                            else player = Main.player2;
                            for (int i = 0; i < 5; i++) {
                                if (player.coin[i].color.equals(coin.colorCoin)) player.coin[i].price++;
                            }
                            for (int i = 0; i < 5; i++) {
                                sumCoins += player.coin[i].price;
                            }
                            if (sumCoins > 10) {
                                Main.outOfRange = false;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                player playerWarn;
                                if (!Main.turn) playerWarn = Main.player2;
                                else playerWarn = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn.name + ", Number of your coins is out of range!!!");
                                GameGraphic.warning.setBackground(Color.red);
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                GameGraphic.warn.setBackground(Color.lightGray);
                            } else {
                                Main.turn = !Main.turn;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                player playerWarn;
                                if (!Main.turn) playerWarn = Main.player2;
                                else playerWarn = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn.name + " is Your turn!");
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                if (!Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
                                    GameGraphic.getContentPane().setBackground(Main.player2Color);
                                }
                                if (Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player1Color));
                                    GameGraphic.getContentPane().setBackground(Main.player1Color);
                                }
                            }
                        } else
                            JOptionPane.showOptionDialog(oneLabel.getParent(), "The balance of this bag is 0!", "Zero",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);

                        dispose();
                    }
                });
            }
            if(Main.outOfRange) {
                twoLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Object[] option = {"OK"};
                        player player;
                        if (coin.number == 4) {
                            coin.number = coin.number - 2;
                            if (Main.turn) player = Main.player1;
                            else player = Main.player2;
                            for (int i = 0; i < 5; i++) {
                                if (player.coin[i].color.equals(coin.colorCoin))
                                    player.coin[i].price = player.coin[i].price + 2;
                            }
                            int sumCoins = 0;
                            for (int i = 0; i < 5; i++) {
                                sumCoins += player.coin[i].price;
                            }
                            if (sumCoins > 10) {
                                Main.outOfRange = false;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                player playerWarn;
                                if (!Main.turn) playerWarn = Main.player2;
                                else playerWarn = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn.name + ", Number of your coins is out of range!!!");
                                GameGraphic.warn.setBackground(Color.red);
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                GameGraphic.warn.setBackground(Color.lightGray);
                            } else {
                                Main.turn = !Main.turn;
                                GameGraphic.warning.removeAll();
                                GameGraphic.centerPnl.remove(GameGraphic.warning);
                                player playerWarn;
                                if (!Main.turn) playerWarn = Main.player2;
                                else playerWarn = Main.player1;
                                GameGraphic.warn = new JLabel(playerWarn.name + " is Your turn!");
                                GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                                if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                                else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                                GameGraphic.warning.add(GameGraphic.warn);
                                GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                                GameGraphic.centerPnl.revalidate();
                                GameGraphic.centerPnl.repaint();
                                if (!Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
                                    GameGraphic.getContentPane().setBackground(Main.player2Color);
                                }
                                if (Main.turn) {
                                    GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player1Color));
                                    GameGraphic.getContentPane().setBackground(Main.player1Color);
                                }
                                dispose();
                            }
                        } else
                            JOptionPane.showOptionDialog(twoLabel.getParent(), "You can't buy 2 coins because the bag is not full!", "Can't Buying",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);

                    }
                });
            }
            centerPnl.add(Box.createRigidArea(new Dimension(20, 10)));
            centerPnl.add(oneLabel);
            centerPnl.add(Box.createRigidArea(new Dimension(20, 10)));
            centerPnl.add(twoLabel);
            centerPnl.add(Box.createRigidArea(new Dimension(20, 10)));
            mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
            mainPnl.add(Box.createRigidArea(new Dimension(0, 15)));
            mainPnl.add(topPnl);
            mainPnl.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPnl.add(centerPnl);
            mainPnl.add(Box.createRigidArea(new Dimension(0, 15)));
            getContentPane().add(mainPnl);
        }
    }

    public static class dialogReduceCoin extends JDialog {
        public dialogReduceCoin(player player, int i, int x, int y, GameGraphic GameGraphic) {
            super(GameGraphic, "Reduction of Coins", true);
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
                    if (text.charAt(0) > String.valueOf(player.coin[i].price).charAt(0))
                        JOptionPane.showOptionDialog(number.getParent(), "Out Of Range!", "Oops",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                    else {
                        player.coin[i].price -= Integer.parseInt(text);
                        for(int x =0; x<5; x++){
                            if(player.coin[i].color.equals(coin[x].colorCoin)) coin[x].number+=Integer.parseInt(text);
                        }
                    }
                    int sumCoins=0;
                    for(int j = 0; j < 5; j++){
                        sumCoins+=player.coin[j].price;
                    }
                    if(sumCoins<=10 && !Main.outOfRange){
                        int warnIndex = GameGraphic.centerPnl.getComponentZOrder(GameGraphic.warning);
                        Main.outOfRange=true;
                        Main.turn=!Main.turn;
                        GameGraphic.warning.removeAll();
                        GameGraphic.centerPnl.remove(GameGraphic.warning);
                        player playerWarn;
                        if (!Main.turn) playerWarn = Main.player2;
                        else playerWarn = Main.player1;
                        GameGraphic.warn = new JLabel(playerWarn.name + " is Your turn!");
                        GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
                        if (!Main.turn) GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
                        else GameGraphic.warning.setBorder(new LineBorder(Main.player1Color, 7));
                        GameGraphic.warning.add(GameGraphic.warn);
                        GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
                        GameGraphic.centerPnl.revalidate();
                        GameGraphic.centerPnl.repaint();
                        if (!Main.turn) {
                            GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
                            GameGraphic.getContentPane().setBackground(Main.player2Color);
                        }
                        if (Main.turn) {
                            GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player1Color));
                            GameGraphic.getContentPane().setBackground(Main.player1Color);
                        }
                    }
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
