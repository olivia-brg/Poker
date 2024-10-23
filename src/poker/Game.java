package poker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    private final List<Player> players;
    private final List<Card> communityCards;
    private int smallBlind;
    private int currentMinBet;
    private int dealerIndex;
    private int totalBetTokens;

    public Game(List<Player> players) {
        this.players = players;
        this.communityCards = new ArrayList<>();
        this.smallBlind = 2;
        this.currentMinBet = this.smallBlind * 2;
    }

    public void playersTurn(Player player, Scanner inputScanner) {

        if (!player.hasFolded()) {

            System.out.println("\n" + player.getName() + " : you have " + player.getTokens() + " tokens.\nDo you want to see your cards ? (Y for yes)");
            String choice = inputScanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nYour cards are : " + player.getHand());
            }

            System.out.println("\nWhat do you want to do ? \n1 : Fold\n2 : Call\n3 : Raise");

            int playerChoice = 0;
            while (playerChoice < 1 || playerChoice > 3) {
                do {
                    try {
                        String s = inputScanner.nextLine();
                        playerChoice = Integer.parseInt(s);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("\nPlease select a number between 1 and 3.");
                    }

                } while (true);
            }

            switch (playerChoice) {

                case 1 -> {
                    player.setFolded(true);
                    System.out.println("\n" + player.getName() + " fold");
                }
                case 2 -> {
                    int call = (currentMinBet - player.getActualBet());
                    player.betTokens(call);
                    totalBetTokens += call;

                    System.out.println("\n" + player.getName() + " call\nTotal : " + player.getActualBet());
                }
                case 3 -> {
                    System.out.println("\nYou have " + player.getTokens() + " tokens.\nCurrent bet is at " + currentMinBet + "\nHow much more do you want to bet ?");

                    int addToBet = 0;

                    do {
                        try {
                            String s = inputScanner.nextLine();
                            addToBet = Integer.parseInt(s);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("\nCounldn't parse input, please select a number.");
                        }

                    } while (true);

                    int bet = addToBet + (currentMinBet - player.getActualBet());

                    player.betTokens(bet);
                    totalBetTokens += bet;
                    if (currentMinBet < player.getActualBet()) {
                        currentMinBet = player.getActualBet();
                    }
                    System.out.println("\n" + player.getName() + " raise by " + addToBet + " tokens\nTotal : " + player.getActualBet());
                }
            }
            System.out.println("\nTotal bet tokens : " + totalBetTokens);
        } else {
            System.out.println("\n" + player.getName() + " has folded.");
        }
    }

    public void setCommunityCards(int numberOfCards) {
        communityCards.addAll(Deck.Deal(numberOfCards));
    }

    public void getCommunityCards() {
        System.out.println("\nCommunity Cards :\n");
        for (Card card : communityCards) {
            System.out.println(card);
        }
    }

    public void nextDealer() {
        dealerIndex = (dealerIndex + 1) % players.size();
    }

    public Player getDealer(Integer... nth) {
        Integer n1 = nth.length > 0 ? nth[0] : 0;
        return players.get(dealerIndex + n1);
    }

    public void playRound(Scanner inputScanner) {
        int currentPlayerIndex = (dealerIndex + 3) % players.size();

        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(currentPlayerIndex);
            playersTurn(currentPlayer, inputScanner);

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        List<Player> players = new ArrayList<>();
        int numberOfPlayers;

        System.out.println("\nHow many player there is ?");
        do {
            try {
                String s = inputScanner.nextLine();
                numberOfPlayers = Integer.parseInt(s);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nCouldn't parse input, please try again");
            }

        } while (true);

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("\nEnter username:");

            String userName = inputScanner.nextLine();
            System.out.println("Hello " + userName + " !");

            Player player = new Player(userName);
            players.add(player);
        }

        int gameNumber = 0;
        String playAgain;

        Game game = new Game(players);

        do {
            gameNumber++;

            System.out.println("\nGame n°" + gameNumber);
            System.out.println("Dealer : " + game.getDealer().getName());

            Deck.CreateDeck();

            for (Player player : players) {
                player.setFolded(false);
                player.setHand(Deck.Deal(2));
            }

            Player smallBlindPlayer = game.getDealer(1);
            Player bigBlindPlayer = game.getDealer(2);

            smallBlindPlayer.betTokens(game.smallBlind);
            bigBlindPlayer.betTokens(game.smallBlind * 2);

            game.totalBetTokens += game.smallBlind * 3;

            System.out.println(smallBlindPlayer.getName() + " is the small blind (" + game.smallBlind + " tokens)");
            System.out.println(bigBlindPlayer.getName() + " is the big blind (" + (game.smallBlind * 2) + " tokens)");

            for (int round = 0; round < 3; round++) {

                boolean newTurn;
                do {
                    game.playRound(inputScanner);

                    LinkedList<Integer> playersActualBet = new LinkedList<>();
                    for (Player player : players) {
                        playersActualBet.push(player.getActualBet());
                    }


                    newTurn = false; 
                    for (int i = 1; i < numberOfPlayers; i++) {
                        if (!Objects.equals(playersActualBet.get(0), playersActualBet.get(i))) {
                            newTurn = true;
                            break;
                        }
                    }

                } while (newTurn);

                if (round == 0) {
                    game.setCommunityCards(3); // Flop
                } else {
                    game.setCommunityCards(1); // Turn & River
                }
                game.getCommunityCards();

            }
            game.playRound(inputScanner);

            System.out.println("\n\nPlay again ? (Y for yes)");
            playAgain = inputScanner.nextLine();

        } while (playAgain.equalsIgnoreCase("Y"));
    }
}


/*
 * TODO
 * what happens when you don't have tokens anymore and you can't call or bet?
 * winner implementation
 * next game logic (reset var / change dealer and blinds...)
 */
