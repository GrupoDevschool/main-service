package br.com.devschool.demo.domain.model.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String versionNumber;

    private String gmud;

    private String description;

    private LocalDate deployDate;

    private Boolean status;

    private Integer order;

    @Nullable
    private Integer versionCloneId;

    @ManyToOne()
    private Project project;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
