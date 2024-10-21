package org.example.demos.topn.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserBehavior {
    private Long user_id;
    private Long item_id;
    private int behavior_type;
    private String user_geohash;
    private int item_category;
    private String time;
}
