import java.time.LocalDate;
import java.time.Period;
import java.util.*;
public class Competitor {
	private int competitorNo;
	private String name, email, country;
	private LocalDate birthDate;
	private Float overallScore;
	private ArrayList<String> category = new ArrayList<>() ;
	private HashMap<String, ArrayList<Integer>> scorePerCategory = new HashMap<String, ArrayList<Integer>>();
	private HashMap<String, Float> overallPerCategory = new HashMap<String, Float>();
	private HashMap<String, String> categoryLevels = new HashMap<String, String>();
	
	public Competitor (String name, String email, String country, String YYYY, String MM, String DD, String Category, String lvl)
	{
		this.name = name;
		this.email = email;
		this.country = country;
		category.add(Category);
		String dob = YYYY + "-" + MM +"-"+DD;
		birthDate = LocalDate.parse(dob);
		ArrayList<Integer> scores = new ArrayList<Integer>(5);
		for (int i = 0; i<category.size(); i++) { //might  change
			scorePerCategory.put(Category, scores);
			overallPerCategory.put(Category, null);
			categoryLevels.put(Category, lvl);
		}
	}
	
	//setters
	public void setParticipantNo(int num){
		this.competitorNo = num;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCountryName(String country) {
		this.country = country;
	}
	
	//getters
	public String getName() {
		return name;
	}
	public String getCountryName() {
		return country;
	}
	public int getAge() {
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(birthDate, currentDate);
		int age = period.getYears();
		return age;
	}
	public float getOverall() {
		float avg = 0;
		for (String key:overallPerCategory.keySet()) {
			avg += overallPerCategory.get(key);
		}
		avg /= overallPerCategory.size();
		this.overallScore = avg;
		return avg;
	}
	public ArrayList<Integer> getScoreArray(String categ){
		ArrayList<Integer> scoreList = new ArrayList<Integer>();
		for (String key:scorePerCategory.keySet()) {
			if (categ == key) {
				scoreList = scorePerCategory.get(key);
			}
		}
		return scoreList;
	}
	public String getInitials() {
		String[] names = name.split(" ");
		String ini = "";
		for(int i = 0; i<names.length; i++) {
			ini = ini + names[i].charAt(0);
		}
		return ini;
	}
	public String getLevel(String categ) {
		String lev = null;
		for (String key:categoryLevels.keySet()) {
			if (categ == key) {
				lev = categoryLevels.get(key);
			}
		}
		return lev;
	}
	
	//String methods
	public String listScores(){
		String scoreList = "Categories: \n\t";
		for (String key:scorePerCategory.keySet()) {
			scoreList = scoreList + key + ":" ;
			for (int i=0;i<scorePerCategory.get(key).size(); i++) {
				scoreList = scoreList + String.format("\t%d", scorePerCategory.get(key).get(i));
			}
			scoreList = scoreList + "\n";
		}
		return scoreList;
	}
	public String getFullDetails() {
		String[] names = name.split(" ");
		String details = String.format("Competitor number %d, name %s, country %s.\n%s is a %s aged %d and received these scores: %s\nThis gives him an overall score of %.2f", competitorNo, name, country, names[0], categoryLevels.get(category.get(0)),getAge(),getScoreList(),overallScore);
		return details;
	}
	
	public String getShortDetails() {
		String dets = String.format("CN %d (%s) has overall score %f", competitorNo, getInitials(), getOverall());
		return dets;
	}
	
	//Insertion and Update methods
	public void insertScore(String categ, int score) {
		for (String key:scorePerCategory.keySet()){
			if ((key == categ) && (1 <= score) && (score <= 6)) {
				ArrayList<Integer> list = scorePerCategory.get(key);
				list.add(score);
				scorePerCategory.put(key, list);
			}else if((1>score) || (score>6)) {
				System.out.println("Invalid Score. Enter a number within the acceptable range of 1-6");
			}
		}
		updateOverallpcScore();
	}
	public void updateOverallpcScore() {
		for (String key:scorePerCategory.keySet()){
			float avg = 0;
			for (int value : scorePerCategory.get(key)) {
				avg += value;
			}
			avg /= scorePerCategory.size();
			overallPerCategory.put(key, avg);
		}
	}

}
