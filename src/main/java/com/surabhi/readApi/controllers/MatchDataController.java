package com.surabhi.readApi.controllers;

import com.surabhi.readApi.exceptions.ReadApiException;
import com.surabhi.readApi.responses.MatchDataResponse;
import com.surabhi.readApi.responses.ValidateDataResponse;
import com.surabhi.readApi.services.FetchDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MatchDataController {
    //private static final Logger logger = LoggerFactory.getLogger(MatchDataController.class);

    @Autowired
    FetchDataService fetchDataService;

    //@Autowired
    private MatchDataResponse matchDataResponse = new MatchDataResponse();

    @RequestMapping(value = "/fetch-data-auto", method = RequestMethod.GET)
    public ResponseEntity<?> fetchData(){
        //logger.info("Testing fetching data");
//        List<String> apiName = new ArrayList<>();
//        apiName.add("abc");
//        //apiName.add("CassendraApi");
//        for(String api : apiName){
//            fetchDataService.fetchAllData(api+"_cassendraUrl");
//        }
        matchDataResponse.setRequestTimeStamp(Date.from(Instant.now()));
        try{
            List<ValidateDataResponse> responseList = fetchDataService.fetchApiResponse();
            System.out.println(responseList);
            if(responseList.size() == 0){
                matchDataResponse.setStatus("FAILURE");
                matchDataResponse.setDescription("Bad Exception");
            }
            else{
                matchDataResponse.setResponse(responseList);
                matchDataResponse.setStatus("SUCCESS");
            }
        }
        catch(Exception e){
            //logger.error("Error occurred while calling service to fetch data. Exception :: {}", e);
            //throw new ReadApiException("Error occurred while calling service to fetch data.");
            System.out.println(e);
        }
        matchDataResponse.setResponseTimeStamp(Date.from(Instant.now()));
        return new ResponseEntity<>(matchDataResponse, HttpStatus.OK);
    }
}
