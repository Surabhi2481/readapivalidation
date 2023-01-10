package com.surabhi.readApi.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surabhi.readApi.configuration.EnvProperties;
import com.surabhi.readApi.exceptions.ReadApiException;
import com.surabhi.readApi.responses.MatchDataResponse;
import com.surabhi.readApi.responses.ValidateDataResponse;
import com.surabhi.readApi.services.FetchDataService;
import com.surabhi.readApi.services.MatchDataService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource(value={"classpath:services.properties"})
public class FetchDataServiceImpl implements FetchDataService {

    @Autowired
    MatchDataService matchDataService;

    @Autowired
    EnvProperties env;

    //private static final Logger logger = LoggerFactory.getLogger(FetchDataServiceImpl.class);


    @Override
    public String fetchAllData(String readUrl) {
        String dataList = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(readUrl);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                //logger.error("Got non HTTP 200 response while calling api to fetch all data. statusCode :: {}",
                        //response.getStatusLine().getStatusCode());
                return null;
            }
            if(response.getStatusLine().getStatusCode() == 200) {
                //logger.error("Got HTTP 200 OK response while calling api to fetch all data.");
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    String responseString = EntityUtils.toString(httpEntity);
                    if(!StringUtils.isEmpty(responseString)) {
                        ObjectMapper mapper = new ObjectMapper();
                        dataList = responseString;
                    }
                }

            }
        } catch (Exception e) {
            //logger.error("Error occurred while calling service to fetch data. Exception :: {}", e);
            throw new ReadApiException("Error occurred while calling service to fetch data.");
        }
        return dataList;
    }

    @Override
    public List<ValidateDataResponse> fetchApiResponse(){
        List<ValidateDataResponse> validateDataResponses = new ArrayList<>();
        Map<String, List<String>> apiMap = new HashMap<>();
        List<String> apiUrlAbc = new ArrayList<>();
        apiUrlAbc.add(env.getAbc_cassandraUrl());
        apiUrlAbc.add(env.getAbc_mongoUrl());
        apiMap.put("abc",apiUrlAbc);
        List<String> apiUrlDef = new ArrayList<>();
        apiUrlDef.add(env.getDef_cassandraUrl());
        apiUrlDef.add(env.getDef_mongoUrl());
        apiMap.put("def",apiUrlDef);
        for (Map.Entry<String,List<String>> api : apiMap.entrySet()){
            ValidateDataResponse response = new ValidateDataResponse();
            response.setApiName(api.getKey());
            try{
                String cassandraUrl = api.getValue().get(0);
                String mongoUrl = api.getValue().get(1);
                String cassUrl = fetchAllData(cassandraUrl);
                if(StringUtils.isEmpty(cassUrl)){
                    response.setTestStatus("Cassandra response failed.");
                    validateDataResponses.add(response);
                    continue;
                }
                String monUrl =  fetchAllData(mongoUrl);
                if(StringUtils.isEmpty(monUrl)){
                    response.setTestStatus("Mongo response failed.");
                    validateDataResponses.add(response);
                    continue;
                }
                if(matchDataService.matchData(cassUrl,monUrl)){
                    response.setTestStatus("Cassendra and Mongo are matching.");
                }
                else{
                    response.setTestStatus("Cassendra and Mongo are not matching.");
                }
                validateDataResponses.add(response);
            }
            catch(Exception e){
                System.out.println(e+"inside try catch block");
            }
        }
        return validateDataResponses;
    }
}
