package com.traplaner.travelplanservice.travelplan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
    CREATE TABLE `tbl_journey` (
            `id`        int        NOT NULL  auto_increment primary key,
            `travel_id`        int        NULL,
            `journey_name`        varchar(100)        NOT NULL,
            `accommodation_name`        varchar(255)        NOT NULL,
            `accommodation_road_address_name`        varchar(255)        NOT NULL,
            `start_time`        datetime        NOT NULL,
            `end_time`        datetime        NOT NULL,
            `create_time`        datetime        NOT NULL,
            `update_time`        datetime        NULL,
            `google_map_location_pin_information`        varchar(50)        NOT NULL,
            `reservation_confirm_image_path`        varchar(500)        NULL,
            `share`        BOOLEAN        NOT NULL,
            `place_type`        ENUM('LODGING', 'RESTAURANT', 'LOCATION', 'ETC')        NOT NULL
    );
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="tbl_journey")
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    Travel travel;

    @Column(name = "journey_name", nullable = false)
    private String journeyName;

    @Column(name = "accommodation_name", nullable = false)
    private String accommodationName;

    @Column(name = "accomodation_road_address_name", nullable = false)
    private String accommodationRoadAddressName;

    @Column(nullable = false)
    private int day;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "google_map_location_pin_information")
    private String googleMapLocationPinInformation;

    @Column(name = "reservation_confirm_image_path")
    private String reservationConfirmImagePath;

    private boolean share;

    @Column(name = "journey_img")
    private String journeyImg;

    private int budget;


}