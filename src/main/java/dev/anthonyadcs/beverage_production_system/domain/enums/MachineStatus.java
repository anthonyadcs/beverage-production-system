package dev.anthonyadcs.beverage_production_system.domain.enums;

public enum MachineStatus {
    AVAILABLE,
    IN_USE,
    MAINTENANCE,
    INACTIVE;

    public static boolean isAvailable(MachineStatus status){
        return status == MachineStatus.AVAILABLE;
    }
}
