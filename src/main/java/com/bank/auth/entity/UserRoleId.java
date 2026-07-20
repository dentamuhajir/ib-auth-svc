package com.bank.auth.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;
@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    private UUID userId;
    private Long roleId;
}
