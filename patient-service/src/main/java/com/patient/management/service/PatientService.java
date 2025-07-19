package com.patient.management.service;

import com.patient.management.dto.PatientDTO;
import com.patient.management.model.Patient;
import com.patient.management.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    PatientRepository patientRepository;

    public PatientService (PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return
    }

}
