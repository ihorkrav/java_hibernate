package org.example.models;

import lombok.Data;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name="tabl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date cateCreated;
    @Column(length = 200, nullable = false)
    private String name;
    @Column(length = 200)
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
