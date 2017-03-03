package hyatt.api.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hyatt.api.cms.service.CmsService;
import hyatt.api.config.AppInitializers;
import hyatt.api.model.Amenity;
import hyatt.api.model.AppKeyValue;
import hyatt.api.model.AvailableUnitNumber;
import hyatt.api.model.Errata;
import hyatt.api.model.ErrataRequest;
import hyatt.api.model.NodeProperty;
import hyatt.api.model.Property;
import hyatt.api.model.ReservationTypePolicy;
import hyatt.api.model.UnitType;
import hyatt.api.rcc.service.RccService;

@RestController
@RequestMapping("/hyatt")
public class AppController {
	private static final Logger LOGGER = Logger.getLogger(AppController.class);

	@Autowired
	RccService rccService;

	@Autowired
	CmsService cmsService;

	@Autowired
	AppInitializers appInitializers;

	/**
	 * Query and return the unified Property data from both RCC & CMS systems
	 * based on the given Property Id.
	 * 
	 * @param propertyId
	 *            Property ID
	 * @param languageCd
	 *            Language code
	 * 
	 * @return The unified Property data.
	 */
	@RequestMapping(value = "/properties/{propertyId}/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listProperties(@PathVariable("propertyId") Integer propertyId,
			@PathVariable("languageCd") String languageCd) {
		return getForProperties(propertyId, languageCd);
	}

