package animal;

import java.util.Random;

public class Main {
	private static final int DICE_MAX = 6;
	private static final int ITEM_ATTACK = 0;
	private static final int ITEM_BOOSTER = 1;

	public static void main(String[] args) {
		Random random = new Random();
		Animal[] animals = {
			new 기린(),
			new 타조(),
			new 코끼리(),
			new 원숭이(),
			new 알파카()
		};

		System.out.println("=== 동물 배틀 시작 ===");
		printStatus(animals);

		int turn = 1;
		Animal winner = null;

		while (winner == null) {
			System.out.println("\n=== " + turn + "턴 ===");

			for (Animal animal : animals) {
				System.out.println("\n[" + animal.getName() + " 차례]");

				int dice = rollDice(random);
				animal.moveBy(dice);
				System.out.println(animal.getName() + " 주사위: " + dice + "칸 이동");

				if (animal.getDistance() == Animal.FINISH_DISTANCE) {
					winner = animal;
					break;
				}

				int item = random.nextInt(2);
				if (item == ITEM_ATTACK) {
					Animal target = pickTarget(random, animals, animal);
					System.out.println("아이템: 공격, 대상: " + target.getName());
					((공격) animal).attack(target);
				} else if (item == ITEM_BOOSTER) {
					System.out.println("아이템: 부스터");
					((부스터) animal).booster();
				}

				printStatus(animals);

				if (animal.getDistance() == Animal.FINISH_DISTANCE) {
					winner = animal;
					break;
				}
			}

			turn++;
		}

		System.out.println("\n=== 배틀 종료 ===");
		System.out.println("승리 동물: " + winner.getName());
	}

	private static void printStatus(Animal[] animals) {
		System.out.println("[현재 위치]");
		for (Animal animal : animals) {
			System.out.println(animal.getName() + ": " + animal.getDistance() + "/" + Animal.GOAL_DISTANCE);
		}
	}

	private static int rollDice(Random random) {
		return random.nextInt(DICE_MAX) + 1;
	}

	private static Animal pickTarget(Random random, Animal[] animals, Animal attacker) {
		Animal target;
		do {
			target = animals[random.nextInt(animals.length)];
		} while (target == attacker);

		return target;
	}
}
