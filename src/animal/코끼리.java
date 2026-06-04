package animal;

public class 코끼리 extends Animal implements 공격, 부스터 {
	public 코끼리() {
		super("코끼리");
	}

	public void attack(Animal target) {
		target.damaged(getAttackPower());
	}

	public void booster() {
		moveBy(getBoosterSpeed());
	}

	public int getSpeedRank() {
		return 3;
	}

	public String getAttackName() {
		return "코로 때리기";
	}

	public void 물위로뿜기() {
		System.out.println(getName() + "가 물을 위로 뿜었습니다!");
	}
}
