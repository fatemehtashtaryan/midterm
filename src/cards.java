import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class cards {
    static public int  numberOfCards1 = 15;
    static public int  numberOfCards2 = 15;
    static public int  numberOfCards3 = 15;
    static public  int numberOfCardsPrize = 3;
    public static cardsArray[] getArrayCoin() {
        cardsArray[] arrayCoin = new cardsArray[10];
        for(int i=0; i<10; i++){
            arrayCoin[i] = new cardsArray();
            arrayCoin[i].price=0;
        }
        arrayCoin[0].color=new Color(70, 231, 141);
        arrayCoin[5].color=new Color(70, 231, 141);
        arrayCoin[1].color= new Color(241, 78, 125);
        arrayCoin[6].color= new Color(241, 78, 125);
        arrayCoin[2].color = new Color(91, 230, 249 );
        arrayCoin[7].color = new Color(91, 230, 249 );
        arrayCoin[3].color = new Color(255, 156, 69);
        arrayCoin[8].color = new Color(255, 156, 69);
        arrayCoin[4].color = new Color(194, 199, 40);
        arrayCoin[9].color = new Color(194, 199, 40);
        return arrayCoin;
    }

    public int score;
    static Random random = new Random();
    private int scoreStart, scoreEnd;
    public cards(int scoreStart, int scoreEnd){
        set( scoreStart, scoreEnd);
        score = getScore();
    }
    public void set(int scoreStart, int scoreEnd){
        if(scoreEnd<scoreStart){
            int x = scoreEnd;
            scoreEnd =scoreStart;
            scoreStart =x;
        }
        if(scoreStart <0) scoreStart *=-1;
        this.scoreStart = scoreStart;
        if(scoreEnd <0) scoreEnd *=-1;
        this.scoreEnd = scoreEnd;
    }

    private int getScore(){
        score = random.nextInt(scoreEnd-scoreStart+1) +scoreStart;
        return score;
    }

    public static Color getColorGift(){
        Color[] colorGift = {new Color(70, 231, 141), new Color(241, 78, 125),
                new Color(91, 230, 249 ), new Color(255, 156, 69),
                new Color(194, 199, 40)};
        int randomNumber = random.nextInt(500);
        return colorGift[randomNumber/100];
    }
    public cardsArray[] getValue(){
        int value ;
        colorIntPair card = switch (score) {
            case (0) -> new colorIntPair(2, 4);
            case (1) -> {
                value = random.nextInt(2) + 5;
                yield new colorIntPair(2, value);
            }
            case (2) -> new colorIntPair(2, 6);
            case (3) -> new colorIntPair(2, 7);
            case (4) -> new colorIntPair(3, 8);
            case (5) -> {
                value = random.nextInt(2) + 9;
                yield new colorIntPair(4, value);
            }
            default -> null;
        };
        assert card != null;
        return card.array;
    }

    public static class cardsArray{
        Color color;
        int price;
    }

    static class colorIntPair{
        public int price;
        private int number;
        public cardsArray[] array;
        public colorIntPair(int number, int price){
            this.number=number;
            this.price=price;
            array = new cardsArray[number];
            for(int i =0; i<number; i++){
                array[i] = new cardsArray();
            }
            getColor();
            getPrices();
        }
        public void getPrices(){
            if(price==4 || price==5||price==6||price==7){
                array[0].price=price/2;
                array[1].price=price-array[0].price;
            }
            if (price==8){
                array[0].price=3;
                array[1].price=3;
                array[2].price=2;
            }
            if(price==9 || price==10 || price==11 || price==12){
                array[0].price=3;
                array[1].price=3;
                array[2].price=2;
                array[3].price=1;
                if(price==10 || price==11 || price==12) array[3].price++;
                if(price==11 || price==12) array[2].price++;
                if(price==12) array[3].price++;

            }
        }
        public void getColor(){
            List<Color> colorList = new ArrayList<>(Arrays.asList(new Color(70, 231, 141), new Color(241, 78, 125),
                    new Color(91, 230, 249 ), new Color(255, 156, 69),
                    new Color(194, 199, 40)));
            for(int i=0; i<number; i++) {
                int randomNumber = random.nextInt(colorList.size() * 100);
                array[i].color=colorList.get(randomNumber / 100);
                colorList.remove(randomNumber/100);
            }
        }

    }

    public cardInformation firstLevel(cards card1, String imagePath, JPanel panel, boolean giftCard
            , int numberOfCard, int bounds){
        JPanel cardMain = new JPanel();
        JPanel topPnl = new JPanel();
        ImageIcon image = new ImageIcon(imagePath);
        Image img = image.getImage();
        ImageIcon newImage = new ImageIcon(img.getScaledInstance(75,75,Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(newImage);
        imageLabel.setPreferredSize(new Dimension(40,40));
        JPanel buttonPnl = new JPanel();
        String scor;
        JLabel score;
        Color gift;
        cardsArray[] value;
        int scoreCard;

        if(!giftCard){
            value = card1.getValue();
            scoreCard = card1.score;
            scor = String.valueOf(scoreCard);
            gift = card1.getColorGift();
            topPnl.setLayout(new GridLayout(1,2));
            score = new JLabel("           "+scor);
        }
        else {
            Random random = new Random();
            int price, number;
            scoreCard=random.nextInt(2)+3;
            scor = String.valueOf(scoreCard);
            score = new JLabel('★' + scor + '★');
            if(scoreCard==3) {
                price = 8;
                number=3;
            }
            else{
                price = random.nextInt(4)+9;
                number=4;
            }
            value = new cards.colorIntPair(number, price).array;
            gift = cards.getColorGift();
        }

        topPnl.add(score);
        if(!giftCard)
            topPnl.add(new CircleLabel(String.valueOf('★'), gift));
        cardMain.setLayout(new BorderLayout());
        cardMain.add(imageLabel, BorderLayout.CENTER);
        cardMain.add(topPnl, BorderLayout.NORTH);
        for (cards.cardsArray cardsArray : value) {
            buttonPnl.setLayout(new GridLayout(1, value.length));
            buttonPnl.add(new CircleLabel(String.valueOf(cardsArray.price), cardsArray.color));
        }
        cardMain.setPreferredSize(new Dimension(50,50));
        cardMain.add(buttonPnl, BorderLayout.SOUTH);
        cardMain.setBorder(new LineBorder(gift, 5));
        return new cardInformation(imagePath, gift, value,  scoreCard, giftCard, false, false, cardMain);
    }

    public static class CircleLabel extends JLabel {
        private final String text;
        private final Color color;

        public CircleLabel(String text, Color color) {
            this.text = text;
            this.color = color;
            setPreferredSize(new Dimension(28, 28)); // size
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            // Draw circle
            int diameter = Math.min(getWidth(), getHeight()) - 6; // Adjusted for padding
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            Shape circle = new Ellipse2D.Double(x, y, diameter, diameter);
            g2d.setColor(color);
            g2d.fill(circle);
            g2d.draw(circle);

            // Draw number inside the circle
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int centerX = getWidth() / 2 - textWidth / 2;
            int centerY = getHeight() / 2 + textHeight / 4;
            g2d.drawString(text, centerX, centerY);
            g2d.dispose();
        }
    }
}

