package messages.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    private static final String LOGIN_VIEW = "login";

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    /**
     *
     * @param loginForm The model object that is only required when we are using Spring MVC to authenticate. If we allow
     * {@link UsernamePasswordAuthenticationFilter} to process the request this is not necessary.
     * @return
     */
    @RequestMapping("/login")
    public String login(LoginForm loginForm) {
        return LOGIN_VIEW;
    }

    /**
     * An alternative way to authenticate instead of using {@link UsernamePasswordAuthenticationFilter}
     * @param request
     * @param response
     * @param loginForm
     * @param result
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String customLogin(HttpServletRequest request, HttpServletResponse response, @Valid LoginForm loginForm, BindingResult result) throws IOException, ServletException {
        // do whatever validation you want to do
        String j_username = loginForm.getJ_username();
        String j_password = loginForm.getJ_password();
        if(!j_username.equals(j_password)) {
            result.reject("login.fail","Bad username/password");
        }
        if(result.hasErrors()) {
            return LOGIN_VIEW;
        }

        // authenticate the user
        Authentication authentication =
                   new UsernamePasswordAuthenticationToken(j_username, j_password, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    /**
     * An alternative way to authenticate instead of using {@link UsernamePasswordAuthenticationFilter}. We also
     * demonstrate the use of {@link AuthenticationManager} and the {@link AuthenticationSuccessHandler}.
     * @param request
     * @param response
     * @param loginForm
     * @param result
     * @return
     * @throws IOException
     * @throws ServletException
     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, @Valid LoginForm loginForm, BindingResult result) throws IOException, ServletException {
        String j_username = loginForm.getJ_username();
        String j_password = loginForm.getJ_password();
        if(result.hasErrors()) {
            return LOGIN_VIEW;
        }
        // use the AuthenticationManager to help with authentication
        Authentication token= new UsernamePasswordAuthenticationToken(j_username, j_password);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(AuthenticationException error) {
            result.reject("login.fail",error.getMessage());
            return LOGIN_VIEW;
        }
        return "redirect:/";
    }
}
