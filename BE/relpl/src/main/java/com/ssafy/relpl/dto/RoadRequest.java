package com.ssafy.relpl.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadRequest {
    private String tmap_id;
    private String type;
    private List<List<Double>> coordinates;
    private int lanetype;
    private int speed;
    private int total_distance;
    private int lane;
}
