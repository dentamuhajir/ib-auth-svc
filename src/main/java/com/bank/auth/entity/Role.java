package com.bank.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Penting: Menandakan auto-increment (BIGSERIAL / IDENTITY)
    private Long id; // Ubah dari UUID ke Long
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
}
