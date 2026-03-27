package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.Machine;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateMachineRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.MachineResponse;
import dev.anthonyadcs.beverage_production_system.repository.MachineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
    @Autowired
    private MachineRepository machineRepository;

    @Transactional
    public MachineResponse create(CreateMachineRequest machineRequest){
        EntityCode code;
        do{
            code = EntityCode.create("MACH");
        } while(machineRepository.existsByCode(code));

        Machine machine = new Machine(
                machineRequest.name(),
                machineRequest.description(),
                code,
                machineRequest.type(),
                machineRequest.productionCapacity(),
                machineRequest.capacityUnit()
        );

        machineRepository.save(machine);

        return MachineResponse.fromEntity(machine);
    }
}
