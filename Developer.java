
public class Developer extends Worker {

	static int developers;
	static int tarif;
	static int technicDocumentationHours, developementHours;

	public Developer(int ID, String name, String surname) {
		super(ID, name, surname);
		// TODO Auto-generated constructor stub
		this.tarif = 250;
		this.technicDocumentationHours = 0;
		this.developementHours = 0;
		developers++;
	}

	public static String getPosition() {
		return ("Vývojový pracovník");
	}
	
	public int getTarif() {
		return this.tarif;
	}

	public int getAssignedWorkHours() {
		return getDevelopementHours() + getTechnicDocumentationHours();
	}
	
	public int getTechnicDocumentationHours() {
		return this.technicDocumentationHours ;
	}

	public int getDevelopementHours() {
		return this.developementHours;
	}

	public void setTechnicDocumentationHours(int hours) {
		this.technicDocumentationHours = hours;
	}

	public void setDevelopementHours(int hours) {
		this.developementHours = hours;
	}

}
