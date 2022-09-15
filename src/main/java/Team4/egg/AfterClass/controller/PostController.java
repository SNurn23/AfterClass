package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Post;
import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.service.PostService;
import Team4.egg.AfterClass.service.StudyGroupService;
import Team4.egg.AfterClass.service.UserService;
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
@RequestMapping("/studyGroups/MyGroup/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StudyGroupService studyGroupService;
    private final UserService userService;


    @GetMapping("/form")//incorporar el perfil
    public ModelAndView getFormPost() {
        ModelAndView mav = new ModelAndView("form_post");//ver / Post no tiene formulario en si
        mav.addObject("post", new Post());
        mav.addObject("action", "create");
        return mav;
    }

    @GetMapping("/form/{id}")//no editar el post , sacar
    public ModelAndView getForm(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("form_post");
        mav.addObject("post", postService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/create")
    public RedirectView create(Post post, @RequestParam(value="image",required = false) MultipartFile file, RedirectAttributes attributes, HttpSession session) {
        RedirectView redirect = new RedirectView(studyGroupService.getView());
        postService.createPost(studyGroupService.getGroup(),userService.getMember(Integer.parseInt(session.getAttribute("id").toString())),post,file);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PostMapping("/update")
    public RedirectView update(Post post, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView(studyGroupService.getView());
        postService.updatePost(post);
        attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        return redirect;
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView(studyGroupService.getView()+"#post");
        postService.deleteById(id);
        attributes.addFlashAttribute("success", "The post has been removed successfully");
        return redirect;
    }

}
