package hyatt.api.config;

/**
 * This class is used to hold all the constants and properties from
 * application.properties.
 * 
 * @author smartosc
 */
public class AppInitializers {
	public static final String NODE_QUERY_PROPERTIES = "properties";
	public static final String NODE_QUERY_CHILDREN = "children";
	public static final String NODE_QUERY_ID = "id";
	public static final String RCC_AUTH_TOKEN = "token";
	public static final String AUTH_USERNAME = "username";
	public static final String AUTH_PASSWORD = "password";
	public static final String AUTH_HEADER = "Authorization";
	public static final String AUTH_METHOD_BASIC = "Basic ";
	public static final String AUTH_METHOD_OAUTH2 = "Bearer ";
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_LANG = "en";
	public static final String ERROR_CODE = "error_code";
	public static final String MESSAGE = "message";

	private String[] languageCode;
	private String cacheControl;
	private String rccPropertyEndpointUrl;
	private String rccErrataEndpointUrl;
	private String rccAppKeyValuesEndpointUrl;
	private String rccAuthenEndpointUrl;
	private String rccUsername;
	private String rccPassword;
	private String cmsBaseUrl;
	private String cmsBaseExternalUrl;
	private String cmsBaseContextUrl;
	private String cmsBaseNode;
	private String cmsBaseQuery;
	private String cmsBaseWorkspace;
	private String cmsBasePathIncludeFullChildren;
	private String cmsUsername;
	private String cmsPassword;
	private String cmsBasePathResolveReferences;
	private String cmsBasePathFileContext;
	private String propertyNode;
	private String bonusWeekAvailable;
	private String city;
	private String country;
	private String id;
	private String name;
	private String paymentTypesAccepted;
	private String reservationType;
	private String numberOfNightsAllowed;
	private String phoneNumber;
	private String postalCode;
	private String regionCode;
	private String regionName;
	private String reservationTypePolicies;
	private String seasonNames;
	private String state;
	private String streetAddress;
	private String unitTypes;
	private String adaOptions;
	private String adaUnit;
	private String availableFloorNumbers;
	private String availableUnitNumbers;
	private String availableUnitAdaOption;
	private String availableUnitAdaUnit;
	private String availableUnitPropertyId;
	private String availableUnitType;
	private String availableUnitUnitNumber;
	private String checkInCheckOutOptions;
	private String fullWeekCheckInDay;
	private String maxOccupancy;
	private String roomAmenities;
	private String roomType;
	private String viewTypePreferenceOptions;
	private String vacationTypes;
	private String viewType;

	private String errataNode;
	private String errataId;
	private String message;
	private String propertyId;
	private String startDate;
	private String endDate;

	private String newsNode;
	private String dealNode;
	private String eventNode;
	private String errorNode;
	private String errorCode;
	private String errorMessage;

	private String promotedpropertyImages;
	private String promotedpropertyAmenities;
	private String promotedpropertyAmenitiesName;
	private String promotedpropertyAmenitiesLogo;
	private String promotedpropertyWhyVisit;

	private String staticContentjntPage;
	private String staticContentjntArea;
	private String staticContentjntContentList;
	private String staticContentjntImageReferenceLink;
	private String staticContentjntBanner;
	private String staticContentjntFileList;
	private String staticContenthyattSimpleText;
	private String staticContenthyattRichText;
	private String staticContenthyattSampleImage;
	private String staticContenthyattSimpleImage;
	private String staticContentjntText;
	private String staticContentHeaderNavigator;
	private String staticContentFooterNavigator;
	private String staticContentjntBigText;
	private String staticContenthyattDate;
	private String staticContenthyattCheckBox;

	private String cmsBaseRender;
	private String cmsBaseSite;
	private String cmsBasePage;

	/**
	 * @return the languageCode
	 */
	public String[] getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String[] languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the rccPropertyEndpointUrl
	 */
	public String getRccPropertyEndpointUrl() {
		return rccPropertyEndpointUrl;
	}

	/**
	 * @param rccPropertyEndpointUrl
	 *            the rccPropertyEndpointUrl to set
	 */
	public void setRccPropertyEndpointUrl(String rccPropertyEndpointUrl) {
		this.rccPropertyEndpointUrl = rccPropertyEndpointUrl;
	}

	/**
	 * @return the rccErrataEndpointUrl
	 */
	public String getRccErrataEndpointUrl() {
		return rccErrataEndpointUrl;
	}

