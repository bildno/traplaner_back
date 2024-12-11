package com.traplaner.travelboardservice.travelBoard.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nickName")
    private String nickName;
}
