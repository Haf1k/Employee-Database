
public class Technician extends Worker {

	static int technicians;
	static int tarif;
	static int administrationHours, technicDocumentationHours;

	public Technician(int ID, String name, String surname) {
		super(ID, name, surname);
		// TODO Auto-generated constructor stub
		this.tarif = 200;
		this.administrationHours = 0;
		this.technicDocumentationHours = 0;
		technicians++;
	}

	public static String getPosition() {
		return ("Technický pracovník");
	}
	
	public int getTarif() {
		return this.tarif;
	}
	
	public int getAssignedWorkHours() {
		return getAdministrationHours() + getTechnicDocumentationHours();
	}
	
	public int getAdministrationHours() {
		return this.administrationHours;
	}
	
	public int getTechnicDocumentationHours() {
		return this.technicDocumentationHours ;
	}
	
	public void setAdministrationHours(int hours) {
		this.administrationHours = hours;
	}

	public void setTechnicDocumentationHours(int hours) {
		this.technicDocumentationHours = hours;
	}

}