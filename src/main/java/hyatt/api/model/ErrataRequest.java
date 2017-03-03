package hyatt.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrataRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("start_date")
	private String startDate;
	
	@JsonProperty("end_date")
	private String endDate;
	
	@JsonProperty("selected_week")
	private SelectedWeek selectedWeek;
	
	@JsonProperty("property_ids")
	private List<Integer> propertyIds = new ArrayList<Integer>();

	/**
	 * 
	 * @return The startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * @param startDate
	 *            The start_date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return The endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate
	 *            The end_date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return The selectedWeek
	 */
	public SelectedWeek getSelectedWeek() {
		return selectedWeek;
	}

	/**
	 * 
	 * @param selectedWeek
	 *            The selected_week
	 */
	public void setSelectedWeek(SelectedWeek selectedWeek) {
		this.selectedWeek = selectedWeek;
	}

	/**
	 * 
	 * @return The propertyIds
	 */
	public List<Integer> getPropertyIds() {
		return propertyIds;
	}

	/**
	 * 
	 * @param propertyIds
	 *            The property_ids
	 */
	public void setPropertyIds(List<Integer> propertyIds) {
		this.propertyIds = propertyIds;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(startDate).append(endDate).append(selectedWeek).append(propertyIds)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ErrataRequest) == false) {
			return false;
		}
		ErrataRequest rhs = ((ErrataRequest) other);
		return new EqualsBuilder().append(startDate, rhs.startDate).append(endDate, rhs.endDate)
				.append(selectedWeek, rhs.selectedWeek).append(propertyIds, rhs.propertyIds).isEquals();
	}

}