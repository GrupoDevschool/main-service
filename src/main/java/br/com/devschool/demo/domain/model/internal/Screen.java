package br.com.devschool.demo.domain.model.internal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    private Version version;

    @ManyToOne()
    @Nullable
    private Screen screenFather;

    @ManyToOne()
    @Nullable
    private Version cloneVersion;

    private String name;

    private String image;

    private boolean active;

    private Integer order;

    private String urlog;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
