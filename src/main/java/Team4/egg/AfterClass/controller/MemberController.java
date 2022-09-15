package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.entity.User;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import Team4.egg.AfterClass.service.*;
import Team4.egg.AfterClass.utility.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/members")//arreglar cuando se tenga los formularios
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final UserService userService;

    private final RegisterDTOService registerDTO;

    private RegisterDTO dto;

    private final StudyGroup_MemberService sGMService;


    @GetMapping("/MyProfile")
    public ModelAndView viewMyProfile(  HttpSession session) {
        ModelAndView mav = new ModelAndView("profile_Member");

        registerDTO.setDto(Integer.parseInt(session.getAttribute("member").toString()),Integer.parseInt(session.getAttribute("id").toString()));
        dto = registerDTO.getDto();

        mav.addObject("registerDTO",dto);
        mav.addObject("action2", "Update");
        mav.addObject("studyGroups", sGMService.getAllByMember(Integer.parseInt(session.getAttribute("id").toString())));
        mav.addObject("action", "go");
        return mav;
    }


    @GetMapping("/MyProfile/edit")
    public ModelAndView getForm() {
        ModelAndView mav = new ModelAndView("form_member");
        mav.addObject("registerDTO", dto);
        mav.addObject("action","update");
        return mav;
    }


    @PostMapping("/update")
    public RedirectView update(RegisterDTO registerDTO, @RequestParam(value="image",required = false) MultipartFile profile, @RequestParam(value="image2",required = false) MultipartFile cover, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/members/MyProfile");
        try{
            memberService.updateMember(registerDTO,profile,cover);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        }catch(ErrorService e){
            attributes.addFlashAttribute("registerDto", registerDTO);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/members/MyProfile/edit");
        }

        return redirect;
    }


}
