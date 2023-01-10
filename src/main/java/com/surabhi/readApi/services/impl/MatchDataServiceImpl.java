package com.surabhi.readApi.services.impl;

import com.surabhi.readApi.services.MatchDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MatchDataServiceImpl implements MatchDataService {

    //private static final Logger logger = LoggerFactory.getLogger(FetchDataServiceImpl.class);
    @Override
    public Boolean matchData(String mongoApi, String cassendraApi) {
        if(mongoApi.equals(cassendraApi)){
            return true;
        }
        return false;
    }
}
