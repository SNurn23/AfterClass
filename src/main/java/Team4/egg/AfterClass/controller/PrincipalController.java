package Team4.egg.AfterClass.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PrincipalController {

    @GetMapping
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }



}