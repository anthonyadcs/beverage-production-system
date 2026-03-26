package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.entity.StockMovement;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateStockMovementRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.GetAllByRawMaterialIdRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.StockMovementResponse;
import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityStateException;
import dev.anthonyadcs.beverage_production_system.repository.StockMovementRepository;
import dev.anthonyadcs.beverage_production_system.specification.StockMovementSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class StockMovementService {
    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private RawMaterialService rawMaterialService;


    @Transactional
    public StockMovementResponse create(UUID id, CreateStockMovementRequest stockMovementRequest) {
        RawMaterial rawMaterial = rawMaterialService.findRawRawMaterialById(id);

        if (!rawMaterial.isActive()) {
            throw new InvalidEntityStateException("O produto está inativo e não pode ter movimentações de estoque vinculadas a ele.");
        }

        BigDecimal previousStock = rawMaterial.getActualStock();

        BigDecimal resultingStock = rawMaterial.calculateStockAfterMovement(
                stockMovementRequest.type(),
                stockMovementRequest.movedQuantity(),
                previousStock
        );

        StockMovement stockMovement = new StockMovement(
                rawMaterial,
                stockMovementRequest.type(),
                stockMovementRequest.movedQuantity(),
                previousStock,
                resultingStock,
                stockMovementRequest.reason()
        );

        stockMovementRepository.saveAndFlush(stockMovement);
        rawMaterial.applyStockMovement(stockMovement.getResultingStock());

        return StockMovementResponse.fromEntity(
                stockMovement
        );
    }

    public PageResponse<StockMovementResponse> getAllByRawMaterialId(GetAllByRawMaterialIdRequest rawMaterialRequest) {
        rawMaterialService.findRawRawMaterialById(rawMaterialRequest.id());

        Specification<StockMovement> specification = Specification.where(StockMovementSpecification.hasRawMaterialId(rawMaterialRequest.id()));

        if (rawMaterialRequest.stockMovementTypes() != null && !rawMaterialRequest.stockMovementTypes().isEmpty()) {
            Specification<StockMovement> typeSpecification = StockMovementSpecification.hasType(rawMaterialRequest.stockMovementTypes());
            specification = specification.and(typeSpecification);
        }

        if (rawMaterialRequest.startRangeDate() != null && rawMaterialRequest.endRangeDate() != null) {
            Specification<StockMovement> dateSpecification = StockMovementSpecification.betweenDates(
                    rawMaterialRequest.startRangeDate(),
                    rawMaterialRequest.endRangeDate()
            );
            specification = specification.and(dateSpecification);
        }

        Page<StockMovementResponse> stockMovements =
                stockMovementRepository.findAll(specification, rawMaterialRequest.pageable()).map(StockMovementResponse::fromEntity);

        return PageResponse.fromPage(stockMovements);
    }
}
