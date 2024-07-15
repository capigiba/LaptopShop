package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsers();
        System.out.println(arrUsers);
        List<User> arrUsersSameEmail = this.userService.getAllUsersByEmail("admin@example.com");
        System.out.println(arrUsersSameEmail);
        model.addAttribute("Capybara", "test");
        model.addAttribute("java", "javakhoquahuhu");
        return "hello12";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User capigiba) {
        System.out.println("run here => " + capigiba);
        this.userService.handleSaveUser(capigiba);
        return "hello12";
    }

}
