package animal;

public class Giraffe extends Animal {
	public Giraffe() {
		super("기린", 0);
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
		return 1;
	}

	@Override
	public String getAttackName() {
		return "꼬리로 때리기";
	}
}
