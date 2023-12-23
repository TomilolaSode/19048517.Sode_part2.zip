import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import java.time.*;

public class CompetitionGUI extends JFrame {

    private List<Competitor> competitors;
    private JTextArea competitorListTextArea;
    private JButton refreshButton;
    private JTextField searchTextField;
    private JComboBox<String> levelComboBox, genderComboBox, ageComboBox, categoryComboBox;
    private Manager manager;
    private DefaultTableModel tableModel;
    private JTable competitorListTable;
    private String insertedFile;

    public CompetitionGUI(Manager manager) {
    	this.manager = manager;
        initializeUI();
        competitors = new ArrayList<>();
        updateCompetitorList();
        insertedFile = "";
    }

    private void initializeUI() {
        setTitle("Competition Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Filter Panel
        JPanel filterPanel = new JPanel(new GridLayout(5, 2));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        levelComboBox = new JComboBox<>(new String[] {"All","Beginner","Intermediate","Advanced"});
        genderComboBox = new JComboBox<>(new String[] {"All","Male", "Female", "Other"});
        ageComboBox = new JComboBox<>();
        ageComboBox.addItem("All");
        categoryComboBox = new JComboBox<>(new String[] {"All","Running", "Swimming"});

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
        
        //report generation 
        JButton generateReportButton = new JButton("Generate Report");
        filterPanel.add(generateReportButton);
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	PrintStream originalOut = System.out;
            	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            	System.setOut(new PrintStream(outputStream));
            	manager.generateReport();
                String report = outputStream.toString();
                		
                displayReportInNewWindow(report);
                saveReportToFile(report);
            }
        });
        
        //Modification Button Panel 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //Get Buttons
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        JButton addButton = new JButton("Add");
        JButton insertFromButton = new JButton("Insert From: ");
        JButton saveButton = new JButton("Save");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(insertFromButton);
        buttonPanel.add(saveButton);
        //Add buttons to table 
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(buttonPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(competitorListTable), BorderLayout.CENTER);
        
        //Refresh Table or Add Filters
        refreshButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           updateCompetitorList();
        }
        });
        
        //Button to delete content from table
        deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           int selectedRow = competitorListTable.getSelectedRow();
           if (selectedRow != -1) {
           // Remove the competitor from the list
           competitors.remove(selectedRow);
           // Remove the row from the table model
           tableModel.removeRow(selectedRow);
           } else {
           // Show a message indicating no row is selected
           JOptionPane.showMessageDialog(null, "Please select a row to delete.");
           }
          }
        });
        
        //Button to Edit contents in Table
        //editButton.addActionListener(new ActionListener() {
           // @Override
            //public void actionPerformed(ActionEvent e) {
           // 	createDialog("Edit Competitor", true); 
       // }
       // });
        
                /*int selectedRow = competitorListTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the competitor to edit
                    Competitor competitor = competitors.get(selectedRow);

                    // Create a dialog for editing a competitor
                    JDialog editDialog = new JDialog(CompetitionGUI.this, "Edit Competitor", true);
                    editDialog.setLayout(new GridLayout(9, 2));

                    
                    JTextField idField = new JTextField(String.valueOf(competitor.getParticipantNo()));
                    JTextField nameField = new JTextField(competitor.getName());
                    JTextField genderField = new JTextField(competitor.getGender());
                    JTextField emailField = new JTextField(competitor.getEmail());
                    JTextField countryField = new JTextField(competitor.getCountry());

                    
                    // Assuming dob is a Date, you can use a JDateChooser to edit the date
                    JDateChooser dobChooser = new JDateChooser();
                    LocalDate date = LocalDate.parse(competitor.getDoB());
                    Date dt = java.sql.Date.valueOf(date);
                    dobChooser.setDate(dt); // Set the current date

                    JTextField ageField = new JTextField(String.valueOf(competitor.getAge()));
                    JTextField categoryField = new JTextField(competitor.getCategory());
                    JTextField levelField = new JTextField(competitor.getLevel());

                    editDialog.add(new JLabel("IdNo:"));
                    editDialog.add(idField);
                    editDialog.add(new JLabel("Name:"));
                    editDialog.add(nameField);
                    editDialog.add(new JLabel("Gender:"));
                    editDialog.add(genderField);
                    editDialog.add(new JLabel("Email:"));
                    editDialog.add(emailField);
                    editDialog.add(new JLabel("Country:"));
                    editDialog.add(countryField);
                    editDialog.add(new JLabel("DoB:"));
                    editDialog.add(dobChooser);
                    editDialog.add(new JLabel("Category:"));
                    editDialog.add(categoryField);
                    editDialog.add(new JLabel("Level:"));
                    editDialog.add(levelField);

                    JButton saveButton = new JButton("Save");
                    editDialog.add(saveButton);

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Validate and save the edited competitor
                            try {
                                // ... (validate input)

                                // Update the competitor details
                                competitor.setParticipantNo(Integer.parseInt(idField.getText()));
                                competitor.setName(nameField.getText());
                                competitor.setGender(genderField.getText());
                                competitor.setEmail(emailField.getText());
                                competitor.setCountry(countryField.getText());

                                // Assuming dob is a Date
                                competitor.setDoB(date);

                                competitor.setAge(Integer.parseInt(ageField.getText()));
                                competitor.setCategory(categoryField.getText());
                                competitor.setLevel(levelField.getText());

                                // Update the table model
                                tableModel.setValueAt(competitor.getParticipantNo(), selectedRow, 0);
                                tableModel.setValueAt(competitor.getName(), selectedRow, 1);
                                tableModel.setValueAt(competitor.getGender(), selectedRow, 2);
                                tableModel.setValueAt(competitor.getEmail(), selectedRow, 3);
                                tableModel.setValueAt(competitor.getCountry(), selectedRow, 4);
                                tableModel.setValueAt(competitor.getDoB(), selectedRow, 5);
                                tableModel.setValueAt(competitor.getAge(), selectedRow, 6);
                                tableModel.setValueAt(competitor.getCategory(), selectedRow, 7);
                                tableModel.setValueAt(competitor.getLevel(), selectedRow, 8);

                                // Close the dialog
                                editDialog.dispose();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter valid numeric values for IdNo and Age.");
                            }
                        }
                    });

                    editDialog.setSize(300, 300);
                    editDialog.setLocationRelativeTo(CompetitionGUI.this);
                    editDialog.setVisible(true);
                } else {
                    // Show a message indicating no row is selected
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
            	
        });*/
        
        //Button to add content to Table
       // addButton.addActionListener(new ActionListener() {
        //	createDialog("Add Competitor", false);
        //});
        ActionListener addOrEdit = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JDialog dialog = new JDialog(CompetitionGUI.this, title, true);
        	    dialog.setLayout(new GridLayout(9, 2));

        	    JTextField idField = new JTextField();
        	    JTextField nameField = new JTextField();
        	    JComboBox<String> levelField = new JComboBox<>(new String[] {"", "Beginner", "Intermediate", "Advanced"});
        	    JTextField emailField = new JTextField();
        	    JDateChooser dobField = new JDateChooser();
        	    JComboBox<String> categoryField = new JComboBox<>(new String[] {"", "Running", "Swimming"});
        	    JComboBox<String> genderField = new JComboBox<>(new String[] {"", "Male", "Female", "Other"});
        	    List<String> countries = Arrays.asList("USA", "Canada", "UK", "Brazil", "UAE", "France", "Germany", "Nigeria", "Ghana");
        	    JComboBox<String> countryField = new JComboBox<>(new DefaultComboBoxModel<>(countries.toArray(new String[0])));

        	    dialog.add(new JLabel("IdNo:"));
                dialog.add(idField);
                dialog.add(new JLabel("Name:"));
                dialog.add(nameField);
                dialog.add(new JLabel("Gender:"));
                dialog.add(genderField);
                dialog.add(new JLabel("Email:"));
                dialog.add(emailField);
                dialog.add(new JLabel("Country:"));
                dialog.add(countryField);
                dialog.add(new JLabel("DoB:"));
                dialog.add(dobField);
                dialog.add(new JLabel("Category:"));
                dialog.add(categoryField);
                dialog.add(new JLabel("Level:"));
                dialog.add(levelField);
                
                
                if (e.getSource() == addButton) {
                	
                }
        	}
        }

        	/*@Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog for adding a new competitor
                JDialog addDialog = new JDialog(CompetitionGUI.this, "Add Competitor", true);
                addDialog.setLayout(new GridLayout(10, 2));

                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JComboBox<String> levelField = new JComboBox<>(new String[] {"","Beginner","Intermediate","Advanced"});
                JTextField emailField = new JTextField();
                JDateChooser dobField = new JDateChooser();
                JComboBox<String> categoryField = new JComboBox<>(new String[] {"","Running", "Swimming"});;
                JComboBox<String> genderField = new JComboBox<>(new String[] {"","Male","Female","Other"});
                List<String> countries = Arrays.asList("USA", "Canada", "UK", "Brazil", "UAE", "France", "Germany", "Nigeria", "Ghana");
                JComboBox<String> countryField = new JComboBox<>(new DefaultComboBoxModel<>(countries.toArray(new String[0])));
                
                addDialog.add(new JLabel("IdNo:"));
                addDialog.add(idField);
                addDialog.add(new JLabel("Name:"));
                addDialog.add(nameField);
                addDialog.add(new JLabel("Gender:"));
                addDialog.add(genderField);
                addDialog.add(new JLabel("Email:"));
                addDialog.add(emailField);
                addDialog.add(new JLabel("Country:"));
                addDialog.add(countryField);
                addDialog.add(new JLabel("DoB:"));
                addDialog.add(dobField);
                addDialog.add(new JLabel("Category:"));
                addDialog.add(categoryField);
                addDialog.add(new JLabel("Level:"));
                addDialog.add(levelField);

                JButton addButton = new JButton("Add");
                addDialog.add(addButton);

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Validate and add the new competitor
                        try {
                            int id = Integer.parseInt(idField.getText());
                            String name = nameField.getText();
                            String gender = (String) genderField.getSelectedItem();
                            String email = emailField.getText();
                            String country = (String) countryField.getSelectedItem();
                            Date dob = dobField.getDate();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dob);
                            String y = Integer.toString(calendar.get(Calendar.YEAR));
                            String m = Integer.toString(calendar.get(Calendar.MONTH)+1);
                            String d = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                            //int age = Integer.parseInt(ageField.getText());
                            String category = (String) categoryField.getSelectedItem();
                            String level = (String) levelField.getSelectedItem();

                            // Create a new competitor
                            //Competitor newCompetitor = null;
                            //if (category.equals("Running")) {
                            Competitor newCompetitor = new Runner(id, name, gender, email, country, y, m, d, category, level);
                            //}else if(category.equals("Swimming")) {
                            	//newCompetitor = new Swimmer(id, name, gender, email, country, y, m, d, category, level);
                            //}

                            // Add the new competitor to the list and table model
                            competitors.add(newCompetitor);
                            updateCompetitorList();
                            // Close the dialog
                            addDialog.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for IdNo and Age.");
                        }
                    }
                });

                addDialog.setSize(300, 300);
                addDialog.setLocationRelativeTo(CompetitionGUI.this);
                addDialog.setVisible(true);
            }
        });*/
        
        
        insertFromButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.handleInsertFromFile(); // Delegate to the manager
                competitors = manager.getList().getCompetitors();
                insertedFile = manager.getList().getPath();
                updateCompetitorList();
            }
        }); 
        
        saveButton.addActionListener(new ActionListener() {
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser fileChooser = new JFileChooser();

                if (!insertedFile.isEmpty()) {
                    // Set the initial directory to the last inserted file's parent directory
                    File initialDirectory = new File(insertedFile).getParentFile();
                    if (initialDirectory.exists()) {
                        fileChooser.setCurrentDirectory(initialDirectory);
                    }
                }
                int result = fileChooser.showSaveDialog(CompetitionGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    manager.saveCompetitorsToFile(file.getPath());
                }
        	}
        });
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Report Functions 
	private void displayReportInNewWindow(String report) {
		JFrame reportFrame = new JFrame("Competition Report");
		JTextArea reportTextArea = new JTextArea(report);
		reportTextArea.setEditable(false);
		reportFrame.add(new JScrollPane(reportTextArea));
		reportFrame.setSize(600,400);
		reportFrame.setLocationRelativeTo(this);
		reportFrame.setVisible(true);
	}
	private void saveReportToFile(String txt) {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        try (PrintWriter writer = new PrintWriter(file)) {
	            writer.println(txt);
	            JOptionPane.showMessageDialog(this, "Report saved successfully!");
	        } catch (IOException ex) {
	            JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage());
	        }
	    }
	}
	
	private void createDialog(String title, boolean isEditMode) {
	    JDialog dialog = new JDialog(CompetitionGUI.this, title, true);
	    dialog.setLayout(new GridLayout(9, 2));

	    JTextField idField = new JTextField();
	    JTextField nameField = new JTextField();
	    JComboBox<String> levelField = new JComboBox<>(new String[] {"", "Beginner", "Intermediate", "Advanced"});
	    JTextField emailField = new JTextField();
	    JDateChooser dobField = new JDateChooser();
	    JComboBox<String> categoryField = new JComboBox<>(new String[] {"", "Running", "Swimming"});
	    JComboBox<String> genderField = new JComboBox<>(new String[] {"", "Male", "Female", "Other"});
	    List<String> countries = Arrays.asList("USA", "Canada", "UK", "Brazil", "UAE", "France", "Germany", "Nigeria", "Ghana");
	    JComboBox<String> countryField = new JComboBox<>(new DefaultComboBoxModel<>(countries.toArray(new String[0])));

	    dialog.add(new JLabel("IdNo:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Gender:"));
        dialog.add(genderField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Country:"));
        dialog.add(countryField);
        dialog.add(new JLabel("DoB:"));
        dialog.add(dobField);
        dialog.add(new JLabel("Category:"));
        dialog.add(categoryField);
        dialog.add(new JLabel("Level:"));
        dialog.add(levelField);
        
	    if (isEditMode) {
	    	int selectedRow = competitorListTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the competitor to edit
                Competitor competitor = competitors.get(selectedRow);
                
                idField.setText(String.valueOf(competitor.getParticipantNo()));
                nameField.setText(competitor.getName());
                genderField.setSelectedItem(competitor.getGender());
                emailField.setText(competitor.getEmail());
                countryField.setSelectedItem(competitor.getCountry());
                categoryField.setSelectedItem(competitor.getCategory());
                levelField.setSelectedItem(competitor.getLevel());
                
                // Assuming dob is a Date, you can use a JDateChooser to edit the date
                LocalDate date = LocalDate.parse(competitor.getDoB());
                Date dt = java.sql.Date.valueOf(date);
                dobField.setDate(dt); // Set the current date
                JButton saveButton = new JButton("Save");
                dialog.add(saveButton);

                saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     // Validate and save the edited competitor
                try {
                	// ... (validate input)

                    // Update the competitor details
                    competitor.setParticipantNo(Integer.parseInt(idField.getText()));
                    competitor.setName(nameField.getText());
                    competitor.setGender((String)genderField.getSelectedItem());
                    competitor.setEmail(emailField.getText());
                    competitor.setCountry((String)countryField.getSelectedItem());

                    // Assuming dob is a Date
                    competitor.setDoB(date);

                    competitor.setAge(competitor.getAge());
                    competitor.setCategory((String)categoryField.getSelectedItem());
                    competitor.setLevel((String)levelField.getSelectedItem());

                    // Update the table model
                    tableModel.setValueAt(competitor.getParticipantNo(), selectedRow, 0);
                    tableModel.setValueAt(competitor.getName(), selectedRow, 1);
                    tableModel.setValueAt(competitor.getGender(), selectedRow, 2);
                    tableModel.setValueAt(competitor.getEmail(), selectedRow, 3);
                    tableModel.setValueAt(competitor.getCountry(), selectedRow, 4);
                    tableModel.setValueAt(competitor.getDoB(), selectedRow, 5);
                    tableModel.setValueAt(competitor.getAge(), selectedRow, 6);
                    tableModel.setValueAt(competitor.getCategory(), selectedRow, 7);
                    tableModel.setValueAt(competitor.getLevel(), selectedRow, 8);

                    // Close the dialog
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values for IdNo and Age.");
                }
            }
        });

        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(CompetitionGUI.this);
        dialog.setVisible(true);
        }else {
                // Show a message indicating no row is selected
                JOptionPane.showMessageDialog(null, "Please select a row to edit.");
            }
	 }else{
	    	JButton addButton = new JButton("Add");
            dialog.add(addButton);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    // Validate and add the new competitor
                    try {
                        int id = Integer.parseInt(idField.getText());
                        String name = nameField.getText();
                        String gender = (String) genderField.getSelectedItem();
                        String email = emailField.getText();
                        String country = (String) countryField.getSelectedItem();
                        Date dob = dobField.getDate();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dob);
                        String y = Integer.toString(calendar.get(Calendar.YEAR));
                        String m = Integer.toString(calendar.get(Calendar.MONTH)+1);
                        String d = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                        String category = (String) categoryField.getSelectedItem();
                        String level = (String) levelField.getSelectedItem();

                        // Create a new competitor
                        //Competitor newCompetitor = null;
                        //if (category.equals("Running")) {
                        Competitor newCompetitor = new Runner(id, name, gender, email, country, y, m, d, category, level);
                        //}else if(category.equals("Swimming")) {
                        	//newCompetitor = new Swimmer(id, name, gender, email, country, y, m, d, category, level);
                        //}

                        // Add the new competitor to the list and table model
                        competitors.add(newCompetitor);
                        updateCompetitorList();
                        // Close the dialog
                        dialog.dispose();
                    }catch (NumberFormatException ex) {
    	                JOptionPane.showMessageDialog(null, "Please enter valid numeric values for IdNo.");
    	            }
                }
            });
	    dialog.setSize(300, 300);
	    dialog.setLocationRelativeTo(CompetitionGUI.this);
	    dialog.setVisible(true);
	    }
	}
	
	
	//Update View 
    void updateCompetitorList() {
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
}