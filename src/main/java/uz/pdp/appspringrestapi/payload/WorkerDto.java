package uz.pdp.appspringrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak")
    private String name;

    @NotNull(message = "phoneNumber bo'sh bo'lmasligi kerak")
    private String phoneNumber;

    @NotNull(message = "addressId bo'sh bo'lmasligi kerak")
    private Integer addressId;

    @NotNull(message = "departmentId bo'sh bo'lmasligi kerak")
    private Integer departmentId;
}
