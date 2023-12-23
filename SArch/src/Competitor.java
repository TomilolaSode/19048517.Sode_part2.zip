import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public abstract class Competitor {
	private int competitorNo;
	private String name, gender, email, country, category, level, dob;
	private int age; 
	private LocalDate birthDate;
	private Float overallScore;
	private ArrayList<Integer> scores = new ArrayList<>(5); 
	
	public Competitor (int idNo, String name, String gender, String email, String country, String YYYY, String MM, String DD, String Category, String lvl)
	{
		this.competitorNo = idNo;
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.country = country;
		this.category = Category;
		//category.add(Category);
		dob = YYYY + "-" + MM +"-"+DD;
		birthDate = LocalDate.parse(dob);
		this.overallScore = getOverall();
		this.level = lvl;
	}
	
	//setters
	public void setParticipantNo(int num){
		this.competitorNo = num;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setOverallScore(Float overallScore) {
		this.overallScore = overallScore;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setLevel(String lvl) {
		this.level = lvl;
	}
	public void setDoB(LocalDate dt) {
		this.birthDate = dt;
	}
	public void setAge(int a) {
		this.age = a;
	}
	//getters
	public int getParticipantNo(){
		return this.competitorNo;
	}
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
	public String getCountry() {
		return country;
	}
	public String getEmail() {
		return email;
	}
	public int getAge() {
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(birthDate, currentDate);
		int age = period.getYears();
		return age;
	}
	public String getDoB() {
		return this.dob;
	}
	public abstract Float getOverall();
	public String getCategory() {
		return this.category;
	}
	public void setCategory(String name) {
		this.category = name;
	}
	public ArrayList<Integer> getScoreArray(){
		return this.scores;
	}
	public String getInitials() {
		String[] names = name.split(" ");
		String ini = "";
		for(int i = 0; i<names.length; i++) {
			ini = ini + names[i].charAt(0);
		}
		return ini;
	}

	public String getLevel() {
		return this.level;
	}
	
	//String methods
	public String listScores() {
		String scoreList = "Category: \n\t" + category + " - " + level + ":";
		for (int i = 0;  i<scores.size(); i++) {
			scoreList = scoreList + String.format("\t%d", scores.get(i));
		}
		scoreList = scoreList + "\n";
		return scoreList;
	}
	public String getFullDetails() {
		String[] names = name.split(" ");
		String details = String.format("\t\tCompetitor #%d: name %s, first name %s\n\t\tCountry: %s\n\t\tAge: %d\n\t\tScores: %s\n\t\tOverall Score: %.2f\n", competitorNo, name,  names[0], country, getAge(), listScores(), getOverall());
		return details;
	}
	
	public String getShortDetails() {
		String dets = String.format("CN #%d (%s): Overall score %.2f", competitorNo, getInitials(), getOverall());
		return dets;
	}
	
	//Insertion and Update methods
	public void insertScore(int score) {
		if ((0 <= score) && (score <= 5)) {
			this.scores.add(score);
		}else if((0>score) || (score>5)) {
			System.out.println("Invalid Score. Enter a number within the acceptable range of 0-5");
		}
	}


}
