package animal;

public class 기린 extends Animal implements 공격, 부스터 {
	public 기린() {
		super("기린");
	}

	public void attack(Animal target) {
		target.damaged(getAttackPower());
	}

	public void booster() {
		moveBy(getBoosterSpeed());
	}

	public int getSpeedRank() {
		return 4;
	}

	public String getAttackName() {
		return "꼬리로 때리기";
	}

	public void 목늘리기() {
		System.out.println(getName() + "이 목을 길게 늘립니다!");
	}
}
