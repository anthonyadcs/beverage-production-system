package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
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

        RawMaterial rawMaterial = new RawMaterial(code, rawMaterialRequest);

        return RawMaterialResponse.fromEntity(
                rawMaterialRepository.save(rawMaterial)
        );
    }

    @Transactional
    public RawMaterialResponse update(UUID id, UpdateRawMaterialRequest rawMaterialRequest){
        RawMaterial rawMaterial = findRawRawMaterialById(id);

        rawMaterial.update(rawMaterialRequest);

        return RawMaterialResponse.fromEntity(
                rawMaterial
        );
    }

    public RawMaterialResponse getById(UUID id){
        RawMaterial rawMaterial = findRawRawMaterialById(id);

        return RawMaterialResponse.fromEntity(
                rawMaterial
        );
    }

    public PageResponse<RawMaterialResponse> getAll(
            String name,
            List<Boolean> activeValues,
            List<RawMaterialUnitOfMeasure> unitOfMeasures,
            boolean lowStock,
            Pageable pageable
    ) {
        Specification<RawMaterial> specification = Specification.where(RawMaterialSpecification.isActiveIn(activeValues));

        if (name != null && !name.isBlank()) {
            specification = specification.and(RawMaterialSpecification.hasNameIgnoreCase(name));
        }

        if (unitOfMeasures != null && !unitOfMeasures.isEmpty()) {
            specification = specification.and(RawMaterialSpecification.hasUnitOfMeasure(unitOfMeasures));
        }

        if (lowStock) {
            specification = specification.and(RawMaterialSpecification.hasLowStock());
        }

        Page<RawMaterialResponse> rawMaterialPage = rawMaterialRepository.findAll(specification, pageable).map(RawMaterialResponse::fromEntity);

        return PageResponse.fromPage(rawMaterialPage);
    }

    //Utilitário utilizado para evitar repetição de código.
    private RawMaterial findRawRawMaterialById(UUID id){
        return rawMaterialRepository.findById(id).orElseThrow(
                () -> new RawMaterialNotFoundException("Insumo", "id", String.valueOf(id))
        );
    }
}
