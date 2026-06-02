package animal;

public class Alpaca extends Animal {
	public Alpaca() {
		super("알파카", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - 2);
		System.out.println(getName() + "가 침을 뱉었습니다!");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + 2);
		System.out.println(getName() + "가 부스터를 사용했습니다!");
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
