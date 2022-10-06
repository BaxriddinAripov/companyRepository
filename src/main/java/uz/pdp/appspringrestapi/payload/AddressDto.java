package uz.pdp.appspringrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {

    @NotNull(message = "street bo'sh bo'lmasligi kerak")
    private String street;

    @NotNull(message = "homeNumber bo'sh bo'lmasligi kerak")
    private String homeNumber;
}
