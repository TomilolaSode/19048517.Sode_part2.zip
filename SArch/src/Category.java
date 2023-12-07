import java.util.*;

public class Category {
	private String name;
	private Set<Level> level = new HashSet<Level>();
	private Set<Competitor> competitors = new HashSet<>();
	private Set<Competitor> winners = new HashSet<>();
	//private HashMap<Competitor, Float> scoreList;
	
	public Category(String name) {
		this.setName(name);
		level.add(new Level("Beginner"));
		level.add(new Level("Intermediate"));
		level.add(new Level("Expert"));
		winners = getWinners();
		competitors = getCompetitors();	
	}

	//setters
	public void setName(String name) {
		this.name = name;
	}
	
	//getters
	public String getName() {
		return name;
	}
	public Set<Level> getLevels() {
		return level;
	}
	public Set<Competitor> getCompetitors() {
		return competitors;
	}
	public Set<Competitor> getCompetitorsbyCategoryLevel(String lvl) {
		Set<Competitor> result = null;
		for (Competitor c : this.competitors) {
			if(lvl == c.getLevelpc(this.name)) {
				result.add(c);
			}
		}
		return result;
	}
	public Set<Competitor> getWinners() {
		Set<Competitor> result = new HashSet<>();
		Float max = null;
		Competitor maxCompetitor = null;
		Competitor[] comps = null;
		for (Level l:level) {
			Set<Competitor> comp = l.getCompetitorsbyLevel();
			for (Competitor c : comp) {
				if (competitors.contains(c)){
					Float i = c.getOverall();
					if(i > max || max == null) {
						max = c.getOverall();
						maxCompetitor = c;
					}/*else if(i==max) {
						comps = {maxCompetitor, c};
					}*/
				}
			}
			if(maxCompetitor != null) {
				result.add(maxCompetitor);
			}
		}
		return result;
	}
	public Competitor getWinnerbyCategoryLevel(String lvl) {
		Set <Competitor> win = getWinners();
		Competitor winner = null;
		for (Competitor c : win) {
			if(lvl == c.getLevel()) {
				winner = c;
			}
		}
		return winner;
	}
	
	//to add objects
	public void addCompetitor(Competitor competitor) {
		String lvlName = competitor.getLevel();
		for (Level lvl : level) {
			if (lvlName == lvl.getName()){
				lvl.addCompetitor(competitor);
			}
		}
	}
	public void addLevel(Level lvl) {
		level.add(lvl);
	}

	//to remove objects
	public void removeCompetitor(String compName) {
		this.winners = winners;
	}
	public void removeLevel(String lvl) {
		level.remove(lvl);
	}


}
