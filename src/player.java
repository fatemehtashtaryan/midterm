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
}
