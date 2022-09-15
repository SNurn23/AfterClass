package Team4.egg.AfterClass.controller;

import Team4.egg.AfterClass.entity.Career;
import Team4.egg.AfterClass.entity.Subject;
import Team4.egg.AfterClass.service.CareerService;
import Team4.egg.AfterClass.service.SubjectService;
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
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final CareerService careerService;
    private final UniversityService uniService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView getSubjects(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("table_subject");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("success", inputFlashMap.get("success"));
        }

        mav.addObject("subjects", subjectService.getAll());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public ModelAndView getForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("form_subject");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("subject", inputFlashMap.get("subject"));
        } else {
            mav.addObject("subject", new Subject());
        }
        mav.addObject("universities",uniService.getAll());
        mav.addObject("careers", careerService.getAll());
        mav.addObject("action", "create");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form/{id}")
    public ModelAndView getForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("form_subject");
        mav.addObject("subject", subjectService.getById(id));
        mav.addObject("universities",uniService.getAll());
        mav.addObject("careers", careerService.getAll());
        mav.addObject("action", "update");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public RedirectView create(Subject subject, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/subjects");
        try {
            subjectService.createSubject(subject);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        }catch(ErrorService e){
            attributes.addFlashAttribute("subject", subject);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/subjects/form");
        }

        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public RedirectView update(Subject subject, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/subjects");
        try{
            subjectService.updateSubject(subject);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("subject", subject);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/subjects/form/" + subject.getId());
        }
        return redirect;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/subjects");
        subjectService.deleteById(id);
        attributes.addFlashAttribute("success", "The subject has been removed successfully");
        return redirect;
    }

    @GetMapping("/cancel")
    public RedirectView cancelUpdate(){
        return new RedirectView("/subjects");
    }

    //agregar softdelete
}
