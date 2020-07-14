package com.techelevator.model;

import com.techelevator.model.models.Medication;
import com.techelevator.model.models.AuthenticatedUser;
import com.techelevator.model.models.UserCredentials;
import com.techelevator.model.services.AuthenticationService;
import com.techelevator.model.services.AuthenticationServiceException;
import com.techelevator.model.services.MedicationService;
import com.techelevator.model.services.UserService;
import com.techelevator.model.view.ConsoleService;

public class App {

	// API Server base URL
	private static final String API_BASE_URL = "http://localhost:8080/";
 
	// Constants used for menu option passed to consoleService
	private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_LIST_MEDS = "View your list of medications";
	private static final String MAIN_MENU_OPTION_ADD_MED = "Add a medication";
	private static final String MAIN_MENU_OPTION_TAKE_DOSE = "Take a dose";
	private static final String MAIN_MENU_OPTION_VIEW_DOSES = "View past medication doses you've taken";
	private static final String MAIN_MENU_OPTION_ADD_CAREGIVER = "Add a caregiver";
	private static final String MAIN_MENU_OPTION_VIEW_DETAILS_SPECIFIC_MED = "Get Details on specific medication";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_LIST_MEDS, 
														MAIN_MENU_OPTION_ADD_MED, 
														MAIN_MENU_OPTION_TAKE_DOSE,
														MAIN_MENU_OPTION_VIEW_DOSES,
														MAIN_MENU_OPTION_ADD_CAREGIVER, 
														MAIN_MENU_OPTION_VIEW_DETAILS_SPECIFIC_MED, 
														MAIN_MENU_OPTION_LOGIN, 
														MENU_OPTION_EXIT };

	//  Object to hold instance of the current user
	private AuthenticatedUser currentUser;       // Value assigned in login() method
	   
	// instance variables for services used in the App - assigned values in App contructor
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private MedicationService medicationService;
    private UserService userService;
    
    // will need to add careGiverService

    
  // Application program - main()
  //
  //     1. Instantiate the App
  //     2. Invoke the run() for the App to start processing
    
    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new MedicationService(API_BASE_URL), new UserService(API_BASE_URL));
    	app.run();
    }

   // App constructor with services dependency injected by Spring
   // All services should be listed as parameters and assigned to instance variable for the App
    
    public App(ConsoleService console, AuthenticationService authenticationService, MedicationService medicationService, UserService userService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.medicationService = medicationService;
		this.userService = userService;
	}

	public void run() {   // Main program processing and looping
		
		startProgram();        // Perform program startup processing
		registerAndLogin();    // Login or register user
		mainMenu();            // display main menu and loop
		exitProgram();         // Perform end of program processing
	}

	private void mainMenu() {
		boolean shouldLoop = true;   // Loop control variable
		while(shouldLoop) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			switch(choice) {
				case MAIN_MENU_OPTION_LOGIN: {
					login();
					break;
				} 
				case MAIN_MENU_OPTION_VIEW_LIST_MEDS: {
					viewListOfMeds();
					break;
				} 
				case MAIN_MENU_OPTION_ADD_MED: {
					addMedication();
					break;
				} 
				case MAIN_MENU_OPTION_TAKE_DOSE: {
					takeDose();
					break;
				} 
				case MAIN_MENU_OPTION_VIEW_DOSES: {
					processOtherMenuOption();
					break;
				}
				case MAIN_MENU_OPTION_ADD_CAREGIVER: {
					processOtherMenuOption();
					break;
				} 
				case MAIN_MENU_OPTION_VIEW_DETAILS_SPECIFIC_MED: {
					processOtherMenuOption();
					break;
				}
				
				default:  {              // the only other option on the main menu is to exit
					shouldLoop = false;  // set loop to exit
					break;
			}
		}
	}
		}
	
	private void viewListOfMeds() {
		Medication [] allMeds = null;		
		allMeds = medicationService.getAllMedications(currentUser.getToken());
		
		// if the length of the array > 0, foreach loop to extract/print each element
		if(allMeds.length > 0) {
			System.out.println("Medication List");
			for(Medication med: allMeds) {
				System.out.println("Name: " + med.getMedicationName() + "ID: " + med.getMedicationId());
			} 
		} else {
			System.out.println("No medications to display");
		}
	}
	
	
	private void addMedication() {
		
		// need to create a medication object from user input
		int userId = currentUser.getUser().getId();
		
		String medNameInput = console.getUserInput("Please enter the name of the medication: ");		
		String medTypeInput = console.getUserInput("Please enter the type of medication (Pill, Liquid, Injection");		
		int medFrequencyTakenInput = console.getUserInputInteger("Please enter how many times a day you take the medication");	
		int dosageSizeInput = console.getUserInputInteger("Please enter the dosage size each time you take your medication: (Example, 1 or 2 pills each dose)");		
		int pillCountInput = console.getUserInputInteger("Please enter how many doses were included in your prescription");
		
				
		// create medication object based on user input
		Medication medication = new Medication(medNameInput, medTypeInput, medFrequencyTakenInput, dosageSizeInput, pillCountInput, userId);
		
		medicationService.addMedication(medication, currentUser.getToken());
	}
	
	private void takeDose() {
		medicationService.getAllMedications(currentUser.getToken()); // display list of medications
		
		int medIdInput = console.getUserInputInteger("Please enter the ID of which medication you need to take: ");
		
		medicationService.takeDose(medIdInput, currentUser.getToken());
	}
	
	private void viewPastDoses() {
		
	}

	private void addCaregiver() {
		
	}
	
	private void viewMed() {
		
	}
	
	
	
	
	
	private void processOtherMenuOption() {      // create a method to handle each menu ite
		// TODO add code to process a menu item
		
	}


	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			switch(choice) {
				case LOGIN_MENU_OPTION_LOGIN: {
					login();
					break;
				} 
				case LOGIN_MENU_OPTION_REGISTER: {
					register();
					break;
				} 
				default: {  // the only other option on the login menu is to exi
					exitProgram();
					break;
				}
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	
	private void startProgram() { 
		System.out.println("************************************");
		System.out.println("* Welcome to Careminder *");
		System.out.println("************************************");
	}
	
	
	private void exitProgram() {

		System.out.println("*************************************************");
		System.out.println("* Thanks for using Careminder! *");
		System.out.println("*************************************************");
		
		System.exit(0);  // Terminate programs with return code 0
	}
}
