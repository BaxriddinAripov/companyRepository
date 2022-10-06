package uz.pdp.appspringrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrestapi.entity.Department;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.DepartmentDto;
import uz.pdp.appspringrestapi.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DepartmentController {

    final
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // GET ALL
    /**
     * BARCHA DEPARTMENT LARNI QAYTARISH
     * @return Departments
     */
    @GetMapping("api/department")
    public ResponseEntity<List<Department>> allDepartment(){
        List<Department> departments = departmentService.allDepartment();
        return ResponseEntity.ok(departments);
    }

    // GET BY ID
    /**
     * Department ni ID bo'yicha olish method
     * @param id Integer
     * @return department
     */
    @GetMapping("api/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Integer id){
        Department department = departmentService.getDepartment(id);
        return ResponseEntity.ok(department);
    }

    // CREAT
    /**
     * Department qo'shish method
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    @PostMapping("api/department")
    public ResponseEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // UPDATE BY ID
    /**
     * Department ni ID orqali tahrirlash method
     * @param id Integer
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    @PutMapping("api/department/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE BY ID
    /**
     * Department ni ID orqali o'chirish
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("api/department/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
