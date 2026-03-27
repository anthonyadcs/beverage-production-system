package dev.anthonyadcs.beverage_production_system.controller;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import dev.anthonyadcs.beverage_production_system.dto.request.*;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.RawMaterialResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.StockMovementResponse;
import dev.anthonyadcs.beverage_production_system.service.RawMaterialService;
import dev.anthonyadcs.beverage_production_system.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {
    @Autowired
    private RawMaterialService rawMaterialService;

    @Autowired
    private StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<RawMaterialResponse> create(@RequestBody @Valid CreateRawMaterialRequest requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rawMaterialService.create(requestBody));
    }

    @PostMapping("/{id}/movements")
    public ResponseEntity<StockMovementResponse> createStockMovement(
            @PathVariable String id,
            @RequestBody @Valid CreateStockMovementRequest requestBody
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMovementService.create(UUID.fromString(id), requestBody));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> update(@PathVariable String id, @RequestBody @Valid UpdateRawMaterialRequest requestBody) {
        return ResponseEntity.status(HttpStatus.OK).body(rawMaterialService.update(UUID.fromString(id), requestBody));
    }

    @PatchMapping("/{id}/{action}")
    public ResponseEntity<RawMaterialResponse> updateStatus(@PathVariable String id, @PathVariable String action) {
        return ResponseEntity.status(HttpStatus.OK).body(rawMaterialService.updateStatus(UUID.fromString(id), action));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> listById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(rawMaterialService.getById(UUID.fromString(id)));
    }

    @GetMapping
    public PageResponse<RawMaterialResponse> listAll(
            @RequestParam(required = false) String name,
            @RequestParam(name = "unitOfMeasure", required = false) List<String> unitOfMeasures,
            @RequestParam(required = false, defaultValue = "false") Boolean lowStock,
            @RequestParam(name = "active", required = false, defaultValue = "true") List<Boolean> activeValues,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("active"),
                            Sort.Order.asc("name"),
                            Sort.Order.asc("actualStock"),
                            Sort.Order.desc("minimumStock")
                    )
            );
        }

        GetAllRawMaterialsRequest rawMaterialRequest = new GetAllRawMaterialsRequest(
                name,
                activeValues,
                RawMaterialUnitOfMeasure.parseFromStringList(unitOfMeasures),
                lowStock,
                pageable
        );

        return rawMaterialService.getAll(rawMaterialRequest);
    }

    @GetMapping("/{id}/movements")
    public PageResponse<StockMovementResponse> listAllStockMovementByRawMaterialId(
            @PathVariable String id,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false, defaultValue = "2026-03-25") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("createdAt")
                    )
            );
        }

        Instant parsedStartDate = null;
        Instant parsedEndDate = null;
        if (startDate != null) {
            parsedStartDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }

        if (endDate != null) {
            parsedEndDate = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }

        GetAllByRawMaterialIdRequest getAllByRawMaterialIdRequest = new GetAllByRawMaterialIdRequest(
                UUID.fromString(id),
                pageable,
                StockMovementType.parseFromStringList(type),
                parsedEndDate,
                parsedStartDate
        );

        return stockMovementService.getAllByRawMaterialId(getAllByRawMaterialIdRequest);
    }
}
