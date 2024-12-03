package com.traplaner.travelboardservice.travelBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class MemberDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nickName")
    private String nickName;
}
