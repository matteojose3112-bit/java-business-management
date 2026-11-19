package com.matteovelez.business.product;

import com.matteovelez.business.inventory.StockMovement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    public record CreateProductRequest(@NotBlank @Size(max=60) String sku,
        @NotBlank @Size(max=160) String name, @Min(0) int openingQuantity, @Min(0) int reorderLevel) {}
    public record ProductResponse(Long id, String sku, String name, int quantity, int reorderLevel, boolean lowStock, Instant createdAt) {
        static ProductResponse from(Product p) {
            return new ProductResponse(p.getId(), p.getSku(), p.getName(), p.getQuantity(),
                p.getReorderLevel(), p.getQuantity() <= p.getReorderLevel(), p.getCreatedAt());
        }
    }
    public record MovementRequest(@NotBlank String type, @Min(1) int quantity, @Size(max=300) String note) {}
    public record MovementResponse(Long id, Long productId, String type, int quantity, String note, Instant createdAt) {
        static MovementResponse from(StockMovement m) {
            return new MovementResponse(m.getId(), m.getProductId(), m.getType(), m.getQuantity(), m.getNote(), m.getCreatedAt());
        }
    }

    @GetMapping("/products")
    public List<ProductResponse> list() { return service.list().stream().map(ProductResponse::from).toList(); }

    @GetMapping("/products/low-stock")
    public List<ProductResponse> lowStock() { return service.lowStock().stream().map(ProductResponse::from).toList(); }

    @PostMapping("/products")
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        return ProductResponse.from(service.create(request.sku(), request.name(), request.openingQuantity(), request.reorderLevel()));
    }

    @PostMapping("/products/{id}/stock-movements")
    public MovementResponse movement(@PathVariable Long id, @Valid @RequestBody MovementRequest request) {
        return MovementResponse.from(service.recordMovement(id, request.type(), request.quantity(), request.note()));
    }

    @GetMapping("/products/{id}/stock-movements")
    public List<MovementResponse> movements(@PathVariable Long id) {
        return service.movements(id).stream().map(MovementResponse::from).toList();
    }
}
