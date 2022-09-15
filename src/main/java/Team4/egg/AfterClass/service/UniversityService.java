package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.University;
import Team4.egg.AfterClass.repository.UniversityRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService implements StringsMethods {
    private final UniversityRepository universityRepository;

    @Transactional
    public void createUniversity(University university) throws ErrorService {
        university.setName(transformString(university.getName()));
        if (validateUniversity(university)) {
            universityRepository.save(university);
        }
    }

    @Transactional
    public void updateUniversity(University university) throws ErrorService {
        University uni = universityRepository.findById(university.getId()).get();
        university.setName(transformString(university.getName()));
        if (validateUniversity(university)) {
            uni.setName(university.getName());
            universityRepository.save(uni);
        }
    }

    @Transactional(readOnly = true)
    public List<University> getAll() {
        return universityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));//se utiliza para ORDENAR la lista
    }

    @Transactional(readOnly = true)
    public University getById(int id) {
        return universityRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(int id) {
        universityRepository.deleteById(id);
    }

//    @Transactional
//    public void enableById(int id){universityRepository.enableById(id);}

    @Transactional(readOnly = true)
    public Boolean validateUniversity(University uni) throws ErrorService {
        University u =  universityRepository.findByName(uni.getName());
        if(!uni.getName().isEmpty()){
            if(u == null){
                return true;
            } else {
                throw new ErrorService("ERROR: There is already a university registered under that name");
            }
        }else{
            throw new ErrorService("ERROR: Invalid name");
        }
    }
}