package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.User;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import Team4.egg.AfterClass.service.MemberService;
import Team4.egg.AfterClass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/users")//arreglar cuando se tenga los formularios
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;






    @GetMapping("/form/{id}")
    public ModelAndView getForm(@PathVariable int id){
        ModelAndView mav = new ModelAndView("form_signup");//ver
        mav.addObject("registerDto", userService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }




    @PostMapping("/delete/{id}")
    public RedirectView delete (@PathVariable int id, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/users");//arreglar cuando se tenga los formularios
        userService.deleteById(id);
        attributes.addFlashAttribute("success", "The user has been removed successfully");//agregar para el caso de las excepciones
        return redirect;
    }



}
