package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Career;
import Team4.egg.AfterClass.service.CareerService;
import Team4.egg.AfterClass.service.UniversityService;
import Team4.egg.AfterClass.utility.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/careers")//arreglar cuando se tenga los formularios
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;
    private final UniversityService universityService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getCareers(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("table_career");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("success", inputFlashMap.get("success"));
        }
        mav.addObject("careers", careerService.getAll());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("form_career");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("career", inputFlashMap.get("career"));
        } else {
            mav.addObject("career", new Career());
        }
        mav.addObject("universities", universityService.getAll());
        mav.addObject("action", "create");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("form_career");
        mav.addObject("career", careerService.getById(id));
        mav.addObject("universities", universityService.getAll());
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Career career, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/careers");//arreglar cuando se tenga los formularios
        try {
            careerService.createCareer(career);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("career", career);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/careers/form");
        }
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Career career, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/careers");//arreglar cuando se tenga los formularios
        try {
            careerService.updateCareer(career);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("exception", e.getMessage());
            attributes.addFlashAttribute("career", career);
            redirect.setUrl("/careers/form/" + career.getId());
        }
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/careers");//arreglar cuando se tenga los formularios
        careerService.deleteById(id);
        attributes.addFlashAttribute("success", "The career has been removed successfully");//agregar para el caso de las excepciones
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cancel")
    public RedirectView cancelUpdate() {
        return new RedirectView("/careers");
    }


    //softdelete

}
