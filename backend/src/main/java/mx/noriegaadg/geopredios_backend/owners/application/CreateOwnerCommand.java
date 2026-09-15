package mx.noriegaadg.geopredios_backend.owners.application;

public record CreateOwnerCommand(
        String name,
        String curp,
        String phone
) {
}