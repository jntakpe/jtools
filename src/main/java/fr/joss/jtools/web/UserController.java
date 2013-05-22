package fr.joss.jtools.web;

import fr.joss.jtools.domain.User;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.ResponseMessage;
import fr.joss.jtools.util.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contrôleur gérant les écrans relatifs à l'entité {@link User}
 *
 * @author jntakpe
 */
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String display() {
        return "user_list";
    }

    @RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> list() {
        return userService.findAll();
    }

    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("user_form");
        mv.addObject("roles", Role.values());
        return mv.addObject(userService.findOne(id));
    }

    @RequestMapping(value = "/user/account", method = RequestMethod.GET)
    public ModelAndView detailMyAcc() {
        ModelAndView mv = new ModelAndView("user_form");
        mv.addObject("roles", Role.values());
        mv.addObject("myAcc", true);
        return mv.addObject(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        User user = userService.findOne(id);
        userService.delete(user);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("{} a supprimé l'utilisateur '{}'", username, user);
        return ResponseMessage.getSuccessMessage("Utilisateur '" + user + "' supprimé");
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("{} a modifié l'utilisateur '{}'", username, user);
        ResponseMessage msg = ResponseMessage.getSuccessMessage("Utilisateur '" + user + "' modifié");
        redirectAttributes.addFlashAttribute("responseMessage", msg);
        return new ModelAndView(new RedirectView("/admin/user", true));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ModelAndView editMyAcc(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("{} a modifié son compte", username);
        ResponseMessage msg = ResponseMessage.getSuccessMessage("Votre compte a été mis à jour.");
        redirectAttributes.addFlashAttribute("responseMessage", msg);
        return new ModelAndView(new RedirectView("/portal", true));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("user_form");
        return mv.addObject(new User());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute User user) {
        userService.save(user);
        logger.info("{} s'est créé un compte", user.getLogin());
        ResponseMessage message = ResponseMessage.getSuccessMessage("Compte '" + user.getLogin() + "' créé.");
        return new ModelAndView("connexion").addObject("responseMessage", message);
    }

    @RequestMapping(value = "/user/logincontrol", method = RequestMethod.GET)
    @ResponseBody
    public boolean loginControl(@RequestParam(required = false) Long id, @RequestParam String login) {
        return userService.isLoginAvaillable(id, login);
    }

    @RequestMapping(value = "/user/emailcontrol", method = RequestMethod.GET)
    @ResponseBody
    public boolean emailControl(@RequestParam(required = false) Long id, @RequestParam String email) {
        return userService.isEmailAvaillable(id, email);
    }

    @RequestMapping(value = "/user/phonecontrol", method = RequestMethod.GET)
    @ResponseBody
    public boolean phoneControl(@RequestParam(required = false) Long id, @RequestParam String phone) {
        return userService.isPhoneAvaillable(id, phone);
    }

    @RequestMapping(value = "/sendpassword", method = RequestMethod.GET)
    public String passwordForm() {
        return "password";
    }

    @RequestMapping(value = "/sendpassword", method = RequestMethod.POST)
    public ModelAndView sendPassword(@RequestParam(required = false) String email,
                                     @RequestParam(required = false) String login,
                                     RedirectAttributes redirectAttributes) {
        if (userService.sendUserInfo(email, login)) {
            ResponseMessage msg = ResponseMessage.getSuccessMessage("Mot de passe renvoyé.");
            redirectAttributes.addFlashAttribute("responseMessage", msg);
            return new ModelAndView(new RedirectView("/connexion", true));
        } else {
            ModelAndView mv = new ModelAndView("password");
            ResponseMessage msg = ResponseMessage.getErrorMessage("Compte introuvable.");
            return mv.addObject("responseMessage", msg);
        }
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

}
