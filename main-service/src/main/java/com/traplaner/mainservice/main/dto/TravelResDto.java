package com.traplaner.mainservice.main.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelResDto {
    private int id;
    private int memberId;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean share;
    private String travelImg;

}

/*
{
    "statusCode": 200,
    "statusMessage": "모든 여행 리스트 조회 완료",
    "result": [
        {
            "createAt": null,
            "updateAt": null,
            "id": 32,
            "memberId": 2,
            "title": "여행-32",
            "startDate": "2024-11-19T17:29:28",
            "endDate": "2024-11-22T17:29:28",
            "share": true,
            "travelImg": "0425d9dc324e4d2a822b8ac905123b08.jpg"
        },
        {
            "createAt": null,
            "updateAt": null,
            "id": 85,
            "memberId": 2,
            "title": "여행-85",
            "startDate": "2024-11-19T17:29:29",
            "endDate": "2024-11-22T17:29:29",
            "share": true,
            "travelImg": "0425d9dc324e4d2a822b8ac905123b07.jpg"
        },
        {
            "createAt": null,
            "updateAt": null,
            "id": 93,
            "memberId": 2,
            "title": "여행-93",
            "startDate": "2024-11-19T17:29:29",
            "endDate": "2024-11-22T17:29:29",
            "share": true,
            "travelImg": "0425d9dc324e4d2a822b8ac905123b01.jpg"
        }
    ]
}
 */
