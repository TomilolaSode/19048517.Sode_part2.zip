import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CompetitionGUI extends JFrame {

    private List<Competitor> competitors;
    private JTextArea competitorListTextArea;
    private JButton refreshButton;
    private JTextField searchTextField;
    private JComboBox<String> levelComboBox, genderComboBox, ageComboBox, categoryComboBox;

    private DefaultTableModel tableModel;
    private JTable competitorListTable;

    public CompetitionGUI() {
        initializeUI();
        competitors = new ArrayList<>();
        updateCompetitorList();
    }

    private void initializeUI() {
        setTitle("Competition Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Filter Panel
        JPanel filterPanel = new JPanel(new GridLayout(5, 2));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        levelComboBox = new JComboBox<>();
        levelComboBox.addItem("All");
        levelComboBox.addItem("Beginner");
        levelComboBox.addItem("Intermediate");
        levelComboBox.addItem("Advanced");
        genderComboBox = new JComboBox<>();
        genderComboBox.addItem("All");
        genderComboBox.addItem("Male");
        genderComboBox.addItem("Female");
        genderComboBox.addItem("Other");
        ageComboBox = new JComboBox<>();
        ageComboBox.addItem("All");
        categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("All");
        categoryComboBox.addItem("Running");
        categoryComboBox.addItem("Swimming");

        filterPanel.add(new JLabel("Level:"));
        filterPanel.add(levelComboBox);
        filterPanel.add(new JLabel("Gender:"));
        filterPanel.add(genderComboBox);
        filterPanel.add(new JLabel("Age:"));
        filterPanel.add(ageComboBox);
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryComboBox);
        filterPanel.add(new JLabel("Search:"));
        searchTextField = new JTextField();
        filterPanel.add(searchTextField);

        refreshButton = new JButton("Refresh");
        filterPanel.add(refreshButton);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"IdNo", "Name", "Gender", "Email", "Country", "DoB", "Age", "Category", "Level"}, 0);
        competitorListTable = new JTable(tableModel);
        tablePanel.add(new JScrollPane(competitorListTable), BorderLayout.CENTER);

        mainPanel.add(filterPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCompetitorList();
            }
        });

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateCompetitorList() {
        // Implement logic to update the competitor list based on filters or search
        // For simplicity, let's just display some dummy data
        competitors.clear();
        competitors.add(new Runner(101, "John Jeanne Paul", "Male", ".gmail.com", "Nigeria", "2000", "09", "23", "Running",  "Intermediate"));

        // Apply filters
        String selectedLevel = (String) levelComboBox.getSelectedItem();
        String selectedGender = (String) genderComboBox.getSelectedItem();
        String selectedAge = (String) ageComboBox.getSelectedItem();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String searchText = searchTextField.getText().toLowerCase();

        tableModel.setRowCount(0); // Clear existing rows

        for (Competitor competitor : competitors) {
            if ((selectedLevel.equals("All") || competitor.getLevel().equals(selectedLevel))
                    && (selectedGender.equals("All") || competitor.getGender().equals(selectedGender))
                    && (selectedAge.equals("All") || competitor.getAge()==Integer.parseInt(selectedAge))
                    && (selectedCategory.equals("All") || competitor.getCategory().equals(selectedCategory))
                    && (competitor.getName().toLowerCase().contains(searchText))) {
                tableModel.addRow(new Object[]{
                        competitor.getParticipantNo(),
                        competitor.getName(),
                        competitor.getGender(),
                        competitor.getEmail(),
                        competitor.getCountry(),
                        competitor.getDoB(),
                        competitor.getAge(),
                        competitor.getCategory(),
                        competitor.getLevel(),
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CompetitionGUI();
        });
    }
}