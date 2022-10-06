package uz.pdp.appspringrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrestapi.entity.Company;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.CompanyDto;
import uz.pdp.appspringrestapi.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {

    final
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET ALL
    /**
     * BARCHA COMPANY LARNI QAYTARISH METHOD
     * @return Company
     */
    @GetMapping("api/company")
    public ResponseEntity<List<Company>> allCompany(){
        List<Company> companies = companyService.allCompany();
        return ResponseEntity.ok(companies);
    }

    // GET BY ID
    /**
     * COMPANY NI ID ORQALI QAYTARISH
     * @param id Integer
     * @return Company
     */
    @GetMapping("api/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id){
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    // CREAT
    /**
     * COMPANI SAQLASH METHOD
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    @PostMapping("api/company")
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // UPDATE BY ID
    /**
     * COMPANY NI O'ZGARTIRISH
     * @param id Integer
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    @PutMapping("api/company/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE BY ID
    /**
     * DELETE COMPANY METHOD
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("api/company/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
