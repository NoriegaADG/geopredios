package mx.noriegaadg.geopredios_backend.owners.application;

public record UpdateOwnerCommand(
        String name,
        String curp,
        String phone,
        long version
) {
}