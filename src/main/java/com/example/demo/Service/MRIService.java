package com.example.demo.Service;

import com.example.demo.Entities.MRI;
import com.example.demo.repository.MRIRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MRIService  {

    private final MRIRepository mriRepository;

    public MRIService(MRIRepository mriRepository) {
        this.mriRepository = mriRepository;
    }

    public List<MRI> getAllImages(){
        return mriRepository.findAll();
    }

    public Optional getImageById(String id){
        return mriRepository.findById(id);
    }

    public void addImage(MRI mri){
        if (mri != null)
            mriRepository.save(mri);
        else
            throw new IllegalArgumentException("Usuário não está passando nada para persistir no banco de dados");
        mriRepository.flush();
    }

    public boolean updateMRI(String id, MRI updatedMRI) {
        Optional<MRI> existingMRIOptional = mriRepository.findById(id);
        if (existingMRIOptional.isPresent()) {
            MRI existingMRI = existingMRIOptional.get();
            existingMRI.setUser(updatedMRI.getUser());
            // Set other fields as needed
            mriRepository.save(existingMRI);
            return true;
        } else {
            // Handle not found scenario
            return false;
        }
    }

    public void deleteMri(String id){

    }


}
