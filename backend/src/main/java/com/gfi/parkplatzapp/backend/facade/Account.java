package com.gfi.parkplatzapp.backend.facade;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {

    private String uuid;
    private long mitarbeiterId;
    private String name;
    private String[] roles;

}
