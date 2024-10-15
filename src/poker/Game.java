package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private List<Player> players;
    private Deck deck;
    private List<Card> table;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.table = new ArrayList<>();
    }

    public static void main(String[] args) {

        int numberOfPlayers = Integer.parseInt(args[0]);

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter username");

            String userName = myObj.nextLine();  // Read user input
            System.out.println("Username is: " + userName);
        }
        myObj.close();

        Deck.CreateDeck();

        for (int i = 0; i < numberOfPlayers; i++) {

            Deck.Deal(2);
        }

    }

}
