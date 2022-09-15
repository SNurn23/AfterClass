package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.Post;
import Team4.egg.AfterClass.entity.StudyGroup;
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
@RequestMapping(value="/studyGroups",method={ RequestMethod.GET , RequestMethod.POST })//arreglar cuando se tenga los formularios
@RequiredArgsConstructor
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
    private final SubjectService subjectService;
    private final PostService postService;
    private final StudyGroup_MemberService sGMService;
    private final MeetingService meetingService;
    private final UserService userService;
    private final MemberService memberService;
    private final CareerService careerService;




    @GetMapping("/form")//arreglar
    public ModelAndView getForm(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("form_sGroup");
        Member member = memberService.getById(Integer.parseInt(session.getAttribute("id").toString()));
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("studyGroup", inputFlashMap.get("studyGroup"));
        } else {
            mav.addObject("studyGroup", new StudyGroup());
        }

        mav.addObject("subjects", subjectService.getAllByCareer(member.getCareer().getId()));
        mav.addObject("action", "create");
        return mav;
    }

    @GetMapping("/form/{id}")//arreglar
    public ModelAndView getForm(@PathVariable int id, HttpSession session) {
        Member member = memberService.getById(Integer.parseInt(session.getAttribute("id").toString()));
        ModelAndView mav = new ModelAndView("form_sGroup");
        mav.addObject("studyGroup", studyGroupService.getById(id));
        mav.addObject("subjects", subjectService.getAllByCareer(member.getCareer().getId()));
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/create")
    public RedirectView create(StudyGroup studyGroup ,RedirectAttributes attributes, HttpSession session) {
        RedirectView redirect = new RedirectView("/members/MyProfile#sgroups");
        try {
            studyGroupService.createStudyGroup(studyGroup);
            sGMService.addAdmin(Integer.parseInt(session.getAttribute("id").toString()));
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("studyGroup", studyGroup);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/studyGroups/form");
        }
        return redirect;
    }

    @GetMapping("/MyGroup/{id}")
    public ModelAndView viewStudyGroup(@PathVariable int id, HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("profile_Group");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        studyGroupService.setView("/studyGroups/MyGroup/" + id);//guardamos la vista para utilizar en el controlador post

        if (inputFlashMap != null) {
            mav.addObject("success", inputFlashMap.get("success"));
        }

        StudyGroup group = studyGroupService.getById(id);
        studyGroupService.setGroup(group);//guardamos variable para usar en post y meeting

        mav.addObject("studyGroup", group);
        mav.addObject("users", sGMService.getAllMembersByGroup(group));
        mav.addObject("posts", postService.getAllByGroup(group.getId()));
        mav.addObject("meetings", meetingService.getAllByGroup(group.getId()));
        mav.addObject("member", userService.getMember(Integer.parseInt(session.getAttribute("id").toString())).getId());

        mav.addObject("post", new Post());

        if(session.getAttribute("id").equals(sGMService.getAdmin(id))){
            mav.addObject("action","Delete");
            mav.addObject("action2","Update");
        }else{
            mav.addObject("action","exit");
        }
        return mav;
    }

    @GetMapping("/Groups")
    public ModelAndView getStudyGroupsByUser(HttpServletRequest request, HttpSession session) {
        Member member = memberService.getById(Integer.parseInt(session.getAttribute("member").toString()));
        ModelAndView mav = new ModelAndView("table_sGroup");
        if(member.getCareer()!=null){

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null) {
                mav.addObject("success", inputFlashMap.get("success"));
            }
            mav.addObject("studyGroups", sGMService.getAllStudyGroupsAvailable(member));
            mav.addObject("action", "join");
        }else {
            mav.setViewName("redirect:/studyGroups/careersByUniversity");
        }
        return mav;
    }


    @PostMapping("/join/{id}")
    public RedirectView join(@PathVariable int id, HttpSession session) {
        RedirectView redirect = new RedirectView("/studyGroups/MyGroup/" + id);

        sGMService.addMember(Integer.parseInt(session.getAttribute("id").toString()), id);

        return redirect;
    }

    @PostMapping("/update")
    public RedirectView update(StudyGroup studyGroup, @RequestParam(value="image",required = false) MultipartFile profile,@RequestParam(value="image2",required = false) MultipartFile cover, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/studyGroups/MyGroup/" + studyGroup.getId());

        try {
            studyGroupService.updateStudyGroup(studyGroup,profile,cover);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("studyGroup", studyGroup);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/studyGroups/form/" + studyGroup.getId());//arreglar
        }
        return redirect;
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/members/MyProfile#sgroups");
        studyGroupService.deleteById(id);
        attributes.addFlashAttribute("success", "The study group has been removed successfully");
        return redirect;
    }

    @GetMapping("/cancel")
    public RedirectView cancelUpdate() {
        return new RedirectView("/studyGroups");
    }//arreglar

    @PostMapping("/exit/{id}")
    public RedirectView exit(@PathVariable int id, RedirectAttributes attributes,HttpSession session){
        RedirectView redirect = new RedirectView("/members/MyProfile#sgroups");

        sGMService.exitByIdMember(Integer.parseInt(session.getAttribute("id").toString()),id);

        attributes.addFlashAttribute("success", "Has ceased to be a member in the group");
        return redirect;
    }


    @GetMapping("/form_post")
    public ModelAndView getFormPost() {
        ModelAndView mav = new ModelAndView("profile_Group");//ver / Post no tiene formulario en si
        mav.addObject("post", new Post());
        mav.addObject("action", "create");
        return mav;
    }

    @PostMapping("/create_post")
    public RedirectView create(Post post, @RequestParam(value="image3",required = false) MultipartFile img, RedirectAttributes attributes, HttpSession session) {
        RedirectView redirect = new RedirectView(studyGroupService.getView() + "#post");
        postService.createPost(studyGroupService.getGroup(),userService.getMember(Integer.parseInt(session.getAttribute("id").toString())),post,img);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @GetMapping("/careersByUniversity")
    public ModelAndView careersByUniversity( HttpSession session) {
        System.out.println(Integer.parseInt(session.getAttribute("university").toString()));
            ModelAndView mav = new ModelAndView("careersByUniversity");
            mav.addObject("careers", careerService.getAllByUniversity(Integer.parseInt(session.getAttribute("university").toString())));
            return mav;
    }

    @PostMapping("/setCareer/{id}")
    public RedirectView setCareer(@PathVariable int id , HttpSession session) {
        RedirectView redirect = new RedirectView("/studyGroups/Groups"); /// arreglar

        memberService.setCareer(id, Integer.parseInt(session.getAttribute("member").toString()));

        return redirect;
    }


}