package hyatt.api.cms.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import hyatt.api.cms.service.CmsService;
import hyatt.api.config.AppInitializers;
import hyatt.api.model.Amenity;
import hyatt.api.model.AvailableUnitNumber;
import hyatt.api.model.BaseJcrModel;
import hyatt.api.model.CmsSql2QueryVO;
import hyatt.api.model.Errata;
import hyatt.api.model.JcrQueryModel;
import hyatt.api.model.NodeProperty;
import hyatt.api.model.Property;
import hyatt.api.model.ReservationTypePolicy;
import hyatt.api.model.UnitType;

@Service
public class CmsServiceImpl implements CmsService {
	private static final Logger LOGGER = Logger.getLogger(CmsServiceImpl.class);

	@Autowired
	AppInitializers appInitializers;

	@Override
	public Map<Integer, Property> listProperties(List<Integer> propertyIds, String languageCd)
			throws RestClientException {
		// Build up query details
		StringBuilder queryContent = new StringBuilder()
				.append("SELECT * FROM ")
				.append(appInitializers.getPropertyNode());

		if (!CollectionUtils.isEmpty(propertyIds)) {
			queryContent.append(" WHERE id = ").append(StringUtils.join(propertyIds, " OR id = "));
		}

		// Build up Jahia CMS's SQL-2 query object
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(-1);
		queryObject.setOffset(0);

		LOGGER.debug("Actual query value: " + queryObject.getQuery());

		// Make a request to CMS for the specified Property
		List<HashMap<String, Object>> response = postForObject(cmsQueryBuilder(languageCd).toString(), queryObject);

		Map<Integer, Property> result = null;
		if (response != null) {
			Iterator<HashMap<String, Object>> iterator = response.iterator();
			result = new HashMap<Integer, Property>();
			while (iterator.hasNext()) {
				Property property = getProperty(iterator.next(), languageCd);
				result.put(property.getId(), property);
			}
		}
		return result;
	}

	@Override
	public Map<Integer, Errata> listErratas(List<Integer> errataIds, String languageCd) throws RestClientException {
		// Build up query details
		StringBuilder queryContent = new StringBuilder()
				.append("SELECT * FROM ")
				.append(appInitializers.getErrataNode())
				.append(" WHERE errataId = ")
				.append(StringUtils.join(errataIds, " OR errataId = "));

		// Build up Jahia CMS's SQL-2 query object
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(-1);
		queryObject.setOffset(0);

		LOGGER.debug("Actual query value: " + queryObject.getQuery());

		// Make a request to CMS for the specified Errata
		List<HashMap<String, Object>> response = postForObject(cmsQueryBuilder(languageCd).toString(), queryObject);

		Map<Integer, Errata> result = null;
		if (response != null) {
			Iterator<HashMap<String, Object>> iterator = response.iterator();
			result = new HashMap<Integer, Errata>();
			while (iterator.hasNext()) {
				Errata errata = getErrata(iterator.next(), languageCd);
				result.put(errata.getErrataId(), errata);
			}
		}
		return result;
	}

	/**
	 * Utility method used to create HttpHeaders.
	 * 
	 * @return Initialized HttpHeaders
	 */
	private HttpHeaders createHttpHeaders() {
		// Initial headers
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.setCacheControl(appInitializers.getCacheControl());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String auth = appInitializers.getCmsUsername() + ":" + appInitializers.getCmsPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = AppInitializers.AUTH_METHOD_BASIC + new String(encodedAuth);

		headers.set(AppInitializers.AUTH_HEADER, authHeader);

		return headers;
	}

	/**
	 * Utility method which uses RestTemplate to POST a query to Jahia CMS.
	 * 
	 * @param baseQueryUrl
	 * @param queryObject
	 * @return A List of HashMap of Responses
	 */
	private List<HashMap<String, Object>> postForObject(String baseQueryUrl, Object queryObject) {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(queryObject, createHttpHeaders());

		RestTemplate template = new RestTemplate();

		ParameterizedTypeReference<List<HashMap<String, Object>>> typeRef = new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
		};

		LOGGER.debug("Base query URL: " + baseQueryUrl);

		ResponseEntity<List<HashMap<String, Object>>> response = template.exchange(baseQueryUrl, HttpMethod.POST, httpEntity, typeRef);

