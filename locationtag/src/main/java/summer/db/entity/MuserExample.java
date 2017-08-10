package summer.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MuserExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public MuserExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andUseridIsNull() {
			addCriterion("userid is null");
			return (Criteria) this;
		}

		public Criteria andUseridIsNotNull() {
			addCriterion("userid is not null");
			return (Criteria) this;
		}

		public Criteria andUseridEqualTo(String value) {
			addCriterion("userid =", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotEqualTo(String value) {
			addCriterion("userid <>", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThan(String value) {
			addCriterion("userid >", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThanOrEqualTo(String value) {
			addCriterion("userid >=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThan(String value) {
			addCriterion("userid <", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThanOrEqualTo(String value) {
			addCriterion("userid <=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLike(String value) {
			addCriterion("userid like", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotLike(String value) {
			addCriterion("userid not like", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridIn(List<String> values) {
			addCriterion("userid in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotIn(List<String> values) {
			addCriterion("userid not in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridBetween(String value1, String value2) {
			addCriterion("userid between", value1, value2, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotBetween(String value1, String value2) {
			addCriterion("userid not between", value1, value2, "userid");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNull() {
			addCriterion("password is null");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNotNull() {
			addCriterion("password is not null");
			return (Criteria) this;
		}

		public Criteria andPasswordEqualTo(String value) {
			addCriterion("password =", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotEqualTo(String value) {
			addCriterion("password <>", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThan(String value) {
			addCriterion("password >", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("password >=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThan(String value) {
			addCriterion("password <", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThanOrEqualTo(String value) {
			addCriterion("password <=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLike(String value) {
			addCriterion("password like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotLike(String value) {
			addCriterion("password not like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordIn(List<String> values) {
			addCriterion("password in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotIn(List<String> values) {
			addCriterion("password not in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordBetween(String value1, String value2) {
			addCriterion("password between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotBetween(String value1, String value2) {
			addCriterion("password not between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andAuthcodeIsNull() {
			addCriterion("authcode is null");
			return (Criteria) this;
		}

		public Criteria andAuthcodeIsNotNull() {
			addCriterion("authcode is not null");
			return (Criteria) this;
		}

		public Criteria andAuthcodeEqualTo(String value) {
			addCriterion("authcode =", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeNotEqualTo(String value) {
			addCriterion("authcode <>", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeGreaterThan(String value) {
			addCriterion("authcode >", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeGreaterThanOrEqualTo(String value) {
			addCriterion("authcode >=", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeLessThan(String value) {
			addCriterion("authcode <", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeLessThanOrEqualTo(String value) {
			addCriterion("authcode <=", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeLike(String value) {
			addCriterion("authcode like", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeNotLike(String value) {
			addCriterion("authcode not like", value, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeIn(List<String> values) {
			addCriterion("authcode in", values, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeNotIn(List<String> values) {
			addCriterion("authcode not in", values, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeBetween(String value1, String value2) {
			addCriterion("authcode between", value1, value2, "authcode");
			return (Criteria) this;
		}

		public Criteria andAuthcodeNotBetween(String value1, String value2) {
			addCriterion("authcode not between", value1, value2, "authcode");
			return (Criteria) this;
		}

		public Criteria andPassw1IsNull() {
			addCriterion("passW1 is null");
			return (Criteria) this;
		}

		public Criteria andPassw1IsNotNull() {
			addCriterion("passW1 is not null");
			return (Criteria) this;
		}

		public Criteria andPassw1EqualTo(String value) {
			addCriterion("passW1 =", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1NotEqualTo(String value) {
			addCriterion("passW1 <>", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1GreaterThan(String value) {
			addCriterion("passW1 >", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1GreaterThanOrEqualTo(String value) {
			addCriterion("passW1 >=", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1LessThan(String value) {
			addCriterion("passW1 <", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1LessThanOrEqualTo(String value) {
			addCriterion("passW1 <=", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1Like(String value) {
			addCriterion("passW1 like", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1NotLike(String value) {
			addCriterion("passW1 not like", value, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1In(List<String> values) {
			addCriterion("passW1 in", values, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1NotIn(List<String> values) {
			addCriterion("passW1 not in", values, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1Between(String value1, String value2) {
			addCriterion("passW1 between", value1, value2, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw1NotBetween(String value1, String value2) {
			addCriterion("passW1 not between", value1, value2, "passw1");
			return (Criteria) this;
		}

		public Criteria andPassw2IsNull() {
			addCriterion("passw2 is null");
			return (Criteria) this;
		}

		public Criteria andPassw2IsNotNull() {
			addCriterion("passw2 is not null");
			return (Criteria) this;
		}

		public Criteria andPassw2EqualTo(String value) {
			addCriterion("passw2 =", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2NotEqualTo(String value) {
			addCriterion("passw2 <>", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2GreaterThan(String value) {
			addCriterion("passw2 >", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2GreaterThanOrEqualTo(String value) {
			addCriterion("passw2 >=", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2LessThan(String value) {
			addCriterion("passw2 <", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2LessThanOrEqualTo(String value) {
			addCriterion("passw2 <=", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2Like(String value) {
			addCriterion("passw2 like", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2NotLike(String value) {
			addCriterion("passw2 not like", value, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2In(List<String> values) {
			addCriterion("passw2 in", values, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2NotIn(List<String> values) {
			addCriterion("passw2 not in", values, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2Between(String value1, String value2) {
			addCriterion("passw2 between", value1, value2, "passw2");
			return (Criteria) this;
		}

		public Criteria andPassw2NotBetween(String value1, String value2) {
			addCriterion("passw2 not between", value1, value2, "passw2");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeIsNull() {
			addCriterion("lastlogindatetime is null");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeIsNotNull() {
			addCriterion("lastlogindatetime is not null");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeEqualTo(Date value) {
			addCriterion("lastlogindatetime =", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeNotEqualTo(Date value) {
			addCriterion("lastlogindatetime <>", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeGreaterThan(Date value) {
			addCriterion("lastlogindatetime >", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeGreaterThanOrEqualTo(Date value) {
			addCriterion("lastlogindatetime >=", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeLessThan(Date value) {
			addCriterion("lastlogindatetime <", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeLessThanOrEqualTo(Date value) {
			addCriterion("lastlogindatetime <=", value, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeIn(List<Date> values) {
			addCriterion("lastlogindatetime in", values, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeNotIn(List<Date> values) {
			addCriterion("lastlogindatetime not in", values, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeBetween(Date value1, Date value2) {
			addCriterion("lastlogindatetime between", value1, value2, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andLastlogindatetimeNotBetween(Date value1, Date value2) {
			addCriterion("lastlogindatetime not between", value1, value2, "lastlogindatetime");
			return (Criteria) this;
		}

		public Criteria andRawpasswordIsNull() {
			addCriterion("rawpassword is null");
			return (Criteria) this;
		}

		public Criteria andRawpasswordIsNotNull() {
			addCriterion("rawpassword is not null");
			return (Criteria) this;
		}

		public Criteria andRawpasswordEqualTo(String value) {
			addCriterion("rawpassword =", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordNotEqualTo(String value) {
			addCriterion("rawpassword <>", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordGreaterThan(String value) {
			addCriterion("rawpassword >", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordGreaterThanOrEqualTo(String value) {
			addCriterion("rawpassword >=", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordLessThan(String value) {
			addCriterion("rawpassword <", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordLessThanOrEqualTo(String value) {
			addCriterion("rawpassword <=", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordLike(String value) {
			addCriterion("rawpassword like", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordNotLike(String value) {
			addCriterion("rawpassword not like", value, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordIn(List<String> values) {
			addCriterion("rawpassword in", values, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordNotIn(List<String> values) {
			addCriterion("rawpassword not in", values, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordBetween(String value1, String value2) {
			addCriterion("rawpassword between", value1, value2, "rawpassword");
			return (Criteria) this;
		}

		public Criteria andRawpasswordNotBetween(String value1, String value2) {
			addCriterion("rawpassword not between", value1, value2, "rawpassword");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table muser
	 * @mbg.generated  Thu Aug 10 22:03:11 JST 2017
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table muser
     *
     * @mbg.generated do_not_delete_during_merge Sun Jul 30 21:45:32 JST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}