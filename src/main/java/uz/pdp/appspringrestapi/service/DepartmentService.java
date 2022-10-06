package uz.pdp.appspringrestapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appspringrestapi.entity.Company;
import uz.pdp.appspringrestapi.entity.Department;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.DepartmentDto;
import uz.pdp.appspringrestapi.repository.CompanyRepository;
import uz.pdp.appspringrestapi.repository.DepartmentRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    final
    DepartmentRepository departmentRepository;
    final
    CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    // GET ALL
    /**
     * BARCHA DEPARTMENT LARNI QAYTARISH
     * @return Departments
     */
    public List<Department> allDepartment(){
        return departmentRepository.findAll();
    }

    //GET BY ID
    /**
     * Department ni ID bo'yicha olish method
     * @param id Integer
     * @return department
     */
    public Department getDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    // CREAT
    /**
     * Department qo'shish method
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto){
        // COMPANY TEKSHIRISH
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday company mavjud emas", false);
        //NAME VA COMPANY TEKSHIRISH
        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), optionalCompany.get());
        if (exists)
            return new ApiResponse("Bunday name va company mavjud", false);
        // DEPARTMENT YARATISH
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompanyId(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Malumot muvaffaqiyatli saqlandi", true);
    }

    // UPDATE BY ID
    /**
     * Department ni ID orqali tahrirlash method
     * @param id Integer
     * @param departmentDto DepartmentDto
     * @return ApiResponse
     */
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto){
        // DEPARTMENT NI TEKSHIRISH
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas", false);
        // COMPANY NI TEKSHIRISH
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday company mavjud emas", false);
        // DEPARTMENT NAME & COMPANY ID ORQALI TEKSHIRISH
        boolean exists = departmentRepository.existsByNameAndCompanyIdNot(departmentDto.getName(), optionalCompany.get());
        if (exists)
            return new ApiResponse("Bunday department va company mavjud", false);
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompanyId(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Ma'lumot muvaffaqiyatli o'zgartirildi", true);
    }

    // DELETE BY ID
    /**
     * Department ni ID orqali o'chirish
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse deleteDepartment(Integer id){
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Ma'lumot o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }
}
