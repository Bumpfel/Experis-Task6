package se.experis.task6herokunate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import se.experis.task6herokunate.repositories.ActorRepository;

@Controller
public class ViewController {
  
  @Autowired
  private ActorController actorController;
  @Autowired
  private ActorRepository actorRepository;


  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("title", "Home");
    model.addAttribute("content", "actors");

    var actors = actorRepository.findAll();   
    model.addAttribute("actors", actors);
    return "index";
  }


  @GetMapping("/add")
  public String addActor(Model model) {
    model.addAttribute("title", "Add new actor");
    model.addAttribute("content", "add");
    return "index";
  }

}
