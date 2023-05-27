package com.densoft.productsservice.rest;

import com.densoft.productsservice.command.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final Environment environment;
    private final CommandGateway commandGateway;


    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .title(createProductRestModel.getTitle())
                .quantity(createProductRestModel.getQuantity())
                .productId(UUID.randomUUID().toString())
                .build();

        try {
            return commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }


    }

    @GetMapping
    public String getProduct() {
        return "Http get handled: " + environment.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "Http put handled";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Http delete handled";
    }
}
