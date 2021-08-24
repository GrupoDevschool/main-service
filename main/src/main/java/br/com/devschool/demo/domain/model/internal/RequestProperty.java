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
public class RequestProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    private Request request;

    private String type;

    private String key;

    private Integer value;

    private String order;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