	/**
	 * @param rccErrataEndpointUrl
	 *            the rccErrataEndpointUrl to set
	 */
	public void setRccErrataEndpointUrl(String rccErrataEndpointUrl) {
		this.rccErrataEndpointUrl = rccErrataEndpointUrl;
	}

	/**
	 * @return the rccAppKeyValuesEndpointUrl
	 */
	public String getRccAppKeyValuesEndpointUrl() {
		return rccAppKeyValuesEndpointUrl;
	}

	/**
	 * @param rccAppKeyValuesEndpointUrl the rccAppKeyValuesEndpointUrl to set
	 */
	public void setRccAppKeyValuesEndpointUrl(String rccAppKeyValuesEndpointUrl) {
		this.rccAppKeyValuesEndpointUrl = rccAppKeyValuesEndpointUrl;
	}

	/**
	 * @return the rccAuthenEndpointUrl
	 */
	public String getRccAuthenEndpointUrl() {
		return rccAuthenEndpointUrl;
	}

	/**
	 * @param rccAuthenEndpointUrl
	 *            the rccAuthenEndpointUrl to set
	 */
	public void setRccAuthenEndpointUrl(String rccAuthenEndpointUrl) {
		this.rccAuthenEndpointUrl = rccAuthenEndpointUrl;
	}

	/**
	 * @return the rccUsername
	 */
	public String getRccUsername() {
		return rccUsername;
	}

	/**
	 * @param rccUsername
	 *            the rccUsername to set
	 */
	public void setRccUsername(String rccUsername) {
		this.rccUsername = rccUsername;
	}

	/**
	 * @return the rccPassword
	 */
	public String getRccPassword() {
		return rccPassword;
	}

	/**
	 * @param rccPassword
	 *            the rccPassword to set
	 */
	public void setRccPassword(String rccPassword) {
		this.rccPassword = rccPassword;
	}

	/**
	 * @return the cmsBaseUrl
	 */
	public String getCmsBaseUrl() {
		return cmsBaseUrl;
	}

	/**
	 * @param cmsBaseUrl
	 *            the cmsBaseUrl to set
	 */
	public void setCmsBaseUrl(String cmsBaseUrl) {
		this.cmsBaseUrl = cmsBaseUrl;
	}

	/**
	 * @return the cmsBaseExternalUrl
	 */
	public String getCmsBaseExternalUrl() {
		return cmsBaseExternalUrl;
	}

	/**
	 * @param cmsBaseExternalUrl the cmsBaseExternalUrl to set
	 */
	public void setCmsBaseExternalUrl(String cmsBaseExternalUrl) {
		this.cmsBaseExternalUrl = cmsBaseExternalUrl;
	}

	/**
	 * @return the cmsBaseContextUrl
	 */
	public String getCmsBaseContextUrl() {
		return cmsBaseContextUrl;
	}

	/**
	 * @param cmsBaseContextUrl
	 *            the cmsBaseContextUrl to set
	 */
	public void setCmsBaseContextUrl(String cmsBaseContextUrl) {
		this.cmsBaseContextUrl = cmsBaseContextUrl;
	}

	/**
	 * @return the cmsBaseNode
	 */
	public String getCmsBaseNode() {
		return cmsBaseNode;
	}

	/**
	 * @param cmsBaseNode
	 *            the cmsBaseNode to set
	 */
	public void setCmsBaseNode(String cmsBaseNode) {
		this.cmsBaseNode = cmsBaseNode;
	}

	/**
	 * @return the cmsBaseQuery
	 */
	public String getCmsBaseQuery() {
		return cmsBaseQuery;
	}

	/**
	 * @param cmsBaseQuery
	 *            the cmsBaseQuery to set
	 */
	public void setCmsBaseQuery(String cmsBaseQuery) {
		this.cmsBaseQuery = cmsBaseQuery;
	}

	/**
	 * @return the cmsBaseWorkspace
	 */
	public String getCmsBaseWorkspace() {
		return cmsBaseWorkspace;
	}

	/**
	 * @param cmsBaseWorkspace
	 *            the cmsBaseWorkspace to set
	 */
	public void setCmsBaseWorkspace(String cmsBaseWorkspace) {
		this.cmsBaseWorkspace = cmsBaseWorkspace;
	}

	/**
	 * @return the cmsBasePathIncludeFullChildren
	 */
	public String getCmsBasePathIncludeFullChildren() {
		return cmsBasePathIncludeFullChildren;
	}

