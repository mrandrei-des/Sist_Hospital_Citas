package com.hospital.citas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.citas.repository.CodigoResetContrasennaRepository;

@Service
public class CodigoResetContrasennaService {
    @Autowired
    private CodigoResetContrasennaRepository codigoResetContrasennaRepository;
}
