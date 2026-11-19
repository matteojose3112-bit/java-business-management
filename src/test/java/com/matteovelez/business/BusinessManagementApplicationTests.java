package com.matteovelez.business;

import com.matteovelez.business.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BusinessManagementApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired ProductRepository products;

    @Test void healthEndpointWorks() throws Exception {
        mvc.perform(get("/health")).andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test void createsProductAndRecordsStockInAndOut() throws Exception {
        String sku = "SKU-" + System.nanoTime();
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content("{\"sku\":\""+sku+"\",\"name\":\"Widget\",\"openingQuantity\":10,\"reorderLevel\":3}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.quantity").value(10));

        Long id = products.findAll().stream().filter(p -> p.getSku().equals(sku)).findFirst().orElseThrow().getId();
        mvc.perform(post("/api/products/"+id+"/stock-movements").contentType(MediaType.APPLICATION_JSON)
            .content("{\"type\":\"OUT\",\"quantity\":4,\"note\":\"Order fulfilment\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.type").value("OUT"));
        mvc.perform(get("/api/products")).andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.sku=='"+sku+"')].quantity").value(6));
    }

    @Test void preventsStockGoingNegative() throws Exception {
        String sku = "LOW-" + System.nanoTime();
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content("{\"sku\":\""+sku+"\",\"name\":\"Limited item\",\"openingQuantity\":2,\"reorderLevel\":1}"))
            .andExpect(status().isOk());
        Long id = products.findAll().stream().filter(p -> p.getSku().equals(sku)).findFirst().orElseThrow().getId();
        mvc.perform(post("/api/products/"+id+"/stock-movements").contentType(MediaType.APPLICATION_JSON)
            .content("{\"type\":\"OUT\",\"quantity\":3}"))
            .andExpect(status().isConflict());
    }

    @Test void rejectsInvalidProductInput() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content("{\"sku\":\" \",\"name\":\"\",\"openingQuantity\":-1,\"reorderLevel\":0}"))
            .andExpect(status().isBadRequest());
    }
}
