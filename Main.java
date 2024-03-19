/**
 * Main java file
 *
 * @author Mariano Garcia
 * @version Lab5
 * @Email MGarciaTrujill@cnm.edu
 * @Date 7/25/2021
 * @Class CSCI 2251
 * @bugs
 */


package lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.LocalDate;

public class Main extends JFrame{
    /**
     * Variables that will be needed to create a GUI
     */
    JTextArea textArea;
    JComboBox comboBox;
    JButton button;
    JLabel label;
    JLabel labelHelp;
    static String text;
    /**
     * Needed information to connect to the Database
     */
    static final String DB_URL = "jdbc:mysql://localhost/employees";
    static final String USER = "root";
    static final String PASS = "password";
    /**
     * Going to be needed to pull from the Database and to insert information
     * Variables created to assign them from method call
     */
    static final String QUERY = "SELECT * FROM employees";
    static final String querySalaried = "SELECT * FROM salariedEmployees";
    static final String queryCommission = "SELECT * FROM commissionEmployees";
    static final String queryBasePlus = "SELECT * FROM basePlusCommissionEmployees";
    static final String queryHourly = "SELECT * FROM hourlyEmployees";
    static final String query_insert = "insert into employees(socialSecurityNumber, firstName, lastName, birthday, employeeType, departmentName) values(?, ?, ?, ?, ?, ?)";
    static String birthday;

    /**
     * Constructor to make the new GUI
     */
    public Main(){
        setLayout(new FlowLayout());
        textArea = new JTextArea(5, 30);
        add(textArea);


        /**
         * Options needed for the dropbox in GUI
         */
        String options[] = {"Select all employees working in Department Sales", "Select hourly employees working over 30" +
                " hours", "Select all commission employees in descending commission rate", "Increase Base Salary by 10%" +
                " for all base - plus commission employees", "If it is the employee's birthday in the current month, add" +
                " $100", "For all commission employees with gross sales over $10,000, add $100 bonus"};

        comboBox = new JComboBox(options);
        add(comboBox);

        button = new JButton("Click to submit");
        add(button);

        labelHelp = new JLabel("Enter INSERT command as follows: SocialSecurityNumber(xxx-xx-xxxx) FirstName " +
                "LastName Birthday(xxxx-xx-xx)" + " EmployeeType DepartmentName");
        add(labelHelp);

        label = new JLabel("\n");
        add(label);

        event e  = new event();
        button.addActionListener(e);
    }

    /**
     * Event that will be used when calling the GUI
     */
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            java.util.Date birthday_sql = null;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate;

            Connection conn = null;
            /**
             * Will create the connection needed to connect to the SQL
             */
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            text = textArea.getText();
            if(e.getSource() == comboBox){
                label.setText(comboBox.getSelectedItem() + "selected");
            }

            System.out.println(comboBox.getSelectedItem());

