package com.matteovelez.business.product;

import com.matteovelez.business.inventory.StockMovement;
import com.matteovelez.business.inventory.StockMovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository products;
    private final StockMovementRepository movements;

    public ProductService(ProductRepository products, StockMovementRepository movements) {
        this.products = products; this.movements = movements;
    }

    @Transactional(readOnly = true)
    public List<Product> list() { return products.findAll(); }

    @Transactional(readOnly = true)
    public List<Product> lowStock() {
        return products.findAll().stream().filter(p -> p.getQuantity() <= p.getReorderLevel()).toList();
    }

    @Transactional
    public Product create(String sku, String name, int openingQuantity, int reorderLevel) {
        String normalizedSku = sku.trim().toUpperCase();
        if (products.existsBySku(normalizedSku)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU already exists");
        }
        return products.save(new Product(normalizedSku, name.trim(), openingQuantity, reorderLevel));
    }

    @Transactional
    public StockMovement recordMovement(Long productId, String type, int quantity, String note) {
        Product product = products.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        String normalizedType = type.trim().toUpperCase();
        if (!normalizedType.equals("IN") && !normalizedType.equals("OUT")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movement type must be IN or OUT");
        }
        if (quantity <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
        int updated = normalizedType.equals("IN") ? product.getQuantity() + quantity : product.getQuantity() - quantity;
        if (updated < 0) throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock");
        product.setQuantity(updated);
        products.save(product);
        return movements.save(new StockMovement(product, normalizedType, quantity, note == null ? "" : note.trim()));
    }

    @Transactional(readOnly = true)
    public List<StockMovement> movements(Long productId) {
        if (!products.existsById(productId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        return movements.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
