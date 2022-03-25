package csse220.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    Stage window;
    Scene scene, scene2, scene3, scene4, scene5;
    //Creation of files to store guest and admin info for log in
    File guestFile = new File("C:\\Users\\Lydia Manu\\Desktop\\CSSE 240\\240Project\\Guest.txt"); //MAKE SURE THE PATH IS CORRECT
    File adminFile = new File("C:\\Users\\Lydia Manu\\Desktop\\CSSE 240\\240Project\\Admin.txt"); //MAKE SURE THE PATH IS CORRECT
        
    //Creates two different hashtable with guest and admin sing up credentials by reading file
    Hashtable<String, String> guestHT = new Hashtable();
    Hashtable<String, String[]> adminHT = new Hashtable();
    
    

    public static void createGuest(String guestFN, String guestLN, String guestUN, String guestPW, File guestFile){
        Guest guest_1 = new Guest(guestFN, guestLN, guestUN, guestPW);

        try {
            guest_1.addToGuestFile(guest_1.getUsername(), guest_1.getPassword(), guestFile);
                    }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createAdmin(String adminFN, String adminLN, String adminUN, String adminPW, String adminID, File adminFile){
        Admin admin_1 = new Admin(adminFN, adminLN, adminUN, adminPW, adminID);
        try {
            admin_1.addToAdminFile(admin_1.getUsername(), admin_1.getPassword(), admin_1.getAdminID(), adminFile);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static Hashtable<String, String> readFromGuestFile() {
        Hashtable<String, String> GHT = new Hashtable<>();
        //Try clause that will read from CSV file and add to hashtable
        try{
            String line;
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\Users\\Lydia Manu\\Desktop\\CSSE 240\\240Project\\Guest.txt")); //MAKE SURE THE PATH IS CORRECT

            while((line = br.readLine()) != null){       //while loop resets array values
                String[] values = line.split(","); //add values from line separated by commas to array
                GHT.put(values[0], values[1]);          //add array[0] as key and array[1] as value
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return GHT;
    }

    public static Hashtable<String, String[]> readFromAdminFile() {
        Hashtable<String, String[]> AHT = new Hashtable<>();
        //Try clause that will read from CSV file and add to hashtable
        try{
            String line;
            String[] logInInfo = new String[2]; //array to store username and password
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\Users\\Lydia Manu\\Desktop\\CSSE 240\\240Project\\Admin.txt")); //MAKE SURE THE PATH IS CORRECT

            while((line = br.readLine()) != null){       //while loop resets array values
                String[] values = line.split(","); //add the three values from line separated by commas to array
                logInInfo[0] = values[1];
                logInInfo[1] = values[2];
                AHT.put(values[0], logInInfo);           //Adding a key String and value array to hashtable
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return AHT;
    }
    
    /*
    * Method to determine whether to create a guest or admin for SIGN UP BUTTON
    */
    public void confirmInputs(String fname, String lname, String uname, String pword, String aid, File gFile, File aFile){
        //creates guest or admin and adds them as object to hash table
        if (aid.equals("")){
            createGuest(fname, lname, uname, pword, gFile);
            guestHT = readFromGuestFile();
            toThankYouScreen();
        }
        else {
            createAdmin(fname, lname, uname, pword, aid, aFile);
            adminHT = readFromAdminFile();
            toThankYouScreen();
        }
    }
    
    /*
    * Method to confirm Log In information
    */
    public void confirmLogIn(String uname, String pword, String aid, Hashtable<String, String> guestHT, Hashtable<String, String[]> adminHT){
        guestHT = readFromGuestFile();
        adminHT = readFromAdminFile();
        
        if (aid.equals("")){
            if (guestValidation(uname, pword, guestHT)){
                //go to next screen
                toGuestScreen();
            }
            else{
                System.out.println("Invalid guest log in information");
            }
        }
        else {
            if(adminValidation(aid, uname, pword, adminHT)){
                //switch screens to admin page view
                toAdminScreen();
            }
            else {
                System.out.println("Invalid admin log in information");
            }
        }
    }

    public static boolean guestValidation(String username, String password, Hashtable<String, String> guestHT){
        if (guestHT.containsKey(username)){
            if (guestHT.containsValue(password)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public static boolean adminValidation(String adminID, String adminUsername, String adminPassword, Hashtable<String, String[]> adminHT){
        if(adminHT.keySet().contains(adminID)){
            /*System.out.println(adminHT.get(adminID));
            System.out.println(adminID);
             */

            if(adminHT.get(adminID)[0].equals(adminUsername) && adminHT.get(adminID)[1].equals(adminPassword)){
                /*System.out.println(adminHT.get(adminID)[0] + "  ->  " + adminUsername);
                System.out.println(adminHT.get(adminID)[1] + "  ->  " + adminPassword);
                 */

                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
    
    
    //MAIN //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        launch();
 
    }
    
    @Override
    public void start(Stage primaryStage) {      
        window = primaryStage;
        window.setTitle("Wildfires");
        
        
        /*
        * LOG IN SCREEN ////////////////////////////////////////////////////////////////////////////
        */
        GridPane grid1 = new GridPane();
        //put 10 pixel padding around window
        grid1.setPadding(new Insets(10, 10, 10, 10));
        //add padding for each cell
        grid1.setVgap(8); //vertical padding
        grid1.setHgap(8); //horizontal padding
        
        //Log In Text
        Label logInText = new Label("LOG IN");
        GridPane.setConstraints(logInText, 0, 0);
        
        //username label
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 1);
        
        //username input
        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 1);
        
        //password label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 2);
        
        //password input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 2);
        
        //admin label
        Label adminLabel = new Label("Admin ID:");
        GridPane.setConstraints(adminLabel, 0, 3);
        
        //admin input
        TextField adminInput = new TextField();
        adminInput.setPromptText("admin ID");
        GridPane.setConstraints(adminInput, 1, 3);
        
        //admin info text
        Label blankText = new Label("(Leave blank if regular user)");
        GridPane.setConstraints(blankText, 1, 4);
        
        
        Button loginButton = new Button("LOG IN");
        GridPane.setConstraints(loginButton, 0, 5);
        loginButton.setOnAction( e -> confirmLogIn(nameInput.getText(), passInput.getText(), adminInput.getText(), guestHT, adminHT));
        
        Label createText = new Label("Don't have an account?\n"
                + "Create an Account");
        GridPane.setConstraints(createText, 0, 6);
        
        Button signUpButton = new Button("SIGN UP");
        GridPane.setConstraints(signUpButton, 0, 7);
        signUpButton.setOnAction( e -> toCreateUserScreen());
        
        grid1.getChildren().addAll(logInText, nameLabel, nameInput, passLabel, passInput, 
                adminLabel, adminInput, blankText, loginButton, createText, signUpButton);
        
        scene = new Scene(grid1, 350, 300);
        window.setScene(scene);
        window.show();
        
        

        /*
        * CREATE NEW USER SCREEN /////////////////////////////////////////////////////////////////
        */

        StackPane createUserLayout = new StackPane();
        
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10, 10, 10, 10)); //add padding for each cell
        grid2.setVgap(8); //vertical padding
        grid2.setHgap(8); //horizontal padding
        
        Label createUser = new Label("Create New User");
        GridPane.setConstraints(createUser, 0, 0);
        
        //first name label in topleft of grid
        Label firstNameLabel = new Label("First Name:");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        
        //first name input to the right of label
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("first name");
        GridPane.setConstraints(firstNameInput, 1, 1);
        
        //last name label
        Label lastNameLabel = new Label("Last Name:");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        
        //last name input
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("last name");
        GridPane.setConstraints(lastNameInput, 1, 2);
        
        //username label
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 3);
        
        //username input
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("username");
        GridPane.setConstraints(usernameInput, 1, 3);
        
        //password label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 4);
        
        //password input
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 4);
        
        //confirm password label
        Label confirmPasswordLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 5);
        
        //confirm password input
        TextField confirmPasswordInput = new TextField();
        confirmPasswordInput.setPromptText("password");
        GridPane.setConstraints(confirmPasswordInput, 1, 5);
        
        //admin ID label
        Label adminIDLabel = new Label("AdminID:");
        GridPane.setConstraints(adminIDLabel, 0, 6);
        
        //admin ID input
        TextField adminIDInput = new TextField();
        adminIDInput.setPromptText("adminID");
        GridPane.setConstraints(adminIDInput, 1, 6);
        
        //admin info label
        Label adminInfo = new Label("(leave admin ID blank if regular user)");
        GridPane.setConstraints(adminInfo, 1, 7);
        
        Button createAccout = new Button("CREATE ACCOUNT");
        createAccout.setOnAction( e -> confirmInputs(firstNameInput.getText(), lastNameInput.getText(), 
            usernameInput.getText(), passwordInput.getText(), adminIDInput.getText(), guestFile, adminFile));
       
        GridPane.setConstraints(createAccout, 0, 8);
        
        //button to return back to Log In screen
        Button goBackButton = new Button("GO BACK");
        GridPane.setConstraints(goBackButton, 1, 8);
        goBackButton.setOnAction( e -> returnToLogInScreen());
        
        
        grid2.getChildren().addAll(createUser, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, 
                usernameLabel, usernameInput, passwordLabel, passwordInput, 
                confirmPasswordLabel, confirmPasswordInput, adminIDLabel, 
                adminIDInput, adminInfo, createAccout, goBackButton);
        
          
        createUserLayout.getChildren().addAll(grid2);
        scene2 = new Scene(createUserLayout, 400, 300);
        
        
        /*
        * THANK YOU FOR CREATING AN ACCOUNT SCREEN /////////////////////////////////////////
        */
        
        GridPane tyGrid = new GridPane();
        tyGrid.setPadding(new Insets(10, 10, 10, 10)); //add padding for each cell
        tyGrid.setVgap(50); //vertical padding
        tyGrid.setHgap(8); //horizontal padding
        
        //Thanks for creating an accout Label
        Label thanks = new Label("Thanks for creating an account!!!");
        GridPane.setConstraints(thanks, 0, 0);
        
        Button back = new Button("RETURN TO LOG IN PAGE");
        GridPane.setConstraints(back, 0, 1);
        back.setOnAction( e -> returnToLogInScreen());
        
        tyGrid.getChildren().addAll(thanks, back);
        
        StackPane thankYouScreen = new StackPane();
        thankYouScreen.getChildren().addAll(tyGrid);
        scene3 = new Scene(thankYouScreen, 200, 200);
 
    
    
        /*
        *  GUEST VIEW SCREEN
        */
        Label welcomeToGuestScreen = new Label("Welcome to the Guest Viewing Screen!!!");
        
        StackPane guestScreen = new StackPane();
        guestScreen.getChildren().addAll(welcomeToGuestScreen);
        scene4 = new Scene(guestScreen, 300, 300);
    
        /*
        *  GUEST VIEW SCREEN
        */
        Label welcomeToAdminScreen = new Label("Welcome to the Admin Viewing Screen!!!");
        
        StackPane adminScreen = new StackPane();
        adminScreen.getChildren().addAll(welcomeToAdminScreen);
        scene5 = new Scene(adminScreen, 300, 300);
        
    
    }
    
    
    
    /*
    * Switch to Log in Screen Method
    */
    private void returnToLogInScreen(){
        window.setScene(scene);
    }
    
    
    /*
    * Switch Create User Screen Method
    */
    private void toCreateUserScreen(){
        window.setScene(scene2);
    }
    
    private void toThankYouScreen(){
        window.setScene(scene3);
    }
    
    private void toGuestScreen(){
        window.setScene(scene4);
    }
    
    private void toAdminScreen(){
        window.setScene(scene5);
    }
    
    /*
    *  Method to check if username and password is correct
    */
    private void isValid(String nameInput, String passInput){
            String adminUser = "admin";
            String adminPass = "fpuadmin";
            boolean userMatches = false;
            boolean passMatches = false;
            
            String regUser = "guest";
            String regPass = "fpuguest";
            boolean regUserMatches = false;
            boolean regPassMatches = false;
            
            userMatches = Pattern.matches(adminUser, nameInput);
            passMatches = Pattern.matches(adminPass, passInput);
            
            regUserMatches = Pattern.matches(regUser, nameInput);
            regPassMatches = Pattern.matches(regPass, passInput);
            
            //if admin user and pass are correct allow acces to screen
            if (userMatches && passMatches){
                System.out.println("access granted");
                window.setScene(scene2);
                // allow access to main screen w add and remove button
            }
            //if guest user and pass are correct allow acces to screen
            if (regUserMatches && regPassMatches) {
                System.out.println("access granted");
                window.setScene(scene3);
                // allow access to main screen
            }
            else {
                System.out.println("Invalid Input");
            }
    }
}