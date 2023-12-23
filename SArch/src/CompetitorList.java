import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CompetitorList {
	private ArrayList<Competitor> competitorList = new ArrayList<Competitor>();
	private String path = "";
	public CompetitorList(String fp) throws IOException{
		path = fp;
		inputCompetitorsFromCSVFile(fp);
	}
		
	public ArrayList<Competitor> getCompetitors() {
		return competitorList;
	}
	public String getPath() {
		return this.path;
	}
	public void inputCompetitor(Competitor n) {
		competitorList.add(n);
	}
	public void inputCompetitorsFromCSVFile(String filepath) throws IOException {
		if (competitorList.isEmpty() == false) {
			competitorList.clear();
		}
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line;
		while (reader.ready()) {
			line = reader.readLine();
			String[] data = line.split(",");
			String[] DoB = data[3].split("/");
			Competitor comp = new Runner(Integer.parseInt(data[0]),data[1],data[5], data[2],data[6],DoB[2],DoB[1],DoB[0],data[7],data[8]);	
			for (int i = 0; i < 5; i++) {
				comp.insertScore(Integer.parseInt(data[9+i]));
			}
			competitorList.add(comp);
		}
		reader.close();
	}
	
	public float getAverageOverall() {
		float avg = 0;
		for(int i = 0; i < competitorList.size(); i++) {
			avg += competitorList.get(i).getOverall();
		}
		avg /= competitorList.size();
		return avg;
	}
	
	public ArrayList<Float> getOverall() {
		ArrayList<Float> overall = new ArrayList<Float>();
		for(Competitor c: competitorList) {
			overall.add(c.getOverall());
		}
		return overall;
	}
	
	public String getDetails() {
		String details = "";
		for (Competitor c: competitorList) {
			details += c.getFullDetails() + "\n";
		}
		return details;
	}
	
	public int getParticipantCount() {
		int count = competitorList.size();
		return count;
	}
	 
	public String getHighestScorer() {
		String output = "";
		ArrayList<Float> overall = getOverall();
		float max = Collections.max(overall);
		for(Competitor c: competitorList) {
			if (c.getOverall() == max) {
				output = c.getFullDetails();
			}
		}
		return output;
	}
	
	public Competitor getCompetitor(int idNo) {
		Competitor comp = null;
		for (Competitor c : competitorList) {
			if (c.getParticipantNo() == idNo) {
				comp = c;
			}
		}
		return comp;
	}

	public ArrayList<Integer> getScoresAchieved(){
		ArrayList<Integer> scores = new ArrayList<>();
		for (Competitor c: competitorList) {
			for (int n : c.getScoreArray()) {
				scores.add(n);
			}
		}
		return scores;
	}
	
	public void removeCompetitor(Competitor c) {
		competitorList.remove(c);
	}
	
	
	public String getfrequencyScores() {
		String output = "\nFrequency Table\n------------------------------\nScore\t|\tFrequency\n";
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();
		ArrayList<Integer> scores = getScoresAchieved();
		for (int n : scores) {
			if (count.containsKey(n)) {
				count.put(n,count.get(n)+1);
			}else {
				count.put(n, 1);
			}
		}
		for(Map.Entry<Integer, Integer> entry:count.entrySet()) { 
			output = output + String.format("%d\t|\t%d\n", entry.getKey(), entry.getValue());
		}
	output = output + "------------------------------";
	return output;
	}
	
	//Totals
	public HashMap<String, Integer> getTotalsCountry() {
		HashMap<String, Integer> totalCompetitorsbyCountry = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalCompetitorsbyCountry.containsKey(c.getCountry())) {
	                totalCompetitorsbyCountry.put(c.getCountry(), totalCompetitorsbyCountry.get(c.getCountry()) + 1);
	          } else {
	                totalCompetitorsbyCountry.put(c.getCountry(), 1);
	         }
		}
		return totalCompetitorsbyCountry;
	}
	public HashMap<String, Float> getTotalScoreCountry() {
		HashMap<String, Float> totalScorebyCountry = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalScorebyCountry.containsKey(c.getCountry())) {
	                totalScorebyCountry.put(c.getCountry(), totalScorebyCountry.get(c.getCountry()) + c.getOverall());
	          } else {
	                totalScorebyCountry.put(c.getCountry(), c.getOverall());
	         } 
		}
		return totalScorebyCountry;
	}
	public HashMap<String, Integer> getTotalsLevel() {
		HashMap<String, Integer> totalCompetitorsbyLevel = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalCompetitorsbyLevel.containsKey(c.getLevel())) {
	                totalCompetitorsbyLevel.put(c.getLevel(), totalCompetitorsbyLevel.get(c.getLevel()) + 1);
	          } else {
	                totalCompetitorsbyLevel.put(c.getLevel(), 1);
	         } 
		}
		return totalCompetitorsbyLevel;
	}
	public HashMap<String, Float> getTotalScoreLevel() {
		HashMap<String, Float> totalScorebyLevel = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalScorebyLevel.containsKey(c.getLevel())) {
	                totalScorebyLevel.put(c.getLevel(), totalScorebyLevel.get(c.getLevel()) + c.getOverall());
	          } else {
	                totalScorebyLevel.put(c.getLevel(), c.getOverall());
	         } 
		}
		return totalScorebyLevel;
	}
	public HashMap<String, Integer> getTotalsGender() {
		HashMap<String, Integer> totalCompetitorsbyGender = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalCompetitorsbyGender.containsKey(c.getGender())) {
	                totalCompetitorsbyGender.put(c.getGender(), totalCompetitorsbyGender.get(c.getGender()) + 1);
	          } else {
	                totalCompetitorsbyGender.put(c.getGender(), 1);
	         } 
		}
		return totalCompetitorsbyGender;
	}
	public HashMap<String, Float> getTotalScoreGender() {
		HashMap<String, Float> totalScorebyGender = new HashMap<>();
		for (Competitor c : competitorList) {
			 if (totalScorebyGender.containsKey(c.getGender())) {
	                totalScorebyGender.put(c.getGender(), totalScorebyGender.get(c.getGender()) + c.getOverall());
	          } else {
	                totalScorebyGender.put(c.getGender(), c.getOverall());
	         } 
		}
		return totalScorebyGender;
	}
	
	//Average Scores
	public Map<String, Float> getAveragebyLevel() {
		Map<String, Float> averageScorebyLevel = getTotalScoreLevel();
		for (Map.Entry<String, Float> entry: averageScorebyLevel.entrySet()) {
			averageScorebyLevel.put(entry.getKey(), entry.getValue()/getTotalsLevel().get(entry.getKey()));
		}
		return averageScorebyLevel;
	}
	public Map<String, Float> getAveragebyGender() {
		Map<String, Float> averageScorebyGender = getTotalScoreGender();
		for (Map.Entry<String, Float> entry: averageScorebyGender.entrySet()) {
			averageScorebyGender.put(entry.getKey(), entry.getValue()/getTotalsGender().get(entry.getKey()));
		}
		return averageScorebyGender;
	}
	public Map<String, Float> getAveragebyCountry() {
		Map<String, Float> averageScorebyCountry = getTotalScoreCountry();
		for (Map.Entry<String, Float> entry: averageScorebyCountry.entrySet()) {
			averageScorebyCountry.put(entry.getKey(), entry.getValue()/getTotalsCountry().get(entry.getKey()));
		}
		return averageScorebyCountry;
	}
	
	//Highest and Lowest Scorers(Max-Min)
	public Map<String, Map<String, List<Competitor>>> getScorersbyLevel() {
		Map<String, Map<String, List<Competitor>>> scorersbyLevel = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !scorersbyLevel.containsKey(c.getLevel()) ){
				scorersbyLevel.put(c.getLevel(), new HashMap<String, List<Competitor>>());
				scorersbyLevel.get(c.getLevel()).put("Min", new ArrayList<Competitor>());
				scorersbyLevel.get(c.getLevel()).put("Max", new ArrayList<Competitor>());	
			}
			//boolean minCondition = c.getOverall() < scorersbyLevel.get(c.getLevel()).get("Min").getOverall();
			//boolean maxCondition = c.getOverall() > scorersbyLevel.get(c.getLevel()).get("Max").getOverall();
			List<Competitor> min = scorersbyLevel.get(c.getLevel()).get("Min");
			List<Competitor> max = scorersbyLevel.get(c.getLevel()).get("Max");
			if (min.isEmpty() || c.getOverall() < min.get(0).getOverall()) {
				min.add(c);
				scorersbyLevel.get(c.getLevel()).put("Min", min);
			}else if (max.isEmpty() || c.getOverall() < max.get(0).getOverall()){
				max.add(c);
				scorersbyLevel.get(c.getLevel()).put("Max", max);
			} 
		}
		return scorersbyLevel;
	}
	public Map<String, Map<String, List<Competitor>>> getScorersbyGender() {
		Map<String, Map<String, List<Competitor>>> scorersbyGender = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !scorersbyGender.containsKey(c.getGender()) ){
				scorersbyGender.put(c.getGender(), new HashMap<String, List<Competitor>>());
				scorersbyGender.get(c.getGender()).put("Min", new ArrayList<Competitor>());
				scorersbyGender.get(c.getGender()).put("Max", new ArrayList<Competitor>());	
			}
			//boolean minCondition = c.getOverall() < scorersbyGender.get(c.getGender()).get("Min").getOverall();
			//boolean maxCondition = c.getOverall() > scorersbyGender.get(c.getGender()).get("Max").getOverall();
			List<Competitor> min = scorersbyGender.get(c.getGender()).get("Min");
			List<Competitor> max = scorersbyGender.get(c.getGender()).get("Max");
			if (min.isEmpty() || c.getOverall() < min.get(0).getOverall()) {
				min.add(c);
				scorersbyGender.get(c.getGender()).put("Min", min);
			}else if (max.isEmpty() || c.getOverall() < max.get(0).getOverall()){
				max.add(c);
				scorersbyGender.get(c.getGender()).put("Max", max);
			} 
		}
		return scorersbyGender;
	}
	public Map<String, Map<String, List<Competitor>>> getScorersbyCountry() {
		Map<String, Map<String, List<Competitor>>> scorersbyCountry = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !scorersbyCountry.containsKey(c.getCountry()) ){
				scorersbyCountry.put(c.getCountry(), new HashMap<String, List<Competitor>>());
				scorersbyCountry.get(c.getCountry()).put("Min", new ArrayList<Competitor>());
				scorersbyCountry.get(c.getCountry()).put("Max", new ArrayList<Competitor>());	
			}
			//boolean minCondition = c.getOverall() < scorersbyCountry.get(c.getCountry()).get("Min").getOverall();
			//boolean maxCondition = c.getOverall() > scorersbyCountry.get(c.getCountry()).get("Max").getOverall();
			List<Competitor> min = scorersbyCountry.get(c.getCountry()).get("Min");
			List<Competitor> max = scorersbyCountry.get(c.getCountry()).get("Max");
			if (min.isEmpty() || c.getOverall() < min.get(0).getOverall()) {
				min.add(c);
				scorersbyCountry.get(c.getCountry()).put("Min", min);
			}else if (max.isEmpty() || c.getOverall() < max.get(0).getOverall()){
				max.add(c);
				scorersbyCountry.get(c.getCountry()).put("Max", max);
			} 
		}
		return scorersbyCountry;
	}
	
	//Age Ranges for Competitors
	public String getAgeRange() {
		int max=0, min=0;
		for (Competitor c: competitorList) {
			if (c.getAge() < min || min == 0) {
				min = c.getAge();
			}
			if (c.getAge() > max || max == 0) {
				max = c.getAge();
			}
		}
		String output = String.format(" %d - %d\n", min, max);
		return output;
	}
	
	//The following method is used instead of the if..equals method to accommodate new values
	public Map<String, Map<String, Integer>> getAgeRangebyGender() {
		Map<String, Map<String, Integer>> ageRangebyGender = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !ageRangebyGender.containsKey(c.getGender()) ){
				ageRangebyGender.put(c.getGender(), new HashMap<String, Integer>());
				ageRangebyGender.get(c.getGender()).put("Min", 0);
				ageRangebyGender.get(c.getGender()).put("Max", 0);
			}
			boolean minCondition = c.getAge() <= ageRangebyGender.get(c.getGender()).get("Min");
			boolean maxCondition = c.getAge() >= ageRangebyGender.get(c.getGender()).get("Max");
			boolean nullminCondition = ageRangebyGender.get(c.getGender()).get("Min") == 0;
			boolean nullmaxCondition = ageRangebyGender.get(c.getGender()).get("Max") == 0;
			if (minCondition || nullminCondition) {
				ageRangebyGender.get(c.getGender()).put("Min", c.getAge());
			}
			if (maxCondition || nullmaxCondition){
				ageRangebyGender.get(c.getGender()).put("Max", c.getAge());
			} 
		}
		return ageRangebyGender;
		}
	public Map<String, Map<String, Integer>> getAgeRangebyLevel() {
		Map<String, Map<String, Integer>> ageRangebyLevel = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !ageRangebyLevel.containsKey(c.getLevel()) ){
				ageRangebyLevel.put(c.getLevel(), new HashMap<String, Integer>());
				ageRangebyLevel.get(c.getLevel()).put("Min", 0);
				ageRangebyLevel.get(c.getLevel()).put("Max", 0);
			}
			boolean minCondition = c.getAge() <= ageRangebyLevel.get(c.getLevel()).get("Min");
			boolean maxCondition = c.getAge() >= ageRangebyLevel.get(c.getLevel()).get("Max");
			boolean nullminCondition = ageRangebyLevel.get(c.getLevel()).get("Min") == 0;
			boolean nullmaxCondition = ageRangebyLevel.get(c.getLevel()).get("Max") == 0;
			if (minCondition || nullminCondition) {
				ageRangebyLevel.get(c.getLevel()).put("Min", c.getAge());
			}
			if (maxCondition || nullmaxCondition){
				ageRangebyLevel.get(c.getLevel()).put("Max", c.getAge());
			} 
		}
		return ageRangebyLevel;
		}
	public Map<String, Map<String, Integer>> getAgeRangebyCountry() {
		Map<String, Map<String, Integer>> ageRangebyCountry = new HashMap<>();
		for (Competitor c : competitorList) {
			if ( !ageRangebyCountry.containsKey(c.getCountry()) ){
				ageRangebyCountry.put(c.getCountry(), new HashMap<String, Integer>());
				ageRangebyCountry.get(c.getCountry()).put("Min", 0);
				ageRangebyCountry.get(c.getCountry()).put("Max", 0);
			}
			boolean minCondition = c.getAge() <= ageRangebyCountry.get(c.getCountry()).get("Min");
			boolean maxCondition = c.getAge() >= ageRangebyCountry.get(c.getCountry()).get("Max");
			boolean nullminCondition = ageRangebyCountry.get(c.getCountry()).get("Min") == 0;
			boolean nullmaxCondition = ageRangebyCountry.get(c.getCountry()).get("Max") == 0;
			if (minCondition || nullminCondition) {
				ageRangebyCountry.get(c.getCountry()).put("Min", c.getAge());
			}
			if (maxCondition || nullmaxCondition){
				ageRangebyCountry.get(c.getCountry()).put("Max", c.getAge());
			} 
		}
		return ageRangebyCountry;
		}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CompetitorList list = new CompetitorList("C:\\Users\\tomil\\OneDrive\\Desktop\\RunCompetitor.csv");
		//list.inputCompetitor("C:\\Users\\tomil\\OneDrive\\Desktop\\RunCompetitor.csv");
		String res = list.getfrequencyScores();
		System.out.println(list.getAgeRange());
		System.out.println(list.getAgeRangebyLevel());
		System.out.println(res);
	}

}
 