package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.Career;
import Team4.egg.AfterClass.entity.Subject;
import Team4.egg.AfterClass.repository.SubjectRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService implements StringsMethods {
    private final SubjectRepository subjectRepository;

    @Transactional
    public void createSubject(Subject newSubject) throws ErrorService {
        newSubject.setName(transformString(newSubject.getName()));
        if (validateSubject(newSubject)) {
            subjectRepository.save(newSubject);
        }
    }

    @Transactional
    public void updateSubject(Subject modifiedSubject) throws ErrorService {
        Subject subject = subjectRepository.findById(modifiedSubject.getId()).get();
        modifiedSubject.setName(transformString(modifiedSubject.getName()));

        if(validateSubject(modifiedSubject)){
            subject.setName(modifiedSubject.getName());
            subject.setCareer(modifiedSubject.getCareer());
            subjectRepository.save(subject);
        }
    }

    @Transactional(readOnly = true)
    public Subject getById(Integer id) {
        return subjectRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Subject> getAll() {
        return subjectRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

//    @Transactional
//    public void enableById(Integer id) {
//        subjectRepository.enableById(id);
//    }

    @Transactional(readOnly = true)//ver despues
    public List<Subject> getAllByCareer(int idCareer) {
        return subjectRepository.findAllByCareer(idCareer);
    }

    @Transactional(readOnly = true)
    public Boolean validateSubject(Subject subject) throws ErrorService {
        Subject s = subjectRepository.findByNameAndCareer(subject.getName(), subject.getCareer().getId());

        if (!subject.getName().isEmpty() && (subject.getCareer() != null)) {
            if (s == null) {
                return true;
            } else {
                throw new ErrorService("ERROR: There is already a subject registered under that name");
            }
        } else {
            throw new ErrorService("ERROR: Invalid name");
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        subjectRepository.deleteById(id);
    }
}