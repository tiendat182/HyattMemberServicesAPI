package hyatt.api.rcc.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import hyatt.api.config.AppInitializers;
import hyatt.api.model.AppKeyValue;
import hyatt.api.model.Errata;
import hyatt.api.model.ErrataRequest;
import hyatt.api.model.Property;
import hyatt.api.rcc.service.RccService;

@SuppressWarnings("deprecation")
@Service
public class RccServiceImpl implements RccService {
	private static final Logger LOGGER = Logger.getLogger(RccServiceImpl.class);
	
	@Autowired
	AppInitializers appInitializers;

	@Override
	public List<Property> listProperties(Integer propertyId) throws RestClientException {
		List<Property> result = null;

		RestTemplate template = new RestTemplate(createSSLSocketForTest());

		// Create a new HTTP Entity
		HttpEntity<String> httpEntity = new HttpEntity<String>(createHttpHeaders());

		StringBuilder endpointUrl = new StringBuilder().append(appInitializers.getRccPropertyEndpointUrl());

		LOGGER.debug("RCC Property Endpoint URL: " + endpointUrl.toString());
		if (propertyId != null) {
			// Make a request to RCC for the specified Property
			endpointUrl.append("/" + propertyId);
		}
		ParameterizedTypeReference<List<Property>> typeRef = new ParameterizedTypeReference<List<Property>>() {};
		ResponseEntity<List<Property>> response = template.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity, typeRef);
		if (response != null) {
			result = response.getBody();
		}
		return result;
	}

	@Override
	public List<Errata> listErratas(ErrataRequest errataRequest) throws RestClientException {
		LOGGER.debug("RCC Errata Endpoint URL: " + appInitializers.getRccErrataEndpointUrl());

		RestTemplate template = new RestTemplate(createSSLSocketForTest());
		// Make a request to RCC for the specified CmsProperty

		HttpEntity<ErrataRequest> httpEntity = new HttpEntity<ErrataRequest>(errataRequest, createHttpHeaders());

		ParameterizedTypeReference<List<Errata>> typeRef = new ParameterizedTypeReference<List<Errata>>() {};

		// Make a request to RCC for the specified Property
		ResponseEntity<List<Errata>> response = template.exchange(appInitializers.getRccErrataEndpointUrl(), HttpMethod.POST, httpEntity, typeRef);

		List<Errata> result = response.getBody();

		return result;
	}

	/**
	 * Initial common http headers.
	 */
	private HttpHeaders createHttpHeaders() {
		// Initial headers
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setCacheControl(appInitializers.getCacheControl());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, String> authRequest = new HashMap<String, String>();
		authRequest.put(AppInitializers.AUTH_USERNAME, appInitializers.getRccUsername());
		authRequest.put(AppInitializers.AUTH_PASSWORD, appInitializers.getRccPassword());
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(authRequest, headers);

		RestTemplate template = new RestTemplate(createSSLSocketForTest());

		ParameterizedTypeReference<HashMap<String, String>> typeRef = new ParameterizedTypeReference<HashMap<String, String>>() {};

		LOGGER.debug("RCC Authen URL: " + appInitializers.getRccAuthenEndpointUrl());

		ResponseEntity<HashMap<String, String>> response = template.exchange(appInitializers.getRccAuthenEndpointUrl(), HttpMethod.POST, httpEntity, typeRef);

		if (response != null) {
			String authToken = response.getBody().get(AppInitializers.RCC_AUTH_TOKEN);
			if (StringUtils.isNotEmpty(authToken)) {
				headers.set(AppInitializers.AUTH_HEADER, AppInitializers.AUTH_METHOD_OAUTH2 + authToken);
			}
		}

		return headers;
	}

	@Override
	public List<AppKeyValue> listAppKeyValues() throws RestClientException {
		LOGGER.debug("RCC AppKeyValues Endpoint URL: " + appInitializers.getRccAppKeyValuesEndpointUrl());

		RestTemplate template = new RestTemplate(createSSLSocketForTest());
		// Make a request to RCC for the specified CmsProperty

		HttpEntity<String> httpEntity = new HttpEntity<String>(createHttpHeaders());

		ParameterizedTypeReference<List<AppKeyValue>> typeRef = new ParameterizedTypeReference<List<AppKeyValue>>() {};

		// Make a request to RCC for the specified Property
		ResponseEntity<List<AppKeyValue>> response = template.exchange(appInitializers.getRccAppKeyValuesEndpointUrl(), HttpMethod.GET, httpEntity, typeRef);

		List<AppKeyValue> result = response.getBody();

		return result;
	}
	
	/**
	 * TODO: WARNING!!!
	 * This is an utility method which used to self certified ssl connection to "hyfn-hmw-api" server,
	 * and it should be used for testing purpose only. DO NOT use it in production env.
	 * 
	 * TODO: To be replaced before deploying to production env.
	 * 
	 * @return HttpComponentsClientHttpRequestFactory
	 */
	@SuppressWarnings("deprecation")
	private HttpComponentsClientHttpRequestFactory createSSLSocketForTest() {
		SSLConnectionSocketFactory socketFactory = null;
		try {
			socketFactory = new SSLConnectionSocketFactory(
					new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), null, null,
					new X509HostnameVerifier() {
						@Override
						public boolean verify(String s, SSLSession sslSession) {
							return true;
						}
						@Override
						public void verify(String host, SSLSocket ssl) throws IOException {
						}
						public void verify(String host, X509Certificate cert) throws SSLException {
						}
						@Override
						public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
						}
						@Override
						public void verify(String arg0, java.security.cert.X509Certificate arg1) throws SSLException {
							// TODO Auto-generated method stub
						}
					});
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
}
