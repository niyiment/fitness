package com.niyiment.fitness.external;

import com.niyiment.fitness.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "extenal_api_setting")
@Data
public class ExternalApiSetting extends BaseEntity {
    @Column(name = "api_name", unique = true, nullable = false)
    private String apiName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    private String token;

    private Boolean active = true;
}