            if (text.isBlank()){ //The if blank will let the code know that the user did not type anything into the textBox
                /**
                 * Will go into the first options of choice from the user
                 */
                if (comboBox.getSelectedItem().toString().startsWith("Select all employee")){
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(QUERY);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if(rs.getString("departmentName").equals("SALES")){ //Will look for each employee to see if they have SALES as a department name
                                label.setText("Social Security Number: " + rs.getString("socialSecurityNumber") +
                                        ", FirstName: " + rs.getString("firstName") + ", LastName: " + rs.getString("lastName")
                                        + ", Birthday: " + rs.getDate("birthday") + ", EmployeeType: " + rs.getString("employeeType")
                                        + ", DepartmentName: " + rs.getString("departmentName"));
                                System.out.println("Social Security Number: " + rs.getString("socialSecurityNumber"));
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }else if (comboBox.getSelectedItem().toString().startsWith("Select hourly")){ //Check to see if option 2 is selected
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(queryHourly);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if(rs.getInt("hours") >= 30){ //Check hours to see if they're over 30
                                label.setText("Social Security Number: " + rs.getString("socialSecurityNumber") +
                                        ", hours: " + rs.getString("hours"));
                                System.out.println("Social Security Number: " + rs.getString("socialSecurityNumber"));
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                } else if (comboBox.getSelectedItem().toString().startsWith("Select all commission")){ //Option 3
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(queryCommission + " ORDER BY commissionRate DESC"); //Order by descending
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            label.setText("Social Security Number: " + rs.getString("socialSecurityNumber") +
                                    ", Comission Rate: " + rs.getString("commissionRate"));
                            System.out.println("Social Security Number: " + rs.getString("socialSecurityNumber"));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }else if (comboBox.getSelectedItem().toString().startsWith("Increase")){ //Option 4
                    String sql = "UPDATE basePlusCommissionEmployees SET baseSalary = ? ";
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(queryBasePlus);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        double number = 0; //Creates the number after the 10% increase
                        try {
                            PreparedStatement ps = conn.prepareStatement(sql);
                            number = rs.getDouble("baseSalary");
                            number *= 1.1;
                            System.out.println(number);
                            ps.setDouble(1, number);
                            label.setText("Updated Base Salary: " + number + ", Social Security Number: " +
                                    rs.getString("socialSecurityNumber"));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                }else if (comboBox.getSelectedItem().toString().startsWith("If")){//Option 5
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(QUERY);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    /**
                     * Needed to create and grab the Date from a String
                     */
                    LocalDate ld = LocalDate.now();
                    Month currentMonth = ld.getMonth();

                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if(currentMonth.getValue() == (rs.getDate("birthday").getMonth() + 1)){
                                String ssNumber = rs.getString("socialSecurityNumber");
                                String sql = "UPDATE employees SET bonus = 100 WHERE socialSecurityNumber = ?";
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setString(1, rs.getString("socialSecurityNumber"));
                                label.setText("Gave $100 to employees with Birthday's in the current month: " +
                                        rs.getString("socialSecurityNumber"));
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }else if (comboBox.getSelectedItem().toString().startsWith("For")){ //Option 6
                    String sql = "UPDATE commissionEmployees SET bonus = ? ";
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(queryCommission);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    while(true){
                        try {
                            if (!rs.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if (rs.getInt("grossSales") > 10000){
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setDouble(1, 100);
                                label.setText("Gave $100 to employees with Social Security Number: " + rs.getString("socialSecurityNumber"));
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }else{ //If the user types into the TextArea
                System.out.println(text);
                String[] arr = text.split(" "); //Splits userInput at every space
                birthday = arr[3];
                try {
                    birthday_sql = df.parse(birthday);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                sqlDate = new Date(birthday_sql.getTime());

                PreparedStatement ps = null;
                try {
                    ps = conn.prepareStatement(query_insert);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                /**
                 * Try and Catch for every element in the array for the INSERT
                 */
                try {
                    ps.setString(1, arr[0]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ps.setString(2, arr[1]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ps.setString(3, arr[2]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ps.setDate(4, sqlDate);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ps.setString(5, arr[4]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    ps.setString(6, arr[5]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                /**
                 * updates everything
                 */
                try {
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                /**
                 * Prints everything in the database to make sure it updated
                 */
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery(QUERY);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                while(true){
                    try {
                        if (!rs.next()) break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print("Social Security Number: " + rs.getString("socialSecurityNumber"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print(", FirstName: " + rs.getString("firstName"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print(", LastName: " + rs.getString("lastName"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print(", Birthday: " + rs.getDate("birthday"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print(", EmployeeType: " + rs.getString("employeeType"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        System.out.print(", DepartmentName: " + rs.getString("departmentName"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    System.out.println("");
                }
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                label.setText("\nInserted into Database");
            }
        }
    }

    public static void main(String[] args) {
        Main application = new Main();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1200, 400);
        application.setVisible(true);
    }
}
