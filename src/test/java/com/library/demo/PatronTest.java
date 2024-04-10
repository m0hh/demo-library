package com.library.demo;

import com.library.demo.exeption.NotFoundExecption;
import com.library.demo.patron.dtos.PatronDTO;
import com.library.demo.patron.entity.Patron;
import com.library.demo.patron.mapper.PatronMapper;
import com.library.demo.patron.repo.PatronRepo;
import com.library.demo.patron.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PatronTest {
    @Mock
    private PatronRepo patronRepo;

    @Mock
    private PatronMapper patronMapper;

    @InjectMocks
    private PatronService patronService;

    private Patron testPatron;

    @BeforeEach
    void setUp() {
        testPatron = new Patron();
        testPatron.setId(1);
        testPatron.setName("Test Patron");
        testPatron.setEmail("example@example.com");
        testPatron.setPhone("0100000000");
    }

    @Test
    void testAddNewPatron() {
        when(patronRepo.save(testPatron)).thenReturn(testPatron);

        Patron savedPatron = patronService.addNewPatron(testPatron);

        assertEquals(testPatron, savedPatron);
        verify(patronRepo, times(1)).save(testPatron);
    }

    @Test
    void testListAllPatronsName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Patron> page = new PageImpl<>(Collections.singletonList(testPatron));

        int page_number = 0;
        when(patronRepo.findByNameContaining("Test",  PageRequest.of(page_number, 10))).thenReturn(page);

        List<PatronDTO> pageDTO = new ArrayList<>();
        PatronDTO dto = new PatronDTO();
        dto.setPhone(testPatron.getPhone());
        dto.setId(testPatron.getId());
        dto.setName(testPatron.getName());
        dto.setEmail(testPatron.getEmail());
        pageDTO.add(dto);

        List<PatronDTO> result = patronService.listAllPatronsName("Test", 0);

        assertEquals(pageDTO, result);
        verify(patronRepo, times(1)).findByNameContaining("Test", PageRequest.of(page_number, 10));
    }

    @Test
    void testListAllPatrons() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Patron> page = new PageImpl<>(Collections.singletonList(testPatron));

        int page_number = 0;
        when(patronRepo.findAll(pageable)).thenReturn(page);

        List<PatronDTO> pageDTO = new ArrayList<>();
        PatronDTO dto = new PatronDTO();
        dto.setPhone(testPatron.getPhone());
        dto.setId(testPatron.getId());
        dto.setName(testPatron.getName());
        dto.setEmail(testPatron.getEmail());
        pageDTO.add(dto);


        List<PatronDTO> result = patronService.listAllPatrons(0);

        assertEquals(pageDTO, result);
        verify(patronRepo, times(1)).findAll(PageRequest.of(page_number, 10));
    }

    @Test
    void testGetPatronId() {
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));

        Patron result = patronService.getPatronId(1);

        assertEquals(testPatron, result);
        verify(patronRepo, times(1)).findById(anyInt());
    }

    @Test
    void testGetPatronId_NotFound() {
        when(patronRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundExecption.class, () -> patronService.getPatronId(1));
        verify(patronRepo, times(1)).findById(1);
    }

    @Test
    void testUpdatePatron() {
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setName("Updated Name");

        Patron updated_test_patron = testPatron;
        updated_test_patron.setName("Updated Name");
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));
        when(patronRepo.save(updated_test_patron)).thenReturn(testPatron);

        Patron updatedPatron = patronService.updatePatron(1, patronDTO);

        assertEquals(testPatron, updated_test_patron);
        verify(patronRepo, times(1)).findById(1);
        verify(patronMapper, times(1)).mapPatronDto(patronDTO, updated_test_patron);
        verify(patronRepo, times(1)).save(updated_test_patron);
    }

    @Test
    void testDeletePatron() {
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));

        patronService.deletePatron(1);

        verify(patronRepo, times(1)).findById(1);
        verify(patronRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeletePatron_NotFound() {
        when(patronRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundExecption.class, () -> patronService.deletePatron(1));
        verify(patronRepo, times(1)).findById(1);
        verify(patronRepo, never()).deleteById(1);
    }
}

