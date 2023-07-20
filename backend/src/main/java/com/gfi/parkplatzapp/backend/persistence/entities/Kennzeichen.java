package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Kennzeichen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kennzeichenID;
    private String kennzeichen;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Kennzeichen other = (Kennzeichen) obj;
        return other.getKennzeichenID() == this.getKennzeichenID();
    }

}
