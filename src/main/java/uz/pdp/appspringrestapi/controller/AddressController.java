package uz.pdp.appspringrestapi.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrestapi.entity.Address;
import uz.pdp.appspringrestapi.payload.AddressDto;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    final
    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // GET ALL
    /**
     * BARCHA ADDRESS LARNI QAYTARISH METHOD
     * @return addresses
     */
    @GetMapping("api/address")
    public ResponseEntity<List<Address>> getAddresses(){
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(addresses);
    }

    // GET BY ID
    /**
     * ADDRESS NI ID BILAN QAYTARISH METHOD
     * @param id Intrger
     * @return address
     */
    @GetMapping("api/address/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id){
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    // CREAT
    /**
     * ADDRESS QO'SHISH METHOD
     * @param addressDto AddressDto
     * @return ApiResponse
     */
    @PostMapping("api/address")
    public HttpEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    //DELETE BY ID
    /**
     * ADDRESS NI ID ORQALI O'CHIRISH METHOD
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("api/address/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    // UPDATE BY ID
    /**
     * ADDRESS NI O'ZGSRTIRISH METHOD
     * @param id Integer
     * @param addressDto AddressDto
     * @return ApiResponse
     */
    @PutMapping("api/address/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
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
