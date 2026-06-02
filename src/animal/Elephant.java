package animal;

public class Elephant extends Animal {
	public Elephant() {
		super("코끼리", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - ITEM_DISTANCE);
		System.out.println(getName() + " 공격(" + getAttackName() + ")");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + ITEM_DISTANCE);
		System.out.println(getName() + " 부스터 사용");
	}

	@Override
	public int getSpeedRank() {
		return 3;
	}

	@Override
	public String getAttackName() {
		return "코로 때리기";
	}
}
