package ee.katrina.lemmikloomad.service;

import ee.katrina.lemmikloomad.dto.OwnerDTO;
import ee.katrina.lemmikloomad.entity.Owner;
import ee.katrina.lemmikloomad.repository.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    ModelMapper modelMapper;
    public List<OwnerDTO> findAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        List<OwnerDTO> ownerDTOs = new ArrayList<>();
//        ModelMapper modelMapper = new ModelMapper();
        System.out.println(modelMapper);
        for (Owner owner : owners) {
            OwnerDTO ownerDTO = modelMapper.map(owner, OwnerDTO.class);
            ownerDTOs.add(ownerDTO);
        }
        return ownerDTOs;
    }

}
