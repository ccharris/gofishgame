package carolyn.harris;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean isGameOn = true;
		Game game = new Game();
		Deck deck = new Deck();
		game.startGame(deck);
		while (isGameOn) {
			while(game.playerMove(deck) && !game.checkIfGameOver());
			while(game.computerMove(deck) && !game.checkIfGameOver());
			if (game.checkIfGameOver()) {
				isGameOn = false;
			}
		}
		System.out.println("Game Over!");
		System.out.println(game.checkWinner());
	}

}
