package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.RawMaterialResponse;
import dev.anthonyadcs.beverage_production_system.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawMaterialService {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public RawMaterialResponse create(CreateRawMaterialRequest rawMaterialRequest){
        EntityCode code;
        do{
            code = EntityCode.create("PROD");
        } while(rawMaterialRepository.existsByCode(code));

        RawMaterial rawMaterial = new RawMaterial(code, rawMaterialRequest);

        return RawMaterialResponse.fromEntity(
                rawMaterialRepository.save(rawMaterial)
        );
    }

}
