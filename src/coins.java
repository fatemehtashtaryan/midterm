
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class coins {
    int number =4;
    String name;
    Color colorCoin;
    String imagePath;
    public static int goldCn=5;
    public static CircularImageButton gold = new CircularImageButton("src/goldcoin.jpg", coins.goldCn);
    public static coins[] coin={
            new coins(4, "Green Coin", new Color(70, 231, 141), "src/greenBag.png"),
            new coins(4, "Pink Coin", new Color(241, 78, 125), "src/pinkBag.png"),
            new coins(4, "Blue Coin", new Color(91, 230, 249), "src/blueBag.png"),
            new coins(4, "Orange Coin", new Color(255, 156, 69), "src/orangeBag.png"),
            new coins(4, "Yellow Coin", new Color(194, 199, 40), "src/yellowBag.png")};
    public coins(int number, String name, Color colorCoin, String imagePath){
        this.number = number;
        this.name = name;
        this.colorCoin = colorCoin;
        this.imagePath = imagePath;
    }
    public SquareImageButton coinCreate(){
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
        public CircularImageButton(String imagePath, int label){
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
            int x = (getWidth() - metrics.stringWidth(label)) / 2-4;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent()+2;

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


}