		if (response != null) {
			return response.getBody();
		}
		return null;
	}

	/**
	 * Utility method which uses RestTemplate to GET a query to Jahia CMS.
	 * 
	 * @param baseQueryUrl
	 * @param params
	 * @return A HashMap of Response
	 */
	private HashMap<String, Object> getForObject(String baseQueryUrl, String params) {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(createHttpHeaders());

		RestTemplate template = new RestTemplate();

		ParameterizedTypeReference<HashMap<String, Object>> typeRef = new ParameterizedTypeReference<HashMap<String, Object>>() {
		};

		LOGGER.debug("Base query URL: " + baseQueryUrl);
		StringBuilder finalQueryUrl = new StringBuilder().append(baseQueryUrl);
		if (StringUtils.isNotEmpty(params)) {
			finalQueryUrl.append("?").append(params);
		}
		LOGGER.debug("Final query URL: " + finalQueryUrl);

		ResponseEntity<HashMap<String, Object>> response = template.exchange(finalQueryUrl.toString(), HttpMethod.GET,
				httpEntity, typeRef);

		if (response != null) {
			return response.getBody();
		}
		return null;
	}

	/**
	 * Get Property from RAW JSON data which is returned by Jahia CMS system.
	 * 
	 * @param inputMap
	 *            RAW JSON data which is returned by Jahia CMS system
	 * @param languageCd
	 *            Requested language code
	 * @return Expected Property object
	 */
	@SuppressWarnings("unchecked")
	private Property getProperty(HashMap<String, Object> inputMap, String languageCd) {
		Property property = null;
		HashMap<String, Object> properties = (HashMap<String, Object>) inputMap
				.get(AppInitializers.NODE_QUERY_PROPERTIES);
		if (properties != null) {
			property = new Property();
			// bonus week available
			property.setBonusWeekAvailable(
					(Boolean) getMapValue(properties.get(appInitializers.getBonusWeekAvailable())));

			// city
			property.setCity((String) getMapValue(properties.get(appInitializers.getCity())));

			// country
			property.setCountry((String) getMapValue(properties.get(appInitializers.getCountry())));

			// id
			property.setId((Integer) getMapValue(properties.get(appInitializers.getId())));

			// name
			property.setName((String) getMapValue(properties.get(appInitializers.getName())));

			// payment types accepted
			property.setPaymentTypesAccepted(
					(List<String>) getMapValue(properties.get(appInitializers.getPaymentTypesAccepted())));

			// phone number
			property.setPhoneNumber((String) getMapValue(properties.get(appInitializers.getPhoneNumber())));

			// postal code
			property.setPostalCode((String) getMapValue(properties.get(appInitializers.getPostalCode())));

			// region code
			property.setRegionCode((String) getMapValue(properties.get(appInitializers.getRegionCode())));

			// region name
			property.setRegionName((String) getMapValue(properties.get(appInitializers.getRegionName())));

			// season names
			property.setSeasonNames((List<String>) getMapValue(properties.get(appInitializers.getSeasonNames())));

			// state
			property.setState((String) getMapValue(properties.get(appInitializers.getState())));

			// street address
			property.setStreetAddress((String) getMapValue(properties.get(appInitializers.getStreetAddress())));

			// vacation types
			property.setVacationTypes((List<String>) getMapValue(properties.get(appInitializers.getVacationTypes())));

			// view type
			property.setViewType((List<String>) getMapValue(properties.get(appInitializers.getViewType())));

			// promoted property images
			property.setImages(
					getRealPathImage(getMapValue(properties.get(appInitializers.getPromotedpropertyImages()))));

			// promoted property why visit
			property.setWhyVisit((String) getMapValue(properties.get(appInitializers.getPromotedpropertyWhyVisit())));
		}

		// processing for children node
		HashMap<String, Object> children = (HashMap<String, Object>) inputMap.get(AppInitializers.NODE_QUERY_CHILDREN);
		if (!CollectionUtils.isEmpty(children)) {

			// processing for reservation type policies
			List<ReservationTypePolicy> reservationTypePolicies = new ArrayList<ReservationTypePolicy>();

			List<HashMap<String, Object>> reservationTypePolicyMaps = processChildrenNode(children,
					appInitializers.getReservationTypePolicies(), languageCd);

			if (!CollectionUtils.isEmpty(reservationTypePolicyMaps)) {
				for (HashMap<String, Object> reservationTypePolicyMap : reservationTypePolicyMaps) {
					ReservationTypePolicy policy = new ReservationTypePolicy();

					HashMap<String, Object> reservationTypePolicyProperties = (HashMap<String, Object>) reservationTypePolicyMap
							.get(AppInitializers.NODE_QUERY_PROPERTIES);

					// reservation type
					policy.setReservationType((String) getMapValue(
							reservationTypePolicyProperties.get(appInitializers.getReservationType())));

					// number of nights allowed
					policy.setNumberOfNightsAllowed((List<Object>) getMapValue(
							reservationTypePolicyProperties.get(appInitializers.getNumberOfNightsAllowed())));
					reservationTypePolicies.add(policy);
				}
			}

			// processing for unit types
			List<UnitType> unitTypes = new ArrayList<UnitType>();

			List<HashMap<String, Object>> unitTypeMaps = processChildrenNode(children, appInitializers.getUnitTypes(),
					languageCd);

			if (!CollectionUtils.isEmpty(unitTypeMaps)) {
				for (HashMap<String, Object> unitTypeMap : unitTypeMaps) {
					UnitType unitType = new UnitType();

					HashMap<String, Object> unitTypeProperties = (HashMap<String, Object>) unitTypeMap
							.get(AppInitializers.NODE_QUERY_PROPERTIES);

					// ada options
					unitType.setAdaOptions(
							(List<String>) getMapValue(unitTypeProperties.get(appInitializers.getAdaOptions())));

					// ada unit
					unitType.setAdaUnit((Boolean) getMapValue(unitTypeProperties.get(appInitializers.getAdaUnit())));

					// available floor numbers
					unitType.setAvailableFloorNumbers((List<Object>) getMapValue(
							unitTypeProperties.get(appInitializers.getAvailableFloorNumbers())));

					HashMap<String, Object> unitTypeChildren = (HashMap<String, Object>) unitTypeMap
							.get(AppInitializers.NODE_QUERY_CHILDREN);
					if (!CollectionUtils.isEmpty(unitTypeChildren)) {
						List<AvailableUnitNumber> availableUnitNumbers = new ArrayList<AvailableUnitNumber>();

						List<HashMap<String, Object>> availableUnitNumberMaps = processChildrenNode(unitTypeChildren,
								appInitializers.getAvailableUnitNumbers(), languageCd);

						if (!CollectionUtils.isEmpty(availableUnitNumberMaps)) {
							for (HashMap<String, Object> availableUnitNumberMap : availableUnitNumberMaps) {
								// if availableUnitNumbersChild is not null so
								// it always contain properties
								HashMap<String, Object> availableUnitNumberProperties = (HashMap<String, Object>) availableUnitNumberMap
										.get(AppInitializers.NODE_QUERY_PROPERTIES);
								AvailableUnitNumber availableUnitNumber = new AvailableUnitNumber();

								// ada option
								availableUnitNumber.setAdaOption((String) getMapValue(availableUnitNumberProperties
										.get(appInitializers.getAvailableUnitAdaOption())));

								// ada unit
								availableUnitNumber.setAdaUnit((Boolean) getMapValue(
										availableUnitNumberProperties.get(appInitializers.getAvailableUnitAdaUnit())));

								// property id
								availableUnitNumber.setPropertyId((Integer) getMapValue(availableUnitNumberProperties
										.get(appInitializers.getAvailableUnitPropertyId())));

								// unit type
								availableUnitNumber.setType((String) getMapValue(
										availableUnitNumberProperties.get(appInitializers.getAvailableUnitType())));

								// unit number
								availableUnitNumber.setUnitNumber((String) getMapValue(availableUnitNumberProperties
										.get(appInitializers.getAvailableUnitUnitNumber())));
								availableUnitNumbers.add(availableUnitNumber);
							}
						}
						unitType.setAvailableUnitNumbers(availableUnitNumbers);
					}

					// check in check out options
					unitType.setCheckInCheckOutOptions((List<String>) getMapValue(
							unitTypeProperties.get(appInitializers.getCheckInCheckOutOptions())));

					// full week check in day
					unitType.setFullWeekCheckInDay((List<String>) getMapValue(
							unitTypeProperties.get(appInitializers.getFullWeekCheckInDay())));

					// max occupancy
					unitType.setMaxOccupancy(
							(Integer) getMapValue(unitTypeProperties.get(appInitializers.getMaxOccupancy())));

					// room amenities
					unitType.setRoomAmenities(
							(List<String>) getMapValue(unitTypeProperties.get(appInitializers.getRoomAmenities())));

					// room type
					unitType.setRoomType((String) getMapValue(unitTypeProperties.get(appInitializers.getRoomType())));

					// view type preference options
					unitType.setViewTypePreferenceOptions((List<String>) getMapValue(
							unitTypeProperties.get(appInitializers.getViewTypePreferenceOptions())));
					unitTypes.add(unitType);
				}
			}

			List<Amenity> amenities = new ArrayList<Amenity>();

			List<HashMap<String, Object>> amenityMaps = processChildrenNode(children,
					appInitializers.getPromotedpropertyAmenities(), languageCd);

			if (!CollectionUtils.isEmpty(amenityMaps)) {
				for (HashMap<String, Object> amenityMap : amenityMaps) {
					Amenity amenity = new Amenity();

					HashMap<String, Object> amenityProperties = (HashMap<String, Object>) amenityMap
							.get(AppInitializers.NODE_QUERY_PROPERTIES);

					// amenity name
					amenity.setName((String) getMapValue(
							amenityProperties.get(appInitializers.getPromotedpropertyAmenitiesName())));

					// amenity logo
					List<String> logoPaths = getRealPathImage(
							getMapValue(amenityProperties.get(appInitializers.getPromotedpropertyAmenitiesLogo())));
					if (!CollectionUtils.isEmpty(logoPaths)) {
						amenity.setLogo(logoPaths.get(0));
					}
					amenities.add(amenity);
				}
			}

			// reservation type policies
			property.setReservationTypePolicies(reservationTypePolicies);

			// unit types
			property.setUnitTypes(unitTypes);

			// amenities
			property.setAmenities(amenities);
		}

		return property;
	}

	@SuppressWarnings("unchecked")
	private List<HashMap<String, Object>> processChildrenNode(Map<String, Object> childrenMap, String nodeName,
			String languageCd) {
		List<HashMap<String, Object>> result = null;
		if (childrenMap.get(nodeName) != null) {
			// extract node's id
			String id = ((HashMap<String, String>) childrenMap.get(nodeName)).get(AppInitializers.NODE_QUERY_ID);

			// getting a list of records based on its node id
			HashMap<String, Object> response = getForObject(cmsNodeBuilder(languageCd) + id,
					appInitializers.getCmsBasePathIncludeFullChildren());

			HashMap<String, HashMap<String, Object>> children = (HashMap<String, HashMap<String, Object>>) response
					.get(AppInitializers.NODE_QUERY_CHILDREN);

			if (!CollectionUtils.isEmpty(children)) {
				result = new ArrayList<HashMap<String, Object>>();
				Iterator<Entry<String, HashMap<String, Object>>> iterator = children.entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry<String, HashMap<String, Object>> pair = (Map.Entry<String, HashMap<String, Object>>) iterator
							.next();
					result.add((HashMap<String, Object>) pair.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * Get Errata from RAW JSON data which is returned by Jahia CMS system.
	 * 
	 * @param inputMap
	 *            RAW JSON data which is returned by Jahia CMS system
	 * @param languageCd
	 *            Requested language code
	 * @return Expected Errata object
	 */
	@SuppressWarnings("unchecked")
	private Errata getErrata(HashMap<String, Object> inputMap, String languageCd) {
		Errata errata = null;
		HashMap<String, Object> properties = (HashMap<String, Object>) inputMap.get(AppInitializers.NODE_QUERY_PROPERTIES);
		if (!CollectionUtils.isEmpty(properties)) {
			errata = new Errata();
			errata.setErrataId((Integer) getMapValue(properties.get(appInitializers.getErrataId())));
			if (getMapValue(properties.get(appInitializers.getStartDate())) != null) {
				errata.setStartDate(DateFormatUtils.format((Long) getMapValue(properties.get(appInitializers.getStartDate())),
						AppInitializers.ISO_DATE_FORMAT));
			}
			if (getMapValue(properties.get(appInitializers.getEndDate())) != null) {
				errata.setEndDate(DateFormatUtils.format((Long) getMapValue(properties.get(appInitializers.getEndDate())),
						AppInitializers.ISO_DATE_FORMAT));
			}
			errata.setPropertyId((Integer) getMapValue(properties.get(appInitializers.getPropertyId())));
			errata.setMessage((String) getMapValue(properties.get(appInitializers.getMessage())));
		}
		return errata;
	}

	/**
	 * Build a base context URL for SQL2 to Jahia CMS.
	 * 
	 * @param languageCd The language code
	 * @return Base context URL for SQL 2
	 */
	private StringBuilder cmsQueryBuilder(String languageCd) {
		return new StringBuilder().append(appInitializers.getCmsBaseContextUrl())
				.append(appInitializers.getCmsBaseWorkspace()).append("/" + languageCd)
				.append(appInitializers.getCmsBaseQuery());
	}

	/**
	 * Build a base context URL for Node query (by id) to Jahia CMS.
	 * 
	 * @param languageCd The language code
	 * @return Base context URL for Node query
	 */
	private StringBuilder cmsNodeBuilder(String languageCd) {
		return new StringBuilder().append(appInitializers.getCmsBaseContextUrl())
				.append(appInitializers.getCmsBaseWorkspace()).append("/" + languageCd)
				.append(appInitializers.getCmsBaseNode());
	}

	/**
	 * Utility method to get certain value of specific map data.
	 * 
	 * @param map Map object to get certain value
	 * @return The certain value of a map
	 */
	@SuppressWarnings("unchecked")
	private Object getMapValue(Object map) {
		Object result = null;
		if (!CollectionUtils.isEmpty((HashMap<String, Object>) map)) {
			result = ((HashMap<String, Object>) map).get("value");
		}
		return result;
	}

	/**
	 * Get real image file path by its id(s).
	 * 
	 * @param nodeIds
	 *            Image's node id(s)
	 * @return List of img file path.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getRealPathImage(Object nodeIds) {
		List<String> result = null;
		List<String> listNodeIds = null;
		if (nodeIds instanceof String) {
			listNodeIds = new ArrayList<String>();
			listNodeIds.add((String) nodeIds);
		} else {
			listNodeIds = (ArrayList<String>) nodeIds;
		}
		if (!CollectionUtils.isEmpty(listNodeIds)) {
			result = new ArrayList<String>();
			for (String nodeId : listNodeIds) {
				try {
					HashMap<String, Object> response = getForObject(cmsNodeBuilder(AppInitializers.DEFAULT_LANG) + nodeId,
							appInitializers.getCmsBasePathResolveReferences());
					if (response != null) {
						String imgPath = (String) response.get("path");
						StringBuilder realImgPath = new StringBuilder();
						if (StringUtils.isNotEmpty(imgPath)) {
							realImgPath.append(appInitializers.getCmsBaseExternalUrl())
									.append(appInitializers.getCmsBasePathFileContext()).append(imgPath);
							result.add(realImgPath.toString());
						}
					}
				} catch (RestClientResponseException rcre) {
					result.add("Path Not Found!");
				}
			}
		}
		return result;
	}

	@Override
	public NodeProperty getNodeProperty(String languageCd) {
		StringBuilder urlNode = new StringBuilder().append(appInitializers.getCmsBaseUrl())
				.append("/")
				.append(languageCd)
				.append(appInitializers.getCmsBaseSite())
				.append("/")
				.append(appInitializers.getCmsBasePage());
		NodeProperty result = null;
		HttpEntity<String> httpEntity = new HttpEntity<String>(createHttpHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NodeProperty> cmsResponse = restTemplate.exchange(urlNode.toString(), HttpMethod.GET, httpEntity,
				NodeProperty.class);
		if (cmsResponse != null) {
			result = cmsResponse.getBody();
		}
		return result;
	}

	/**
	 * Processing first node of CSM
	 * 
	 * @param nodeProperty
	 * @param dictionaryModel
	 * @return
	 */
	@Override
	public List<HashMap<String, Object>> processFirstNode(NodeProperty nodeProperty,
			List<HashMap<String, Object>> dictionaryModel, String languageCd) {
		HashMap<String, Object> resultHashMapFirstNode = new HashMap<String, Object>();
		if (nodeProperty.isHasChildren()) {
			resultHashMapFirstNode.put(generateJsonName(nodeProperty.getPath()), putDataIntoChilds(nodeProperty,languageCd));
		} else {
			resultHashMapFirstNode.putAll(corePuttingData(nodeProperty,languageCd));
		}
		dictionaryModel.add(resultHashMapFirstNode);
		return dictionaryModel;
	}

	/**
	 * Put Data from NodeProperty into a Map and recursive NodeProperty if it
	 * has child
	 * 
	 * @param nodeProperty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> putDataIntoChilds(NodeProperty nodeProperty, String languageCd) {
		Map<String, Object> resultHashMap = new HashMap<String, Object>();
		List<NodeProperty> listChilds = nodeProperty.getChildNodes();
		Iterator<NodeProperty> iterator = listChilds.iterator();
		if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntPage())) {
			resultHashMap.put("title", nodeProperty.getText());
		}
		if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattRichText())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattSimpleText())) {
			resultHashMap.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getValue());

		}
		if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntText())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntBigText())) {
			JcrQueryModel jrcModel = getJcrModel(nodeProperty.getIdentifier(), languageCd);
			Map<String, Object> textObject = new HashMap<String, Object>();
			textObject = (Map<String, Object>) jrcModel.getProperties().get("text");
			if (!CollectionUtils.isEmpty(textObject)) {
				resultHashMap.put(generateJsonName(nodeProperty.getPath()), textObject.get("value"));
			} else {
				resultHashMap.put(generateJsonName(nodeProperty.getPath()), "");
			}
		}
		while (iterator.hasNext()) {
			NodeProperty cmsMapItem = iterator.next();
			if (cmsMapItem.isHasChildren()) {
				resultHashMap.put(generateJsonName(cmsMapItem.getPath()), putDataIntoChilds(cmsMapItem,languageCd));

			} else {
				resultHashMap.putAll(corePuttingData(cmsMapItem,languageCd));
			}
		}
		return resultHashMap;
	}

	/**
	 * Processing and putting data of last CMS NodeProperty
	 * 
	 * @param nodeProperty
	 * @param languageCd
	 * @return A Hashmap that contains key-value of returned json
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> corePuttingData(NodeProperty nodeProperty,String languageCd) {
		Map<String, Object> coreResult = new HashMap<String, Object>();
		if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattSampleImage())
				||nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattSimpleImage())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), getRealPathImage(nodeProperty.getImage()));
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntImageReferenceLink())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntFileList())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntBanner())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getPath());
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntContentList())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getText());
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattSimpleText())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattRichText())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getValue());
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattDate())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getDate());
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntText())
				|| nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContentjntBigText())) {
			JcrQueryModel jrcModel = getJcrModel(nodeProperty.getIdentifier(), languageCd);
			Map<String, Object> textObject = new HashMap<String, Object>();
			textObject = (Map<String, Object>) jrcModel.getProperties().get("text");
			if (!CollectionUtils.isEmpty(textObject)) {
				coreResult.put(generateJsonName(nodeProperty.getPath()), textObject.get("value"));
			} else {
				coreResult.put(generateJsonName(nodeProperty.getPath()), "");
			}
		} else if (nodeProperty.getPrimaryNodeType().equals(appInitializers.getStaticContenthyattCheckBox())) {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getCheckbox());
		} else {
			coreResult.put(generateJsonName(nodeProperty.getPath()), nodeProperty.getValue());
		}
		return coreResult;
	}

	/**
	 * Get navigatorId(header, footer) from SQL2 Query.
	 * 
	 * @param jntNavigator
	 *            name of navigator e.g. 'footer', 'header'
	 * @param languageCd
	 * @return jntNavigatorId
	 */
	private String getJntNavigatorId(String jntNavigator, String languageCd) {
		StringBuilder queryContent = new StringBuilder().append("SELECT * FROM ").append("[")
				.append(appInitializers.getStaticContentjntContentList()).append("]").append(" WHERE localname() = ")
				.append(jntNavigator);
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(-1);
		queryObject.setOffset(0);
		LOGGER.debug("getJntAreaId queryObject: " + queryObject.getQuery());
		List<HashMap<String, Object>> response = postForObject(cmsQueryBuilder(languageCd).toString(), queryObject);
		Map<String, Object> result = null;
		String jntNavigatorId = null;
		if (response != null) {
			Iterator<HashMap<String, Object>> iterator = response.iterator();
			while (iterator.hasNext()) {
				result = iterator.next();
				if (result.get("path").toString().contains("hyatt")) {
					jntNavigatorId = result.get("id").toString();
				}
			}
		}
		return jntNavigatorId;
	}

	/**
	 * Get JrcModel from jrcModelId. JrcModel contains all of list informations
	 * of a navigator
	 * 
	 * @param jcrModelId
	 * @param languageCd
	 * @return JcrQueryModel
	 */
	private JcrQueryModel getJcrModel(String jcrModelId, String languageCd) {
		String queryContent = cmsNodeBuilder(languageCd) + jcrModelId;
		LOGGER.debug("getJcrModel queryContent: " + queryContent.toString());
		JcrQueryModel result = null;
		HttpEntity<String> httpEntity = new HttpEntity<String>(createHttpHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JcrQueryModel> jcrResponse = restTemplate.exchange(queryContent, HttpMethod.GET,
				httpEntity, JcrQueryModel.class);
		if (jcrResponse != null) {
			result = jcrResponse.getBody();
		}
		return result;
	}

	/**
	 * Processing the first node of Jrc result request. Usually header or footer
	 * first node (navigator node)
	 * 
	 * @param jcrQueryModel
	 * @param navigatorModel
	 * @param languageCd
	 * @return A list of HashMap contains full of navigator
	 */
	private List<HashMap<String, Object>> processFirstJrcNode(JcrQueryModel jcrQueryModel,
			List<HashMap<String, Object>> navigatorModel, String languageCd) {
		HashMap<String, Object> resultHashMapFirstNode = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(jcrQueryModel.getChildren())) {
			resultHashMapFirstNode.put(generateJsonName(jcrQueryModel.getName()),
					putDataIntoJrcModel(jcrQueryModel, languageCd));
		} else {
			resultHashMapFirstNode.putAll(corePuttingJrcModel(jcrQueryModel));
		}
		navigatorModel.add(resultHashMapFirstNode);
		return navigatorModel;
	}

	/**
	 * Processing and putting Data from a jrcQueryModel into a HashMap. If
	 * jrcQueryModel has child, using recursive to continue processing
	 * 
	 * @param jcrQueryModel
	 * @param languageCd
	 * @return A Hashmap that contains key-value to return
	 */
	private Map<String, Object> putDataIntoJrcModel(JcrQueryModel jcrQueryModel, String languageCd) {
		Map<String, Object> resultHashMap = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(jcrQueryModel.getChildren())) {
			List<JcrQueryModel> listJcrModel = getChildNodesByUuid(jcrQueryModel.getChildren(), languageCd);
			for (JcrQueryModel model : listJcrModel) {
				if (!CollectionUtils.isEmpty(model.getChildren())) {
					resultHashMap.put(generateJsonName(model.getName()), putDataIntoJrcModel(model, languageCd));
				} else {
					resultHashMap.putAll(corePuttingJrcModel(model));
				}
			}
		} else {
			resultHashMap.putAll(corePuttingJrcModel(jcrQueryModel));
		}
		return resultHashMap;
	}

	/**
	 * Processing and putting Data from last jrcQueryModel into a HashMap.
	 * 
	 * @param jcrQueryModel
	 * @param languageCd
	 * @return A Hashmap that contains key-value to return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> corePuttingJrcModel(JcrQueryModel jcrQueryModel) {
		Map<String, Object> coreResult = new HashMap<String, Object>();
		if (jcrQueryModel.getType().equals(appInitializers.getStaticContenthyattSampleImage())
				|| jcrQueryModel.getType().equals(appInitializers.getStaticContenthyattSimpleImage())) {
			HashMap<String, String> imageObject = (HashMap<String, String>) jcrQueryModel.getProperties().get("image");
			if (!CollectionUtils.isEmpty(imageObject)) {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), getRealPathImage(imageObject.get("value")));
			} else {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), "");
			}
		} else if (jcrQueryModel.getType().equals(appInitializers.getStaticContentjntText())
				|| jcrQueryModel.getType().equals(appInitializers.getStaticContentjntBigText())) {
			Map<String, Object> textObject = new HashMap<String, Object>();
			textObject = (Map<String, Object>) jcrQueryModel.getProperties().get("text");
			if (!CollectionUtils.isEmpty(textObject)) {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), textObject.get("value"));
			} else {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), "");
			}
		} else if (jcrQueryModel.getType().equals(appInitializers.getStaticContenthyattRichText())
				|| jcrQueryModel.getType().equals(appInitializers.getStaticContenthyattSimpleText())) {
			Map<String, Object> textObject = new HashMap<String, Object>();
			textObject = (Map<String, Object>) jcrQueryModel.getProperties().get("value");
			if (!CollectionUtils.isEmpty(textObject)) {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), textObject.get("value"));
			} else {
				coreResult.put(generateJsonName(jcrQueryModel.getName()), "");
			}
		} else if (jcrQueryModel.getType().equals(appInitializers.getStaticContentjntImageReferenceLink())) {
			coreResult.put(generateJsonName(jcrQueryModel.getName()), jcrQueryModel.getPath());
		} else if (jcrQueryModel.getType().matches("(?i)(.*)(date)(.*)")) {
			Map<String, Object> valueObject = (Map<String, Object>) jcrQueryModel.getProperties().get("value");
			if (valueObject.get("value") != null) {
				coreResult.put(generateJsonName(generateJsonName(jcrQueryModel.getName())),
						DateFormatUtils.format((Long) valueObject.get("value"), AppInitializers.ISO_DATE_FORMAT));
			}
		} else {
			coreResult.put(generateJsonName(jcrQueryModel.getName()), jcrQueryModel.getName());
		}
		return coreResult;
	}

	@Override
	public List<HashMap<String, Object>> navigatorBuilder(String navigatorName, String languageCd) {
		String headerNodeId = getJntNavigatorId(navigatorName, languageCd);
		JcrQueryModel firstJrcNode = getJcrModel(headerNodeId, languageCd);
		List<HashMap<String, Object>> headerNavigator = new ArrayList<HashMap<String, Object>>();
		headerNavigator = processFirstJrcNode(firstJrcNode, headerNavigator, languageCd);
		return headerNavigator;
	}

	@Override
	public List<Map<String, Object>> listDataByNodeType(Integer nodeId, String languageCd, String nodeType, String whereCondition) throws RestClientException {
		// Build up query details
		StringBuilder queryContent = new StringBuilder()
				.append("SELECT * FROM ")
				.append(nodeType);

		if (nodeId != null) {
			queryContent.append(" WHERE ")
					.append(whereCondition)
					.append(" = ")
					.append(nodeId)
					.append(" OR ")
					.append(generateJsonName(whereCondition)) // convert to JSON_NAME_TYPE
					.append(" = ")
					.append(nodeId);
		}
		
		// -1 to un-limit response records.
		int limit = -1;

		// Process for News node
		if (nodeType.equalsIgnoreCase(appInitializers.getNewsNode())) {
			queryContent.append(" ORDER BY [j:lastPublished] DESC");
			limit = 3; // restrict to top 3 latest news.
		}

		// Build up Jahia CMS's SQL-2 query object
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(limit);
		queryObject.setOffset(0);

		LOGGER.debug("Actual News query: " + queryObject.getQuery());

		HttpEntity<CmsSql2QueryVO> httpEntity = new HttpEntity<CmsSql2QueryVO>(queryObject, createHttpHeaders());

		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<JcrQueryModel>> typeRef = new ParameterizedTypeReference<List<JcrQueryModel>>() {
		};

		// Make a request to CMS for the specified News Article
		ResponseEntity<List<JcrQueryModel>> response = restTemplate.exchange(cmsQueryBuilder(languageCd).toString(), HttpMethod.POST, httpEntity, typeRef);
		List<Map<String, Object>> result = null;
		if (response != null) {
			List<JcrQueryModel> responseBody = response.getBody();
			if (!CollectionUtils.isEmpty(responseBody)) {
				result = new ArrayList<Map<String, Object>>();
				for (JcrQueryModel jcrQueryModel : responseBody) {
					result.add(processDynamicJcrContent(jcrQueryModel, languageCd));
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String listErrorMessageByCode(Integer errorCd, String languageCd) {
		// Build up query details
		StringBuilder queryContent = new StringBuilder()
				.append("SELECT * FROM ")
				.append(appInitializers.getErrorNode());

		if (errorCd != 0) {
			queryContent.append(" WHERE ")
					.append(appInitializers.getErrorCode())
					.append(" = ")
					.append(errorCd);
		}

		// Build up Jahia CMS's SQL-2 query object
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(-1);
		queryObject.setOffset(0);

		LOGGER.debug("Actual Error query: " + queryObject.getQuery());

		// Make a request to CMS for the specified Property
		List<HashMap<String, Object>> response = postForObject(cmsQueryBuilder(languageCd).toString(), queryObject);

		String message = null;
		if (response != null) {
			Iterator<HashMap<String, Object>> iterator = response.iterator();
			while (iterator.hasNext()) {
				HashMap<String, Object> map = iterator.next();
				HashMap<String, Object> properties = (HashMap<String, Object>) map.get(AppInitializers.NODE_QUERY_PROPERTIES);
				if (!CollectionUtils.isEmpty(properties)) {
					message = (String) getMapValue(properties.get(appInitializers.getErrorMessage()));
					break;
				}
			}
		}
		return message;
	}
	/**
	 * Generate Json Name for returned Model
	 * 
	 * @param nodeName/nodePath
	 * @return A string that follow convention of Json name field
	 */
	private String generateJsonName(String nodeName) {
		StringBuilder resultJsonName = new StringBuilder();
		if (nodeName.contains("/")) {
			String[] paths = nodeName.split("/");
			resultJsonName.append(paths[paths.length - 1]);
		} else {
			resultJsonName.append(nodeName);
		}
		boolean hasUpperCase = resultJsonName.toString().matches(".*[A-Z].*");
		if (resultJsonName.toString().contains("-")) {
			return resultJsonName.toString().replaceAll("-", "_").replaceAll("\\s", "").toLowerCase();
		} else if (hasUpperCase) {
			return resultJsonName.toString().replaceAll("(.)(\\p{Lu})", "$1_$2").replaceAll("\\s", "").toLowerCase();
		} else {
			return resultJsonName.toString().toLowerCase();
		}

	}
	
	/**
	 * To process and get the custom-defined properties only from Jahia CMS' properties.
	 * 
	 * @param properties Properties map from Jahia CMS
	 * @return A map of defined properties which excludes all built-in properties like jcr__***, j__*** or jcr:***
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> processDynamicJcrContent(JcrQueryModel jcrQueryModel, String languageCd) {
		Map<String, Object> result = new HashMap<String, Object>();

		// process for children objects
		if (!CollectionUtils.isEmpty(jcrQueryModel.getChildren())) {
			List<JcrQueryModel> listJcrModel = getChildNodesByUuid(jcrQueryModel.getChildren(), languageCd);

			List<Map<String, Object>> lastNodes = new ArrayList<Map<String, Object>>();
			for (JcrQueryModel jcrModel : listJcrModel) {
				if (!CollectionUtils.isEmpty(jcrModel.getChildren())) {
					result.putAll(processDynamicJcrContent(jcrModel, languageCd));
				} else {
					lastNodes.add(processDynamicJcrContent(jcrModel, languageCd));
				}
			}
			if (!CollectionUtils.isEmpty(lastNodes)) {
				result.put(generateJsonName(jcrQueryModel.getName()), lastNodes);
			}
		}

		// process for properties
		if (!CollectionUtils.isEmpty(jcrQueryModel.getProperties())) {
			Iterator<Entry<String, Object>> iterator = jcrQueryModel.getProperties().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> pair = (Map.Entry<String, Object>) iterator.next();
				if (!pair.getKey().matches("^(?i)(j__|jcr__|jcr\\:).*$")) {
					Map<String, Object> valueMap = (Map<String, Object>) pair.getValue();
					Object actualValue = null;
					if (pair.getKey().matches("(?i)(.*)(image|img|logo|photo|picture)(.*)")) {
						// process for Image type
						actualValue = getRealPathImage(valueMap.get("value"));
					} else if (pair.getKey().matches("(?i)(.*)(date)(.*)")) {
						// process for Date type
						if (valueMap.get("value") != null) {
							actualValue = DateFormatUtils.format((Long) valueMap.get("value"), AppInitializers.ISO_DATE_FORMAT);
						}
					} else {
						actualValue = valueMap.get("value");
					}
					result.put(generateJsonName(pair.getKey()), actualValue);
				}
			}
		}
		return result;
	}

	/**
	 * Get a list of JCR content from Jahia CMS using SQL-2 query and specified language code.
	 * @param query SQL-2 query content
	 * @param languageCd Language code
	 * @return A list of JCR content from Jahia CMS
	 */
	private List<JcrQueryModel> getListJcrModel(String query, String languageCd) {
		StringBuilder queryContent = new StringBuilder().append("SELECT * FROM ").append("[")
				.append("jnt:content").append("]").append(" WHERE ").append(query);
		CmsSql2QueryVO queryObject = new CmsSql2QueryVO();
		queryObject.setQuery(queryContent.toString());
		queryObject.setLimit(-1);
		queryObject.setOffset(0);
		LOGGER.debug("getAllJcrModel queryObject: " + queryObject.getQuery());
		List<HashMap<String, Object>> response = postForObject(cmsQueryBuilder(languageCd).toString(), queryObject);
		List<JcrQueryModel> result = null;
		if (response != null) {
			Iterator<HashMap<String, Object>> iterator = response.iterator();
			result = new ArrayList<JcrQueryModel>();
			while (iterator.hasNext()) {
				result.add(buildJrcModel(iterator.next()));
			}
		}
		return result;
	}
	
	/**
	 * Process a JCR response object and return JcrQueryModel.
	 * @param responseMap Response map content from Jahia CMS
	 * @return JcrQueryModel object
	 */
	@SuppressWarnings("unchecked")
	private JcrQueryModel buildJrcModel(HashMap<String, Object> responseMap) {
		JcrQueryModel jcrQueryModel = new JcrQueryModel();
		HashMap<String, Object> properties = (HashMap<String, Object>) responseMap.get(AppInitializers.NODE_QUERY_PROPERTIES);
		HashMap<String, HashMap<String, Object>> children = (HashMap<String, HashMap<String, Object>>) responseMap.get(AppInitializers.NODE_QUERY_CHILDREN);
		jcrQueryModel.setId(responseMap.get("id").toString());
		jcrQueryModel.setName(responseMap.get("name").toString());
		jcrQueryModel.setPath(responseMap.get("path").toString());
		jcrQueryModel.setType(responseMap.get("type").toString());
		jcrQueryModel.setChildren(buildBaseJrcModel(children));
		jcrQueryModel.setProperties(properties);
		return jcrQueryModel;
	}

	/**
	 * Process a JCR response children map object and return into HashMap<String, BaseJcrModel> data type.
	 * @param childNodeMap Response map content from Jahia CMS
	 * @return Children Map object with HashMap<String, BaseJcrModel> data type
	 */
	private HashMap<String, BaseJcrModel> buildBaseJrcModel(HashMap<String, HashMap<String, Object>> childNodeMap) {
		HashMap<String, BaseJcrModel> result = new HashMap<String, BaseJcrModel>();
		Iterator<Entry<String, HashMap<String, Object>>> iterator = childNodeMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, HashMap<String, Object>> pair = (Map.Entry<String, HashMap<String, Object>>) iterator.next();
			BaseJcrModel jcrQueryModel = new BaseJcrModel();
			jcrQueryModel.setId(pair.getValue().get("id").toString());
			jcrQueryModel.setName(pair.getValue().get("name").toString());
			jcrQueryModel.setPath(pair.getValue().get("path").toString());
			jcrQueryModel.setType(pair.getValue().get("type").toString());
			result.put(pair.getKey(), jcrQueryModel);
		}
		return result;
	}
	
	/**
	 * Get JCR Child Nodes by their UUID.
	 * @param node Node which has children inside.
	 * @param languageCd Language code
	 * @return List JCR Child Nodes
	 */
	private List<JcrQueryModel> getChildNodesByUuid(HashMap<String, BaseJcrModel> node, String languageCd) {
		Iterator<String> iterator = node.keySet().iterator();

		StringBuilder uuid = new StringBuilder("[jcr:uuid]=1");

		while (iterator.hasNext()) {
			BaseJcrModel baseJrcItem = node.get(iterator.next());
			uuid.append(" or [jcr:uuid]='" + baseJrcItem.getId() + "'");
		}
		return getListJcrModel(uuid.toString(), languageCd);
	}
}
