package animal;

public class Alpaca extends Animal {
	public Alpaca() {
		super("알파카", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - ITEM_DISTANCE);
		System.out.println(getName() + " 공격(" + getAttackName() + ")");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + ITEM_DISTANCE);
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
}
