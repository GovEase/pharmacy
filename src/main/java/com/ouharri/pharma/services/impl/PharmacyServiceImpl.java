package com.ouharri.pharma.services.impl;

import com.ouharri.pharma.mapper.PharmacyMapper;
import com.ouharri.pharma.model.dto.requests.PharmacyRequestDto;
import com.ouharri.pharma.model.dto.responses.PharmacyResponseDto;
import com.ouharri.pharma.model.entities.Pharmacy;
import com.ouharri.pharma.repositories.PharmacyRepository;
import com.ouharri.pharma.services.spec.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl extends _ServiceImp<UUID, PharmacyRequestDto, PharmacyResponseDto, Pharmacy, PharmacyRepository, PharmacyMapper> implements PharmacyService {
}
