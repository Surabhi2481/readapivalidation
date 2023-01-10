package com.surabhi.readApi.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDataResponse {

    private Date requestTimeStamp;
    private Date responseTimeStamp;
    private String status;
    private String description;
    private List<ValidateDataResponse> response;

}
