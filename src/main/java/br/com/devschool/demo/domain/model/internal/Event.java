package br.com.devschool.demo.domain.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    private Screen screen;

    @ManyToOne()
    private EventType eventType;

    private boolean active;

    private int order;

    private String parameter;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
