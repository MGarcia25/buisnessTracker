/**
 * File to add Employees to Database
 *
 * @author Mariano Garcia
 * @version Lab5
 * @Email MGarciaTrujill@cnm.edu
 * @Date 7/25/2021
 * @Class CSCI 2251
 * @bugs
 */

package lab5;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Employees {
    String firstName;
    String lastName;
    String socialSecurityNumber;
    String birthday;
    String userInput;
    String[] checkInput;

    public Employees(){

    }
    public ArrayList<String> addEmployee(){
        boolean ss_check = true;
        boolean birthday_check = true;
        Scanner input = new Scanner(System.in);

        /**
         * Employee First Name - User Input
         */
        System.out.println("Please enter the employee's first name: ");
        firstName = input.nextLine();
        System.out.println(firstName);
        /**
         * Employee Last Name - User Input
         */
        System.out.println("Please enter the employee's last name: ");
        lastName = input.nextLine();
        System.out.println(lastName);
        /**
         * Social Security - User Input
         */
        while(ss_check == true){
            System.out.println("Please enter the social security number(FORMAT: xxx-xx-xxxx): ");
            socialSecurityNumber = input.nextLine();
            if (socialSecurityNumber.contains("-")){
                checkInput = socialSecurityNumber.split("-");
                if (checkInput[0].length() != 3){
                    System.out.println("You didn't enter the SS in the correct format.");
                }else if (checkInput[1].length() != 2){
                    System.out.println("You didn't enter the SS in the correct format.");
                }else if (checkInput[2].length() != 4){
                    System.out.println("You didn't enter the SS in the correct format.");
                }else{
                    System.out.println(socialSecurityNumber);
                    ss_check = false;
                }
            }else{
                System.out.println("You didn't enter the SS in the correct format.");
            }
        }

        /**
         * Birthday - User Input
         */
        while(birthday_check == true){
            System.out.println("Please enter the Birthday(FORMAT: xxxx-xx-xx): ");
            birthday = input.nextLine();
            if (birthday.contains("-")){
                checkInput = birthday.split("-");
                if (checkInput[0].length() != 4){
                    System.out.println("You didn't enter the birthday in the correct format.(First Section)");
                }else if (checkInput[1].length() > 2 || checkInput[1].length() < 1){
                    System.out.println("You didn't enter the birthday in the correct format. (Middle Section)");
                }else if (checkInput[2].length() > 2 || checkInput[1].length() < 1){
                    System.out.println("You didn't enter the birthday in the correct format.(End Section)");
                }else{
                    System.out.println(birthday);
                    birthday_check = false;
                }
            }else{
                System.out.println("You didn't enter the birthday in the correct format.");
            }
        }
        /**
         * Random employeeData - User Input
         */
        List<String> list_employee = new ArrayList<>();
        list_employee.add("salariedEmployee");
        list_employee.add("commisionEmployee");
        list_employee.add("hourlyEmployee");

        Collections.shuffle(list_employee);
        Collections.shuffle(list_employee);         //shuffles twice so its random

        System.out.println("EmployeeType: " + list_employee.get(0));

        /**
         * Random departmentName - User Input
         */
        List<String> list_department = new ArrayList<>();
        list_department.add("salariedEmployee");
        list_department.add("commissionEmployee");
        list_department.add("hourlyEmployee");

        Collections.shuffle(list_department);
        Collections.shuffle(list_department); //shuffles twice so its random

        System.out.println("DepartmentType: " + list_department.get(0));

        ArrayList<String> data = new ArrayList<String>();
        data.add(firstName);
        data.add(lastName);
        data.add(socialSecurityNumber);
        data.add(birthday);
        data.add(list_employee.get(0));
        data.add(list_department.get(0));

        return data;
    }

    public void comboBox(String textBox){
        if(textBox == "1"){
            /*Select everyone in department Sales */
        }else if (textBox == "2"){

        }

    }

}
