package org.example.models;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="tbl_photos")
public class ProductPhoto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name="name", nullable = false)
        private String name;

        @ManyToOne
        @JoinColumn(name="product_id", nullable = false)
        private Product product;

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", product=" + product.getName() +
                    '}';
        }

}
