package animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GameManager {
	private static final int DICE_MIN = 1;
	private static final int DICE_MAX = 6;

	private final Random random = new Random();
	private Animal[] animals;
	private Animal[] itemUsersThisTurn = new Animal[0];
	private Animal me;
	private boolean itemPhase;
	private int itemUserIndex;

	public GameManager(Animal[] selectedAnimals) {
		setAnimals(selectedAnimals);
	}

	public void setAnimals(Animal[] selectedAnimals) {
		if (selectedAnimals == null || selectedAnimals.length == 0) {
			animals = createAnimalsWithSelectedFirst("코끼리");
		} else {
			animals = selectedAnimals;
		}
		me = animals[0];
		itemPhase = false;
		itemUserIndex = 0;
		itemUsersThisTurn = new Animal[0];
	}

	public DiceTurnResult rollDiceTurn() {
		int[] diceValues = new int[animals.length];
		for (int i = 0; i < animals.length; i++) {
			int dice = rollDice();
			animals[i].move(dice);
			diceValues[i] = dice;
		}
		return new DiceTurnResult(animals, diceValues);
	}

	public int rollDice() {
		return random.nextInt(DICE_MAX - DICE_MIN + 1) + DICE_MIN;
	}

	public boolean advanceDisplayDistances() {
		boolean changed = false;
		for (Animal animal : animals) {
			if (animal.increaseDisplayDistance()) {
				changed = true;
			}
		}
		return changed;
	}

	public void prepareItemPhaseAfterDice() {
		for (Animal animal : animals) {
			animal.markDistanceAfterDice();
		}
		itemUsersThisTurn = getItemUsersAfterDice();
		itemPhase = true;
		itemUserIndex = 0;
	}

	public Animal[] getItemUsersAfterDice() {
		Animal[] rankedAnimals = Arrays.copyOf(animals, animals.length);
		Arrays.sort(rankedAnimals, new Comparator<Animal>() {
			public int compare(Animal first, Animal second) {
				return second.getDistanceAfterDice() - first.getDistanceAfterDice();
			}
		});

		Animal firstPlace = rankedAnimals[0];
		Animal[] tempItemUsers = new Animal[animals.length];
		int count = 0;
		if (me != firstPlace) {
			tempItemUsers[count] = me;
			count++;
		}
		for (Animal animal : animals) {
			if (animal != me && animal != firstPlace) {
				tempItemUsers[count] = animal;
				count++;
			}
		}
		return Arrays.copyOf(tempItemUsers, count);
	}

	public boolean hasCurrentItemUser() {
		return itemPhase && itemUserIndex < itemUsersThisTurn.length;
	}

	public Animal getCurrentItemUser() {
		if (!hasCurrentItemUser()) {
			return null;
		}
		return itemUsersThisTurn[itemUserIndex];
	}

	public void moveToNextItemUser() {
		itemUserIndex++;
	}

	public void finishItemPhase() {
		itemPhase = false;
		itemUserIndex = 0;
		itemUsersThisTurn = new Animal[0];
	}

	public ItemResult useRandomItem(Animal attacker) {
		if (random.nextBoolean()) {
			Animal target = pickTarget(attacker);
			if (target != null) {
				AttackResult attackResult = attack(attacker, target);
				return ItemResult.attack(attackResult);
			}
		}
		return ItemResult.booster(useBooster(attacker));
	}

	public AttackResult attack(Animal attacker, Animal target) {
		int beforeDistance = target.getDistance();
		if (attacker instanceof 공격) {
			((공격) attacker).attack(target);
		} else {
			target.damaged(attacker.getAttackPower());
		}
		target.syncDisplayDistance();
		return new AttackResult(attacker, target, beforeDistance, target.getDistance(), attacker.getAttackName());
	}

	public BoosterResult useBooster(Animal animal) {
		int beforeDistance = animal.getDistance();
		if (animal instanceof 부스터) {
			((부스터) animal).booster();
		} else {
			animal.move(animal.getBoosterSpeed());
		}
		animal.syncDisplayDistance();
		return new BoosterResult(animal, beforeDistance, animal.getDistance());
	}

	public Animal pickTarget(Animal attacker) {
		Animal[] targets = new Animal[animals.length - 1];
		int count = 0;
		for (Animal animal : animals) {
			if (animal != attacker) {
				targets[count] = animal;
				count++;
			}
		}
		if (count == 0) {
			return null;
		}
		return targets[random.nextInt(count)];
	}

	public Animal findWinner() {
		for (Animal animal : animals) {
			if (animal.getDistance() >= Animal.GOAL_DISTANCE) {
				return animal;
			}
		}
		return null;
	}

	public void reset() {
		for (Animal animal : animals) {
			animal.reset();
		}
		finishItemPhase();
	}

	public Animal[] getAnimals() {
		return animals;
	}

	public Animal getMe() {
		return me;
	}

	public boolean isItemPhase() {
		return itemPhase;
	}

	public int getItemUserIndex() {
		return itemUserIndex;
	}

	public static Animal[] createDefaultAnimals() {
		Animal[] defaults = {
			new 코끼리(),
			new 원숭이(),
			new 타조(),
			new 기린(),
			new 알파카()
		};
		return defaults;
	}

	public static Animal[] createAnimalsWithSelectedFirst(String selectedName) {
		Animal[] animals = createDefaultAnimals();
		for (int i = 0; i < animals.length; i++) {
			if (animals[i].getName().equals(selectedName)) {
				Animal selected = animals[i];
				for (int j = i; j > 0; j--) {
					animals[j] = animals[j - 1];
				}
				animals[0] = selected;
				break;
			}
		}
		return animals;
	}

	public static class DiceTurnResult {
		private final Animal[] animals;
		private final int[] diceValues;

		private DiceTurnResult(Animal[] animals, int[] diceValues) {
			this.animals = animals;
			this.diceValues = diceValues;
		}

		public Animal[] getAnimals() {
			return animals;
		}

		public int[] getDiceValues() {
			return diceValues;
		}

		public String toMessage() {
			StringBuilder message = new StringBuilder();
			for (int i = 0; i < animals.length; i++) {
				message.append(animals[i].getName()).append(" ")
						.append(diceValues[i]).append("칸  ");
			}
			return message.toString();
		}
	}

	public static class AttackResult {
		private final Animal attacker;
		private final Animal target;
		private final int beforeDistance;
		private final int afterDistance;
		private final String attackName;

		private AttackResult(Animal attacker, Animal target, int beforeDistance, int afterDistance, String attackName) {
			this.attacker = attacker;
			this.target = target;
			this.beforeDistance = beforeDistance;
			this.afterDistance = afterDistance;
			this.attackName = attackName;
		}

		public Animal getAttacker() {
			return attacker;
		}

		public Animal getTarget() {
			return target;
		}

		public int getBeforeDistance() {
			return beforeDistance;
		}

		public int getAfterDistance() {
			return afterDistance;
		}

		public String getAttackName() {
			return attackName;
		}
	}

	public static class BoosterResult {
		private final Animal animal;
		private final int beforeDistance;
		private final int afterDistance;

		private BoosterResult(Animal animal, int beforeDistance, int afterDistance) {
			this.animal = animal;
			this.beforeDistance = beforeDistance;
			this.afterDistance = afterDistance;
		}

		public Animal getAnimal() {
			return animal;
		}

		public int getBeforeDistance() {
			return beforeDistance;
		}

		public int getAfterDistance() {
			return afterDistance;
		}
	}

	public static class ItemResult {
		private final AttackResult attackResult;
		private final BoosterResult boosterResult;

		private ItemResult(AttackResult attackResult, BoosterResult boosterResult) {
			this.attackResult = attackResult;
			this.boosterResult = boosterResult;
		}

		public static ItemResult attack(AttackResult result) {
			return new ItemResult(result, null);
		}

		public static ItemResult booster(BoosterResult result) {
			return new ItemResult(null, result);
		}

		public boolean isAttack() {
			return attackResult != null;
		}

		public AttackResult getAttackResult() {
			return attackResult;
		}

		public BoosterResult getBoosterResult() {
			return boosterResult;
		}

		public String toMessage() {
			if (isAttack()) {
				return attackResult.getAttacker().getName() + "이(가) "
						+ attackResult.getTarget().getName() + "을(를) 공격했습니다.";
			}
			return boosterResult.getAnimal().getName() + "이(가) 부스터를 사용했습니다.";
		}
	}
}
