package se.experis.task6herokunate.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.experis.task6herokunate.models.Actor;
import se.experis.task6herokunate.repositories.ActorRepository;
import se.experis.task6herokunate.utils.Logger;
import se.experis.task6herokunate.utils.Tools;

@RestController
@RequestMapping(path = "/api/v1")
public class ActorController {
  
  @Autowired
  private ActorRepository actorRepository;

  private Logger logger = Logger.getInstance();

  @GetMapping("/actors")
  public ResponseEntity<List<Actor>> getAllActors(HttpServletRequest request) {
    var list = actorRepository.findAll();
    var httpStatus = list.isEmpty() 
      ? HttpStatus.NO_CONTENT
      : HttpStatus.OK;
    
    logger.log(request, httpStatus);
    return new ResponseEntity<>(list, httpStatus);
  }

  @GetMapping("/actors/{id}")
  public ResponseEntity<Actor> getActor(HttpServletRequest request, @PathVariable("id") int id) {
    Actor actor = null;
    HttpStatus httpStatus;
    
    if(actorRepository.existsById(id)) {
      actor = actorRepository.findById(id).get();
      httpStatus = HttpStatus.OK;
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    logger.log(request, httpStatus);
    return new ResponseEntity<>(actor, httpStatus);
  }
  
  @PostMapping("/actors")
  public ResponseEntity<Boolean> addActor(HttpServletRequest request, @RequestBody Actor actor) {
    HttpStatus httpStatus;
    try {
      httpStatus = actorRepository.save(actor) != null
      ? HttpStatus.CREATED
      : HttpStatus.BAD_REQUEST;
    } catch (Exception e) { // some values were null or body empty
      httpStatus = HttpStatus.BAD_REQUEST;
    }
      
    logger.log(request, httpStatus);
    return new ResponseEntity<>(httpStatus);
  }
  
  @PatchMapping("/actors/{id}")
  public ResponseEntity<Boolean> updateActor(HttpServletRequest request, @RequestBody Actor newActor, @PathVariable("id") int id) {
    return changeActor(request, newActor, id, false);
  }

  @PutMapping("/actors/{id}")
  public ResponseEntity<Boolean> replaceActor(HttpServletRequest request, @RequestBody Actor newActor, @PathVariable("id") int id) {
    newActor.id = id;
    return changeActor(request, newActor, id, true);
  }

  /**
   * Internal method used by PUT and PATCH. PUT force updates all fields of the RequestBody, while PATCH only updates non null fields
   */
  private ResponseEntity<Boolean> changeActor(HttpServletRequest request, Actor newActor, int id, boolean updateAllFields) {
    HttpStatus httpStatus;
    
    if(actorRepository.existsById(id)) {
      var actor = actorRepository.findById(id).get();
      
      Tools.updateFields(actor, newActor, updateAllFields);
      
      try {
        actorRepository.save(actor);
        httpStatus = HttpStatus.OK;
      } catch(Exception e) {
        httpStatus = HttpStatus.BAD_REQUEST;
      }
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    logger.log(request, httpStatus);
    return new ResponseEntity<>(httpStatus);
  }

  @DeleteMapping("/actors/{id}")
  public ResponseEntity<Boolean> removeActor(HttpServletRequest request, @PathVariable("id") int id) {
    HttpStatus httpStatus;

    if(actorRepository.existsById(id)) {
      actorRepository.deleteById(id);
      httpStatus = HttpStatus.OK;
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }

    logger.log(request, httpStatus);
    return new ResponseEntity<>(httpStatus);
  }



}
