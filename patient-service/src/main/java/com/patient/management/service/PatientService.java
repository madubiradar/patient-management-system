package com.patient.management.service;

import com.patient.management.dto.PatientRequestDTO;
import com.patient.management.dto.PatientResponseDTO;
import com.patient.management.exception.EmailAlreadyExistsException;
import com.patient.management.exception.PatientNotFoundException;
import com.patient.management.mapper.PatientMapper;
import com.patient.management.model.Patient;
import com.patient.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientService (PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients
                .stream()
                .map(PatientMapper::toDTO)
                .toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){


        //Email should be unique
        boolean exists = patientRepository.existsByEmail(patientRequestDTO.getEmail());
        if(!exists){
            Patient patient = PatientMapper.toModel(patientRequestDTO);
            Patient savedPatient =   patientRepository.save(patient);
            return PatientMapper.toDTO(savedPatient);
        } else {
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }

    }

    public PatientResponseDTO updatePatient(UUID uuid, PatientRequestDTO patientRequestDTO){
        Patient existingPatient = patientRepository.findById(uuid).orElseThrow(
                () -> new PatientNotFoundException("Patient Not Found with Id "+ uuid)
        );

        boolean exists = patientRepository.existsByEmail(patientRequestDTO.getEmail());
        if(exists){
            existingPatient.setName(patientRequestDTO.getName());
            existingPatient.setEmail(patientRequestDTO.getEmail());
            existingPatient.setAddress(patientRequestDTO.getAddress());
            existingPatient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
            existingPatient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        } else {
            throw new PatientNotFoundException("Patient with same email id not found "+ patientRequestDTO.getEmail());
        }
        Patient updatedPatient = patientRepository.save(existingPatient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID uuid){
        patientRepository.deleteById(uuid);
    }

}
