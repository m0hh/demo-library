package com.library.demo.patron.service;

import com.library.demo.exeption.ApiRequestException;
import com.library.demo.exeption.NotFoundExecption;
import com.library.demo.patron.dtos.PatronDTO;
import com.library.demo.patron.entity.Patron;
import com.library.demo.patron.mapper.PatronMapper;
import com.library.demo.patron.repo.PatronRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatronService {
    @Autowired
    private final PatronRepo patronRepo;

    @Autowired
    private final PatronMapper mapper;

    public Patron addNewPatron(Patron patron){
        return patronRepo.save(patron);
    }

    public List<PatronDTO> listAllPatronsName(String name, Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Patron>  patrons =  patronRepo.findByNameContaining(name, pageable);
        return  patrons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PatronDTO> listAllPatrons(Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Patron> patrons =  patronRepo.findAll(pageable);
        return patrons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private PatronDTO convertToDTO(Patron patron) {
        PatronDTO dto = new PatronDTO();
        dto.setId(patron.getId());
        dto.setName(patron.getName());
        dto.setEmail(patron.getEmail());
        dto.setPhone(patron.getPhone());

        return dto;
    }

    public  Patron getPatronId(Integer id){
        return patronRepo.findById(id).orElseThrow(NotFoundExecption::new);
    }

    public Patron updatePatron(Integer id, PatronDTO dto){
        Patron patron =  patronRepo.findById(id).orElseThrow(NotFoundExecption::new);
        mapper.mapPatronDto(dto, patron);
        return patronRepo.save(patron);

    }

    public void deletePatron(Integer id){
        patronRepo.findById(id).orElseThrow(NotFoundExecption::new);
        patronRepo.deleteById(id);
    }
}
