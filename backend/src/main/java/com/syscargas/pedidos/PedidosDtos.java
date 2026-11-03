package com.syscargas.pedidos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class PedidosDtos {

    private PedidosDtos() {}

    public record PedidoReq(@NotNull @PastOrPresent LocalDate fecha, @NotNull Integer idCliente,
                            @NotBlank @Size(max = 300) String descrip) {}

    public record CalculoCotizacion(@NotNull Integer idPaisO, @NotNull Integer idPaisD,
                                    @NotNull @DecimalMin(value = "0.01") BigDecimal peso,
                                    @NotNull @DecimalMin(value = "0.01") BigDecimal volumen) {}

    public record CotizacionReq(@NotNull Integer idCliente, @NotNull Integer idPaisO, @NotNull Integer idPaisD,
                                @NotNull @DecimalMin(value = "0.01") BigDecimal peso,
                                @NotNull @DecimalMin(value = "0.01") BigDecimal volumen,
                                @NotNull @Size(max = 50) List<@NotNull Integer> mercaderias) {}

    public record ItemMerc(@NotNull Integer idMerc, @NotNull @DecimalMin(value = "0.01") BigDecimal cantidad) {}

    public record PedidoFleteReq(@NotNull @PastOrPresent LocalDate fecha, @NotNull Integer idRped,
                                 @NotNull Integer idDesp, @NotNull Integer idTic,
                                 @NotBlank @Size(max = 300) String descrip,
                                 @NotEmpty @Size(max = 100) List<@Valid ItemMerc> items) {}

    public record ItemCrt(@NotNull Integer idMerc,
                          @NotNull @DecimalMin(value = "0.01") BigDecimal cantidad,
                          @NotNull @DecimalMin(value = "0.01") BigDecimal peso,
                          @NotNull @DecimalMin(value = "0.01") BigDecimal volumen,
                          @NotNull @DecimalMin(value = "0") BigDecimal valor,
                          @NotNull Integer idMon) {}

    public record CrtReq(@NotNull @PastOrPresent LocalDate fechaEmi, Integer idPed, @NotNull Integer idMon,
                         @NotNull Integer idExpo, @NotNull Integer idImpo, @NotNull Integer idAdu,
                         @NotNull Integer idPaisO, @NotNull Integer idPaisD,
                         @NotEmpty @Size(max = 100) List<@Valid ItemCrt> items) {}

    public record EventoReq(@NotNull Integer idCrt, @NotNull Integer idTip,
                            @NotNull @PastOrPresent LocalDateTime fechaHora, @Size(max = 300) String coment) {}

    public record ReclamoReq(@NotNull @PastOrPresent LocalDate fecha, @NotNull Integer idRped, Integer idPed,
                             @NotBlank @Size(max = 500) String descrip) {}

    public record ResolucionReclamo(@NotBlank @Size(max = 500) String solucion, @NotNull Boolean avisado) {}
}
