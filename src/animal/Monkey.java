package animal;

public class Monkey extends Animal {
	public Monkey() {
		super("원숭이", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - 2);
		System.out.println(getName() + "가 바나나 껍질을 던졌습니다!");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + 2);
		System.out.println(getName() + "가 부스터를 사용했습니다!");
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
