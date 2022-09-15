package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.User;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDTOService {
    private final MemberService memberService;
    private final UserService userService;
    private RegisterDTO dto;

    public void setDto(int idMember, int idUser){
        dto=new RegisterDTO();
        Member member = memberService.getById(idMember);
        User user = userService.getById(idUser);

        dto.setIdUser(user.getId());
        dto.setUser_name(user.getUser_name());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setIdMember(member.getId());
        dto.setFull_name(member.getFull_name());
        dto.setOccupation(member.getOccupation());
        dto.setGender(member.getGender());
        dto.setProfile_img(member.getProfile_img());
        dto.setCover_img(member.getCover_img());
        dto.setDescription(member.getDescription());
    }

    public RegisterDTO getDto(){
        return dto;
    }

}