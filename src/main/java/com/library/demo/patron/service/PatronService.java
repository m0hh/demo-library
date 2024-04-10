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

    public Page<Patron> listAllPatronsName(String name, Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return  patronRepo.findByNameContaining(name, pageable);
    }

    public Page<Patron> listAllPatrons(Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return  patronRepo.findAll(pageable);
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
