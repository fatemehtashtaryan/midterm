//This class is related to the computer player
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CardOnPage {
    cardInformation card;
    int location;
    JPanel panel;
    cards card1;
    static cards.cardsArray[] arrayCoinsCard = cards.getArrayCoin();
    public CardOnPage(cardInformation card, int location, JPanel panel, cards card1){
        this.card=card;
        this.location= location;
        this.panel=panel;
        this.card1=card1;
    }
//This method is for computer related functions when the user is playing with the computer
    public static void computerGame(GameGraphic GameGraphic){
        player.winner(GameGraphic);
        cardInformation.givePrizeCard(GameGraphic);
        int numberOfCards;
        boolean possible = true;
        cards card1;
        boolean buy =false;
        int goldIndex = GameGraphic.left.getComponentZOrder(GameGraphic.goldPnl);
        //Buying cards based on their points
        for(int f =11; f>=0; f--) {
            for (int j = 0; j < Main.cardsOnPage[f].card.array.length && possible; j++) {
                for (int i = 0; i < 5 && possible; i++) {
                    if (Main.player1.coin[i].color.equals(Main.cardsOnPage[f].card.array[j].color)) {
                        if (Main.player1.coin[i].price + Main.player1.coin[i + 5].price + Main.player1.goldCoin < Main.cardsOnPage[f].card.array[j].price)
                            possible = false;
                    }
                }
            }
            if(possible) {
                card1 = Main.cardsOnPage[f].card1;
                if(Main.cardsOnPage[f].panel==GameGraphic.firstPnl) numberOfCards=cards.numberOfCards1;
                else if(Main.cardsOnPage[f].panel==GameGraphic.secondPnl) numberOfCards=cards.numberOfCards2;
                else numberOfCards=cards.numberOfCards3;
                for (int j = 0; j < Main.cardsOnPage[f].card.array.length; j++) {
                    for (int i = 0; i < 5; i++) {
                        if (Main.player1.coin[i].color.equals(Main.cardsOnPage[f].card.array[j].color)) {
                            if (Main.player1.coin[i + 5].price >= Main.cardsOnPage[f].card.array[j].price)
                                Main.cardsOnPage[f].card.array[j].price = 0;
                            if (Main.player1.coin[i + 5].price < Main.cardsOnPage[f].card.array[j].price)
                                Main.cardsOnPage[f].card.array[j].price -= Main.player1.coin[i + 5].price;
                            if (Main.cardsOnPage[f].card.array[j].price > 0) {
                                if (Main.player1.coin[i].price < Main.cardsOnPage[f].card.array[j].price) {
                                    Main.player1.goldCoin--;
                                    coins.goldCn++;
                                    GameGraphic.goldPnl.removeAll();
                                    GameGraphic.left.remove(GameGraphic.goldPnl);
                                    coins.CircularImageButton gold = new coins.CircularImageButton("src/goldcoin.jpg", coins.goldCn);
                                    GameGraphic.goldPnl.add(gold, BorderLayout.CENTER);
                                    GameGraphic.left.add(GameGraphic.goldPnl, goldIndex);
                                    GameGraphic.left.revalidate();
                                    GameGraphic.left.repaint();
                                    coins.coin[i].number += Main.player1.coin[i].price;
                                    Main.player1.coin[i].price = 0;
                                    System.out.print("k,lklkl");
                                }
                                if (Main.player1.coin[i].price >= Main.cardsOnPage[f].card.array[j].price) {
                                    Main.player1.coin[i].price -= Main.cardsOnPage[f].card.array[j].price;
                                    coins.coin[i].number += Main.cardsOnPage[f].card.array[j].price;
                                }
                            }
                        }
                    }
                }
                for (int i = 5; i < 10; i++) {
                    if (Main.player1.coin[i].color.equals(Main.cardsOnPage[f].card.colorGift)) Main.player1.coin[i].price++;
                }
                Main.player1.score += Main.cardsOnPage[f].card.score;
                if (!Main.cardsOnPage[f].card.isHold) Main.cardsOnPage[f].panel.remove(Main.cardsOnPage[f].card.panel);
                if (numberOfCards >= 3) {
                    cardInformation cards = card1.firstLevel(card1, Main.cardsOnPage[f].card.imagePath, Main.cardsOnPage[f].panel, false, numberOfCards, 3, false, Main.cardsOnPage[f].card.numberOfCard);
                    Main.cardsOnPage[f].panel.add(cards.panel, Main.cardsOnPage[f].location);
                    Main.cardsOnPage[f] = new CardOnPage(cards, Main.cardsOnPage[f].location, Main.cardsOnPage[f].panel, card1);
                    GameGraphic.addMouseListenerToCard(cards.panel, card1, Main.cardsOnPage[f].card.imagePath, Main.cardsOnPage[f].panel, false, numberOfCards, 3, cards);
                    Main.cardsOnPage[f].panel.revalidate();
                    Main.cardsOnPage[f].card.panel.repaint();
                    Main.player1.score+=Main.cardsOnPage[f].card.score;
                    GameGraphic.warn = new JLabel("Computer bought a card, "+Main.player2.name + " is Your turn!");
                }
                buy = true;
            }
            possible=!possible;
        }
        //Card reservation based on opponent player's coins
        if(!buy){
            possible=true;
            for(int f = 11; f>=0 && !buy; f--){
                for(int i =0 ; i<Main.cardsOnPage[f].card.array.length && possible; i++) {
                    for(int j = 0; j<5 && possible; j++) {
                        if (Main.cardsOnPage[f].card.array[i].color.equals(Main.player2.coin[j].color)){
                            if(Main.cardsOnPage[f].card.array[i].price<=Main.player2.coin[j].price+Main.player2.coin[j+5].price)possible=true;
                            else possible =false;
                        }
                    }
                }
                if(possible){
                    if(Main.cardsOnPage[f].panel==GameGraphic.firstPnl) numberOfCards=cards.numberOfCards1;
                    else if(Main.cardsOnPage[f].panel==GameGraphic.secondPnl) numberOfCards=cards.numberOfCards2;
                    else numberOfCards=cards.numberOfCards3;
                    card1 = Main.cardsOnPage[f].card1;
                    if (coins.goldCn > 0) {
                        Main.player1.goldCoin++;
                        coins.goldCn--;
                        GameGraphic.goldPnl.removeAll();
                        GameGraphic.left.remove(GameGraphic.goldPnl);
                        coins.CircularImageButton gold = new coins.CircularImageButton("src/goldcoin.jpg", coins.goldCn);
                        GameGraphic.goldPnl.add(gold, BorderLayout.CENTER);
                        GameGraphic.left.add(GameGraphic.goldPnl, goldIndex);
                        GameGraphic.left.revalidate();
                        GameGraphic.left.repaint();
                    }
                    Main.cardsOnPage[f].card.isHold = true;
                    Main.player1.reserveCard[Main.player1.numberReserve] = Main.cardsOnPage[f].card;
                    Main.player1.numberReserve++;
                    Main.cardsOnPage[f].panel.remove(Main.cardsOnPage[f].card.panel);
                    GameGraphic.warn = new JLabel("Computer reserved a cord, "+Main.player2.name + " is Your turn!");
                    if (numberOfCards >= 3) {
                        cardInformation cards = card1.firstLevel(card1, Main.cardsOnPage[f].card.imagePath, Main.cardsOnPage[f].panel, false, numberOfCards, 3, false, Main.cardsOnPage[f].card.numberOfCard);
                        Main.cardsOnPage[f].panel.add(cards.panel, Main.cardsOnPage[f].location);
                        Main.cardsOnPage[f]=new CardOnPage(cards, Main.cardsOnPage[f].location, Main.cardsOnPage[f].panel, card1);
                        GameGraphic.addMouseListenerToCard(cards.panel, card1, Main.cardsOnPage[f].card.imagePath, Main.cardsOnPage[f].panel, false, numberOfCards, 3, cards);
                        Main.cardsOnPage[f].panel.revalidate();
                        Main.cardsOnPage[f].card.panel.repaint();
                    }
                    buy=true;
                }
                if(!buy) possible=true;
            }
        }
        //Buy coins based on the coins of the cards on the game screen
        if(!buy){
            for(int f= 0; f<12; f++){
                for(int t=0; t<Main.cardsOnPage[f].card.array.length; t++){
                    for(int x =0; x<5; x++){
                        if(Main.cardsOnPage[f].card.array[t].color.equals(arrayCoinsCard[x].color)){
                            arrayCoinsCard[x].price+=Main.cardsOnPage[f].card.array[t].price;
                        }
                    }
                }
            }
            for(int x =0; x<5; x++) {
                for (int j = x + 1; j < 5; j++) {
                    if(arrayCoinsCard[x].price<arrayCoinsCard[j].price){
                        cards.cardsArray coin = arrayCoinsCard[x];
                        arrayCoinsCard[x]= arrayCoinsCard[j];
                        arrayCoinsCard[j] = coin;
                    }
                }
            }
            boolean repeat = true;
            for (int j =0 ; j<5 ; j++) {
                for (int i = 0; i < 5 && repeat; i++) {
                    if (coins.coin[i].colorCoin.equals(arrayCoinsCard[j].color)) {
                        if (coins.coin[i].number == 4) {
                            coins.coin[i].number -= 2;
                            Main.player1.coin[i].price += 2;
                            GameGraphic.warn = new JLabel("Computer bought 2 coins, "+Main.player2.name + " is Your turn!");
                            repeat = false;
                        } else if (coins.coin[i].number > 0) {
                            coins.coin[i].number -= 1;
                            Main.player1.coin[i].price += 1;
                            GameGraphic.warn = new JLabel("Computer bought 1 coin, "+Main.player2.name + " is Your turn!");
                            repeat = false;
                        }
                    }
                }
            }
        }
        int sumCoins = 0;
        for (int i = 0; i < 5; i++) {
            sumCoins += Main.player1.coin[i].price;
        }
        //Reducing the number of coins if the number is more than 10
        if(sumCoins>10){
            for(int f= 0; f<12; f++){
                for(int t=0; t<Main.cardsOnPage[f].card.array.length; t++){
                    for(int x =0; x<5; x++){
                        if(Main.cardsOnPage[f].card.array[t].color.equals(arrayCoinsCard[x].color)){
                            arrayCoinsCard[x].price+=Main.cardsOnPage[f].card.array[t].price;
                        }
                    }
                }
            }
            for(int x =0; x<5; x++) {
                for (int j = x + 1; j < 5; j++) {
                    if(arrayCoinsCard[x].price<arrayCoinsCard[j].price){
                        cards.cardsArray coin = arrayCoinsCard[x];
                        arrayCoinsCard[x]= arrayCoinsCard[j];
                        arrayCoinsCard[j] = coin;
                    }
                }
            }
            for(int j =4; j>=0 && sumCoins>10; j--){
                for(int t = 0; t<5 && sumCoins>10; t++){
                    if(arrayCoinsCard[j].color.equals(Main.player1.coin[t].color)){
                        if(Main.player1.coin[t].price>0){
                            Main.player1.coin[t].price--;
                            arrayCoinsCard[j].price++;
                        }
                    }
                }
            }
        }
        int warnIndex = GameGraphic.centerPnl.getComponentZOrder(GameGraphic.warning);
        Main.turn = !Main.turn;
        GameGraphic.warning.removeAll();
        GameGraphic.centerPnl.remove(GameGraphic.warning);
        GameGraphic.warn.setFont(new Font("Freestyle Script", Font.BOLD, 30));
        GameGraphic.warning.setBorder(new LineBorder(Main.player2Color, 7));
        GameGraphic.warning.add(GameGraphic.warn);
        GameGraphic.centerPnl.add(GameGraphic.warning, warnIndex);
        GameGraphic.centerPnl.revalidate();
        GameGraphic.centerPnl.repaint();
        GameGraphic.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 4, 10, 4, Main.player2Color));
        GameGraphic.getContentPane().setBackground(Main.player2Color);
    }
}
