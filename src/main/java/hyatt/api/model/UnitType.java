package hyatt.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"ada_options",
"ada_unit",
"available_floor_numbers",
"check_in_check_out_options",
"full_week_check_in_day",
"max_occupancy",
"room_amenities",
"room_type",
"view_type_preference_options",
"available_unit_numbers"
})
public class UnitType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("ada_options")
	private List<String> adaOptions;
	
	@JsonProperty("ada_unit")
	private Boolean adaUnit;
	
	@JsonProperty("available_floor_numbers")
	private List<Object> availableFloorNumbers = new ArrayList<Object>();
	
	@JsonProperty("available_unit_numbers")
	private List<AvailableUnitNumber> availableUnitNumbers = new ArrayList<AvailableUnitNumber>();
	
	@JsonProperty("check_in_check_out_options")
	private List<String> checkInCheckOutOptions = new ArrayList<String>();
	
	@JsonProperty("full_week_check_in_day")
	private List<String> fullWeekCheckInDay = new ArrayList<String>();
	
	@JsonProperty("max_occupancy")
	private Integer maxOccupancy;
	
	@JsonProperty("room_amenities")
	private List<String> roomAmenities = new ArrayList<String>();
	
	@JsonProperty("room_type")
	private String roomType;
	
	@JsonProperty("view_type_preference_options")
	private List<String> viewTypePreferenceOptions = new ArrayList<String>();

	/**
	 * 
	 * @return The adaOptions
	 */
	public List<String> getAdaOptions() {
		return adaOptions;
	}

	/**
	 * 
	 * @param adaOptions
	 *            The ada_options
	 */
	public void setAdaOptions(List<String> adaOptions) {
		this.adaOptions = adaOptions;
	}

	/**
	 * 
	 * @return The adaUnit
	 */
	public Boolean getAdaUnit() {
		return adaUnit;
	}

	/**
	 * 
	 * @param adaUnit
	 *            The ada_unit
	 */
	public void setAdaUnit(Boolean adaUnit) {
		this.adaUnit = adaUnit;
	}

	/**
	 * 
	 * @return The availableFloorNumbers
	 */
	public List<Object> getAvailableFloorNumbers() {
		return availableFloorNumbers;
	}

	/**
	 * 
	 * @param availableFloorNumbers
	 *            The available_floor_numbers
	 */
	public void setAvailableFloorNumbers(List<Object> availableFloorNumbers) {
		this.availableFloorNumbers = availableFloorNumbers;
	}

	/**
	 * 
	 * @return The availableUnitNumbers
	 */
	public List<AvailableUnitNumber> getAvailableUnitNumbers() {
		return availableUnitNumbers;
	}

	/**
	 * 
	 * @param availableUnitNumbers
	 *            The available_unit_numbers
	 */
	public void setAvailableUnitNumbers(List<AvailableUnitNumber> availableUnitNumbers) {
		this.availableUnitNumbers = availableUnitNumbers;
	}

	/**
	 * 
	 * @return The checkInCheckOutOptions
	 */
	public List<String> getCheckInCheckOutOptions() {
		return checkInCheckOutOptions;
	}

	/**
	 * 
	 * @param checkInCheckOutOptions
	 *            The check_in_check_out_options
	 */
	public void setCheckInCheckOutOptions(List<String> checkInCheckOutOptions) {
		this.checkInCheckOutOptions = checkInCheckOutOptions;
	}

	/**
	 * 
	 * @return The fullWeekCheckInDay
	 */
	public List<String> getFullWeekCheckInDay() {
		return fullWeekCheckInDay;
	}

	/**
	 * 
	 * @param fullWeekCheckInDay
	 *            The full_week_check_in_day
	 */
	public void setFullWeekCheckInDay(List<String> fullWeekCheckInDay) {
		this.fullWeekCheckInDay = fullWeekCheckInDay;
	}

	/**
	 * 
	 * @return The maxOccupancy
	 */
	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}

	/**
	 * 
	 * @param maxOccupancy
	 *            The max_occupancy
	 */
	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	/**
	 * 
	 * @return The roomAmenities
	 */
	public List<String> getRoomAmenities() {
		return roomAmenities;
	}

	/**
	 * 
	 * @param roomAmenities
	 *            The room_amenities
	 */
	public void setRoomAmenities(List<String> roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	/**
	 * 
	 * @return The roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * 
	 * @param roomType
	 *            The room_type
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * 
	 * @return The viewTypePreferenceOptions
	 */
	public List<String> getViewTypePreferenceOptions() {
		return viewTypePreferenceOptions;
	}

	/**
	 * 
	 * @param viewTypePreferenceOptions
	 *            The view_type_preference_options
	 */
	public void setViewTypePreferenceOptions(List<String> viewTypePreferenceOptions) {
		this.viewTypePreferenceOptions = viewTypePreferenceOptions;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(adaOptions).append(adaUnit).append(availableFloorNumbers).append(availableUnitNumbers)
				.append(checkInCheckOutOptions).append(fullWeekCheckInDay).append(maxOccupancy).append(roomAmenities).append(roomType)
				.append(viewTypePreferenceOptions).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof UnitType) == false) {
			return false;
		}
		UnitType rhs = ((UnitType) other);
		return new EqualsBuilder().append(adaOptions, rhs.adaOptions).append(adaUnit, rhs.adaUnit)
				.append(availableFloorNumbers, rhs.availableFloorNumbers).append(availableUnitNumbers, rhs.availableUnitNumbers)
				.append(checkInCheckOutOptions, rhs.checkInCheckOutOptions).append(fullWeekCheckInDay, rhs.fullWeekCheckInDay)
				.append(maxOccupancy, rhs.maxOccupancy).append(roomAmenities, rhs.roomAmenities).append(roomType, rhs.roomType)
				.append(viewTypePreferenceOptions, rhs.viewTypePreferenceOptions).isEquals();
	}
}
