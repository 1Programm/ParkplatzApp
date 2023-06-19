package com.gfi.parkplatzapp.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Account {

    private String name;
    private String uuid;

}
