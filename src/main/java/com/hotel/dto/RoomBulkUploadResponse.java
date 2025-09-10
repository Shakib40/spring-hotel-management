package com.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data                       // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates a no-args constructor
@AllArgsConstructor         // generates an all-args constructor
public class RoomBulkUploadResponse {

    private int totalRecords;
    private int successCount;
    private int failureCount;
    private List<String> errors;

}
