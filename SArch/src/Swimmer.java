
public class Swimmer extends Competitor{

	public Swimmer(int idNo, String name, String gender, String email, String country, String YYYY, String MM, String DD,
			String Category, String lvl) {
		super(idNo, name, gender, email, country, YYYY, MM, DD, Category, lvl);
		if (!Category.equals("Swimming")) {
			throw new IllegalArgumentException("Category must be equal to 'Swimming' for Swimmer objects");
		}
		getOverall();
	}
	
	@Override
	public Float getOverall() {
		Float avg = (float) 0;
		for (int score : getScoreArray()) {
				avg += score;
			}
		avg /= getScoreArray().size();
		this.setOverallScore(avg);
		return avg;
	}

}
