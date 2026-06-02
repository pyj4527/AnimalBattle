package animal;

public class 타조 extends Animal implements 공격, 부스터 {

	public 타조() {
		super("타조", 0);
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
		return 2;
	}

	@Override
	public String getAttackName() {
		return "부리로 쪼기";
	}

	public void 춤추기() {
		System.out.println(getName() + "이 춤을 추고 있어요");
	}
}
