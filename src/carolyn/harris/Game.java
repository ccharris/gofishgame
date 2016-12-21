package carolyn.harris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
// set private properties because they are not needed outside of the class. 
	private ArrayList<Card> computerHand = new ArrayList<Card>();
	private ArrayList<Card> userHand = new ArrayList<Card>();
// use of scanner to get user input
	private Scanner scan = new Scanner(System.in);
	private ArrayList<Rank> userBooks = new ArrayList<Rank>();
	private ArrayList<Rank> computerBooks = new ArrayList<Rank>();
	private String lastChoice = "";

	public void startGame(Deck deck) {
		// this method needs to:
		// display welcome message
		// initialize deck
		// shuffle deck
		// and deal 7 cards to both the player and the computer
		// check and see if there are any books
		System.out.println("Welcome to Go Fish!");
		System.out.println("Dealing cards.");

		deck.deckShuffle();
		for (int i = 0; i < 14; i++) {
			if (i == 0 || i % 2 == 0) {
				computerHand.add(deck.deal());
			} else {
				userHand.add(deck.deal());
			}
		}
		checkUserBooks(userHand);
		checkComputerBooks(computerHand);
		printBooks();

	}

	public void printHand() {
		// This method needs to print out the user hand in a way that isn't super long and groups like ranks on one line
		System.out.println("Your hand is:");
		userHand.sort(Comparator.comparing(Card::getRank));
		String current = "";
		for (Card card : userHand) {
			if (card.getRank().toString().equals(current)) {
				System.out.print(", " + card);
			} else {
				System.out.print("\n" + card);
				current = card.getRank().toString();
			}

		}
		System.out.print("\n");
	}

	public void printBooks() {
		//this method needs to check and see if any books are present and print the ones that are
		if (!userBooks.isEmpty()) {
			StringBuilder userB = new StringBuilder();
			System.out.println("You have books of the following ranks:");
			for (Rank rank : userBooks) {
				userB.append(rank.toString()).append(" ");
			}
			System.out.println(userB.toString());
		}

		if (!computerBooks.isEmpty()) {
			StringBuilder computerB = new StringBuilder();
			System.out.println("Computer has books of the following ranks:");
			for (Rank rank : computerBooks) {
				computerB.append(rank.toString()).append(" ");
			}
			System.out.println(computerB.toString());
		}

	}

	public boolean playerMove(Deck deck) {
		printHand();
		boolean goAgain = false;
		// ask what rank they want to fish for
		// check and see if they have that rank in their hand (valid choice)
		// if so display a message saying okay fishing for that rank
		// check computer hand for that rank
		// if computer has it say okay
		// if not go fish
		// display what is picked up from go fish
		// also check for empty hand, if empty automatically go fish
		if (userHand.size() > 0 && !userHand.isEmpty()) {
			System.out.println("What rank would you like to fish for?");

			String fishResponse = scan.nextLine();
			if (containsRank(userHand, fishResponse.toUpperCase())) {
				System.out.println("Okay, computer do you have " + fishResponse + "?");
				if (containsRank(computerHand, fishResponse.toUpperCase())) {
					System.out.println("Yes! Computer has " + fishResponse + "!");
					transferCardsToUser(fishResponse.toUpperCase());
					goAgain = true;
					System.out.println("You get to go again!");
				} else {
					System.out.println("Computer does not have " + fishResponse + ".");
					if (deck.getDeck().size() != 0) {
						System.out.println("Go Fish!");
						userHand.add(deck.deal());
						System.out.println("You drew " + userHand.get(userHand.size() - 1));
					} else if (deck.getDeck().size() == 0) {
						System.out.println("There are no more cards left in the deck!");
					}
				}
				checkUserBooks(userHand);
				checkComputerBooks(computerHand);
				printBooks();
			} else {
				System.out.println("You must pick a valid rank from your hand.");
				System.out.println("What rank would you like to fish for?");
				String fishResponse2 = scan.nextLine();
				if (containsRank(userHand, fishResponse2.toUpperCase())) {
					System.out.println("Okay, computer do you have " + fishResponse2 + "?");
					if (containsRank(computerHand, fishResponse2.toUpperCase())) {
						System.out.println("Yes! Computer has " + fishResponse2 + "!");
						transferCardsToUser(fishResponse2.toUpperCase());
						goAgain = true;
						System.out.println("You get to go again!");
					} else {
						System.out.println("Computer does not have " + fishResponse2 + ".");
						if (deck.getDeck().size() != 0) {
							System.out.println("Go Fish!");
							userHand.add(deck.deal());
							System.out.println("You drew " + userHand.get(userHand.size() - 1));
						} else if (deck.getDeck().size() == 0) {
							System.out.println("There are no more cards left in the deck!");
						}
					}
				}
				checkUserBooks(userHand);
				checkComputerBooks(computerHand);
				printBooks();
			}
		} else {
			System.out.println("Your hand is empty!");
			if (deck.getDeck().size() != 0) {
				System.out.println("Go Fish!");
				userHand.add(deck.deal());
				System.out.println("You drew " + userHand.get(userHand.size() - 1));
			} else if (deck.getDeck().size() == 0) {
				System.out.println("There are no more cards left in the deck!");
			}
		}
		sleep();
		if(checkIfGameOver()){
			goAgain = false;
		}
		return goAgain;
	}

	public boolean computerMove(Deck deck) {
		// computer turn, check and see if user has choice based on logic (other method)
		// if user does, then add to computer hand
		// if not computer goes fishing
		boolean goAgain = false;
		System.out.println("Computer's turn!");
		if(computerHand.size() > 0 && !computerHand.isEmpty()){
		String choice = computerLogic();
		System.out.println("User do you have " + choice + "?");
		if (containsRank(userHand, choice)) {
			System.out.println("Yes! User has " + choice + "!");
			transferCardsToComputer(choice);
			System.out.println("Computer has taken all of " + choice + "s.");
			goAgain = true;
			System.out.println("Computer gets to go again!");
		} else {
			System.out.println("User does not have " + choice + ".");
			if (deck.getDeck().size() != 0) {
				System.out.println("Go Fish, computer!");
				computerHand.add(deck.deal());
				System.out.println("Computer drew a card.");
			} else if (deck.getDeck().size() == 0) {
				System.out.println("There are no more cards left in the deck!");
			}

		}
		} else {
			System.out.println("Computer's hand is empty!");
			if (deck.getDeck().size() != 0) {
				System.out.println("Go Fish, computer!");
				computerHand.add(deck.deal());
				System.out.println("Computer drew a card.");
			} else if (deck.getDeck().size() == 0) {
				System.out.println("There are no more cards left in the deck!");
			}
		}

		checkComputerBooks(computerHand);
		printBooks();
		sleep();
		if(checkIfGameOver()){
			goAgain = false;
		}
		return goAgain;
	}

	public void transferCardsToUser(String rank) {
		// this method needs to transfer cards from computer deck to user deck depending on inputted rank
		if (Arrays.asList(Rank.values()).contains(Rank.valueOf(rank))) {
			ArrayList<Card> computerDeckCopy = new ArrayList<Card>();
			// to avoid concurrent modification exception
			for (Card card : computerHand) {
				computerDeckCopy.add(card);
			}
			for (Card card : computerDeckCopy) {
				if (card.getRank().toString().equals(rank)) {
					computerHand.remove(card);
					userHand.add(card);
				}
			}
		}
	}

	public void transferCardsToComputer(String rank) {
		// same as to user method, but from user to computer
		if (Arrays.asList(Rank.values()).contains(Rank.valueOf(rank))) {
			ArrayList<Card> userHandCopy = new ArrayList<Card>();
			// to avoid concurrent modification exception
			for (Card card : userHand) {
				userHandCopy.add(card);
			}
			for (Card card : userHandCopy) {
				if (card.getRank().toString().equals(rank)) {
					userHand.remove(card);
					computerHand.add(card);
				}
			}
		}
	}

	public boolean containsRank(ArrayList<Card> hand, String rank) {
		//check and see if the hand contains a card of that rank
		boolean okChoice = false;
		try {
			if (Arrays.asList(Rank.values()).contains(Rank.valueOf(rank))) {
				for (Card card : hand) {
					if (card.getRank().toString().equals(rank)) {
						okChoice = true;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// just catches if input is anything other than correct enum
		}
		return okChoice;
	}

	public void checkUserBooks(ArrayList<Card> hand) {
		// check and see if user has any books. 
		// to do this will need to create a hashmap that stores each rank and how many times they show up
		// then if there are four, take all out of the hand, and add that rank to the users books
		HashMap<Rank, Integer> frequencyMap = new HashMap<Rank, Integer>();
		for (int i = 0; i < hand.size(); i++) {
			if (frequencyMap.containsKey(hand.get(i).getRank())) {
				frequencyMap.put(hand.get(i).getRank(), frequencyMap.get(hand.get(i).getRank()) + 1);
			} else {
				frequencyMap.put(hand.get(i).getRank(), 1);
			}
		}
		for (Map.Entry<Rank, Integer> entry : frequencyMap.entrySet()) {
			if (entry.getValue() == 4) {
				if (entry.getKey().toString() == "SIX") {
					System.out.println("Yay! You have 4 sixes!");
				} else {
					System.out.println("Yay! You have 4 " + entry.getKey().toString().toLowerCase() + "s!");
				}

				userBooks.add(entry.getKey());
				ArrayList<Card> userHandCopy = new ArrayList<Card>();
				for (Card card : userHand) {
					userHandCopy.add(card);
				}
				for (Card card : userHandCopy) {
					if (card.getRank().equals(entry.getKey())) {
						userHand.remove(card);
					}
				}
			}
		}
	}

	public void checkComputerBooks(ArrayList<Card> hand) {
		// identical method but for computer books instead of user books
		HashMap<Rank, Integer> frequencyMap = new HashMap<Rank, Integer>();
		for (int i = 0; i < hand.size(); i++) {
			if (frequencyMap.containsKey(hand.get(i).getRank())) {
				frequencyMap.put(hand.get(i).getRank(), frequencyMap.get(hand.get(i).getRank()) + 1);
			} else {
				frequencyMap.put(hand.get(i).getRank(), 1);
			}
		}
		for (Map.Entry<Rank, Integer> entry : frequencyMap.entrySet()) {
			if (entry.getValue() == 4) {
				if (entry.getKey().toString() == "SIX") {
					System.out.println("Computer has 4 sixes!");
				} else {
					System.out.println("Computer has 4 " + entry.getKey().toString().toLowerCase() + "s!");
				}

				computerBooks.add(entry.getKey());
				ArrayList<Card> computerHandCopy = new ArrayList<Card>();
				for (Card card : computerHand) {
					computerHandCopy.add(card);
				}
				for (Card card : computerHandCopy) {
					if (card.getRank().equals(entry.getKey())) {
						computerHand.remove(card);
					}
				}
			}
		}
	}

	public String computerLogic() {
		// helps make the computer make a slightly better than random choice
		// it checks lastchoice to make sure that it is not just repeating a choice over and over again
		// otherwise it'll just ask for the highest thing over and over again
		String choice = "";
		HashMap<Rank, Integer> frequencyMap = new HashMap<Rank, Integer>();
		for (int i = 0; i < computerHand.size(); i++) {
			if (frequencyMap.containsKey(computerHand.get(i))) {
				frequencyMap.put(computerHand.get(i).getRank(), frequencyMap.get(computerHand.get(i)) + 1);
			} else {
				frequencyMap.put(computerHand.get(i).getRank(), 1);
			}
		}
		for (Map.Entry<Rank, Integer> entry : frequencyMap.entrySet()) {

			if (entry.getValue() == 3) {
				if (lastChoice != entry.getKey().toString()) {
					choice = entry.getKey().toString();
				}
			} else if (entry.getValue() == 2) {
				if (lastChoice != entry.getKey().toString()) {
					choice = entry.getKey().toString();
				}
			} else if (entry.getValue() == 1) {
				if (lastChoice != entry.getKey().toString()) {
					choice = entry.getKey().toString();
				}
			}
		}
		lastChoice = choice;
		return choice;
	}

	public boolean checkIfGameOver() {
		// see if all the books are done
		if (userBooks.size() + computerBooks.size() == 13) {
			return true;
		} else {
			return false;
		}
	}

	public String checkWinner() {
		// return who the winner is by comparing book sizes
		if (userBooks.size() + computerBooks.size() == 13) {
			if (userBooks.size() > computerBooks.size()) {
				return "Congrats user, you win!";
			} else if (computerBooks.size() > userBooks.size()) {
				return "Computer wins!";
			} else {
				return "Error, there cannot be a tie.";
			}
		} else {
			return "";
		}
	}

	public void sleep(){
		try {
		    Thread.sleep(600);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
}
