package poker;

import java.util.*;

public class Game {

    private LinkedList<Player> AllPlayers;
    private LinkedList<Player> playersInGame;
    private final List<Card> communityCards;
    private int smallBlind;
    private int currentMinBet;
    private int dealerIndex;
    private int totalBetTokens;
    // private int playersInGame;

    public Game(LinkedList<Player> players, int numberOfPlayers) {

        this.AllPlayers = new LinkedList<>(players);
        this.playersInGame = AllPlayers;
        this.communityCards = new ArrayList<>();
        this.smallBlind = 2;
        this.currentMinBet = this.smallBlind * 2;
        // this.playersInGame = numberOfPlayers;
    }

    public void playersTurn(Player player, Scanner inputScanner) {

        if (!player.hasFolded()) {

            System.out.println("\n\n-------- " + player.getName() + "'s turn --------\n\nYou have " + player.getTokens() + " tokens and current bet is at " + currentMinBet + " tokens.\n\nDo you want to see your cards ? (Y for yes / Enter for no)");
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
                    playersInGame.remove(player);
                    System.out.println("\n" + player.getName() + " fold\n\n┌------------------------------------┐");
                }
                case 2 -> {
                    int call = (currentMinBet - player.getActualBet());
                    player.betTokens(call);
                    totalBetTokens += call;

                    System.out.println("\nYou call\n\n┌------------------------------------┐\n|        " + player.getName() + " is at " + player.getActualBet() + " tokens bet");
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

                    System.out.println("\n You raise by " + addToBet + " tokens\n\n┌------------------------------------┐\n|        " + player.getName() + " is at " + player.getActualBet() + " tokens bet");
                }
            }
            System.out.println("|        Total tokens bet : " + totalBetTokens + "       |\n└------------------------------------┘");
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

    public Player getDealer(Integer... nth) {
        Integer n = nth.length > 0 ? nth[0] : 0;
        return playersInGame.get((dealerIndex + n) % playersInGame.size());
    }

    public void playRound(Scanner inputScanner) {
        LinkedList<Player> playersInTurn = new LinkedList<>(playersInGame);
        int currentPlayerIndex = (dealerIndex + 3) % playersInTurn.size();

        for (int i = 1; i < playersInTurn.size(); i++) {
            if (playersInGame.size() < 2) {
                break;
            }
            Player currentPlayer = playersInTurn.get(currentPlayerIndex);
            playersTurn(currentPlayer, inputScanner);

            currentPlayerIndex = (currentPlayerIndex + 1) % playersInTurn.size();
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        LinkedList<Player> playersEntry = new LinkedList<>();
        int numberOfPlayers;

        System.out.println("\u001B[30m\u001B[47m\nHow many player there is ?");
        do {
            try {
                String s = inputScanner.nextLine();
                numberOfPlayers = Integer.parseInt(s);
                if (numberOfPlayers >= 3) {
                    break;
                }
                System.out.println("\nYou need to be 3 or more players, please try again");
            } catch (NumberFormatException e) {
                System.out.println("\nCouldn't parse input, please try again");
            }

        } while (true);

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("\nEnter username:");

            String userName = inputScanner.nextLine();

            Player player = new Player(userName);
            playersEntry.add(player);
        }

        String playAgain;
        int gameNumber = 0;

        do {
            Game game = new Game(playersEntry, numberOfPlayers);

            game.dealerIndex += gameNumber;
            gameNumber++;

            System.out.println("\u001B[30m\u001B[47m\n------------------------------------\n------------- Game n°" + gameNumber + " -------------\n------------------------------------\n\n"
                    + "Dealer : " + game.getDealer().getName());

            Deck.CreateDeck();

            for (Player player : playersEntry) {
                player.setFolded(false);
                player.setHand(Deck.Deal(2));
                player.resetActualBet();
            }

            Player winner = null;

            Player smallBlindPlayer = game.getDealer(1);
            Player bigBlindPlayer = game.getDealer(2);

            smallBlindPlayer.betTokens(game.smallBlind);
            bigBlindPlayer.betTokens(game.smallBlind * 2);

            game.totalBetTokens += game.smallBlind * 3;

            System.out.println(smallBlindPlayer.getName() + " is the small blind (" + game.smallBlind + " tokens)");
            System.out.println(bigBlindPlayer.getName() + " is the big blind (" + (game.smallBlind * 2) + " tokens)\n\n------------------------------------");

            for (int round = 0; round < 3; round++) {
                boolean newTurn;
                do {
                    game.playRound(inputScanner);

                    LinkedList<Integer> playersActualBet = new LinkedList<>();
                    for (Player player : game.playersInGame) {
                        playersActualBet.add(player.getActualBet());

                    }

                    newTurn = false;
                    for (int i = 0; i < game.playersInGame.size(); i++) {
                        if (!Objects.equals(playersActualBet.get(0), playersActualBet.get(i)) && game.playersInGame.size() >= 2) {
                            newTurn = true;
                            break;
                        }
                    }

                } while (newTurn);

                if (game.playersInGame.size() >= 2) {
                    if (round == 0) {
                        game.setCommunityCards(3); // Flop
                    } else {
                        game.setCommunityCards(1); // Turn & River
                    }
                    game.getCommunityCards();
                } else {
                    for (Player player : game.playersInGame) {
                        winner = player;
                    }
                }

            }

            game.playRound(inputScanner);

            if (game.playersInGame.size() > 1) {
                for (int i = 0; i < game.playersInGame.size(); i++) {
                    playersEntry.get(i).getHand();
                    System.out.println("Player " + (i + 1) + " - " + game.playersInGame.get(i).getName() + " : " + game.playersInGame.get(i).getHand());
                }
            }

            winner.earnedTokens(game.totalBetTokens);
            System.out.println("\n-_-_-_-_-_- Well played " + winner.getName() + ", you won " + (game.totalBetTokens - winner.getActualBet()) + " tokens ! -_-_-_-_-_-");

            System.out.println("\nPlayers ranking :\n");

            LinkedList<Player> playersRanking = new LinkedList<>(playersEntry);
            playersRanking.sort(Comparator.comparingInt(Player::getTokens).reversed());

            for (int i = 0; i < playersRanking.size(); i++) {
                System.out.println((i + 1) + " : " + playersRanking.get(i).toString());
            }

            System.out.println("\n\nPlay again ? (Y for yes / Enter for no)");
            playAgain = inputScanner.nextLine();
            System.out.println("\u001B[0m");

        } while (playAgain.equalsIgnoreCase("Y"));
    }
}


/*
 * TODO
 * what happens when you don't have tokens anymore and you can't call or bet?
 * winner implementation
 * chek if username is null
 */
