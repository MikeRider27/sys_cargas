package com.syscargas.rendiciones;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public final class RendicionesDtos {

    private RendicionesDtos() {}

    public record AperturaReq(@NotNull Integer idCaj, @NotNull Integer idMon,
                              @NotNull @DecimalMin("0") BigDecimal montoInic) {}

    public record CierreReq(@NotNull @DecimalMin("0") BigDecimal montoCierr) {}

    public record ViaticoReq(@NotNull @PastOrPresent LocalDate fecha, @NotNull Integer idPer, Integer idViaj,
                             @NotNull Integer idApCie, @NotNull @DecimalMin("0.01") BigDecimal monto,
                             @Size(max = 300) String obs) {}

    public record ComprobanteReq(@NotNull Integer idViat, @NotNull @PastOrPresent LocalDate fecha,
                                 @NotBlank @Size(max = 30) String nroComp, @NotBlank @Size(max = 200) String descrip,
                                 @NotNull @DecimalMin("0.01") BigDecimal monto) {}

    public record RendicionReq(@NotNull Integer idViat, @Size(max = 300) String obs) {}
}
