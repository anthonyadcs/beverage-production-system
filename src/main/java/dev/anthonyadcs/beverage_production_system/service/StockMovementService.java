package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.entity.StockMovement;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateStockMovementRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.StockMovementResponse;
import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityStateException;
import dev.anthonyadcs.beverage_production_system.repository.StockMovementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

        if(!rawMaterial.isActive()){
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
}
