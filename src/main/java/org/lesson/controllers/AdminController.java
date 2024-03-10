package org.lesson.controllers;

import org.lesson.dao.PersonDAO;
import org.lesson.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonDAO personDAO;

    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());
        return "adminPage";
    }

    //admin functional isn't implemented, yet
    @PatchMapping("add")
    public String setAdmin(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
