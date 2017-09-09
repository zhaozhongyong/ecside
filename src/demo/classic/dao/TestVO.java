package demo.classic.dao;

public class TestVO {
	private String RN;
	private String CUSTOMER_ID;
	private String FIRST_NAME;
	private String CITY_CODE;
	private String IDENTITY_CODE;
	private String CREATE_DATE;
	
	public String getCITY_CODE() {
		return CITY_CODE;
	}
	public void setCITY_CODE(String city_code) {
		CITY_CODE = city_code;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String create_date) {
		CREATE_DATE = create_date;
	}
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String customer_id) {
		CUSTOMER_ID = customer_id;
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String first_name) {
		FIRST_NAME = first_name;
	}
	public String getIDENTITY_CODE() {
		return IDENTITY_CODE;
	}
	public void setIDENTITY_CODE(String identity_code) {
		IDENTITY_CODE = identity_code;
	}
	public String getRN() {
		return RN;
	}
	public void setRN(String rn) {
		RN = rn;
	}
}
