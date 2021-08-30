package br.com.devschool.demo.domain.model.internal;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "version")
    private List<Screen> screens;
}
