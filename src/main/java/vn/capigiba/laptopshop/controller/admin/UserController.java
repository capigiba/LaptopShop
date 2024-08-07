package vn.capigiba.laptopshop.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import vn.capigiba.laptopshop.domain.User;
import vn.capigiba.laptopshop.service.UploadService;
import vn.capigiba.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private UserService userService;
    private UploadService uploadService;

    public UserController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
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
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(@PathVariable long id, Model model) {
        User user = this.userService.getUserById(id);
        System.out.println("check path id: " + id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") User capigiba,
            @RequestParam("capiFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadfile(file, "avatar");
        System.out.println("upload: " + avatar);
        // this.userService.handleSaveUser(capigiba);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}", method = RequestMethod.GET)
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User capigiba) {
        User currentUser = this.userService.getUserById(capigiba.getId());
        if (currentUser != null) {
            currentUser.setAddress(capigiba.getAddress());
            currentUser.setFullname(capigiba.getFullname());
            currentUser.setPhone(capigiba.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);

        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User capiUser) {
        this.userService.deleteAUser(capiUser.getId());
        return "redirect:/admin/user";
    }

}
