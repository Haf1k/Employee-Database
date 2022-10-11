
public class Assistant extends Worker {

	public static int assistants;
	public int tarif;
	public int administrationHours;

	public Assistant(int ID, String name, String surname) {
		super(ID, name, surname);
		// TODO Auto-generated constructor stub
		this.tarif = 150;
		this.administrationHours = 0;
		assistants++;
	}

	public static String getPosition() {
		return ("Asistent");
	}

	public int getAssignedWorkHours() {
		return getAdministrationHours();
	}
	
	public int getAdministrationHours() {
		return this.administrationHours;
	}
	
	public int getTarif() {
		return this.tarif;
	}

	public void setAdministrationHours(int hours) {
		this.administrationHours = hours;
	}

}
