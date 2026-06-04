package animal;

public class 알파카 extends Animal implements 공격, 부스터 {
	public 알파카() {
		super("알파카");
	}

	public void attack(Animal target) {
		target.damaged(getAttackPower());
	}

	public void booster() {
		moveBy(getBoosterSpeed());
	}

	public int getSpeedRank() {
		return 5;
	}

	public String getAttackName() {
		return "침뱉기";
	}

	public void 콧노래부르기() {
		System.out.println(getName() + "가 콧노래를 부릅니다!");
	}
}
