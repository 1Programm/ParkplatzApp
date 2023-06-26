package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {

    private String uid;
    private Long mitarbeiterId;
    private List<String> roles;

}
