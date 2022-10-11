import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static Database myDatabase = new Database();
	static boolean run = true;
	static int choice, choice1;
	static int ID;
	static String name;
	static String surname;
	static int aHours = 0, tHours = 0, dHours = 0;

	public static void main(String[] args) {

		while (run) {
			System.out.println("\n\n---Vyber moznost---"); 							// Klasicky switch pro volby podle vypsaneho menu
			System.out.println("1) Přidat nového pracovníka");
			System.out.println("2) Zjistit volne hodiny");
			System.out.println("3) Nastav praci");
			System.out.println("4) Odeber praci");
			System.out.println("5) Smazat zamestnance");
			System.out.println("6) Zadat pracovni neschopnost");
			System.out.println("7) Navrat z pracovni neschopnosti");
			System.out.println("8) Upravit pracovni uvazek zamestnanci");
			System.out.println("9) Vypsat databázi");
			System.out.println("10) Vypsat celkove naklady");
			System.out.println("11) Ulozit databazi do souboru");
			System.out.println("12) Nacti databazi ze souboru");
			System.out.println("13) Vypsat pocet pracovniku na jednotlivych pozicich");
			System.out.println("0) Ukonci databazi");
			choice = Tests.number(sc);

			switch (choice) {
			case 1:																	// Zde uzivatel specifikuje jakeho zamestnance chce pridat do databaze
				System.out.println(
						"Zadejte pracovni pozici: \n 1) Asistent \n 2) Technický pracovník \n 3) Vývojový pracovník \n 4) Ředitel");
				choice = Tests.number(sc);
				if (Director.directors > 0 && choice == 4) {
					System.out.println("Reditel jiz existuje.");
					break;
				}
				System.out.println("Zadejte ID: ");
				ID = Tests.number(sc);
				System.out.println("Zadejte jmeno: ");
				name = sc.next();
				System.out.println("Zadejte příjmení: ");
				surname = sc.next();
				if (!myDatabase.setWorker(ID, name, surname, choice)) {
					System.out.println("Pracovnik nebyl zadán.");
				}
				break;

			case 2:																	// Zkontrolovani volnych hodin u konkretniho pracovnika
				System.out.println("Zadejte ID pracovnika: ");
				choice = Tests.number(sc);
				Worker info = myDatabase.getWorker(choice);
				if (info != null)
					System.out.println("Pracovni uvazek: " + info.getFreeWorkHours());
				else
					System.out.println("Vybrany pracovnik neexistuje.");
				break;

			case 3:																	// Zadavani pracovnich hodin zamestnancum
				System.out.println(
						"Jakou praci chcete zadat? \n 1) Administrativni cinnost \n 2) Priprava technicke dokumentace \n 3) Vyvojove prace");
				choice = Tests.number(sc);
				System.out.println("kolik hodin chcete zadat? : ");
				choice1 = Tests.number(sc);
				if (choice == 1)
					myDatabase.administration(choice1);
				else if (choice == 2)
					myDatabase.technicDocumentation(choice1);
				else if (choice == 3)
					myDatabase.developement(choice1);
				else
					break;
				break;

			case 4:																	// Odebrani pracovnich hodin zamestnancum
				System.out.println(
						"Jakou praci chcete odebrat? \n 1) Administrativni cinnost \n 2) Priprava technicke dokumentace \n 3) Vyvojove prace");
				choice = Tests.number(sc);
				System.out.println("kolik hodin chcete odebrat? : ");
				choice1 = Tests.number(sc);
				if (choice == 1)
					myDatabase.administrationRemove(choice1);
				else if (choice == 2)
					myDatabase.technicDocumentationRemove(choice1);
				else if (choice == 3)
					myDatabase.developementRemove(choice1);
				else
					break;
				break;

			case 5:																	// Smazani zamestnance a prerozdeleni jeho prace
				System.out.println("Zadejte ID pracovnika ktereho chcete smazat: ");
				choice = Tests.number(sc);
				Worker info1 = myDatabase.getWorker(choice);
				if (info1 != null) {
					aHours = info1.getAdministrationHours();
					tHours = info1.getTechnicDocumentationHours();
					dHours = info1.getDevelopementHours();
				}
				if (myDatabase.removeWorker(choice)) {
					System.out.println("Pracovnik s ID " + choice + " smazan.");
					myDatabase.administration(aHours);
					myDatabase.technicDocumentation(tHours);
					myDatabase.developement(dHours);
				} else
					System.out.println("Vybrany pracovnik neexistuje.");
				break;

			case 6:																	// Zadani pracovni neschopnosti konkretnimu zamestnanci a prerozdeleni jeho prace
				System.out.println("Zadejte ID pracovnika ktery je pracovne neschopny: ");
				choice = Tests.number(sc);
				Worker info2 = myDatabase.getWorker(choice);
				if (info2 != null) {
					aHours = info2.getAdministrationHours();
					tHours = info2.getTechnicDocumentationHours();
					dHours = info2.getDevelopementHours();
					info2.setIncapable(true);
					System.out.println("Pracovnik s ID " + choice + " nastaven jako pracovne neschopny.");
					myDatabase.administration(aHours);
					myDatabase.technicDocumentation(tHours);
					myDatabase.developement(dHours);
				} else
					System.out.println("Vybrany pracovnik neexistuje.");
				break;

			case 7:																	// Navraceni z pracovni neschopnosti
				System.out.println("Zadejte ID pracovnika ktery je uzdraveny: ");
				choice = Tests.number(sc);
				Worker info3 = myDatabase.getWorker(choice);
				if (info3 != null) {
					info3.setIncapable(false);
					System.out.println("Pracovnik s ID " + choice + " nastaven jako pracovne schopny.");
					System.out.println("Zadejte zamestnancuv novy uvazek (hodiny/mesic napr: 180): ");
					choice = Tests.number(sc);
					info3.setWorkHours(choice);
				} else
					System.out.println("Vybrany pracovnik neexistuje.");
				break;

			case 8:																	// Zmena maximalniho uvazku u koknretniho zamestnance
				System.out.println("Zadejte ID pracovnika kteremu chcete zmenit uvazek: ");
				choice = Tests.number(sc);
				Worker info4 = myDatabase.getWorker(choice);
				if (info4 != null) {
					aHours = info4.getAdministrationHours();
					tHours = info4.getTechnicDocumentationHours();
					dHours = info4.getDevelopementHours();
					info4.setAdministrationHours(0);
					info4.setTechnicDocumentationHours(0);
					info4.setDevelopementHours(0);
					info4.setAssignedWorkHours(0);
					System.out.println("Zadejte kolik hodin ma mit novy uvazek. (napr 100): ");
					choice = Tests.number(sc);
					info4.setWorkHours(choice);
					myDatabase.administration(aHours);
					myDatabase.technicDocumentation(tHours);
					myDatabase.developement(dHours);
				} else
					System.out.println("Vybrany pracovnik neexistuje.");
				break;

			case 9:																	// Vypsani databaze podle zadanych kriterii
				System.out.println("Jakym zpusobem chcete databazi seradit? \n 1) Podle ID \n 2) Podle prijmeni ");
				choice = Tests.number(sc);
				if (choice != 1 && choice != 2)
					break;
				myDatabase.sortDatabase(choice);
				break;

			case 10:																// Vypsani celkovych nakladu na zamestnance za jejich hodinovou praci
				System.out.println("Celkove naklady jsou: " + myDatabase.getTotalExpenses() + "Kc");
				break;

			case 11:																// Vypis databaze do souboru
				try {
					PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
					PrintStream console = System.out;
					System.setOut(out);
					myDatabase.sortDatabase(1);
					out.close();
					System.setOut(console);
				} catch (IOException ex) {
					System.out.println("Databazi se nepodarilo nacist\n");
				}
				break;

			case 12:																// Nacteni databaze ze souboru
				try {
					myDatabase.loadDatabase();

				} catch (IOException ex) {
					System.out.println("Databazi se nepodarilo nacist\n");
				}
				break;

			case 13:																// Vypsani pracovniku na jednotlivych pozicich
				System.out.println("Pocet pracovniku na jednotlivych pozicich");
				System.out.println(
						"Asistenti: " + Assistant.assistants + "\nTechnicti pracovnici: " + Technician.technicians
								+ "\nVyvojovi pracovnici: " + Developer.developers + "\nReditel: " + Director.directors);
				break;

			case 0:																	// Konec
				System.out.println("Ukonceni databaze\n");
				run = false;
				break;
			}

		}
	}
}