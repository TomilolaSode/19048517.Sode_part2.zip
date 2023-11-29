import java.util.*;

public class Level {
	private String levelName;
	private Set<Competitor> competitors = new HashSet<>();
	
	public Level(String name){
		this.setName(name);
	}
	
	public String getName() {
		return this.levelName;
	}
	public void setName(String name) {
		this.levelName = name;
	}
	public void addCompetitor(Competitor competitorName){
		competitors.add(competitorName);
	}
	public Set getCompetitorsbyLevel() {
		return competitors;
	}
}
