package Team4.egg.AfterClass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        Integer status = (int) request.getAttribute(ERROR_STATUS_CODE);
        String message;

        switch (status) {
            case 403:
                message = "You do not have sufficient permissions to access the requested resource";
                break;
            case 404:
                message = "The requested resource was not found";
                break;
            case 500:
                message = "Internal server error";
                break;
            default:
                message = "Unexpected error";
        }

        mav.addObject("message", message);
        mav.addObject("status", status);
        return mav;
    }
}
