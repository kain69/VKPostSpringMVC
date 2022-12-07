package ru.karmazin.vkpostspringmvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Karmazin
 */
@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Should be not blank")
    private String name;
    @NotNull(message = "Should be not null")
    @Negative(message = "Should be negative")
    private Integer group_id;

    public Group(String name, Integer group_id) {
        this.name = name;
        this.group_id = group_id;
    }
}
