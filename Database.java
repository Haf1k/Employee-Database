import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

	public ArrayList<Worker> databaseElements;

	Database() {
		databaseElements = new ArrayList<Worker>();
	}

	public boolean setWorker(int ID, String name, String surname, int position) {		// Vytvoreni noveho pracovnika podle zadanych kriterii, 
																						// pomoci promenne position volime pozadovanou pracovni pozici
		if (position == 1) {
			return databaseElements.add(new Assistant(ID, name, surname));
		} else if (position == 2) {
			return databaseElements.add(new Technician(ID, name, surname));
		} else if (position == 3) {
			return databaseElements.add(new Developer(ID, name, surname));
		} else if (position == 4) {
			return databaseElements.add(new Director(ID, name, surname));
		} else {
			return false;
		}

	}

/**	public void databaseOut() {
		for (Worker myWorker : databaseElements) {
			System.out.print("\nID: " + myWorker.getID() + " jmeno: " + myWorker.getName() + " příjmení: "
					+ myWorker.getSurname() + " Volne hodiny: " + myWorker.getFreeWorkHours() + " pracovní pozice: ");
			if (myWorker instanceof Assistant) {
				System.out.print("Asistent");
			} else if (myWorker instanceof Technician) {
				System.out.print("Technický pracovník");
			} else if (myWorker instanceof Developer) {
				System.out.print("Vývojový pracovník");
			} else if (myWorker instanceof Director) {
				System.out.print("Ředitel");
			}
			System.out.print(" Pracovni neschopnost: " + myWorker.getIncapable());

		}
	} **/

	public Worker getWorker(int ID) {												// Nalezeni konkretniho zamestnance podle ID k provedeni pripadnych
		for (Worker myWorker : databaseElements)									// zmen u zamestnance
			if (myWorker.getID() == ID)
				return myWorker;
		return null;
	}

	public boolean removeWorker(int ID) {											// Smazani zamestnance, na zaklade jeho pracovni pozice se snizi i celkovu
		Iterator<Worker> iter = databaseElements.iterator();						// pocet zamestnancu na jeho pozici
		while (iter.hasNext()) {
			Worker myWorker = iter.next();
			if (myWorker.getID() == ID) {
				if (myWorker instanceof Assistant) {
					Assistant.assistants--;
				} else if (myWorker instanceof Technician) {
					Technician.technicians--;
				} else if (myWorker instanceof Developer) {
					Developer.developers--;
				} else if (myWorker instanceof Director) {
					Director.directors--;
				}
				iter.remove();
				return true;
			}
		}
		return false;
	}

	public int getTotalExpenses() {													// Vypocet pro ziskani celkovych nakladu
		int expenses = 0;
		for (Worker myWorker : databaseElements) {
			expenses = expenses + (myWorker.getTarif() * myWorker.getAssignedWorkHours());
		}
		return expenses;
	}

	public void sortDatabase(int type) {											// Serazeni databaze podle pozadovanych parametru
		List<Worker> sortedWorkers = null;

		if (type == 1) {
			sortedWorkers = databaseElements.stream().sorted(Comparator.comparing(Worker::getID))
					.collect(Collectors.toList());
		} else {
			sortedWorkers = databaseElements.stream().sorted(Comparator.comparing(Worker::getSurname))
					.collect(Collectors.toList());
		}

		System.out.printf("%-6s %-10s %-10s %-15s %-20s %-30s %-15s %-20s %-10s", "ID:", "Jmeno:", "Prijmeni:",
				"Volne hodiny:", "Administrativa:", "Technicka dokumentace", "Vyvoj:", "Pracovni pozice:",
				"Pracovni neschopnost:");

		for (Worker myWorker : sortedWorkers) {

			System.out.printf("%1s %-6s %-10s %-10s %-20s %-20s %-25s %-15s ", "\n", myWorker.getID(),
					myWorker.getName(), myWorker.getSurname(), myWorker.getFreeWorkHours(),
					myWorker.getAdministrationHours(), myWorker.getTechnicDocumentationHours(),
					myWorker.getDevelopementHours());
			if (myWorker instanceof Assistant) {
				System.out.printf("%-25s", "Asistent");
			} else if (myWorker instanceof Technician) {
				System.out.printf("%-25s", "Technický_pracovník");
			} else if (myWorker instanceof Developer) {
				System.out.printf("%-25s", "Vývojový_pracovník");
			} else if (myWorker instanceof Director) {
				System.out.printf("%-25s", "Ředitel");
			}
			System.out.printf("%-15s", myWorker.getIncapable());

		}
	}

	public void administration(int hours) {											// Zde zacina dlouhy blok "efektivniho" rozdelovani praci podle platu zamestnancu
		int remainingHours = hours;													// bylo by vhodnejsi tento problem vyresit nejakou univerzalni funkci 
		while (remainingHours != 0) {												// jelikoz takhle je tato cast kodu neprehledna a pomerne obtizne se v ni hledaji a napravuji chyby
			int a = 0;																// Kazda prace ma svoji vlastni funkci lehce pozmenenou pro jeji konkretni zamestnance
			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Assistant) {
						if ((myWorker.getFreeWorkHours() - remainingHours >= 0)) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setAdministrationHours(myWorker.getAdministrationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setAdministrationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}
			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Technician) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setAdministrationHours(myWorker.getAdministrationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setAdministrationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());

						}
					}
				}
			}

			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Director) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setAdministrationHours(myWorker.getAdministrationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setAdministrationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}
			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt zadano kvuli nedostatku pracovniku.");
			break;
		}
	}

	public void technicDocumentation(int hours) {
		int remainingHours = hours;
		while (remainingHours != 0) {
			int a = 0;
			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Technician) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setTechnicDocumentationHours(
									myWorker.getTechnicDocumentationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setTechnicDocumentationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}
			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Developer) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setTechnicDocumentationHours(
									myWorker.getTechnicDocumentationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setTechnicDocumentationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}

			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Director) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setTechnicDocumentationHours(
									myWorker.getTechnicDocumentationHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setTechnicDocumentationHours(
									myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}
			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt zadano kvuli nedostatku pracovniku.");
			break;
		}
	}

	public void developement(int hours) {
		int remainingHours = hours;
		while (remainingHours != 0) {
			int a = 0;
			for (Worker myWorker : databaseElements) {
				if (!myWorker.getIncapable()) {
					if (myWorker instanceof Developer) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setDevelopementHours(myWorker.getDevelopementHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setDevelopementHours(myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}

			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Director) {
					if (!myWorker.getIncapable()) {
						if (myWorker.getFreeWorkHours() - remainingHours >= 0) {
							a = myWorker.getAssignedWorkHours();
							myWorker.setDevelopementHours(myWorker.getDevelopementHours() + remainingHours);
							a = myWorker.getAssignedWorkHours() - a;
							remainingHours = remainingHours - a;
						} else if (myWorker.getFreeWorkHours() != 0) {
							a = myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours();
							remainingHours = remainingHours - a;
							myWorker.setDevelopementHours(myWorker.getMaxWorkHours() - myWorker.getAssignedWorkHours());
						}
					}
				}
			}
			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt zadano kvuli nedostatku pracovniku.");
			break;
		}
	}

	public void administrationRemove(int hours) {
		int remainingHours = hours;
		while (remainingHours != 0) {
			int a = 0;
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Director) {
					if ((myWorker.getAdministrationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setAdministrationHours(myWorker.getAdministrationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getAdministrationHours();
						myWorker.setAdministrationHours(0);
					}
				}
			}
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Technician) {
					if ((myWorker.getAdministrationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setAdministrationHours(myWorker.getAdministrationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getAdministrationHours();
						myWorker.setAdministrationHours(0);
					}
				}
			}

			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Assistant) {
					if ((myWorker.getAdministrationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setAdministrationHours(myWorker.getAdministrationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getAdministrationHours();
						myWorker.setAdministrationHours(0);
					}
				}
			}
			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt odebrano jelikoz ji nikdo nevykonaval.");
			break;
		}
	}

	public void technicDocumentationRemove(int hours) {
		int remainingHours = hours;
		while (remainingHours != 0) {
			int a = 0;
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Director) {
					if ((myWorker.getTechnicDocumentationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setTechnicDocumentationHours(myWorker.getTechnicDocumentationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getTechnicDocumentationHours();
						myWorker.setTechnicDocumentationHours(0);
					}
				}
			}
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Developer) {
					if ((myWorker.getTechnicDocumentationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setTechnicDocumentationHours(myWorker.getTechnicDocumentationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getTechnicDocumentationHours();
						myWorker.setTechnicDocumentationHours(0);
					}
				}
			}

			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Technician) {
					if ((myWorker.getTechnicDocumentationHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setTechnicDocumentationHours(myWorker.getTechnicDocumentationHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getTechnicDocumentationHours();
						myWorker.setTechnicDocumentationHours(0);
					}
				}
			}
			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt odebrano jelikoz ji nikdo nevykonaval.");
			break;
		}
	}

	public void developementRemove(int hours) {
		int remainingHours = hours;
		while (remainingHours != 0) {
			int a = 0;
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Director) {
					if ((myWorker.getDevelopementHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setDevelopementHours(myWorker.getDevelopementHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getDevelopementHours();
						myWorker.setDevelopementHours(0);
					}
				}
			}
			for (Worker myWorker : databaseElements) {
				if (myWorker instanceof Developer) {
					if ((myWorker.getDevelopementHours() - remainingHours >= 0)) {
						a = myWorker.getAssignedWorkHours();
						myWorker.setDevelopementHours(myWorker.getDevelopementHours() - remainingHours);
						a = a - myWorker.getAssignedWorkHours();
						remainingHours = remainingHours - a;
					} else {
						remainingHours = remainingHours - myWorker.getDevelopementHours();
						myWorker.setDevelopementHours(0);
					}
				}
			}

			if (remainingHours != 0)
				System.out.println(remainingHours + " hodin prace nemohlo byt odebrano jelikoz ji nikdo nevykonaval.");
			break;
		}
	}

	public void loadDatabase() throws IOException {
		try (BufferedReader buffer = new BufferedReader(new FileReader("output.txt"))) {

			String s;													// Nacteni databaze z dokumentu, funkce nacte jednotlive radky dokumentu krome prvniho
			int i = 0;													// Radky se nasledne rozdeli podle mezer a nasleduje aplikovani ziskanych informaci
																		// do funkce pro vytvoreni noveho zamestnance, nasledne pomoci setteru se nastavi
			while ((s = buffer.readLine()) != null) {					// dodatecne informace vztahujici se ke konkretnimu zamestnanci

				if (i == 0)
					i = 1;
				else {
					String[] splitted = s.split("\\s+");

					if (splitted[8].equals("Asistent")) {
						setWorker(Integer.parseInt(splitted[1]), splitted[2], splitted[3], 1);
						Worker myWorker = getWorker(Integer.parseInt(splitted[1]));
						myWorker.setAdministrationHours(Integer.parseInt(splitted[5]));
						myWorker.setTechnicDocumentationHours(Integer.parseInt(splitted[6]));
						myWorker.setDevelopementHours(Integer.parseInt(splitted[7]));
						myWorker.setIncapable(Boolean.parseBoolean(splitted[9]));

					} else if (splitted[8].equals("Technický_pracovník")) {
						setWorker(Integer.parseInt(splitted[1]), splitted[2], splitted[3], 2);
						Worker myWorker = getWorker(Integer.parseInt(splitted[1]));
						myWorker.setAdministrationHours(Integer.parseInt(splitted[5]));
						myWorker.setTechnicDocumentationHours(Integer.parseInt(splitted[6]));
						myWorker.setDevelopementHours(Integer.parseInt(splitted[7]));
						myWorker.setIncapable(Boolean.parseBoolean(splitted[9]));
					} else if (splitted[8].equals("Vývojový_pracovník")) {
						setWorker(Integer.parseInt(splitted[1]), splitted[2], splitted[3], 3);
						Worker myWorker = getWorker(Integer.parseInt(splitted[1]));
						myWorker.setAdministrationHours(Integer.parseInt(splitted[5]));
						myWorker.setTechnicDocumentationHours(Integer.parseInt(splitted[6]));
						myWorker.setDevelopementHours(Integer.parseInt(splitted[7]));
						myWorker.setIncapable(Boolean.parseBoolean(splitted[9]));
					} else {
						setWorker(Integer.parseInt(splitted[1]), splitted[2], splitted[3], 4);
						Worker myWorker = getWorker(Integer.parseInt(splitted[1]));
						myWorker.setAdministrationHours(Integer.parseInt(splitted[5]));
						myWorker.setTechnicDocumentationHours(Integer.parseInt(splitted[6]));
						myWorker.setDevelopementHours(Integer.parseInt(splitted[7]));
						myWorker.setIncapable(Boolean.parseBoolean(splitted[9]));

					}

				}
			}
		}

	}

}
