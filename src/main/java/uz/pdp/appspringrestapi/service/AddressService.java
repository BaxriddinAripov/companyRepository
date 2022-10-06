package uz.pdp.appspringrestapi.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringrestapi.entity.Address;
import uz.pdp.appspringrestapi.payload.AddressDto;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    final
    AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // GET ALL

    /**
     * BARCHA ADDRESS LARNI QAYTARISH METHOD
     *
     * @return addresses
     */
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    // GET BY ID

    /**
     * ADDRESS NI ID BILAN QAYTARISH METHOD
     *
     * @param id Intrger
     * @return address
     */
    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    // CREAT

    /**
     * ADDRESS QO'SHISH METHOD
     *
     * @param addressDto AddressDto
     * @return ApiResponse
     */
    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreet(addressDto.getStreet());
        if (exists)
            return new ApiResponse("Bunday nomli ko'cha mavjud", false);
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Ma'lumot muvaffaqiyatli saqlandi", true);
    }

    // DELETE BY ID
    /**
     * ADDRESS NI ID ORQALI O'CHIRISH METHOD
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address muvaffaqiyatli o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Bunday address mavjud emas", false);
        }
    }

    // UPDATE BY ID
    /**
     * ADDRESS NI O'ZGSRTIRISH METHOD
     * @param id Integer
     * @param addressDto AddressDto
     * @return ApiResponse
     */
    public ApiResponse editAddress(Integer id, AddressDto addressDto){
        boolean exists = addressRepository.existsByStreetAndIdNot(addressDto.getStreet(), id);
        if (exists)
            return new ApiResponse("Bunday ko'cha mavjud", false);
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas", false);
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Ma'lumot muvaffaqiyatli o'zgartirildi", true);
    }
}
