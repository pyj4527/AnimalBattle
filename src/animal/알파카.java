package animal;

public class 알파카 extends Animal implements 공격, 부스터 {

	public 알파카() {
		super("알파카", 0);
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
		return 5;
	}

	@Override
	public String getAttackName() {
		return "침뱉기";
	}

	public void 콧노래부르기() {
		System.out.println(getName() + "이 콧노래를 부르고 있어요");
	}
}
