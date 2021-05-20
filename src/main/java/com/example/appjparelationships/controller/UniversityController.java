package com.example.appjparelationships.controller;

import com.example.appjparelationships.entity.Address;
import com.example.appjparelationships.entity.University;
import com.example.appjparelationships.payload.UniversityDto;
import com.example.appjparelationships.repository.AddressRepository;
import com.example.appjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;


    //READ
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }


    //CREATE
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        //YANGI ADDRES OCHIB OLDIK
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        //YASAB OLGAN ADDRESS OBJECTIMIZNI DB GA SAQLADIK VA U BIZGA SAQLANGAN ADDRESSNI BERDI
        Address savedAddress = addressRepository.save(address);

        //YANGI UNIVERSITET YASAB OLDIK
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University added";
    }

    //UPDATE
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            university.setName(universityDto.getName());

            //universitet addressi
            Address address = university.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            addressRepository.save(address);

            universityRepository.save(university);
            return "University edited";
        }
        return "University not found";
    }


    //DELETE
    @RequestMapping(value = "/university/{id}",method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "University deleted";
    }
}