	/**
	 * @param cmsBasePathIncludeFullChildren
	 *            the cmsBasePathIncludeFullChildren to set
	 */
	public void setCmsBasePathIncludeFullChildren(String cmsBasePathIncludeFullChildren) {
		this.cmsBasePathIncludeFullChildren = cmsBasePathIncludeFullChildren;
	}

	/**
	 * @return the cmsUsername
	 */
	public String getCmsUsername() {
		return cmsUsername;
	}

	/**
	 * @param cmsUsername
	 *            the cmsUsername to set
	 */
	public void setCmsUsername(String cmsUsername) {
		this.cmsUsername = cmsUsername;
	}

	/**
	 * @return the cmsPassword
	 */
	public String getCmsPassword() {
		return cmsPassword;
	}

	/**
	 * @param cmsPassword
	 *            the cmsPassword to set
	 */
	public void setCmsPassword(String cmsPassword) {
		this.cmsPassword = cmsPassword;
	}

	/**
	 * @return the cmsBasePathResolveReferences
	 */
	public String getCmsBasePathResolveReferences() {
		return cmsBasePathResolveReferences;
	}

	/**
	 * @param cmsBasePathResolveReferences
	 *            the cmsBasePathResolveReferences to set
	 */
	public void setCmsBasePathResolveReferences(String cmsBasePathResolveReferences) {
		this.cmsBasePathResolveReferences = cmsBasePathResolveReferences;
	}

	/**
	 * @return the cmsBasePathFileContext
	 */
	public String getCmsBasePathFileContext() {
		return cmsBasePathFileContext;
	}

	/**
	 * @param cmsBasePathFileContext
	 *            the cmsBasePathFileContext to set
	 */
	public void setCmsBasePathFileContext(String cmsBasePathFileContext) {
		this.cmsBasePathFileContext = cmsBasePathFileContext;
	}

	/**
	 * @return the cacheControl
	 */
	public String getCacheControl() {
		return cacheControl;
	}

	/**
	 * @param cacheControl
	 *            the cacheControl to set
	 */
	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	/**
	 * @return the propertyNode
	 */
	public String getPropertyNode() {
		return propertyNode;
	}

	/**
	 * @param propertyNode
	 *            the propertyNode to set
	 */
	public void setPropertyNode(String propertyNode) {
		this.propertyNode = propertyNode;
	}

	/**
	 * @return the bonusWeekAvailable
	 */
	public String getBonusWeekAvailable() {
		return bonusWeekAvailable;
	}

