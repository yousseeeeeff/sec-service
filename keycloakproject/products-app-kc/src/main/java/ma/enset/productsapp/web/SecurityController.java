package ma.enset.productsapp.web;

import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.facade.SimpleHttpFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class SecurityController {
    @Autowired
    private AdapterDeploymentContext adapterDeploymentContext;
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) throws ServletException {
        httpServletRequest.logout();
        return "redirect:/";
    }
    @GetMapping("/profile")
    public String changePassword(RedirectAttributes attributes, HttpServletRequest req, HttpServletResponse res){
        HttpFacade facade = new SimpleHttpFacade(req,res);
        KeycloakDeployment deployment = adapterDeploymentContext.resolveDeployment(facade);
        attributes.addAttribute("referrer", deployment.getResourceName());
        attributes.addAttribute("referrer_uri", req.getHeader("referrer"));
        return "redirect:"+deployment.getAccountUrl();
    }
}
