package animal;

public class Elephant extends Animal {
	public Elephant() {
		super("코끼리", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - 2);
		System.out.println(getName() + "가 코로 공격했습니다!");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + 2);
		System.out.println(getName() + "가 부스터를 사용했습니다!");
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
