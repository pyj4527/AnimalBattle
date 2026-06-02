package animal;

public abstract class Animal {
	public static final int GOAL_DISTANCE = 30;
	public static final int ITEM_DISTANCE = 2;
	public static final int FINISH_DISTANCE = GOAL_DISTANCE;

	private String name;
	private int distance;

	public Animal(String name, int distance) {
		this.name = name;
		setDistance(distance);
	}

	public void moveBy(int value) {
		setDistance(distance + value);
	}

	public void damaged(int damage) {
		setDistance(distance - damage);
	}

	public void reset() {
		setDistance(0);
	}

	public String getName() {
		return name;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		if (distance < 0) {
			this.distance = 0;
		} else if (distance > GOAL_DISTANCE) {
			this.distance = GOAL_DISTANCE;
		} else {
			this.distance = distance;
		}
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
		return "";
	}
}
