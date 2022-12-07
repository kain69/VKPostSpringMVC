package ru.karmazin.vkpostspringmvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Karmazin
 */
@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    private Long id = 1L;

    private String text;

    private String photo_id;
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean started = false;

    public Post(String text, String photo_id) {
        this.text = text;
        this.photo_id = photo_id;
        this.started = false;
    }
}
