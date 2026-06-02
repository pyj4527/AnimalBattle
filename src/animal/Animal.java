package animal;

public abstract class Animal implements Attackable, Boostable {
	public static final int FINISH_DISTANCE = 30;

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
		} else if (distance > FINISH_DISTANCE) {
			this.distance = FINISH_DISTANCE;
		} else {
			this.distance = distance;
		}
	}

	public int getSpeedRank() {
		return 0;
	}

	public int getBoosterSpeed() {
		return 2;
	}

	public int getAttackPower() {
		return 2;
	}

	public String getAttackName() {
		return "";
	}
}
