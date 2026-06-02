package animal;

public class Monkey extends Animal {
	public Monkey() {
		super("원숭이", 0);
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
		return 4;
	}

	@Override
	public String getAttackName() {
		return "바나나 껍질 던지기";
	}
}
