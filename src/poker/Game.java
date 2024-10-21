package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final List<Player> players;
    private final Deck deck;
    private List<Card> communityCards;
    private List<Player> foldedPlayers;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.communityCards = new ArrayList<>();
    }

    public void drawCommunityCards(int numberOfCards) {
        communityCards.addAll(Deck.Deal(numberOfCards));
    }

    public void printCommunityCards() {
        System.out.println("Community Cards:");
        for (Card card : communityCards) {
            System.out.println(card);
        }
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
            System.out.println("Do you want to see your cards " + player.getName() + " ? (Y/N)");
            String choice = inputScanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Your cards are: " + player.getHand() + "\n");
            } else {
                System.out.println(player.getName() + " chose not to see their cards." + "\n");
            }

            System.out.println("What do you want to do ? \n1 : Fold\n2 : Call\n3 : Raise");

            int bet = inputScanner.nextInt();
            while (bet < 1 || bet > 3) {
                bet = inputScanner.nextInt();
                System.out.println("Not a valid choise, please try again");
            }
            inputScanner.nextLine();
            switch (bet) {
                case 1 -> {
                    System.out.println("fold");
                }
                case 2 -> {
                    System.out.println("call");
                }
                case 3 -> {
                    System.out.println("raise");
                }
            }

        }
        game.drawCommunityCards(3);

        game.drawCommunityCards(1);

        game.drawCommunityCards(1);
        game.printCommunityCards();

    }
}
