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
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uri_homolog;

    @ManyToOne()
    private Event event;

    @ManyToOne()
    private Request requestFather;

    private String uri_prod;

    private String description;

    private String layer;

    private boolean status;

    private Integer order;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
