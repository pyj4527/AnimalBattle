package animal;

public class 기린 extends Animal implements 공격, 부스터 {

	public 기린() {
		super("기린", 0);
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
		return 1;
	}

	@Override
	public String getAttackName() {
		return "꼬리로 때리기";
	}

	public void 외발자전거타기() {
		System.out.println(getName() + "이 외발자전거를 타고 있어요");
	}
}
