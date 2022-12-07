package ru.karmazin.vkpostspringmvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

/**
 * @author Vladislav Karmazin
 */
@Entity
@Table(name = "timeOfProcess")
@Getter
@Setter
@NoArgsConstructor
public class Time {
    @Id
    private Long id;

    private LocalTime time;

    public Time(Long id ,LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
