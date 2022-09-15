package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.Career;

import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.entity.University;
import Team4.egg.AfterClass.repository.CareerRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService implements StringsMethods {

    private final CareerRepository careerRepository;

    @Transactional
    public void createCareer(Career newCareer) throws ErrorService {
        newCareer.setName(transformString(newCareer.getName()));

        if(validateCareer(newCareer)){
            careerRepository.save(newCareer);
        }
    }

    @Transactional
    public void updateCareer(Career updateCareer) throws ErrorService {
        Career career = careerRepository.findById(updateCareer.getId()).get();
        updateCareer.setName(transformString(updateCareer.getName()));

        if(validateCareer(updateCareer)){
            career.setName(updateCareer.getName());
            career.setUniversity(updateCareer.getUniversity());
            careerRepository.save(career);
        }
    }

    @Transactional(readOnly = true)
    public Career getById(Integer id) {
        return careerRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Career> getAll() {
        return careerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public List<Career> getAllByUniversity(int idUni) {
        return careerRepository.findAllByUniversity(idUni);
    }

    //ver
//    @Transactional
//    public void enableById(Integer id) {careerRepository.enableById(id); }

    @Transactional
    public void deleteById(Integer id) {
        careerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean validateCareer(Career career) throws ErrorService {
        Career c = careerRepository.findByNameAndUniversity(career.getName(), career.getUniversity().getId());
        if (!career.getName().isEmpty() && (career.getUniversity() != null)) {
            if (c == null) {
                return true;
            } else {
                throw new ErrorService("ERROR: There is already a career registered under that name");
            }
        }else{
            throw new ErrorService("ERROR: Invalid name");
        }
    }

}