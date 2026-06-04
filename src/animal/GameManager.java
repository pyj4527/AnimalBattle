package animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameManager {
	private static final int DICE_MIN = 1;
	private static final int DICE_MAX = 6;

	private final Random random = new Random();
	private final List<Animal> animals = new ArrayList<Animal>();
	private final List<Animal> itemUsersThisTurn = new ArrayList<Animal>();
	private Animal me;
	private boolean itemPhase;
	private int itemUserIndex;

	public GameManager(List<Animal> selectedAnimals) {
		setAnimals(selectedAnimals);
	}

	public void setAnimals(List<Animal> selectedAnimals) {
		animals.clear();
		if (selectedAnimals == null || selectedAnimals.isEmpty()) {
			animals.addAll(createAnimalsWithSelectedFirst("코끼리"));
		} else {
			animals.addAll(selectedAnimals);
		}
		me = animals.get(0);
		itemPhase = false;
		itemUserIndex = 0;
		itemUsersThisTurn.clear();
	}

	public DiceTurnResult rollDiceTurn() {
		Map<Animal, Integer> diceByAnimal = new LinkedHashMap<Animal, Integer>();
		for (Animal animal : animals) {
			int dice = rollDice();
			animal.move(dice);
			diceByAnimal.put(animal, Integer.valueOf(dice));
		}
		return new DiceTurnResult(diceByAnimal);
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
		itemUsersThisTurn.clear();
		itemUsersThisTurn.addAll(getItemUsersAfterDice());
		itemPhase = true;
		itemUserIndex = 0;
	}

	public List<Animal> getItemUsersAfterDice() {
		List<Animal> rankedAnimals = new ArrayList<Animal>(animals);
		Collections.sort(rankedAnimals, new Comparator<Animal>() {
			public int compare(Animal first, Animal second) {
				return second.getDistanceAfterDice() - first.getDistanceAfterDice();
			}
		});

		Animal firstPlace = rankedAnimals.get(0);
		List<Animal> itemUsers = new ArrayList<Animal>();
		if (me != firstPlace) {
			itemUsers.add(me);
		}
		for (Animal animal : animals) {
			if (animal != me && animal != firstPlace) {
				itemUsers.add(animal);
			}
		}
		return itemUsers;
	}

	public boolean hasCurrentItemUser() {
		return itemPhase && itemUserIndex < itemUsersThisTurn.size();
	}

	public Animal getCurrentItemUser() {
		if (!hasCurrentItemUser()) {
			return null;
		}
		return itemUsersThisTurn.get(itemUserIndex);
	}

	public void moveToNextItemUser() {
		itemUserIndex++;
	}

	public void finishItemPhase() {
		itemPhase = false;
		itemUserIndex = 0;
		itemUsersThisTurn.clear();
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
		List<Animal> targets = new ArrayList<Animal>();
		for (Animal animal : animals) {
			if (animal != attacker) {
				targets.add(animal);
			}
		}
		if (targets.isEmpty()) {
			return null;
		}
		return targets.get(random.nextInt(targets.size()));
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

	public List<Animal> getAnimals() {
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

	public static List<Animal> createDefaultAnimals() {
		List<Animal> defaults = new ArrayList<Animal>();
		defaults.add(new 코끼리());
		defaults.add(new 원숭이());
		defaults.add(new 타조());
		defaults.add(new 기린());
		defaults.add(new 알파카());
		return defaults;
	}

	public static List<Animal> createAnimalsWithSelectedFirst(String selectedName) {
		List<Animal> animals = createDefaultAnimals();
		for (int i = 0; i < animals.size(); i++) {
			if (animals.get(i).getName().equals(selectedName)) {
				Animal selected = animals.remove(i);
				animals.add(0, selected);
				break;
			}
		}
		return animals;
	}

	public static class DiceTurnResult {
		private final Map<Animal, Integer> diceByAnimal;

		private DiceTurnResult(Map<Animal, Integer> diceByAnimal) {
			this.diceByAnimal = diceByAnimal;
		}

		public Map<Animal, Integer> getDiceByAnimal() {
			return diceByAnimal;
		}

		public String toMessage() {
			StringBuilder message = new StringBuilder();
			for (Map.Entry<Animal, Integer> entry : diceByAnimal.entrySet()) {
				message.append(entry.getKey().getName()).append(" ")
						.append(entry.getValue()).append("칸  ");
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
