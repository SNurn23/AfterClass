package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.University;
import Team4.egg.AfterClass.service.UniversityService;
import Team4.egg.AfterClass.utility.ErrorService;
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
import java.util.Map;

@Controller
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getUniversities(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("table_university");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if(inputFlashMap != null){
            mav.addObject("success", inputFlashMap.get("success"));
        }

        mav.addObject("universities", universityService.getAll());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("form_university");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if(inputFlashMap != null){
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("university", inputFlashMap.get("university"));
        }else{
            mav.addObject("university", new University());
        }
        mav.addObject("action","create");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getForm(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("form_university");
        mav.addObject("university", universityService.getById(id));
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(University university, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/universities");
        try{
            universityService.createUniversity(university);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        }catch(ErrorService e){
            attributes.addFlashAttribute("university", university);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/universities/form");
        }
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(University university, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/universities");
        try{
            universityService.updateUniversity(university);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        }catch(ErrorService e ){
            attributes.addFlashAttribute("university", university);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/universities/form/"+ university.getId());//parchersito
        }
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id,  RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/universities");
        universityService.deleteById(id);
        attributes.addFlashAttribute("success", "The university has been removed successfully");
        return redirect;
    }


    @GetMapping("/cancel")
    public RedirectView cancelUpdate(){
        return new RedirectView("/universities");
    }

//    @PostMapping("/enable/{id}")
//    public RedirectView enable(@PathVariable int id, RedirectAttributes attributes) {
//        RedirectView redirect = new RedirectView("/universities");
//        universityService.enableById(id);
//        attributes.addFlashAttribute("success", "The university has been removed successfully");
//        return redirect;
//    }

}