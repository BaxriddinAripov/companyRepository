package uz.pdp.appspringrestapi.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringrestapi.entity.Address;
import uz.pdp.appspringrestapi.entity.Company;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.CompanyDto;
import uz.pdp.appspringrestapi.repository.AddressRepository;
import uz.pdp.appspringrestapi.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    final
    CompanyRepository companyRepository;
    final
    AddressRepository addressRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    // GET ALL
    /**
     * BARCHA COMPANY LARNI QAYTARISH METHOD
     *
     * @return Company
     */
    public List<Company> allCompany() {
        return companyRepository.findAll();
    }

    // GET BY ID
    /**
     * COMPANY NI ID ORQALI QAYTARISH
     *
     * @param id Integer
     * @return Company
     */
    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    // CREAT
    /**
     * COMPANI SAQLASH METHOD
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists)
            return new ApiResponse("Bunday CorpName mavjud", false);
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas", false);
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddressId(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Ma'lumot muvaffaqiyatli saqlandi", true);
    }

    // UPDATE BY ID
    /**
     * COMPANY NI O'ZGARTIRISH
     * @param id Integer
     * @param companyDto CompanyDto
     * @return ApiResponse
     */
    public ApiResponse editCompany(Integer id, CompanyDto companyDto){
        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (exists)
            return new ApiResponse("Bunday corpName mavjud", false);
        // COMPANY NI TEKSHIRISH
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday company mavjud emas", false);
        // ADDRESS NI TEKSHIRISH
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas", false);
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddressId(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Ma'lumot muvaffaqiyatli o'zgartirildi", true);
    }

    // DELETE BY ID
    /**
     * DELETE COMPANY METHOD
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse deleteCompany(Integer id){
        try{
            companyRepository.deleteById(id);
            return new ApiResponse("Ma'lumot muvaffaqiyatli o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik", false);
        }
    }
}
