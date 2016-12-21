package carolyn.harris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {

	private ArrayList<Card> computerHand = new ArrayList<Card>();
	private ArrayList<Card> userHand = new ArrayList<Card>();
	private Scanner scan = new Scanner(System.in);
	private ArrayList<Rank> userBooks = new ArrayList<Rank>();
	private ArrayList<Rank> computerBooks = new ArrayList<Rank>();

	public void startGame(Deck deck) {
		// this method needs to:
		// display welcome message
		// initialize deck
		// shuffle deck
		// and deal 7 cards to both the player and the computer
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
		return goAgain;
	}

	public boolean computerMove(Deck deck) {
		boolean goAgain = false;
		System.out.println("Computer's turn!");
		// computer logic, if they have more than one of something ask for one
		// of those.
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
		return goAgain;
	}

	public void transferCardsToUser(String rank) {
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

	private String lastChoice = "";

	public String computerLogic() {
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
			} else if (entry.getValue() == 1 && choice.equals("")) {
				if (lastChoice != entry.getKey().toString()) {
					choice = entry.getKey().toString();
				}
			}
		}
		lastChoice = choice;
		return choice;
	}

	public boolean checkIfGameOver() {
		if (userBooks.size() + computerBooks.size() == 13) {
			return true;
		} else {
			return false;
		}
	}

	public String checkWinner() {
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

}
