package com.userservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Email;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    //    @NotBlank
//    @Size(min = 5, max = 40)
    @Id
    @Indexed(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    private String number;
    @Id
    @Indexed(unique = true)
    @Email(message = "Invalid email address")
    private String email;
    //    @NotBlank
//    @Size(min = 8, max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
