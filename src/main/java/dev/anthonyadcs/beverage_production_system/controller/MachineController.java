package dev.anthonyadcs.beverage_production_system.controller;

import dev.anthonyadcs.beverage_production_system.dto.request.CreateMachineRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.MachineResponse;
import dev.anthonyadcs.beverage_production_system.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
public class MachineController {
    @Autowired
    private MachineService machineService;

    @PostMapping
    public ResponseEntity<MachineResponse> create(@RequestBody @Valid CreateMachineRequest machineRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.create(machineRequest));
    }
}