	/**
	 * @param bonusWeekAvailable
	 *            the bonusWeekAvailable to set
	 */
	public void setBonusWeekAvailable(String bonusWeekAvailable) {
		this.bonusWeekAvailable = bonusWeekAvailable;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the paymentTypesAccepted
	 */
	public String getPaymentTypesAccepted() {
		return paymentTypesAccepted;
	}

	/**
	 * @param paymentTypesAccepted
	 *            the paymentTypesAccepted to set
	 */
	public void setPaymentTypesAccepted(String paymentTypesAccepted) {
		this.paymentTypesAccepted = paymentTypesAccepted;
	}

	/**
	 * @return the reservationType
	 */
	public String getReservationType() {
		return reservationType;
	}

	/**
	 * @param reservationType
	 *            the reservationType to set
	 */
	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	/**
	 * @return the numberOfNightsAllowed
	 */
	public String getNumberOfNightsAllowed() {
		return numberOfNightsAllowed;
	}

	/**
	 * @param numberOfNightsAllowed
	 *            the numberOfNightsAllowed to set
	 */
	public void setNumberOfNightsAllowed(String numberOfNightsAllowed) {
		this.numberOfNightsAllowed = numberOfNightsAllowed;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
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
	 * @return the reservationTypePolicies
	 */
	public String getReservationTypePolicies() {
		return reservationTypePolicies;
	}

	/**
	 * @param reservationTypePolicies
	 *            the reservationTypePolicies to set
	 */
	public void setReservationTypePolicies(String reservationTypePolicies) {
		this.reservationTypePolicies = reservationTypePolicies;
	}

	/**
	 * @return the seasonNames
	 */
	public String getSeasonNames() {
		return seasonNames;
	}

	/**
	 * @param seasonNames
	 *            the seasonNames to set
	 */
	public void setSeasonNames(String seasonNames) {
		this.seasonNames = seasonNames;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
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
	 * @return the unitTypes
	 */
	public String getUnitTypes() {
		return unitTypes;
	}

	/**
	 * @param unitTypes
	 *            the unitTypes to set
	 */
	public void setUnitTypes(String unitTypes) {
		this.unitTypes = unitTypes;
	}

	/**
	 * @return the adaOptions
	 */
	public String getAdaOptions() {
		return adaOptions;
	}

	/**
	 * @param adaOptions
	 *            the adaOptions to set
	 */
	public void setAdaOptions(String adaOptions) {
		this.adaOptions = adaOptions;
	}

	/**
	 * @return the adaUnit
	 */
	public String getAdaUnit() {
		return adaUnit;
	}

	/**
	 * @param adaUnit
	 *            the adaUnit to set
	 */
	public void setAdaUnit(String adaUnit) {
		this.adaUnit = adaUnit;
	}

	/**
	 * @return the availableFloorNumbers
	 */
	public String getAvailableFloorNumbers() {
		return availableFloorNumbers;
	}

	/**
	 * @param availableFloorNumbers
	 *            the availableFloorNumbers to set
	 */
	public void setAvailableFloorNumbers(String availableFloorNumbers) {
		this.availableFloorNumbers = availableFloorNumbers;
	}

	/**
	 * @return the availableUnitNumbers
	 */
	public String getAvailableUnitNumbers() {
		return availableUnitNumbers;
	}

	/**
	 * @param availableUnitNumbers
	 *            the availableUnitNumbers to set
	 */
	public void setAvailableUnitNumbers(String availableUnitNumbers) {
		this.availableUnitNumbers = availableUnitNumbers;
	}

	/**
	 * @return the availableUnitAdaOption
	 */
	public String getAvailableUnitAdaOption() {
		return availableUnitAdaOption;
	}

	/**
	 * @param availableUnitAdaOption
	 *            the availableUnitAdaOption to set
	 */
	public void setAvailableUnitAdaOption(String availableUnitAdaOption) {
		this.availableUnitAdaOption = availableUnitAdaOption;
	}

	/**
	 * @return the availableUnitAdaUnit
	 */
	public String getAvailableUnitAdaUnit() {
		return availableUnitAdaUnit;
	}

	/**
	 * @param availableUnitAdaUnit
	 *            the availableUnitAdaUnit to set
	 */
	public void setAvailableUnitAdaUnit(String availableUnitAdaUnit) {
		this.availableUnitAdaUnit = availableUnitAdaUnit;
	}

	/**
	 * @return the availableUnitPropertyId
	 */
	public String getAvailableUnitPropertyId() {
		return availableUnitPropertyId;
	}

	/**
	 * @param availableUnitPropertyId
	 *            the availableUnitPropertyId to set
	 */
	public void setAvailableUnitPropertyId(String availableUnitPropertyId) {
		this.availableUnitPropertyId = availableUnitPropertyId;
	}

	/**
	 * @return the availableUnitType
	 */
	public String getAvailableUnitType() {
		return availableUnitType;
	}

	/**
	 * @param availableUnitType
	 *            the availableUnitType to set
	 */
	public void setAvailableUnitType(String availableUnitType) {
		this.availableUnitType = availableUnitType;
	}

	/**
	 * @return the availableUnitUnitNumber
	 */
	public String getAvailableUnitUnitNumber() {
		return availableUnitUnitNumber;
	}

	/**
	 * @param availableUnitUnitNumber
	 *            the availableUnitUnitNumber to set
	 */
	public void setAvailableUnitUnitNumber(String availableUnitUnitNumber) {
		this.availableUnitUnitNumber = availableUnitUnitNumber;
	}

	/**
	 * @return the checkInCheckOutOptions
	 */
	public String getCheckInCheckOutOptions() {
		return checkInCheckOutOptions;
	}

	/**
	 * @param checkInCheckOutOptions
	 *            the checkInCheckOutOptions to set
	 */
	public void setCheckInCheckOutOptions(String checkInCheckOutOptions) {
		this.checkInCheckOutOptions = checkInCheckOutOptions;
	}

	/**
	 * @return the fullWeekCheckInDay
	 */
	public String getFullWeekCheckInDay() {
		return fullWeekCheckInDay;
	}

	/**
	 * @param fullWeekCheckInDay
	 *            the fullWeekCheckInDay to set
	 */
	public void setFullWeekCheckInDay(String fullWeekCheckInDay) {
		this.fullWeekCheckInDay = fullWeekCheckInDay;
	}

	/**
	 * @return the maxOccupancy
	 */
	public String getMaxOccupancy() {
		return maxOccupancy;
	}

	/**
	 * @param maxOccupancy
	 *            the maxOccupancy to set
	 */
	public void setMaxOccupancy(String maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	/**
	 * @return the roomAmenities
	 */
	public String getRoomAmenities() {
		return roomAmenities;
	}

	/**
	 * @param roomAmenities
	 *            the roomAmenities to set
	 */
	public void setRoomAmenities(String roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType
	 *            the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the viewTypePreferenceOptions
	 */
	public String getViewTypePreferenceOptions() {
		return viewTypePreferenceOptions;
	}

	/**
	 * @param viewTypePreferenceOptions
	 *            the viewTypePreferenceOptions to set
	 */
	public void setViewTypePreferenceOptions(String viewTypePreferenceOptions) {
		this.viewTypePreferenceOptions = viewTypePreferenceOptions;
	}

	/**
	 * @return the vacationTypes
	 */
	public String getVacationTypes() {
		return vacationTypes;
	}

	/**
	 * @param vacationTypes
	 *            the vacationTypes to set
	 */
	public void setVacationTypes(String vacationTypes) {
		this.vacationTypes = vacationTypes;
	}

	/**
	 * @return the viewType
	 */
	public String getViewType() {
		return viewType;
	}

	/**
	 * @param viewType
	 *            the viewType to set
	 */
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	/**
	 * @return the errataNode
	 */
	public String getErrataNode() {
		return errataNode;
	}

	/**
	 * @param errataNode
	 *            the errataNode to set
	 */
	public void setErrataNode(String errataNode) {
		this.errataNode = errataNode;
	}

	/**
	 * @return the errataId
	 */
	public String getErrataId() {
		return errataId;
	}

	/**
	 * @param errataId
	 *            the errataId to set
	 */
	public void setErrataId(String errataId) {
		this.errataId = errataId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the propertyId
	 */
	public String getPropertyId() {
		return propertyId;
	}

	/**
	 * @param propertyId
	 *            the propertyId to set
	 */
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the newsNode
	 */
	public String getNewsNode() {
		return newsNode;
	}

	/**
	 * @param newsNode
	 *            the newsNode to set
	 */
	public void setNewsNode(String newsNode) {
		this.newsNode = newsNode;
	}

	/**
	 * @return the dealNode
	 */
	public String getDealNode() {
		return dealNode;
	}

	/**
	 * @param dealNode
	 *            the dealNode to set
	 */
	public void setDealNode(String dealNode) {
		this.dealNode = dealNode;
	}

	/**
	 * @return the eventNode
	 */
	public String getEventNode() {
		return eventNode;
	}

	/**
	 * @param eventNode
	 *            the eventNode to set
	 */
	public void setEventNode(String eventNode) {
		this.eventNode = eventNode;
	}

	/**
	 * @return the errorNode
	 */
	public String getErrorNode() {
		return errorNode;
	}

	/**
	 * @param errorNode the errorNode to set
	 */
	public void setErrorNode(String errorNode) {
		this.errorNode = errorNode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the promotedpropertyImages
	 */
	public String getPromotedpropertyImages() {
		return promotedpropertyImages;
	}

	/**
	 * @param promotedpropertyImages
	 *            the promotedpropertyImages to set
	 */
	public void setPromotedpropertyImages(String promotedpropertyImages) {
		this.promotedpropertyImages = promotedpropertyImages;
	}

	/**
	 * @return the promotedpropertyAmenities
	 */
	public String getPromotedpropertyAmenities() {
		return promotedpropertyAmenities;
	}

	/**
	 * @param promotedpropertyAmenities
	 *            the promotedpropertyAmenities to set
	 */
	public void setPromotedpropertyAmenities(String promotedpropertyAmenities) {
		this.promotedpropertyAmenities = promotedpropertyAmenities;
	}

	/**
	 * @return the promotedpropertyAmenitiesName
	 */
	public String getPromotedpropertyAmenitiesName() {
		return promotedpropertyAmenitiesName;
	}

	/**
	 * @param promotedpropertyAmenitiesName
	 *            the promotedpropertyAmenitiesName to set
	 */
	public void setPromotedpropertyAmenitiesName(String promotedpropertyAmenitiesName) {
		this.promotedpropertyAmenitiesName = promotedpropertyAmenitiesName;
	}

	/**
	 * @return the promotedpropertyAmenitiesLogo
	 */
	public String getPromotedpropertyAmenitiesLogo() {
		return promotedpropertyAmenitiesLogo;
	}

	/**
	 * @param promotedpropertyAmenitiesLogo
	 *            the promotedpropertyAmenitiesLogo to set
	 */
	public void setPromotedpropertyAmenitiesLogo(String promotedpropertyAmenitiesLogo) {
		this.promotedpropertyAmenitiesLogo = promotedpropertyAmenitiesLogo;
	}

	/**
	 * @return the promotedpropertyWhyVisit
	 */
	public String getPromotedpropertyWhyVisit() {
		return promotedpropertyWhyVisit;
	}

	/**
	 * @param promotedpropertyWhyVisit
	 *            the promotedpropertyWhyVisit to set
	 */
	public void setPromotedpropertyWhyVisit(String promotedpropertyWhyVisit) {
		this.promotedpropertyWhyVisit = promotedpropertyWhyVisit;
	}

	/**
	 * @return the staticContentjntPage
	 */
	public String getStaticContentjntPage() {
		return staticContentjntPage;
	}

	/**
	 * @param staticContentjntPage
	 *            the staticContentjntPage to set
	 */
	public void setStaticContentjntPage(String staticContentjntPage) {
		this.staticContentjntPage = staticContentjntPage;
	}

	/**
	 * @return the staticContentjntArea
	 */
	public String getStaticContentjntArea() {
		return staticContentjntArea;
	}

	/**
	 * @param staticContentjntArea
	 *            the staticContentjntArea to set
	 */
	public void setStaticContentjntArea(String staticContentjntArea) {
		this.staticContentjntArea = staticContentjntArea;
	}

	/**
	 * @return the staticContentjntContentList
	 */
	public String getStaticContentjntContentList() {
		return staticContentjntContentList;
	}

	/**
	 * @param staticContentjntContentList
	 *            the staticContentjntContentList to set
	 */
	public void setStaticContentjntContentList(String staticContentjntContentList) {
		this.staticContentjntContentList = staticContentjntContentList;
	}

	/**
	 * @return the staticContentjntImageReferenceLink
	 */
	public String getStaticContentjntImageReferenceLink() {
		return staticContentjntImageReferenceLink;
	}

	/**
	 * @param staticContentjntImageReferenceLink
	 *            the staticContentjntImageReferenceLink to set
	 */
	public void setStaticContentjntImageReferenceLink(String staticContentjntImageReferenceLink) {
		this.staticContentjntImageReferenceLink = staticContentjntImageReferenceLink;
	}

	/**
	 * @return the staticContentjntBanner
	 */
	public String getStaticContentjntBanner() {
		return staticContentjntBanner;
	}

	/**
	 * @param staticContentjntBanner
	 *            the staticContentjntBanner to set
	 */
	public void setStaticContentjntBanner(String staticContentjntBanner) {
		this.staticContentjntBanner = staticContentjntBanner;
	}

	/**
	 * @return the staticContentjntFileList
	 */
	public String getStaticContentjntFileList() {
		return staticContentjntFileList;
	}

	/**
	 * @param staticContentjntFileList
	 *            the staticContentjntFileList to set
	 */
	public void setStaticContentjntFileList(String staticContentjntFileList) {
		this.staticContentjntFileList = staticContentjntFileList;
	}

	/**
	 * @return the staticContenthyattSimpleText
	 */
	public String getStaticContenthyattSimpleText() {
		return staticContenthyattSimpleText;
	}

	/**
	 * @param staticContenthyattSimpleText
	 *            the staticContenthyattSimpleText to set
	 */
	public void setStaticContenthyattSimpleText(String staticContenthyattSimpleText) {
		this.staticContenthyattSimpleText = staticContenthyattSimpleText;
	}

	/**
	 * @return the staticContenthyattRichText
	 */
	public String getStaticContenthyattRichText() {
		return staticContenthyattRichText;
	}

	/**
	 * @param staticContenthyattRichText
	 *            the staticContenthyattRichText to set
	 */
	public void setStaticContenthyattRichText(String staticContenthyattRichText) {
		this.staticContenthyattRichText = staticContenthyattRichText;
	}

	/**
	 * @return the staticContenthyattSampleImage
	 */
	public String getStaticContenthyattSampleImage() {
		return staticContenthyattSampleImage;
	}

	/**
	 * @param staticContenthyattSampleImage
	 *            the staticContenthyattSampleImage to set
	 */
	public void setStaticContenthyattSampleImage(String staticContenthyattSampleImage) {
		this.staticContenthyattSampleImage = staticContenthyattSampleImage;
	}

	/**
	 * @return the staticContentjntText
	 */
	public String getStaticContentjntText() {
		return staticContentjntText;
	}

	/**
	 * @param staticContentjntText
	 *            the staticContentjntText to set
	 */
	public void setStaticContentjntText(String staticContentjntText) {
		this.staticContentjntText = staticContentjntText;
	}

	/**
	 * @return the cmsBaseRender
	 */
	public String getCmsBaseRender() {
		return cmsBaseRender;
	}

	/**
	 * @param cmsBaseRender
	 *            the cmsBaseRender to set
	 */
	public void setCmsBaseRender(String cmsBaseRender) {
		this.cmsBaseRender = cmsBaseRender;
	}

	/**
	 * @return the cmsBaseSite
	 */
	public String getCmsBaseSite() {
		return cmsBaseSite;
	}

	/**
	 * @param cmsBaseSite
	 *            the cmsBaseSite to set
	 */
	public void setCmsBaseSite(String cmsBaseSite) {
		this.cmsBaseSite = cmsBaseSite;
	}

	/**
	 * @return the cmsBasePage
	 */
	public String getCmsBasePage() {
		return cmsBasePage;
	}

	/**
	 * @param cmsBasePage
	 *            the cmsBasePage to set
	 */
	public void setCmsBasePage(String cmsBasePage) {
		this.cmsBasePage = cmsBasePage;
	}

	/**
	 * @return the staticContentHeaderNavigator
	 */
	public String getStaticContentHeaderNavigator() {
		return staticContentHeaderNavigator;
	}

	/**
	 * @param staticContentHeaderNavigator
	 *            the staticContentHeaderNavigator to set
	 */
	public void setStaticContentHeaderNavigator(String staticContentHeaderNavigator) {
		this.staticContentHeaderNavigator = staticContentHeaderNavigator;
	}

	/**
	 * @return the staticContentFooterNavigator
	 */
	public String getStaticContentFooterNavigator() {
		return staticContentFooterNavigator;
	}

	/**
	 * @param staticContentFooterNavigator
	 *            the staticContentFooterNavigator to set
	 */
	public void setStaticContentFooterNavigator(String staticContentFooterNavigator) {
		this.staticContentFooterNavigator = staticContentFooterNavigator;
	}

	/**
	 * @return the staticContenthyattSimpleImage
	 */
	public String getStaticContenthyattSimpleImage() {
		return staticContenthyattSimpleImage;
	}

	/**
	 * @param staticContenthyattSimpleImage
	 *            the staticContenthyattSimpleImage to set
	 */
	public void setStaticContenthyattSimpleImage(String staticContenthyattSimpleImage) {
		this.staticContenthyattSimpleImage = staticContenthyattSimpleImage;
	}

	/**
	 * @return the staticContentjntBigText
	 */
	public String getStaticContentjntBigText() {
		return staticContentjntBigText;
	}

	/**
	 * @param staticContentjntBigText
	 *            the staticContentjntBigText to set
	 */
	public void setStaticContentjntBigText(String staticContentjntBigText) {
		this.staticContentjntBigText = staticContentjntBigText;
	}

	/**
	 * @return the staticContenthyattDate
	 */
	public String getStaticContenthyattDate() {
		return staticContenthyattDate;
	}

	/**
	 * @param staticContenthyattDate
	 *            the staticContenthyattDate to set
	 */
	public void setStaticContenthyattDate(String staticContenthyattDate) {
		this.staticContenthyattDate = staticContenthyattDate;
	}

	/**
	 * @return the staticContenthyattCheckBox
	 */
	public String getStaticContenthyattCheckBox() {
		return staticContenthyattCheckBox;
	}

	/**
	 * @param staticContenthyattCheckBox
	 *            the staticContenthyattCheckBox to set
	 */
	public void setStaticContenthyattCheckBox(String staticContenthyattCheckBox) {
		this.staticContenthyattCheckBox = staticContenthyattCheckBox;
	}

}
