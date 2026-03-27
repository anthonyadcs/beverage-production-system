package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.GetAllRawMaterialsRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.UpdateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.RawMaterialResponse;
import dev.anthonyadcs.beverage_production_system.exception.RawMaterialNotFoundException;
import dev.anthonyadcs.beverage_production_system.repository.RawMaterialRepository;
import dev.anthonyadcs.beverage_production_system.specification.RawMaterialSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RawMaterialService {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Transactional
    public RawMaterialResponse create(CreateRawMaterialRequest rawMaterialRequest) {
        EntityCode code;
        do {
            code = EntityCode.create("RAW");
        } while (rawMaterialRepository.existsByCode(code));

        RawMaterial rawMaterial = new RawMaterial(
                code,
                rawMaterialRequest.name(),
                rawMaterialRequest.description(),
                rawMaterialRequest.minimumStock(),
                rawMaterialRequest.unitOfMeasure()
        );

        rawMaterialRepository.save(rawMaterial);

        return RawMaterialResponse.fromEntity(rawMaterial);
    }

    @Transactional
    public RawMaterialResponse update(UUID id, UpdateRawMaterialRequest rawMaterialRequest){
        RawMaterial rawMaterial = findRawRawMaterialById(id);

        rawMaterial.update(
                rawMaterialRequest.name(),
                rawMaterialRequest.description(),
                rawMaterialRequest.minimumStock(),
                rawMaterialRequest.unitOfMeasure()
        );

        return RawMaterialResponse.fromEntity(
                rawMaterial
        );
    }

    @Transactional
    public RawMaterialResponse updateStatus(UUID id, String action) {
        RawMaterial rawMaterial = findRawRawMaterialById(id);

        switch (action.toLowerCase()) {
            case "activate" -> rawMaterial.activate();
            case "deactivate" -> rawMaterial.deactivate();
            default -> {
                throw new IllegalArgumentException();
            }
        }

        return RawMaterialResponse.fromEntity(rawMaterial);
    }

    public RawMaterialResponse getById(UUID id){
        RawMaterial rawMaterial = findRawRawMaterialById(id);

        return RawMaterialResponse.fromEntity(
                rawMaterial
        );
    }

    public PageResponse<RawMaterialResponse> getAll(
        GetAllRawMaterialsRequest rawMaterialsRequest
    ) {
        Specification<RawMaterial> specification = Specification.where(RawMaterialSpecification.isActiveIn(rawMaterialsRequest.activeValues()));

        if (rawMaterialsRequest.name() != null && !rawMaterialsRequest.name().isBlank()) {
            specification = specification.and(RawMaterialSpecification.hasNameIgnoreCase(rawMaterialsRequest.name()));
        }

        if (rawMaterialsRequest.unitOfMeasures() != null && !rawMaterialsRequest.unitOfMeasures().isEmpty()) {
            specification = specification.and(RawMaterialSpecification.hasUnitOfMeasure(rawMaterialsRequest.unitOfMeasures()));
        }

        if (rawMaterialsRequest.lowStock()) {
            specification = specification.and(RawMaterialSpecification.hasLowStock());
        }

        Page<RawMaterialResponse> rawMaterialPage =
                rawMaterialRepository.findAll(specification, rawMaterialsRequest.pageable()).map(RawMaterialResponse::fromEntity);

        return PageResponse.fromPage(rawMaterialPage);
    }

    //Utilitário utilizado para evitar repetição de código.
    protected RawMaterial findRawRawMaterialById(UUID id){
        return rawMaterialRepository.findById(id).orElseThrow(
                () -> new RawMaterialNotFoundException("Insumo", "id", String.valueOf(id))
        );
    }
}
