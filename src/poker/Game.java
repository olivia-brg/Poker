package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final List<Player> players;
    private final List<Card> communityCards;
    // private int currentBet;
    private int dealerIndex;

    public Game(List<Player> players) {
        this.players = players;
        this.communityCards = new ArrayList<>();
    }

    public void playersTurn(Player player, Scanner inputScanner) {

        if (!player.hasFolded()) {

            System.out.println("\n" + player.getName() + " you have " + player.getTokens() + " tokens.\nDo you want to see your cards ? (Y for yes)");
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
                    System.out.println("\n" + player.getName() + " call");
                }
                case 3 -> {
                    System.out.println("\nYou have " + player.getTokens() + " tokens. How much do you want to bet ?");

                    int bet = 0;
                    while (bet < 1 || bet > player.getTokens()) {
                        do {
                            try {
                                String s = inputScanner.nextLine();
                                bet = Integer.parseInt(s);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("\nCounldn't parse input, please select a number.");
                            }

                        } while (true);

                        if (bet > player.getTokens()) {
                            System.out.println("\nNot enough tokens, you have " + player.getTokens() + " tokens left");
                        } else if (bet < 1) {
                            System.out.println("\nYou need to bet 1 or more token(s)");
                        }
                    }
                    player.betTokens(bet);
                    System.out.println("\n" + player.getName() + " raise by " + bet + " tokens");
                }
            }
        } else {
            System.out.println("\n" + player.getName() + " has folded.");
        }
    }

    public void drawCommunityCards(int numberOfCards) {
        communityCards.addAll(Deck.Deal(numberOfCards));
    }

    public void printCommunityCards() {
        System.out.println("\nCommunity Cards :");
        for (Card card : communityCards) {
            System.out.println(card);
        }
    }

    public void nextDealer() {
        dealerIndex = (dealerIndex + 1) % players.size();
        System.out.println(players.get(dealerIndex).getName() + " is the dealer");
    }

    public Player getDealer() {
        return players.get(dealerIndex);
    }

    public void playRound(Scanner inputScanner) {
        int currentPlayerIndex = (dealerIndex + 1) % players.size();

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

            System.out.println("\nGame n°" + gameNumber + "");

            System.out.println("Dealer: " + game.getDealer().getName());

            Deck.CreateDeck();

            for (Player player : players) {
                player.setFolded(false);
                player.setHand(Deck.Deal(2));
            }

            for (int round = 0; round < 3; round++) {

                System.out.println("\nDealer: " + game.getDealer().getName());

                game.playRound(inputScanner);

                if (round == 0) {
                    game.drawCommunityCards(3); // Flop
                } else {
                    game.drawCommunityCards(1); // Turn et River
                }
                game.printCommunityCards();

            }
            game.playRound(inputScanner);


            System.out.println("\n\nPlay again ? (Y for yes)");
            playAgain = inputScanner.nextLine();

        } while (playAgain.equalsIgnoreCase("Y"));
    }
}
