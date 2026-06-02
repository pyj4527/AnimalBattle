package animal;

public class 코끼리 extends Animal implements 공격, 부스터 {

	public 코끼리() {
		super("코끼리", 0);
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
		return 3;
	}

	@Override
	public String getAttackName() {
		return "코로 때리기";
	}

	public void 물위로뿜기() {
		System.out.println(getName() + "가 물을 위로 뿜었습니다!");
	}
}
