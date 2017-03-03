package hyatt.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectedWeek implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("begin_week")
	private Integer beginWeek;
	
	@JsonProperty("begin_year")
	private Integer beginYear;
	
	@JsonProperty("end_week")
	private Integer endWeek;
	
	@JsonProperty("end_year")
	private Integer endYear;

	/**
	 * 
	 * @return The beginWeek
	 */
	public Integer getBeginWeek() {
		return beginWeek;
	}

	/**
	 * 
	 * @param beginWeek
	 *            The begin_week
	 */
	public void setBeginWeek(Integer beginWeek) {
		this.beginWeek = beginWeek;
	}

	/**
	 * 
	 * @return The beginYear
	 */
	public Integer getBeginYear() {
		return beginYear;
	}

	/**
	 * 
	 * @param beginYear
	 *            The begin_year
	 */
	public void setBeginYear(Integer beginYear) {
		this.beginYear = beginYear;
	}

	/**
	 * 
	 * @return The endWeek
	 */
	public Integer getEndWeek() {
		return endWeek;
	}

	/**
	 * 
	 * @param endWeek
	 *            The end_week
	 */
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * 
	 * @return The endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}

	/**
	 * 
	 * @param endYear
	 *            The end_year
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(beginWeek).append(beginYear).append(endWeek).append(endYear).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof SelectedWeek) == false) {
			return false;
		}
		SelectedWeek rhs = ((SelectedWeek) other);
		return new EqualsBuilder().append(beginWeek, rhs.beginWeek).append(beginYear, rhs.beginYear)
				.append(endWeek, rhs.endWeek).append(endYear, rhs.endYear).isEquals();
	}

}