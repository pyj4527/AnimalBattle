package animal;

public class Ostrich extends Animal {
	public Ostrich() {
		super("타조", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - 2);
		System.out.println(getName() + "가 부리로 쪼았습니다!");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + 2);
		System.out.println(getName() + "가 부스터를 사용했습니다!");
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
