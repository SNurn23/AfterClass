package Team4.egg.AfterClass.service;


import Team4.egg.AfterClass.entity.Career;
import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import Team4.egg.AfterClass.repository.MemberRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements StringsMethods {
    private final MemberRepository memberRepository;
    private final CareerService careerService;

    private final ImageService imageService;


    @Transactional
    public Member createMember(RegisterDTO dto) throws ErrorService { //poner validaciones
        Member member = new Member();
        dto.setFull_name(transformString(dto.getFull_name()));
        if(!dto.getFull_name().isEmpty()){
            member.setFull_name(dto.getFull_name());
        }else{
            throw new ErrorService("Invalid Full Name");
        }
        member.setGender(dto.getGender());
        member.setOccupation(dto.getOccupation());
        member.setUniversity(dto.getUniversity());
        memberRepository.save(member);

        return member;
    }


    @Transactional
    public void updateMember(RegisterDTO dto, MultipartFile profile , MultipartFile cover) throws ErrorService {

        Member memb = memberRepository.findById(dto.getIdMember()).get();
        dto.setFull_name(transformString(dto.getFull_name()));
        if(!dto.getFull_name().isEmpty()){
            memb.setFull_name(dto.getFull_name());
        }else{
            throw new ErrorService("Invalid Full Name");
        }
        memb.setGender(dto.getGender());
        memb.setDescription(dto.getDescription());
        memb.setOccupation(dto.getOccupation());

        if (!profile.isEmpty()) memb.setProfile_img(imageService.copyProfileMember(profile));

        if (!cover.isEmpty()) memb.setCover_img(imageService.copyProfileMember(cover));

        memberRepository.save(memb);
    }


    @Transactional
    public void setCareer(int idCareer, int idMember){
        Career career =careerService.getById(idCareer);
        Member member =memberRepository.findById(idMember).get();
        member.setCareer(career);
        memberRepository.save(member);

    }

    @Transactional(readOnly = true)
    public List<Member> getAll(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member getById(int id) {
        return memberRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(int id){//validaciones
        memberRepository.deleteById(id);
    }
}
