package Team4.egg.AfterClass.controller;


import Team4.egg.AfterClass.entity.*;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import Team4.egg.AfterClass.service.CareerService;
import Team4.egg.AfterClass.service.MemberService;
import Team4.egg.AfterClass.service.UniversityService;
import Team4.egg.AfterClass.service.UserService;
import Team4.egg.AfterClass.utility.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

//controlador de autorizacion
@Controller
@RequestMapping(value = "/auth", method = RequestMethod.GET)
@RequiredArgsConstructor
public class AuthController {

    @Lazy
    private final UserService userService;
    private final MemberService memberService;
    private final UniversityService universityService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("form_login");


        if (error != null) mav.addObject("error", "Invalid email or password");
        if (logout != null) mav.addObject("logout", "You have successfully exited the platform");
        if (principal != null) mav.setViewName("redirect:/home");


        return mav;
    }

    @GetMapping("/home")
    public ModelAndView home(){
        return new ModelAndView("index2");
    }

    @GetMapping("/sign_up")
    public ModelAndView signUp(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("form_signup");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request); //vuelve al formulario si pone algo mal

        if (principal != null) mav.setViewName("redirect:/index");

        if (inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("registerDto", inputFlashMap.get("registerDto"));
        } else {
            RegisterDTO dto = new RegisterDTO();
            mav.addObject("registerDto", dto);
        }
        mav.addObject("universities", universityService.getAll());
        mav.addObject("users", userService.getAll());

        return mav;
    }


    //cuando se registra mandarlo al index
    @PostMapping("/register")
    public RedirectView signup(RegisterDTO dto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/login");
        try {
            Member member = memberService.createMember(dto);
            userService.createUser(dto, member);
        } catch (ErrorService e) {
            attributes.addFlashAttribute("registerDto", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/auth/sign_up");
        }

        return redirect;
    }



}