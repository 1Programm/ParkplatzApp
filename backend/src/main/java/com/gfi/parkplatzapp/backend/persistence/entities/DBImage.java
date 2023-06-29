package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DBImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(nullable = false, length = 1000000000)
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DBImage dbImage = (DBImage) o;
        return id != null && Objects.equals(id, dbImage.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
