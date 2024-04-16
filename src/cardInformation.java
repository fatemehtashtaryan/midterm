import javax.swing.*;
import java.awt.*;

public class cardInformation {
    String imagePath;
    Color colorGift;
    cards.cardsArray[] array;
    int score;
    boolean isHold;
    boolean holder;
    boolean isPrizeCard;
    JPanel panel;
    public cardInformation(String imagePath, Color colorGift, cards.cardsArray[] array, int score,
                           boolean isPrizeCard, boolean isHold, boolean holder, JPanel panel){
        this.imagePath= imagePath;
        this.colorGift=colorGift;
        this.array=array;
        this.score=score;
        this.isPrizeCard=isPrizeCard;
        this.isHold=isHold;
        this.holder=holder;
        this.panel = panel;
    }
    public static cardInformation[] getReseve(){
        cardInformation[] reserve= new cardInformation[3];
        for(int i=0; i<3; i++){
            reserve[i]=new cardInformation(null, null, null, 0, false, true, true, null);
        }
        return reserve;
    }
}
