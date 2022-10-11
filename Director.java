
public class Director extends Worker {

	static int directors;
	static int tarif;
	static int administrationHours, technicDocumentationHours, developementHours;

	public Director(int ID, String name, String surname) {
		super(ID, name, surname);
		// TODO Auto-generated constructor stub
		this.tarif = 350;
		this.administrationHours = 0;
		this.technicDocumentationHours = 0;
		this.developementHours = 0;
		directors++;
	}

	public static String getPosition() {
		return ("Ředitel");
	}
	
	public int getTarif() {
		return this.tarif;
	}

	public int getAssignedWorkHours() {
		return getAdministrationHours() + getTechnicDocumentationHours() + getDevelopementHours();
	}
	
	public int getAdministrationHours() {
		return this.administrationHours;
	}

	public int getTechnicDocumentationHours() {
		return this.technicDocumentationHours ;
	}

	public int getDevelopementHours() {
		return this.developementHours;
	}

	public void setAdministrationHours(int hours) {
		this.administrationHours = hours;
	}

	public void setTechnicDocumentationHours(int hours) {
		this.technicDocumentationHours = hours;
	}

	public void setDevelopementHours(int hours) {
		this.developementHours = hours;
	}

}
