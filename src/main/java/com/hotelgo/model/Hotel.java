package com.hotelgo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    private Long id;
    private String name;
    private String location;
    private String createdAt;
    private String updatedAt;
}