	/**
	 * Query and return the unified Property data from both RCC & CMS systems
	 * based on the given Property Id.
	 * 
	 * @param languageCd
	 *            Language code
	 * 
	 * @return The unified Property data.
	 */
	@RequestMapping(value = "/properties/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listProperties(@PathVariable("languageCd") String languageCd) {
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getForProperties(Integer.parseInt(languageCd), AppInitializers.DEFAULT_LANG);
		}
		// Param null indicates that we will get all properties
		return getForProperties(null, languageCd);
	}

	/**
	 * Redirect method for /properties request.
	 * 
	 * @return The unified Property data in default language.
	 */
	@RequestMapping(value = "/properties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listProperties() {
		// Param null indicates that we will get all properties
		return getForProperties(null, AppInitializers.DEFAULT_LANG);
	}

	/**
	 * Query and return the unified Errata data from both RCC & CMS systems.
	 * 
	 * @param errataRequest
	 *            Errata request object
	 * @param languageCd
	 *            Language code
	 * @return The unified Errata data.
	 */
	@RequestMapping(value = "/errata/{languageCd}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listErratas(@RequestBody ErrataRequest errataRequest,
			@PathVariable("languageCd") String languageCd) {
		return getForErrata(errataRequest, languageCd);
	}

	/**
	 * Redirect method for /errata request.
	 * 
	 * @param errataRequest
	 *            Errata request object
	 * 
	 * @return Errata data in default language
	 */
	@RequestMapping(value = "/errata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listErratas(@RequestBody ErrataRequest errataRequest) {
		return getForErrata(errataRequest, AppInitializers.DEFAULT_LANG);
	}

	/**
	 * Query and return Promoted Property data from CMS systems based on the
	 * given Property Id and language code.
	 * 
	 * @param propertyId
	 *            Property ID
	 * @param languageCd
	 *            The language code
	 * @return Promoted Property
	 */
	@RequestMapping(value = "/promoted-properties/{propertyId}/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listPromotedProperties(@PathVariable("propertyId") Integer propertyId,
			@PathVariable("languageCd") String languageCd) {
		return getForPromotedProperty(propertyId, languageCd);
	}

	/**
	 * Query and return all Promoted Property data from CMS systems based on the
	 * given language code.
	 * 
	 * @param languageCd
	 *            The language code
	 * @return All Promoted Property data
	 */
	@RequestMapping(value = "/promoted-properties/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listPromotedProperties(@PathVariable("languageCd") String languageCd) {
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getForPromotedProperty(Integer.parseInt(languageCd), AppInitializers.DEFAULT_LANG);
		}
		// Param null indicates that we will get all promoted properties
		return getForPromotedProperty(null, languageCd);
	}

	/**
	 * Redirect method for /promoted-properties request.
	 * 
	 * @return Promoted Property data in default language
	 */
	@RequestMapping(value = "/promoted-properties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listPromotedProperties() {
		// Param null indicates that we will get all promoted properties
		return getForPromotedProperty(null, AppInitializers.DEFAULT_LANG);
	}

	/**
	 * List a News Article from Jahia CMS by specified id and language code.
	 * @param newsId News ID
	 * @param languageCd Language code
	 * @return News Article with given ID
	 */
	@RequestMapping(value = "/news/{newsId}/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listNews(@PathVariable("newsId") Integer newsId, @PathVariable("languageCd") String languageCd) {
		String whereCondition = "newsId";
		return getDataByNodeType(newsId, languageCd, appInitializers.getNewsNode(), whereCondition);
	}

	/**
	 * List News Articles from Jahia CMS by specified language code.
	 * @param languageCd Language code
	 * @return News Articles in specified language code
	 */
	@RequestMapping(value = "/news/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listNews(@PathVariable("languageCd") String languageCd) {
		String whereCondition = "newsId";
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getDataByNodeType(Integer.parseInt(languageCd), AppInitializers.DEFAULT_LANG, appInitializers.getNewsNode(), whereCondition);
		}
		return getDataByNodeType(null, languageCd, appInitializers.getNewsNode(), whereCondition);
	}

	/**
	 * List News Articles from Jahia CMS by default language (EN).
	 * @return News Articles in default language
	 */
	@RequestMapping(value = "/news", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listNews() {
		String whereCondition = "newsId";
		return getDataByNodeType(null, AppInitializers.DEFAULT_LANG, appInitializers.getNewsNode(), whereCondition);
	}

	/**
	 * List a Deal of Property from Jahia CMS by specified property id and language code.
	 * @param propertyId Property ID
	 * @param languageCd Language code
	 * @return Property Deal with given Property ID
	 */
	@RequestMapping(value = "/deals/{propertyId}/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDeals(@PathVariable("propertyId") Integer propertyId, @PathVariable("languageCd") String languageCd) {
		String whereCondition = "id";
		return getDataByNodeType(propertyId, languageCd, appInitializers.getDealNode(), whereCondition);
	}

	/**
	 * List Deals of every properties from Jahia CMS by specified language code.
	 * @param languageCd Language code
	 * @return Deals in specified language code
	 */
	@RequestMapping(value = "/deals/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDeals(@PathVariable("languageCd") String languageCd) {
		String whereCondition = "id";
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getDataByNodeType(Integer.parseInt(languageCd), AppInitializers.DEFAULT_LANG, appInitializers.getDealNode(), whereCondition);
		}
		return getDataByNodeType(null, languageCd, appInitializers.getDealNode(), whereCondition);
	}

	/**
	 * List Deals of every properties from Jahia CMS by default language (EN).
	 * @return Deals in default language
	 */
	@RequestMapping(value = "/deals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDeals() {
		String whereCondition = "id";
		return getDataByNodeType(null, AppInitializers.DEFAULT_LANG, appInitializers.getDealNode(), whereCondition);
	}

	/**
	 * List an Event of Property from Jahia CMS by specified property id and language code.
	 * @param propertyId Property ID
	 * @param languageCd Language code
	 * @return Event of Property with given Property ID
	 */
	@RequestMapping(value = "/events/{propertyId}/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listEvents(@PathVariable("propertyId") Integer propertyId, @PathVariable("languageCd") String languageCd) {
		String whereCondition = "propertyId";
		return getDataByNodeType(propertyId, languageCd, appInitializers.getEventNode(), whereCondition);
	}

	/**
	 * List Events of every properties from Jahia CMS by specified language code.
	 * @param languageCd Language code
	 * @return Events in specified language code
	 */
	@RequestMapping(value = "/events/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listEvents(@PathVariable("languageCd") String languageCd) {
		String whereCondition = "propertyId";
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getDataByNodeType(Integer.parseInt(languageCd), AppInitializers.DEFAULT_LANG, appInitializers.getEventNode(), whereCondition);
		}
		return getDataByNodeType(null, languageCd, appInitializers.getEventNode(), whereCondition);
	}

	/**
	 * List Events of every properties from Jahia CMS by default language (EN).
	 * @return Events in default language
	 */
	@RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listEvents() {
		String whereCondition = "propertyId";
		return getDataByNodeType(null, AppInitializers.DEFAULT_LANG, appInitializers.getEventNode(), whereCondition);
	}
	
	/**
	 * List AppKeyValue pairs from RCC by the default language code (EN).
	 * @return List of AppKeyValue in the default language
	 */
	@RequestMapping(value = "/dynamicData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDynamicData() {
		return getDynamicDataAppKeyValues(AppInitializers.DEFAULT_LANG);
	}

	/**
	 * List AppKeyValue pairs from RCC by the given language code.
	 * @param languageCd Language code
	 * @return List of AppKeyValue in the given language code
	 */
	@RequestMapping(value = "/dynamicData/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDynamicData(@PathVariable("languageCd") String languageCd) {
		if (StringUtils.isNotEmpty(languageCd) && StringUtils.isNumeric(languageCd)) {
			return getDynamicDataAppKeyValues(AppInitializers.DEFAULT_LANG);
		}
		return getDynamicDataAppKeyValues(languageCd);
	}
	
	/**
	 * Utility method used to list an appropriate list of AppKeyValue based on
	 * the given language code.
	 * 
	 * @param actualLanguageCd Actual language code
	 * @return List of AppKeyValue pairs
	 */
	private ResponseEntity<?> getDynamicDataAppKeyValues(String actualLanguageCd) {
		List<AppKeyValue> appKeyValues = null;
		// TODO ::BEGIN:: These variables are being set by default which should be replaced by appropriate values from CMS later.
		int conversionRate = 100;
		String currencySign = "\\$";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
		// TODO ::END::
		try {
			appKeyValues = rccService.listAppKeyValues();
			if (CollectionUtils.isEmpty(appKeyValues)) {
				return getErrorMessage(206, actualLanguageCd);
			}
			for (AppKeyValue appKeyValue : appKeyValues) {
				Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
				Matcher matcher = pattern.matcher(appKeyValue.getValue());
				StringBuffer sb = new StringBuffer();
				while (matcher.find()) {
					AppKeyValue lookupKeyValue = lookupKeyValue(matcher.group(), appKeyValues);
					if (StringUtils.isNotEmpty(lookupKeyValue.getValue())) {
						String convertedValue = null;
						if (StringUtils.equals(lookupKeyValue.getDataType(), "CURRENCY")) {
							try {
								Float fee = Float.parseFloat(lookupKeyValue.getValue());
								convertedValue = currencySign + String.format("%.2f", (fee / conversionRate));
							} catch (NumberFormatException nfe) {
								Map<String, Object> errorMapResult = new HashMap<String, Object>();
								errorMapResult.put(AppInitializers.ERROR_CODE, 500);
								errorMapResult.put(AppInitializers.MESSAGE, "Unable to convert '" + lookupKeyValue.getValue() + "' of " + lookupKeyValue.getLabel());
								return new ResponseEntity<Object>(errorMapResult, HttpStatus.INTERNAL_SERVER_ERROR);
							}
						} else if (StringUtils.equals(lookupKeyValue.getDataType(), "DATE")
								|| StringUtils.equals(lookupKeyValue.getDataType(), "DATETIME")) {
							try {
								convertedValue = dateFormat.parse(lookupKeyValue.getValue()).toString();
							} catch (ParseException pe) {
								Map<String, Object> errorMapResult = new HashMap<String, Object>();
								errorMapResult.put(AppInitializers.ERROR_CODE, 500);
								errorMapResult.put(AppInitializers.MESSAGE, "Unable to parse '" + lookupKeyValue.getValue() + "' of " + lookupKeyValue.getLabel());
								return new ResponseEntity<Object>(errorMapResult, HttpStatus.INTERNAL_SERVER_ERROR);
							}
						} else {
							convertedValue = lookupKeyValue.getValue();
						}
						matcher.appendReplacement(sb, convertedValue);
					}
				}
				if (sb != null) {
					appKeyValue.setValue(matcher.appendTail(sb).toString());
				}
			}
		} catch (RestClientResponseException rcre) {
			return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(206, actualLanguageCd);
		}
		return new ResponseEntity<List<AppKeyValue>>(appKeyValues, HttpStatus.OK);
	}
	
	/**
	 * Look up object in a list of AppKeyValue by its label as a key.
	 * 
	 * @param key A specific key (label) of an AppKeyValue object
	 * @param appKeyValues List of AppKeyValue objects
	 * @return Specified AppKeyValue object by the given key (label)
	 */
	private AppKeyValue lookupKeyValue(String key, List<AppKeyValue> appKeyValues) {
		for (AppKeyValue appKeyValue : appKeyValues) {
			if (key.indexOf(appKeyValue.getLabel()) != -1) {
				return appKeyValue;
			}
		}
		return null;
	}

	/**
	 * Utility method used to list an appropriate list of Properties based on
	 * the given PropertyId and language code.
	 * 
	 * @param propertyId
	 *            Property Id
	 * @param languageCd
	 *            Language code
	 * @return List of Properties
	 */
	private ResponseEntity<?> getForProperties(Integer propertyId, String languageCd) {
		// Get actual language code based on the language code input by the
		// user.
		String actualLanguageCd = getActualLanguageCode(languageCd);
		List<Property> rccProperties = null;

		// Request to RCC
		try {
			// Process request for Property data to RCC API
			rccProperties = rccService.listProperties(propertyId);
		} catch (RestClientResponseException rcre) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode actualObj = mapper.readTree(rcre.getResponseBodyAsString());
				if (actualObj != null) {
					JsonNode errorCode = actualObj.get("error_code");
					return getErrorMessage(errorCode.intValue(), actualLanguageCd);
				}
			} catch (JsonProcessingException e) {
				return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
			} catch (IOException e) {
				return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
			}
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}

		// Request to CMS
		try {
			if (!CollectionUtils.isEmpty(rccProperties)) {
				LOGGER.debug(rccProperties.size() + " elements are found!");

				// Get list property ids for querying data to CMS system
				List<Integer> propertyIds = new ArrayList<Integer>();
				for (Property rccProperty : rccProperties) {
					propertyIds.add(rccProperty.getId());
				}

				Map<Integer, Property> cmsPropertyMap = cmsService.listProperties(propertyIds, actualLanguageCd);

				if (!CollectionUtils.isEmpty(cmsPropertyMap)) {
					unifyProperties(rccProperties, cmsPropertyMap, languageCd);
				}
			} else {
				return getErrorMessage(206, actualLanguageCd);
			}
		} catch (RestClientResponseException rcre) {
			return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
		return new ResponseEntity<List<Property>>(rccProperties, HttpStatus.OK);
	}

	private ResponseEntity<?> getForErrata(ErrataRequest errataRequest, String languageCd) {
		// Get actual language code based on the language code input by the
		// user.
		String actualLanguageCd = getActualLanguageCode(languageCd);
		List<Errata> rccErratas = null;

		// Request to RCC
		try {
			// Process request for Errata data to RCC API
			rccErratas = rccService.listErratas(errataRequest);
		} catch (RestClientResponseException rcre) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode actualObj = mapper.readTree(rcre.getResponseBodyAsString());
				if (actualObj != null) {
					JsonNode errorCode = actualObj.get("error_code");
					return getErrorMessage(errorCode.intValue(), actualLanguageCd);
				}
			} catch (JsonProcessingException e) {
				return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
			} catch (IOException e) {
				return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
			}
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}

		// Request to CMS
		try {
			if (!CollectionUtils.isEmpty(rccErratas)) {
				// Get list property ids for querying data to CMS system
				List<Integer> rccErrataIds = new ArrayList<Integer>();
				for (Errata rccErrata : rccErratas) {
					rccErrataIds.add(rccErrata.getErrataId());
				}

				Map<Integer, Errata> cmsErrataMap = cmsService.listErratas(rccErrataIds, actualLanguageCd);

				if (!CollectionUtils.isEmpty(cmsErrataMap)) {
					unifyErratas(rccErratas, cmsErrataMap, languageCd);
				}
			} else {
				return getErrorMessage(206, actualLanguageCd);
			}
		} catch (RestClientResponseException rcre) {
			return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
		return new ResponseEntity<List<Errata>>(rccErratas, HttpStatus.OK);
	}

	/**
	 * Utility method used to list an appropriate list of Promoted Properties
	 * based on the given PropertyId and language code.
	 * 
	 * @param propertyId
	 *            Property Id
	 * @param languageCd
	 *            Language code
	 * @return List of Promoted Properties
	 */
	private ResponseEntity<?> getForPromotedProperty(Integer propertyId, String languageCd) {
		// Get actual language code based on the language code input by the
		// user.
		String actualLanguageCd = getActualLanguageCode(languageCd);
		List<Property> result = new ArrayList<Property>();
		try {
			// result = cmsService.listPromotedProperties(propertyId,
			// actualLanguageCd);
			List<Integer> propertyIds = new ArrayList<Integer>();
			if (propertyId != null) {
				propertyIds.add(propertyId);
			}
			Map<Integer, Property> propertyMap = cmsService.listProperties(propertyIds, actualLanguageCd);
			Iterator<Entry<Integer, Property>> iterator = propertyMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Property> pair = iterator.next();
				result.add(pair.getValue());
			}
			if (CollectionUtils.isEmpty(result)) {
				return getErrorMessage(206, actualLanguageCd);
			}
		} catch (RestClientResponseException rcre) {
			return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(206, actualLanguageCd);
		}
		return new ResponseEntity<List<Property>>(result, HttpStatus.OK);
	}

	/**
	 * Utility method used to list an appropriate Node contents
	 * based on the given Node ID, Node type name, where's condition and language code.
	 * 
	 * @param nodeId News ID
	 * @param languageCd Language code
	 * @param nodeType Node type name
	 * @param whereCondition Where condition used in SQL-2 query
	 * @return Node contents
	 */
	private ResponseEntity<?> getDataByNodeType(Integer nodeId, String languageCd, String nodeType, String whereCondition) {
		// Get actual language code based on the language code input by the user.
		String actualLanguageCd = getActualLanguageCode(languageCd);
		List<Map<String, Object>> result = null;
		try {
			result = cmsService.listDataByNodeType(nodeId, actualLanguageCd, nodeType, whereCondition);
			if (CollectionUtils.isEmpty(result)) {
				return getErrorMessage(206, actualLanguageCd);
			}
		} catch (RestClientResponseException rcre) {
			return getErrorMessage(rcre.getRawStatusCode(), actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
		return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
	}

	private ResponseEntity<Object> getErrorMessage(Integer errorCode, String actualLanguageCd) {
		String errorMessage = cmsService.listErrorMessageByCode(errorCode, actualLanguageCd);
		errorMessage = StringUtils.isEmpty(errorMessage) ? "Unkown Error" : errorMessage;
		
		Map<String, Object> errorMapResult = new HashMap<String, Object>();
		
		errorMapResult.put(AppInitializers.ERROR_CODE, errorCode);
		errorMapResult.put(AppInitializers.MESSAGE, errorMessage);
		
		return new ResponseEntity<Object>(errorMapResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * Unify properties from both RCC & CMS systems.
	 * 
	 * @param rccProperties
	 *            A list of Properties get from RCC
	 * @param cmsPropertyMap
	 *            A map of Properties get from CMS
	 * @param languageCd
	 *            Requested language code
	 */
	private void unifyProperties(List<Property> rccProperties, Map<Integer, Property> cmsPropertyMap,
			String languageCd) {
		for (Property rccProperty : rccProperties) {
			if (cmsPropertyMap.containsKey(rccProperty.getId())) {
				Property cmsProperty = cmsPropertyMap.get(rccProperty.getId());

				if (cmsProperty.getBonusWeekAvailable() != null) {
					rccProperty.setBonusWeekAvailable(cmsProperty.getBonusWeekAvailable());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getCity())) {
					rccProperty.setCity(cmsProperty.getCity());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getCountry())) {
					rccProperty.setCountry(cmsProperty.getCountry());
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getPaymentTypesAccepted())) {
					for (int i = 0; i < cmsProperty.getPaymentTypesAccepted().size(); i++) {
						if (StringUtils.isNotEmpty(cmsProperty.getPaymentTypesAccepted().get(i))) {
							if (i < rccProperty.getPaymentTypesAccepted().size()) {
								rccProperty.getPaymentTypesAccepted().remove(i);
								rccProperty.getPaymentTypesAccepted().add(i,
										cmsProperty.getPaymentTypesAccepted().get(i));
							} else {
								rccProperty.getPaymentTypesAccepted().add(cmsProperty.getPaymentTypesAccepted().get(i));
							}
						}
					}
				}

				if (StringUtils.isNotEmpty(cmsProperty.getPhoneNumber())) {
					rccProperty.setPhoneNumber(cmsProperty.getPhoneNumber());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getPostalCode())) {
					rccProperty.setPostalCode(cmsProperty.getPostalCode());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getRegionCode())) {
					rccProperty.setRegionCode(cmsProperty.getRegionCode());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getRegionName())) {
					rccProperty.setRegionName(cmsProperty.getRegionName());
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getSeasonNames())) {
					for (int i = 0; i < cmsProperty.getSeasonNames().size(); i++) {
						if (StringUtils.isNotEmpty(cmsProperty.getSeasonNames().get(i))) {
							if (i < rccProperty.getSeasonNames().size()) {
								rccProperty.getSeasonNames().remove(i);
								rccProperty.getSeasonNames().add(i, cmsProperty.getSeasonNames().get(i));
							} else {
								rccProperty.getSeasonNames().add(cmsProperty.getSeasonNames().get(i));
							}
						}
					}
				}

				if (StringUtils.isNotEmpty(cmsProperty.getState())) {
					rccProperty.setState(cmsProperty.getState());
				}

				if (StringUtils.isNotEmpty(cmsProperty.getStreetAddress())) {
					rccProperty.setStreetAddress(cmsProperty.getStreetAddress());
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getVacationTypes())) {
					for (int i = 0; i < cmsProperty.getVacationTypes().size(); i++) {
						if (StringUtils.isNotEmpty(cmsProperty.getVacationTypes().get(i))) {
							if (i < rccProperty.getVacationTypes().size()) {
								rccProperty.getVacationTypes().remove(i);
								rccProperty.getVacationTypes().add(i, cmsProperty.getVacationTypes().get(i));
							} else {
								rccProperty.getVacationTypes().add(cmsProperty.getVacationTypes().get(i));
							}
						}
					}
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getViewType())) {
					for (int i = 0; i < cmsProperty.getViewType().size(); i++) {
						if (StringUtils.isNotEmpty(cmsProperty.getViewType().get(i))) {
							if (i < rccProperty.getViewType().size()) {
								rccProperty.getViewType().remove(i);
								rccProperty.getViewType().add(i, cmsProperty.getViewType().get(i));
							} else {
								rccProperty.getViewType().add(cmsProperty.getViewType().get(i));
							}
						}
					}
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getImages())) {
					for (int i = 0; i < cmsProperty.getImages().size(); i++) {
						if (StringUtils.isNotEmpty(cmsProperty.getImages().get(i))) {
							if (i < rccProperty.getImages().size()) {
								rccProperty.getImages().remove(i);
								rccProperty.getImages().add(i, cmsProperty.getImages().get(i));
							} else {
								rccProperty.getImages().add(cmsProperty.getImages().get(i));
							}
						}
					}
				}

				if (StringUtils.isNotEmpty(cmsProperty.getWhyVisit())) {
					rccProperty.setWhyVisit(cmsProperty.getWhyVisit());
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getReservationTypePolicies())) {
					for (int i = 0; i < cmsProperty.getReservationTypePolicies().size(); i++) {
						ReservationTypePolicy cmsPolicy = cmsProperty.getReservationTypePolicies().get(i);
						if (i < rccProperty.getReservationTypePolicies().size()) {
							ReservationTypePolicy rccPolicy = rccProperty.getReservationTypePolicies().get(i);
							if (StringUtils.isNotEmpty(cmsPolicy.getReservationType())) {
								rccPolicy.setReservationType(cmsPolicy.getReservationType());
							}
							if (!CollectionUtils.isEmpty(cmsPolicy.getNumberOfNightsAllowed())) {
								for (int j = 0; j < cmsPolicy.getNumberOfNightsAllowed().size(); j++) {
									if (cmsPolicy.getNumberOfNightsAllowed().get(j) != null) {
										try {
											if (j < rccPolicy.getNumberOfNightsAllowed().size()) {
												rccPolicy.getNumberOfNightsAllowed().remove(j);
												rccPolicy.getNumberOfNightsAllowed().add(j, Integer.parseInt(
														cmsPolicy.getNumberOfNightsAllowed().get(j).toString()));
											} else {
												rccPolicy.getNumberOfNightsAllowed().add(Integer.parseInt(
														cmsPolicy.getNumberOfNightsAllowed().get(j).toString()));
											}
										} catch (NumberFormatException e) {
											throw new NumberFormatException(
													appInitializers.getNumberOfNightsAllowed() + " - For input string: "
															+ cmsPolicy.getNumberOfNightsAllowed().get(j).toString());
										}
									}
								}
							}
						} else {
							rccProperty.getReservationTypePolicies().add(cmsPolicy);
						}
					}
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getUnitTypes())) {
					for (int i = 0; i < cmsProperty.getUnitTypes().size(); i++) {
						UnitType cmsUnitType = cmsProperty.getUnitTypes().get(i);
						if (i < rccProperty.getUnitTypes().size()) {
							UnitType rccUnitType = rccProperty.getUnitTypes().get(i);
							if (!CollectionUtils.isEmpty(cmsUnitType.getAdaOptions())) {
								for (int j = 0; j < cmsUnitType.getAdaOptions().size(); j++) {
									if (StringUtils.isNotEmpty(cmsUnitType.getAdaOptions().get(j))) {
										if (j < rccUnitType.getAdaOptions().size()) {
											rccUnitType.getAdaOptions().remove(j);
											rccUnitType.getAdaOptions().add(j, cmsUnitType.getAdaOptions().get(j));
										} else {
											rccUnitType.getAdaOptions().add(cmsUnitType.getAdaOptions().get(j));
										}
									}
								}
							}
							if (cmsUnitType.getAdaUnit() != null) {
								rccUnitType.setAdaUnit(cmsUnitType.getAdaUnit());
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getAvailableFloorNumbers())) {
								for (int j = 0; j < cmsUnitType.getAvailableFloorNumbers().size(); j++) {
									if (cmsUnitType.getAvailableFloorNumbers().get(j) != null) {
										try {
											if (j < rccUnitType.getAvailableFloorNumbers().size()) {
												rccUnitType.getAvailableFloorNumbers().remove(j);
												rccUnitType.getAvailableFloorNumbers().add(j, Integer.parseInt(
														cmsUnitType.getAvailableFloorNumbers().get(j).toString()));
											} else {
												rccUnitType.getAvailableFloorNumbers().add(Integer.parseInt(
														cmsUnitType.getAvailableFloorNumbers().get(j).toString()));
											}
										} catch (NumberFormatException e) {
											throw new NumberFormatException(
													appInitializers.getAvailableFloorNumbers() + " - For input string: "
															+ cmsUnitType.getAvailableFloorNumbers().get(j).toString());
										}
									}
								}
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getAvailableUnitNumbers())) {
								for (int j = 0; j < cmsUnitType.getAvailableUnitNumbers().size(); j++) {
									AvailableUnitNumber cmsUnitNumber = cmsUnitType.getAvailableUnitNumbers().get(j);
									if (cmsUnitNumber != null) {
										if (j < rccUnitType.getAvailableUnitNumbers().size()) {
											AvailableUnitNumber rccUnitNumber = rccUnitType.getAvailableUnitNumbers()
													.get(j);
											if (StringUtils.isNotEmpty(cmsUnitNumber.getAdaOption())) {
												rccUnitNumber.setAdaOption(cmsUnitNumber.getAdaOption());
											}
											if (cmsUnitNumber.getAdaUnit() != null) {
												rccUnitNumber.setAdaUnit(cmsUnitNumber.getAdaUnit());
											}
											if (cmsUnitNumber.getPropertyId() != null) {
												rccUnitNumber.setPropertyId(cmsUnitNumber.getPropertyId());
											}
											if (StringUtils.isNotEmpty(cmsUnitNumber.getType())) {
												rccUnitNumber.setType(cmsUnitNumber.getType());
											}
											if (StringUtils.isNotEmpty(cmsUnitNumber.getUnitNumber())) {
												rccUnitNumber.setUnitNumber(cmsUnitNumber.getUnitNumber());
											}
										} else {
											rccUnitType.getAvailableUnitNumbers().add(cmsUnitNumber);
										}
									}
								}
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getCheckInCheckOutOptions())) {
								for (int j = 0; j < cmsUnitType.getCheckInCheckOutOptions().size(); j++) {
									if (StringUtils.isNotEmpty(cmsUnitType.getCheckInCheckOutOptions().get(j))) {
										if (j < rccUnitType.getCheckInCheckOutOptions().size()) {
											rccUnitType.getCheckInCheckOutOptions().remove(j);
											rccUnitType.getCheckInCheckOutOptions().add(j,
													cmsUnitType.getCheckInCheckOutOptions().get(j));
										} else {
											rccUnitType.getCheckInCheckOutOptions()
													.add(cmsUnitType.getCheckInCheckOutOptions().get(j));
										}
									}
								}
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getFullWeekCheckInDay())) {
								for (int j = 0; j < cmsUnitType.getFullWeekCheckInDay().size(); j++) {
									if (StringUtils.isNotEmpty(cmsUnitType.getFullWeekCheckInDay().get(j))) {
										if (j < rccUnitType.getFullWeekCheckInDay().size()) {
											rccUnitType.getFullWeekCheckInDay().remove(j);
											rccUnitType.getFullWeekCheckInDay().add(j,
													cmsUnitType.getFullWeekCheckInDay().get(j));
										} else {

											rccUnitType.getFullWeekCheckInDay()
													.add(cmsUnitType.getFullWeekCheckInDay().get(j));
										}
									}
								}
							}

							if (cmsUnitType.getMaxOccupancy() != null) {
								rccUnitType.setMaxOccupancy(cmsUnitType.getMaxOccupancy());
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getRoomAmenities())) {
								for (int j = 0; j < cmsUnitType.getRoomAmenities().size(); j++) {
									if (StringUtils.isNotEmpty(cmsUnitType.getRoomAmenities().get(j))) {
										if (j < rccUnitType.getRoomAmenities().size()) {
											rccUnitType.getRoomAmenities().remove(j);
											rccUnitType.getRoomAmenities().add(j,
													cmsUnitType.getRoomAmenities().get(j));
										} else {
											rccUnitType.getRoomAmenities().add(cmsUnitType.getRoomAmenities().get(j));
										}
									}
								}
							}

							if (StringUtils.isNotEmpty(cmsUnitType.getRoomType())) {
								rccUnitType.setRoomType(cmsUnitType.getRoomType());
							}

							if (!CollectionUtils.isEmpty(cmsUnitType.getViewTypePreferenceOptions())) {
								for (int j = 0; j < cmsUnitType.getViewTypePreferenceOptions().size(); j++) {
									if (StringUtils.isNotEmpty(cmsUnitType.getViewTypePreferenceOptions().get(j))) {
										if (j < rccUnitType.getViewTypePreferenceOptions().size()) {
											rccUnitType.getViewTypePreferenceOptions().remove(j);
											rccUnitType.getViewTypePreferenceOptions().add(j,
													cmsUnitType.getViewTypePreferenceOptions().get(j));
										} else {
											rccUnitType.getViewTypePreferenceOptions().add(j,
													cmsUnitType.getViewTypePreferenceOptions().get(j));
										}
									}
								}
							}
						} else {
							rccProperty.getUnitTypes().add(cmsUnitType);
						}
					}
				}

				if (!CollectionUtils.isEmpty(cmsProperty.getAmenities())) {
					for (int i = 0; i < cmsProperty.getAmenities().size(); i++) {
						Amenity cmsAmenity = cmsProperty.getAmenities().get(i);
						if (i < rccProperty.getAmenities().size()) {
							Amenity rccAmenity = rccProperty.getAmenities().get(i);
							if (StringUtils.isNotEmpty(cmsAmenity.getName())) {
								rccAmenity.setName(cmsAmenity.getName());
							}
							if (StringUtils.isNotEmpty(cmsAmenity.getLogo())) {
								rccAmenity.setLogo(cmsAmenity.getLogo());
							}
						} else {
							rccProperty.getAmenities().add(cmsAmenity);
						}
					}
				}
			}
		}
	}

	/**
	 * Unify erratas from both RCC & CMS systems.
	 * 
	 * @param rccErratas
	 *            A list of Erratas get from RCC
	 * @param cmsErrataMap
	 *            A map of Erratas get from CMS
	 * @param languageCd
	 *            Requested language code
	 */
	private void unifyErratas(List<Errata> rccErratas, Map<Integer, Errata> cmsErrataMap, String languageCd) {
		for (Errata rccErrata : rccErratas) {
			if (cmsErrataMap.containsKey(rccErrata.getErrataId())) {
				Errata cmsErrata = cmsErrataMap.get(rccErrata.getErrataId());

				if (cmsErrata.getStartDate() != null) {
					rccErrata.setStartDate(cmsErrata.getStartDate());
				}

				if (cmsErrata.getEndDate() != null) {
					rccErrata.setEndDate(cmsErrata.getEndDate());
				}

				if (cmsErrata.getMessage() != null) {
					rccErrata.setMessage(cmsErrata.getMessage());
				}

				if (cmsErrata.getPropertyId() != null) {
					rccErrata.setPropertyId(cmsErrata.getPropertyId());
				}
			}
		}
	}

	/**
	 * Get the actual language code based on the given language code parameter.
	 * If the user doesn't specific the language code so it will return EN by
	 * default.
	 * 
	 * @param inputLanguageCd
	 *            The given language code parameter
	 * @return The actual language code
	 */
	private String getActualLanguageCode(String inputLanguageCd) {
		if (StringUtils.isEmpty(inputLanguageCd.trim())
				|| !ArrayUtils.contains(appInitializers.getLanguageCode(), inputLanguageCd.trim())) {
			return AppInitializers.DEFAULT_LANG; // English
		} else {
			return inputLanguageCd.trim();
		}
	}

	/**
	 * List all dictionaries with language.
	 * 
	 * @param languageCd
	 *            Requested language code
	 * @return A JSON file contains all of dictionary values
	 */
	@RequestMapping(value = "/dictionaries/{languageCd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDictionaries(@PathVariable("languageCd") String languageCd) {
		return getDictionary(languageCd);
	}

	/**
	 * List all dictionaries without language. It would make a default
	 * language(EN) when request.
	 * 
	 * @return A JSON file contains all of dictionary values
	 */
	@RequestMapping(value = "/dictionaries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listDictionaries() {
		return getDictionary(AppInitializers.DEFAULT_LANG);
	}

	/**
	 * Get all dictionary values from CMS
	 * 
	 * 
	 * @param languageCd
	 * @return A JSON file contains all of dictionary values
	 */
	private ResponseEntity<?> getDictionary(String languageCd) {
		String actualLanguageCd = getActualLanguageCode(languageCd);
		NodeProperty cmsContentNode = null;
		List<HashMap<String, Object>> contentModel = new ArrayList<HashMap<String, Object>>();
//		List<HashMap<String, Object>> headerModel = null;
//		List<HashMap<String, Object>> footerModel = null;
		List<HashMap<String, Object>> dictionaryModel = null;
		try {
			cmsContentNode = cmsService.getNodeProperty(actualLanguageCd);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
		try {
			dictionaryModel = new ArrayList<HashMap<String, Object>>();
//			headerModel = cmsService.navigatorBuilder(appInitializers.getStaticContentHeaderNavigator(),
//					actualLanguageCd);
//			dictionaryModel.addAll(headerModel);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
		try {
			contentModel = cmsService.processFirstNode(cmsContentNode, contentModel,actualLanguageCd);
			dictionaryModel.addAll(contentModel);
		} catch (Exception e) {
			return getErrorMessage(500, actualLanguageCd);
		}
//		try {
//			footerModel = cmsService.navigatorBuilder(appInitializers.getStaticContentFooterNavigator(),
//					actualLanguageCd);
//			dictionaryModel.addAll(footerModel);
//		} catch (Exception e) {
//			return getErrorMessage(500, actualLanguageCd);
//		}
		return new ResponseEntity<List<HashMap<String, Object>>>(dictionaryModel, HttpStatus.OK);
	}
}