package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.entity.Subject;
import Team4.egg.AfterClass.repository.StudyGroupRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService implements StringsMethods {
    private final StudyGroupRepository studyGroupRepository;
    private StudyGroup sGroup;
    private String view;

    private final ImageService imageService;


    @Transactional
    public void createStudyGroup(StudyGroup studyGroup ) throws ErrorService {

        studyGroup.setName(transformString(studyGroup.getName()));
        studyGroup.setCreation_date(LocalDate.now());

        if(validateStudyGroup(studyGroup)){
            studyGroupRepository.save(studyGroup);
        }
    }

    @Transactional(readOnly = true)
    public StudyGroup getActualGroup(){
      int idGroup= studyGroupRepository.findLastId();

      return getById(idGroup);
    }

    @Transactional
    public void updateStudyGroup(StudyGroup studyGroup,MultipartFile profile ,MultipartFile cover) throws ErrorService {
        StudyGroup sgroup = studyGroupRepository.findById(studyGroup.getId()).get();
        studyGroup.setName(transformString(studyGroup.getName()));
        //arreglar validacion
        if(validateStudyGroupUpdate(studyGroup)){
            sgroup.setName(studyGroup.getName());
            if (!profile.isEmpty()) sgroup.setProfile_img(imageService.copyImageGroup(profile));

            if (!cover.isEmpty()) sgroup.setCover_img(imageService.copyImageGroup(cover));

            sgroup.setDescription(studyGroup.getDescription());
            sgroup.setSubject(studyGroup.getSubject());
            sgroup.setLimit_people(studyGroup.getLimit_people());
            sgroup.setLink(studyGroup.getLink());
            studyGroupRepository.save(sgroup);
        }

    }

    @Transactional(readOnly = true)
    public List<StudyGroup> getAll(){
        return studyGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public StudyGroup getById(int id) {
        return studyGroupRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(int id){
        studyGroupRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean validateStudyGroup(StudyGroup studyGroup) throws ErrorService {
        StudyGroup sg = studyGroupRepository.findByNameAndSubject(studyGroup.getName(), studyGroup.getSubject().getId(),studyGroup.getSubject().getCareer().getId());

        if(!studyGroup.getName().isEmpty() && (studyGroup.getSubject() != null)) {
            if (sg == null) {
                return true;
            } else {
                throw new ErrorService("ERROR: There is already a Study Group registered under that name");
            }
        }else{
            throw new ErrorService("ERROR: Invalid name");
        }
    }

    @Transactional(readOnly = true)
    public Boolean validateStudyGroupUpdate(StudyGroup studyGroup) throws ErrorService {
        StudyGroup sg = studyGroupRepository.findByNameAndSubject(studyGroup.getName(), studyGroup.getSubject().getId(),studyGroup.getSubject().getCareer().getId());

        if(!studyGroup.getName().isEmpty() && (studyGroup.getSubject() != null)){
            if(!(sg.getName().equals(studyGroup.getName())) && (sg.getSubject().getId() != studyGroup.getSubject().getId())){
                if (sg == null) {
                    return true;
                } else {
                    throw new ErrorService("ERROR: There is already a Study Group registered under that name");
                }
            }
        }else{
            throw new ErrorService("ERROR: Invalid name");
        }
        return true;
    }

    ///////METODOS EXTRAS
    public void setGroup(StudyGroup group){
         sGroup = group;
    }

    public StudyGroup getGroup(){
       return sGroup;
    }

    public void setView(String url){
        view = url;
    }

    public String getView(){
        return view;
    }
}
