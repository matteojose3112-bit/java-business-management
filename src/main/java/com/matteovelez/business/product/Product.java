package com.matteovelez.business.product;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 60)
    private String sku;
    @Column(nullable = false, length = 160)
    private String name;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int reorderLevel;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Product() {}
    public Product(String sku, String name, int quantity, int reorderLevel) {
        this.sku = sku; this.name = name; this.quantity = quantity; this.reorderLevel = reorderLevel;
    }
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getReorderLevel() { return reorderLevel; }
    public Instant getCreatedAt() { return createdAt; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
