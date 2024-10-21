package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final List<Player> players;
    private final Deck deck;
    private final List<Card> communityCards;
    private int higherBet;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.communityCards = new ArrayList<>();
    }

    public void playersTurn(Player player, Scanner inputScanner) {

        if (!player.hasFolded()) {

            System.out.println(player.getName() + " you have " + player.getTokens() + " tokens.\nDo you want to see your cards ? (Y/N)");
            String choice = inputScanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Your cards are : " + player.getHand() + "\n");
            } else {
                System.out.println(player.getName() + " chose not to see their cards." + "\n");
            }

            System.out.println("What do you want to do ? \n1 : Fold\n2 : Call\n3 : Raise");

            int playerChoice = inputScanner.nextInt();
            while (playerChoice < 1 || playerChoice > 3) {
                playerChoice = inputScanner.nextInt();
                System.out.println("Not a valid choise, please try again");
            }
            inputScanner.nextLine();
            switch (playerChoice) {

                case 1 -> {
                    player.setFolded(true);
                    System.out.println(player.getName() + " fold\n");
                }
                case 2 -> {
                    System.out.println(player.getName() + "call\n");
                }
                case 3 -> {
                    System.out.println("How much do you want to bet ?");
                    int bet = inputScanner.nextInt();
                    while (bet > player.getTokens() || bet < 1) {
                        if (bet > player.getTokens()) {
                            System.out.println("Not enough tokens, you have " + player.getTokens() + " tokens left");
                        } else {
                            System.out.println("You need to bet 1 or more token(s)");
                        }
                        bet = inputScanner.nextInt();
                    }
                    inputScanner.nextLine();
                    player.betTokens(bet);
                    System.out.println(player.getName() + " raise to " + bet + " tokens\n");
                }
            }
        } else {
            System.out.println(player.getName() + " has folded !");
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
        System.out.println("\n");
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();

        int numberOfPlayers = Integer.parseInt(args[0]);

        Scanner inputScanner = new Scanner(System.in);

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter username:");

            String userName = inputScanner.nextLine();
            System.out.println("Username is: " + userName + "\n");

            Player player = new Player(userName);
            players.add(player);
        }

        Deck.CreateDeck();

        for (Player player : players) {
            player.setHand(Deck.Deal(2));
        }

        Game game = new Game(players);

        for (Player player : players) {
            game.playersTurn(player, inputScanner);
        }

        game.drawCommunityCards(3);
        game.printCommunityCards();

        for (Player player : players) {
            game.playersTurn(player, inputScanner);
        }

        game.drawCommunityCards(1);
        game.printCommunityCards();

        for (Player player : players) {
            game.playersTurn(player, inputScanner);
        }

        game.drawCommunityCards(1);
        game.printCommunityCards();

        for (Player player : players) {
            game.playersTurn(player, inputScanner);
        }
    }
}
