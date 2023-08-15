
public abstract class APerson implements java.io.Serializable {

	private String id;
	private String pwd;
	private String name;
	private String phone;
	
	public APerson(String id, String pwd, String name, String phone) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
	}
	
	// Getters & Setters
	public String getId() {
		return id;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


	public boolean equals(String id, String pwd) {
		return this.id.equals(id) && this.pwd.equals(pwd);
	}
}
