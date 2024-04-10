package com.library.demo.patron.controller;

import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.exeption.ApiRequestException;
import com.library.demo.patron.dtos.PatronDTO;
import com.library.demo.patron.entity.Patron;
import com.library.demo.patron.repo.PatronRepo;
import com.library.demo.patron.service.PatronService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patrons")
@AllArgsConstructor
public class PatronController {

    @Autowired
    private final PatronService patronService;

    @PostMapping("")
    public Patron addPatron(@RequestBody @Valid Patron patron, final BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            String errorMessage = String.join(", ", errors);
            throw new ApiRequestException(errorMessage);
        }
        return patronService.addNewPatron(patron);
    }

    @GetMapping("")
    public List<PatronDTO> listPatrons(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false) Integer page
    ){
        if (page == null){
            throw new ApiRequestException("Specify the page");
        }

        if (name == null){
            return patronService.listAllPatrons(page);
        }else {
            return patronService.listAllPatronsName(name, page);
        }
    }

    @Cacheable("application")
    @GetMapping("/{id}")
    public Patron getPatron(@PathVariable Integer id){
        return patronService.getPatronId(id);
    }

    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable Integer id, @RequestBody PatronDTO dto) {
        return patronService.updatePatron(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Integer id){
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
