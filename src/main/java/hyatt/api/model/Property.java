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
"bonus_week_available",
"city",
"country",
"payment_types_accepted",
"phone_number",
"postal_code",
"id",
"name",
"region_code",
"region_name",
"season_names",
"state",
"street_address",
"view_type",
"unit_types",
"vacation_types",
"reservation_type_policies",
"images",
"why_visit",
"amenities"
})
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("bonus_week_available")
	private Boolean bonusWeekAvailable;

	@JsonProperty("city")
	private String city;

	@JsonProperty("country")
	private String country;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("payment_types_accepted")
	private List<String> paymentTypesAccepted = new ArrayList<String>();

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("postal_code")
	private String postalCode;

	@JsonProperty("region_code")
	private String regionCode;

	@JsonProperty("region_name")
	private String regionName;

	@JsonProperty("reservation_type_policies")
	private List<ReservationTypePolicy> reservationTypePolicies = new ArrayList<ReservationTypePolicy>();

	@JsonProperty("season_names")
	private List<String> seasonNames = new ArrayList<String>();

	@JsonProperty("state")
	private String state;

	@JsonProperty("street_address")
	private String streetAddress;

	@JsonProperty("unit_types")
	private List<UnitType> unitTypes = new ArrayList<UnitType>();

	@JsonProperty("vacation_types")
	private List<String> vacationTypes = new ArrayList<String>();

	@JsonProperty("view_type")
	private List<String> viewType = new ArrayList<String>();

	@JsonProperty("images")
	private List<String> images = new ArrayList<String>();

	@JsonProperty("why_visit")
	private String whyVisit;

	@JsonProperty("amenities")
	private List<Amenity> amenities = new ArrayList<Amenity>();

	/**
	 * 
	 * @return The bonusWeekAvailable
	 */
	public Boolean getBonusWeekAvailable() {
		return bonusWeekAvailable;
	}

	/**
	 * 
	 * @param bonusWeekAvailable
	 *            The bonus_week_available
	 */
	public void setBonusWeekAvailable(Boolean bonusWeekAvailable) {
		this.bonusWeekAvailable = bonusWeekAvailable;
	}

	/**
	 * 
	 * @return The city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 *            The city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @return The country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @param country
	 *            The country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The paymentTypesAccepted
	 */
	public List<String> getPaymentTypesAccepted() {
		return paymentTypesAccepted;
	}

	/**
	 * 
	 * @param paymentTypesAccepted
	 *            The payment_types_accepted
	 */
	public void setPaymentTypesAccepted(List<String> paymentTypesAccepted) {
		this.paymentTypesAccepted = paymentTypesAccepted;
	}

	/**
	 * 
	 * @return The phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 * @param phoneNumber
	 *            The phone_number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 
	 * @return The postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * 
	 * @param postalCode
	 *            The postal_code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * 
	 * @return The regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 
	 * @param regionCode
	 *            The region_code
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 
	 * @return The reservationTypePolicies
	 */
	public List<ReservationTypePolicy> getReservationTypePolicies() {
		return reservationTypePolicies;
	}

	/**
	 * 
	 * @param reservationTypePolicies
	 *            The reservation_type_policies
	 */
	public void setReservationTypePolicies(List<ReservationTypePolicy> reservationTypePolicies) {
		this.reservationTypePolicies = reservationTypePolicies;
	}

	/**
	 * 
	 * @return The seasonNames
	 */
	public List<String> getSeasonNames() {
		return seasonNames;
	}

	/**
	 * 
	 * @param seasonNames
	 *            The season_names
	 */
	public void setSeasonNames(List<String> seasonNames) {
		this.seasonNames = seasonNames;
	}

	/**
	 * 
	 * @return The state
	 */
	public String getState() {
		return state;
	}

	/**
	 * 
	 * @param state
	 *            The state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress
	 *            the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * 
	 * @return The unitTypes
	 */
	public List<UnitType> getUnitTypes() {
		return unitTypes;
	}

	/**
	 * 
	 * @param unitTypes
	 *            The unit_types
	 */
	public void setUnitTypes(List<UnitType> unitTypes) {
		this.unitTypes = unitTypes;
	}

	/**
	 * 
	 * @return The vacationTypes
	 */
	public List<String> getVacationTypes() {
		return vacationTypes;
	}

	/**
	 * 
	 * @param vacationTypes
	 *            The vacation_types
	 */
	public void setVacationTypes(List<String> vacationTypes) {
		this.vacationTypes = vacationTypes;
	}

	/**
	 * 
	 * @return The viewType
	 */
	public List<String> getViewType() {
		return viewType;
	}

	/**
	 * 
	 * @param viewType
	 *            The view_type
	 */
	public void setViewType(List<String> viewType) {
		this.viewType = viewType;
	}

	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}

	/**
	 * @param images
	 *            the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}

	/**
	 * @return the whyVisit
	 */
	public String getWhyVisit() {
		return whyVisit;
	}

	/**
	 * @param whyVisit
	 *            the whyVisit to set
	 */
	public void setWhyVisit(String whyVisit) {
		this.whyVisit = whyVisit;
	}

	/**
	 * @return the amenities
	 */
	public List<Amenity> getAmenities() {
		return amenities;
	}

	/**
	 * @param amenities
	 *            the amenities to set
	 */
	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(bonusWeekAvailable).append(city).append(country).append(id).append(name)
				.append(paymentTypesAccepted).append(phoneNumber).append(postalCode).append(regionCode).append(regionName)
				.append(reservationTypePolicies).append(seasonNames).append(state).append(streetAddress).append(unitTypes)
				.append(vacationTypes).append(viewType).append(images).append(whyVisit).append(amenities).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Property) == false) {
			return false;
		}
		Property rhs = ((Property) other);
		return new EqualsBuilder().append(bonusWeekAvailable, rhs.bonusWeekAvailable).append(city, rhs.city).append(country, rhs.country)
				.append(id, rhs.id).append(name, rhs.name).append(paymentTypesAccepted, rhs.paymentTypesAccepted)
				.append(phoneNumber, rhs.phoneNumber).append(postalCode, rhs.postalCode).append(regionCode, rhs.regionCode)
				.append(regionName, rhs.regionName).append(reservationTypePolicies, rhs.reservationTypePolicies)
				.append(seasonNames, rhs.seasonNames).append(state, rhs.state).append(streetAddress, rhs.streetAddress)
				.append(unitTypes, rhs.unitTypes).append(vacationTypes, rhs.vacationTypes).append(viewType, rhs.viewType)
				.append(images, rhs.images).append(whyVisit, rhs.whyVisit).append(amenities, rhs.amenities).isEquals();
	}
}
