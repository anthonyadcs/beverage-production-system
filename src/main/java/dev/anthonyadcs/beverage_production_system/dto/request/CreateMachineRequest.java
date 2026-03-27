package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.CapacityUnit;
import dev.anthonyadcs.beverage_production_system.domain.enums.MachineType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateMachineRequest(
        @NotBlank(message = "Informar o nome da máquina é necessário para sua criação.")
        @Size(max = 100, message = "O nome da máquina deve ter no máximo 100 caracteres.")
        String name,

        @Size(max = 500, message = "A descrição da máquina deve ter no máximo 500 caracteres.")
        String description,

        @NotNull(message = "Informar o tipo da máquina é necessário para sua criação.")
        MachineType type,

        @NotNull(message = "Informar a capacidade de produção da máquina é necessário para sua criação.")
        @DecimalMin(value = "0.000", inclusive = false, message = "A capacidade de produção deve ser maior que 0.")
        @Digits(integer = 10, fraction = 3, message = "A capacidade de produção deve ter no máximo 7 números na parte inteira e 3 casas decimais.")
        BigDecimal productionCapacity,

        @NotNull(message = "Informar a unidade de medida da capacidade da máquina é necessário para sua criação.")
        CapacityUnit capacityUnit
) {
}
