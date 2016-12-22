package carolyn.harris;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean isGameOn = true;
		Game game = new Game();
		Deck deck = new Deck();
		game.startGame(deck);
		while (isGameOn) {
			game.playerMove(deck);
			game.computerMove(deck);
			if (game.checkIfGameOver()) {
				isGameOn = false;
			}
		}
		System.out.println("Game Over!");
		System.out.println(game.checkWinner());
	}

}
