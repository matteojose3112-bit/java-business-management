package com.matteovelez.business.inventory;

import com.matteovelez.business.product.Product;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;
    @Column(nullable = false, length = 3)
    private String type;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false, length = 300)
    private String note;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected StockMovement() {}
    public StockMovement(Product product, String type, int quantity, String note) {
        this.product = product; this.type = type; this.quantity = quantity; this.note = note;
    }
    public Long getId() { return id; }
    public Long getProductId() { return product.getId(); }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public String getNote() { return note; }
    public Instant getCreatedAt() { return createdAt; }
}
