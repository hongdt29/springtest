package summer.db.entity;

public class Mcategory {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mcategory.id
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	private String id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mcategory.name
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column mcategory.delete_flag
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	private Boolean deleteFlag;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mcategory.id
	 * @return  the value of mcategory.id
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mcategory.id
	 * @param id  the value for mcategory.id
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mcategory.name
	 * @return  the value of mcategory.name
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mcategory.name
	 * @param name  the value for mcategory.name
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column mcategory.delete_flag
	 * @return  the value of mcategory.delete_flag
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column mcategory.delete_flag
	 * @param deleteFlag  the value for mcategory.delete_flag
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}