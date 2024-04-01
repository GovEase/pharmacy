package com.ouharri.pharma.services.impl;

import com.ouharri.pharma.mapper.MedicineMapper;
import com.ouharri.pharma.model.dto.responses.MedicineResponseDto;
import com.ouharri.pharma.model.entities.Medicine;
import com.ouharri.pharma.model.dto.requests.MedicineRequestDto;
import com.ouharri.pharma.repositories.MedicineRepository;
import com.ouharri.pharma.services.spec.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicineServiceImpl extends _ServiceImp<String, MedicineRequestDto, MedicineResponseDto, Medicine, MedicineRepository, MedicineMapper> implements MedicineService {
}
