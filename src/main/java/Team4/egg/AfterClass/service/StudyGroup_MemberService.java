package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.*;
import Team4.egg.AfterClass.repository.StudyGroup_MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroup_MemberService {

    private final UserService userService;
    private final StudyGroupService studyGroupService;

    private final StudyGroup_MemberRepository sGMRepository;

    @Transactional
    public void addAdmin(int idUser) {

        StudyGroup group = studyGroupService.getActualGroup();
        Member member = userService.getMember(idUser);

        if (group != null && member != null) {
            StudyGroup_Member sGM = new StudyGroup_Member(0, member, group, RoleGroup.ADMIN);
            sGMRepository.save(sGM);
        }
    }

    @Transactional
    public void addMember(int idUser, int idGroup) {
        StudyGroup group = studyGroupService.getById(idGroup);
        Member member = userService.getMember(idUser);

        if (group != null && member != null) {
            StudyGroup_Member sGM = new StudyGroup_Member(0, member, group, RoleGroup.MEMBER);
            sGMRepository.save(sGM);
        }
    }

    @Transactional(readOnly = true)
    public List<StudyGroup> getAllByMember(int idUser) {
        Member member = userService.getMember(idUser);
        return sGMRepository.findAllByMember(member.getId());
    }

    @Transactional(readOnly = true)
    public List<User> getAllMembersByGroup(StudyGroup group) {
        return sGMRepository.findAllMembersByGroup(group.getId());
    }

    @Transactional(readOnly = true)
    public List<StudyGroup> getAllStudyGroupsAvailable(Member member) {

        List<StudyGroup> lista = new ArrayList<StudyGroup>();
        List<StudyGroup> lista2 = sGMRepository.findAllStudyGroupsAvailable(member.getId(),member.getCareer().getId());

        for (StudyGroup s : lista2 ) {
            if( (sGMRepository.countMembers(s.getId())) < s.getLimit_people()){
                lista.add(s);
            }
        }

        return lista;
    }

    @Transactional(readOnly = true)
    public int getAdmin (int idGroup) {
        StudyGroup_Member sGM= sGMRepository.findAdmin(idGroup);
        return userService.getUserByIdMember(sGM.getMember().getId()).getId();
    }

    @Transactional
    public void exitByIdMember (int idUser,int idGroup) {
        Member member = userService.getMember(idUser);
        StudyGroup_Member sGM= sGMRepository.findSGMByMember(member.getId(),idGroup);
        sGMRepository.deleteById(sGM.getId());
    }


}
