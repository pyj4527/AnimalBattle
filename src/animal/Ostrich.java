package animal;

public class Ostrich extends Animal {
	public Ostrich() {
		super("타조", 0);
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
		return 2;
	}

	@Override
	public String getAttackName() {
		return "부리로 쪼기";
	}
}
