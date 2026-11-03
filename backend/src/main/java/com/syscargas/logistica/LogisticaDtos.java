package com.syscargas.logistica;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class LogisticaDtos {

    private LogisticaDtos() {}

    public record ItemAcc(@NotNull Integer idAcc, @Size(max = 150) String obs) {}

    public record AsignacionReq(@NotNull LocalDate fechaCarg, @NotNull Integer idPed, @NotNull Integer idCond,
                                @NotNull Integer idTc, @NotNull Integer idSemi,
                                @NotBlank @Size(max = 300) String descrip,
                                @NotNull @Size(max = 30) List<@Valid ItemAcc> accesorios) {}

    public record SalidaReq(@NotNull @PastOrPresent LocalDate fechaSal, @NotNull Integer idAsig,
                            @NotNull @DecimalMin("0") BigDecimal kilomSal,
                            @NotNull @DecimalMin("0") BigDecimal combSal) {}

    public record LlegadaReq(@NotNull @PastOrPresent LocalDate fechaLleg, @NotNull Integer idSalid,
                             @NotNull @DecimalMin("0") BigDecimal kilomLleg,
                             @NotNull @DecimalMin("0") BigDecimal combLleg) {}

    public record ViajeReq(@NotNull LocalDate fechaSal, @NotNull Integer idIti, @NotNull Integer idAdu,
                           @NotNull Integer idRuta, @NotNull @Size(max = 30) List<@NotNull Integer> ciudades,
                           @NotNull @Size(max = 30) List<@NotNull Integer> asignaciones) {}

    public record FleteroReq(@NotNull Integer idFlet, @NotNull LocalDate fecha) {}
}
