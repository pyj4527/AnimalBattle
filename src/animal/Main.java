package animal;

public class Main {
	public static void main(String[] args) {
		GameManager gameManager = new GameManager(GameManager.createDefaultAnimals());
		Animal winner = null;
		int turn = 1;

		while (winner == null) {
			System.out.println("=== " + turn + "턴 ===");
			GameManager.DiceTurnResult diceTurnResult = gameManager.rollDiceTurn();
			System.out.println(diceTurnResult.toMessage());
			gameManager.prepareItemPhaseAfterDice();

			winner = gameManager.findWinner();
			while (winner == null && gameManager.hasCurrentItemUser()) {
				Animal itemUser = gameManager.getCurrentItemUser();
				GameManager.ItemResult itemResult = gameManager.useRandomItem(itemUser);
				System.out.println(itemResult.toMessage());
				winner = gameManager.findWinner();
				gameManager.moveToNextItemUser();
			}
			gameManager.finishItemPhase();
			printStatus(gameManager);
			turn++;
		}

		System.out.println("승리 동물: " + winner.getName());
	}

	private static void printStatus(GameManager gameManager) {
		for (Animal animal : gameManager.getAnimals()) {
			System.out.println(animal.getName() + ": " + animal.getDistance() + "/" + Animal.GOAL_DISTANCE);
		}
	}
}
