package animal;

public class 원숭이 extends Animal implements 공격, 부스터 {

	public 원숭이() {
		super("원숭이", 0);
	}

	@Override
	public void attack(Animal target) {
		target.damaged(getAttackPower());
		System.out.println(getName() + " 공격(" + getAttackName() + ")");
	}

	@Override
	public void booster() {
		moveBy(getBoosterSpeed());
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

	public void 저글링() {
		System.out.println(getName() + "이 저글링을 하고 있어요");
	}
}
