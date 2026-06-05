package animal;

public abstract class Animal {
	public static final int GOAL_DISTANCE = 30;
	public static final int ITEM_DISTANCE = 2;
	public static final int FINISH_DISTANCE = GOAL_DISTANCE;

	private final String name;
	private final String imagePath;
	private int distance;
	private int displayDistance;
	private int distanceAfterDice;

	public Animal(String name) {
		this(name, findAnimalImagePath(name));
	}

	public Animal(String name, String imagePath) {
		this.name = name;
		this.imagePath = imagePath;
		reset();
	}

	public void move(int value) {
		setDistance(distance + value);
	}

	public void moveBy(int value) {
		move(value);
	}

	public void damaged(int damage) {
		move(-damage);
	}

	public void reset() {
		distance = 0;
		displayDistance = 0;
		distanceAfterDice = 0;
	}

	public void markDistanceAfterDice() {
		distanceAfterDice = distance;
	}

	public boolean increaseDisplayDistance() {
		if (displayDistance >= distance) {
			return false;
		}
		displayDistance++;
		return true;
	}

	public void syncDisplayDistance() {
		displayDistance = distance;
	}

	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = limitDistance(distance);
		if (displayDistance > this.distance) {
			displayDistance = this.distance;
		}
	}

	public int getDisplayDistance() {
		return displayDistance;
	}

	public int getDistanceAfterDice() {
		return distanceAfterDice;
	}

	public int getSpeedRank() {
		return 0;
	}

	public int getBoosterSpeed() {
		return ITEM_DISTANCE;
	}

	public int getAttackPower() {
		return ITEM_DISTANCE;
	}

	public String getAttackName() {
		return "공격";
	}

	public String toString() {
		return name;
	}

	private int limitDistance(int value) {
		return Math.max(0, Math.min(GOAL_DISTANCE, value));
	}

	public static String findAnimalImagePath(String animalName) {
		return "/images/end/" + animalName + ".jpg";
	}
}
