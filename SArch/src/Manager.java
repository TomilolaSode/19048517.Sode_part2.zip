import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.*;

public class Manager{
	private CompetitorList list;
	//private CompetitionGUI gui;
	
	
	public Manager() throws IOException{
		list = new CompetitorList("C:\\Users\\tomil\\OneDrive\\Desktop\\RunCompetitor.csv");
	}
	
	
	public void searchforCompetitor() {
		try (Scanner input = new Scanner(System.in)) {
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
	}
	
	
	public void saveCompetitorsToFile(String fp) {
		try {
			if (!fp.endsWith(".csv")) {
				fp += ".csv";
			}
	        FileWriter fileWriter = new FileWriter(fp); 
	        PrintWriter printWriter = new PrintWriter(fileWriter);

	        // Write competitor data in CSV format
	        for (Competitor competitor : list.getCompetitors()) {
	            printWriter.print(competitor.getParticipantNo()); // Write participant number
	            printWriter.print(",");
	            printWriter.print(competitor.getName()); // Write name
	            printWriter.print(",");
	            printWriter.print(competitor.getEmail()); // Write email address
	            printWriter.print(",");
	            printWriter.print(competitor.getDoB()); // Write date of birth
	            printWriter.print(",");
	            printWriter.print(competitor.getAge()); // Write age
	            printWriter.print(",");
	            printWriter.print(competitor.getGender()); // Write gender
	            printWriter.print(",");
	            printWriter.print(competitor.getCountry()); // Write country
	            printWriter.print(",");
	            printWriter.print(competitor.getCategory()); // Write category
	            printWriter.print(",");
	            printWriter.print(competitor.getLevel()); // Write level
	            printWriter.print(",");
	            for(int i=0; i<competitor.getScoreArray().size()-1; i++) {
	            	printWriter.print(competitor.getScoreArray().get(i)); // Write Scores
	            	printWriter.print(",");
	            }
	            printWriter.print(competitor.getScoreArray().get(competitor.getScoreArray().size()-1)); // Write final score
	            printWriter.println();
	        }
	        printWriter.close();
	        JOptionPane.showMessageDialog(null, "Competitors saved to CSV file successfully!"); // Inform the user of success
	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error saving competitors to CSV file: " + e.getMessage());
	    }
	}
	
	public void handleInsertFromFile() {
	    JFileChooser fileChooser = new JFileChooser();
	    int result = fileChooser.showOpenDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        // Determine file format (CSV or JSON) and read data
	        try {
	            if (selectedFile.getName().endsWith(".csv")) {
	                list.inputCompetitorsFromCSVFile(selectedFile.getAbsolutePath());
	            } /*else if (selectedFile.getName().endsWith(".json")) {
	                readCompetitorsFromJSON(selectedFile.getAbsolutePath());
	            }*/ else {
	                JOptionPane.showMessageDialog(null, "Unsupported file format.");
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
	        }
	        // Notify the GUI to update the table
	        //gui.updateCompetitorList();
	    }
	}
	
	public CompetitorList getList() {
		return this.list;
	}
	
	/*private void readCompetitorsFromJSON(String fp) {
		list.inputCompetitorsFromJSONFile(fp);
	}
	private void readCompetitorsFromCSV(String fp) throws IOException {
		list.inputCompetitorsFromCSVFile(fp);
	}*/


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
		//Score Frequency Table
		decoratedHeaderLines();
		System.out.println("Score Frequency Table");
		decoratedHeaderLines();
		System.out.println(list.getfrequencyScores());
}

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Manager tomi = new Manager();
		CompetitionGUI gui = new CompetitionGUI(tomi);
		//tomi.searchforCompetitor();
		gui.setVisible(true);
	}

}
