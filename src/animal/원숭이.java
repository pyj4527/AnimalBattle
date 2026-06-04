package animal;

public class 원숭이 extends Animal implements 공격, 부스터 {
	public 원숭이() {
		super("원숭이");
	}

	public void attack(Animal target) {
		target.damaged(getAttackPower());
	}

	public void booster() {
		moveBy(getBoosterSpeed());
	}

	public int getSpeedRank() {
		return 2;
	}

	public String getAttackName() {
		return "바나나 껍질 던지기";
	}

	public void 저글링() {
		System.out.println(getName() + "가 바나나로 저글링합니다!");
	}
}
