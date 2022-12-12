package ru.karmazin.vkpostspringmvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Karmazin
 */
@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
public class VkAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the name")
    private String name;
    @NotNull(message = "Please fill the user_id")
    private Integer user_id;
    @NotBlank(message = "Please fill the access_token")
    private String access_token;
    @NotNull
    private Boolean selected = false;


    public VkAccount(String name, Integer user_id, String access_token) {
        this.name = name;
        this.user_id = user_id;
        this.access_token = access_token;
    }
}
