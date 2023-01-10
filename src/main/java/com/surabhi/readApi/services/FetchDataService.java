package com.surabhi.readApi.services;

import com.surabhi.readApi.responses.ValidateDataResponse;

import java.util.List;

public interface FetchDataService {
    String fetchAllData(String readUrl);

    List<ValidateDataResponse> fetchApiResponse();

    //String fetchAlldata();

}
