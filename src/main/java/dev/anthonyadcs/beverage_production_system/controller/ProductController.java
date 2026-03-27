package dev.anthonyadcs.beverage_production_system.controller;

import dev.anthonyadcs.beverage_production_system.dto.request.*;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.ProductResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.RecipeResponse;
import dev.anthonyadcs.beverage_production_system.service.ProductService;
import dev.anthonyadcs.beverage_production_system.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RecipeService recipeService;

    /* -------------------- ENDPOINTS DE PRODUTOS --------------------*/

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(requestBody));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody @Valid UpdateProductRequest requestBody) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(UUID.fromString(id), requestBody));
    }

    @PatchMapping("/{id}/{action}")
    public ResponseEntity<ProductResponse> updateStatus(@PathVariable String id, @PathVariable String action) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateStatus(UUID.fromString(id), action));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> listById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(UUID.fromString(id)));
    }


    @GetMapping
    public PageResponse<ProductResponse> listAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(name = "active", required = false, defaultValue = "true") List<Boolean> activeValues,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        //Em caso de ordenação não definida, atualiza 'pageable' para um sort por: 'active', 'name' e 'code', respectivamente.
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("active"),
                            Sort.Order.asc("name"),
                            Sort.Order.asc("code")
                    )
            );
        }

        GetAllProductsRequest getAllProductsRequest = new GetAllProductsRequest(
                activeValues,
                name,
                code,
                pageable
        );

        return productService.getAll(getAllProductsRequest);
    }

    /* -------------------- ENDPOINTS DE RECEITAS --------------------*/

    @PostMapping("/{id}/recipes")
    public ResponseEntity<RecipeResponse> createRecipe(@PathVariable String id, @RequestBody @Valid CreateRecipeRequest requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.create(UUID.fromString(id), requestBody));
    }

    @GetMapping("/{id}/recipes")
    public PageResponse<RecipeResponse> listRecipesByProduct(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "true", name = "active") List<Boolean> activeValues,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("active")
                    )
            );
        }

        GetRecipesByProductRequest recipesByProductRequest = new GetRecipesByProductRequest(
                UUID.fromString(id),
                activeValues,
                pageable
        );

        return recipeService.getByProduct(recipesByProductRequest);
    }
}
