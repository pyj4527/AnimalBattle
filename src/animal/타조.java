package animal;

public class 타조 extends Animal implements 공격, 부스터 {
	public 타조() {
		super("타조");
	}

	public void attack(Animal target) {
		target.damaged(getAttackPower());
	}

	public void booster() {
		moveBy(getBoosterSpeed());
	}

	public int getSpeedRank() {
		return 1;
	}

	public String getAttackName() {
		return "부리로 쪼기";
	}

	public void 춤추기() {
		System.out.println(getName() + "가 춤을 춥니다!");
	}
}
