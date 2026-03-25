package dev.anthonyadcs.beverage_production_system.controller;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.RawMaterialResponse;
import dev.anthonyadcs.beverage_production_system.service.RawMaterialService;
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
@RequestMapping("/raw-materials")
public class RawMaterialController {
    @Autowired
    private RawMaterialService rawMaterialService;

    @PostMapping
    public ResponseEntity<RawMaterialResponse> create(@RequestBody @Valid CreateRawMaterialRequest requestBody) {
        return ResponseEntity.status(HttpStatus.OK).body(rawMaterialService.create(requestBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> listById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(rawMaterialService.getById(UUID.fromString(id)));
    }

    @GetMapping
    public PageResponse<RawMaterialResponse> listAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> unitOfMeasure,
            @RequestParam(required = false, defaultValue = "false") Boolean lowStock,
            @RequestParam(required = false, defaultValue = "true") List<Boolean> active,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if(pageable.getSort().isUnsorted()){
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("active"),
                            Sort.Order.asc("name"),
                            Sort.Order.asc("actualStock"),
                            Sort.Order.desc("minimalStock")
                    )
            );
        }

        return rawMaterialService.getAll(name, active, RawMaterialUnitOfMeasure.parseFromStringList(unitOfMeasure), lowStock, pageable);
    }
}
