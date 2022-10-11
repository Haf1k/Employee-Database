
public abstract class Worker {

	private String name;
	private int ID;
	private String surname;
	public int workHours;
	public int assignedHours;
	public int administrationHours, technicDocumentationHours, developementHours;
	static int aHours = 0, tHours = 0, dHours = 0;
	boolean incapable = false;

	public Worker(int ID, String name, String surname) {
		this.ID = ID;
		this.name = name;
		this.surname = surname;
		this.workHours = 180;
		this.assignedHours = 0;
		this.administrationHours = 0;
		this.technicDocumentationHours = 0;
		this.developementHours = 0;
	}

	// getters
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getID() {
		return ID;
	}

	public boolean getIncapable() {
		return this.incapable;
	}

	public int getTarif() {
		return this.getTarif();
	}

	public int getMaxWorkHours() {
		return this.workHours;
	}

	public int getAssignedWorkHours() {
		return this.assignedHours;
	}

	public int getFreeWorkHours() {
		return (this.workHours - getAssignedWorkHours());
	}

	public int getAdministrationHours() {
		return this.administrationHours;
	}

	public int getTechnicDocumentationHours() {
		return this.technicDocumentationHours;
	}

	public int getDevelopementHours() {
		return this.developementHours;
	}

	// setters
	public void setName(String newName) {
		this.name = newName;
	}

	public void setSurname(String newSurname) {
		this.surname = newSurname;
	}

	public void setID(int newID) {
		this.ID = newID;
	}

	public void setWorkHours(int newWorkHours) {
		this.workHours = newWorkHours;
	}

	public void setAssignedWorkHours(int newAssignedWorkHours) {
		this.assignedHours = newAssignedWorkHours;
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

	public void setIncapable(boolean inc) {
		if (inc == true) {
			setWorkHours(0);
			setAdministrationHours(0);
			setTechnicDocumentationHours(0);
			setDevelopementHours(0);
			setAssignedWorkHours(0);
		}
		this.incapable = inc;
	}

}
