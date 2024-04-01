package com.ouharri.pharma.controllers;

import com.ouharri.pharma.model.dto.requests.PharmacyRequestDto;
import com.ouharri.pharma.model.dto.responses.PharmacyResponseDto;
import com.ouharri.pharma.services.spec.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller class for handling Pharmacy-related endpoints.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/pharmacies")
public class PharmacyController extends _Controller<UUID, PharmacyRequestDto, PharmacyResponseDto, PharmacyService>{
}
