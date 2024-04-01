package com.ouharri.pharma.controllers;

import com.ouharri.pharma.model.dto.responses.MedicineResponseDto;
import com.ouharri.pharma.model.dto.requests.MedicineRequestDto;
import com.ouharri.pharma.services.spec.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling Medicine-related endpoints.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/medicines")
public class MedicineController extends _Controller<String, MedicineRequestDto, MedicineResponseDto, MedicineService>{
}
