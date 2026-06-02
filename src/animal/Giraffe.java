package animal;

public class Giraffe extends Animal {
	public Giraffe() {
		super("기린", 0);
	}

	@Override
	public void attack(Animal target) {
		target.setDistance(target.getDistance() - 2);
		System.out.println(getName() + "이 꼬리로 때렸습니다!");
	}

	@Override
	public void booster() {
		setDistance(getDistance() + 2);
		System.out.println(getName() + "이 부스터를 사용했습니다!");
	}

	@Override
	public int getSpeedRank() {
		return 1;
	}

	@Override
	public String getAttackName() {
		return "꼬리로 때리기";
	}
}
