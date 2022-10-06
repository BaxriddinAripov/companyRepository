package uz.pdp.appspringrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "name bo'sh bo'masligi kerak")
    private String name;

    @NotNull(message = "companyId bo'sh bo'masligi kerak")
    private Integer companyId;
}
