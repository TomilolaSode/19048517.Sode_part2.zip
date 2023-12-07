import java.util.*;
import java.io.*;

public class Manager{
	CompetitorList list;
	
	public Manager() throws IOException{
		list = new CompetitorList("C:\\Users\\tomil\\OneDrive\\Desktop\\RunCompetitor.csv");
	}
	
	public void searchforCompetitor() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Competitor ID: ");
		String errorMessage = "The ID number entered does not exist.";
		int competitorID = input.nextInt();
		Competitor c = null;
		if (list.getCompetitor(competitorID) != null) {
			c = list.getCompetitor(competitorID);
			System.out.println(c.getShortDetails());
		}else {
			System.out.println(errorMessage);
		}
	}
	
	public void decoratedHeaderLines() {
		System.out.println("**********************************************************************************************************************************************");
	}
	public void decoratedSubHeaderLines() {
		System.out.println("***************************************************************************");
	}
	
	public void generateReport() {
		decoratedHeaderLines();
		System.out.println("\t\t\t\t\t\tCompetition Statistics Report");
		decoratedHeaderLines();
		
		//print overall statistics
		System.out.println("•Total Participants: "+list.getParticipantCount());
		System.out.printf("•Average Overall Score: %.2f\n",list.getAverageOverall());
		System.out.println("\n•Highest Scorer(s): \n"+list.getHighestScorer());
		System.out.println("•Age Range of Participants: "+list.getAgeRange());
		
		//Print Filtered Statistics
		//by Level
		decoratedHeaderLines();
		System.out.println("Statistics by Level");
		decoratedHeaderLines();
		for (String lvl: list.getTotalsLevel().keySet()) {
			System.out.println(lvl + " Level");
			decoratedSubHeaderLines();
			System.out.println("•Total Participants: " + list.getTotalsLevel().get(lvl));
			System.out.printf("•With Ages Ranging from: %d - %d\n", list.getAgeRangebyLevel().get(lvl).get("Min"), list.getAgeRangebyLevel().get(lvl).get("Max"));
			System.out.printf("•Average Overall Score: %.2f\n", list.getAveragebyLevel().get(lvl));
			System.out.printf("TOP AND LOW SCORERS:\n\t\t•Top %s Scorer: %s\n\t\t•Lowest %s Scorer: %s\n\n", lvl, list.getScorersbyLevel().get(lvl).get("Max").get(0).getShortDetails(), lvl, list.getScorersbyLevel().get(lvl).get("Min").get(0).getShortDetails());
		}
		//by Gender
		decoratedHeaderLines();
		System.out.println("Statistics by Gender");
		decoratedHeaderLines();
		for (String gen: list.getTotalsGender().keySet()) {
			System.out.println(gen + " Gender");
			decoratedSubHeaderLines();
			System.out.println("•Total Participants: " + list.getTotalsGender().get(gen));
			System.out.printf("•With Ages Ranging from: %d - %d\n", list.getAgeRangebyGender().get(gen).get("Min"), list.getAgeRangebyGender().get(gen).get("Max"));
			System.out.printf("•Average Overall Score: %.2f\n\n", list.getAveragebyGender().get(gen));
			System.out.printf("TOP AND LOW SCORERS:\n\t\t•Top %s Scorer: %s\n\t\t•Lowest %s Scorer: %s\n\n", gen, list.getScorersbyGender().get(gen).get("Max").get(0).getShortDetails(), gen, list.getScorersbyGender().get(gen).get("Min").get(0).getShortDetails());
		}
		//by Country
		decoratedHeaderLines();
		System.out.println("Statistics by Country");
		decoratedHeaderLines();
		for (String country: list.getTotalsCountry().keySet()) {
			System.out.println(country);
			decoratedSubHeaderLines();
			System.out.println("•Total Participants: " + list.getTotalsCountry().get(country));
			System.out.printf("•With Ages Ranging from: %d - %d", list.getAgeRangebyCountry().get(country).get("Min"), list.getAgeRangebyCountry().get(country).get("Max"));
			System.out.printf("\n•Average Overall Score: %.2f\n\n", list.getAveragebyCountry().get(country));
			System.out.printf("TOP AND LOW SCORERS:\n\t\t•Top Scorer from %s: %s\n\t\t•Lowest Scorer from %s: %s\n\n", country, list.getScorersbyCountry().get(country).get("Max").get(0).getShortDetails(), country, list.getScorersbyCountry().get(country).get("Min").get(0).getShortDetails());
		}	
		}
		
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Manager tomi = new Manager();
		//tomi.searchforCompetitor();
		tomi.generateReport();
	}

}
