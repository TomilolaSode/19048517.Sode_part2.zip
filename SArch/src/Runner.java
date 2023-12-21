
public class Runner extends Competitor{
	public Runner(int idNo, String name, String gender, String email, String country, String YYYY, String MM, String DD,
			String Category, String lvl) {
		super(idNo, name, gender, email, country, YYYY, MM, DD, Category, lvl);
		if (!Category.equals("Running")) {
			throw new IllegalArgumentException("Category must be equal to 'Running' for Runner objects");
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
