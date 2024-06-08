package com.notification.notification_service.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MainController {
    
    
    @GetMapping("/")
    public RedirectView  renderSwagger(){
        return new RedirectView("/swagger-ui/index.html");
    }

}
