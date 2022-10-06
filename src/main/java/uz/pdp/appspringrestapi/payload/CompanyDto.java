package uz.pdp.appspringrestapi.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "corpName bo'sh bo'masligi kerak")
    private String corpName;

    @NotNull(message = "directorName bo'sh bo'masligi kerak")
    private String directorName;

    @NotNull(message = "addressId bo'sh bo'masligi kerak")
    private Integer addressId;
}
