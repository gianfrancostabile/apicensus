package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@Builder
@JsonDeserialize(builder = UserCredentials.UserCredentialsBuilder.class)
@Document(value = "users")
public class UserCredentials implements Serializable {

    @NotBlank
    @Indexed(name = "unq_username", unique = true)
    private String username;

    @NotBlank
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserCredentialsBuilder {

    }
}
