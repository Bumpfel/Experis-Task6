package se.experis.task6herokunate.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
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

@RestController
@RequestMapping(path = "/api/v1")
public class ActorController {
  
  @Autowired
  private ActorRepository actorRepository;


  @GetMapping("/actors")
  public ResponseEntity<List<Actor>> getAllActors() {
    var list = actorRepository.findAll();
    return new ResponseEntity<>(list, list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
  }

  @GetMapping("/actors/{id}")
  public ResponseEntity<Actor> getActor(@PathVariable("id") int id) {
    Actor actor = null;
    HttpStatus httpStatus;
    
    if(actorRepository.existsById(id)) {
      actor = actorRepository.findById(id).get();
      httpStatus = HttpStatus.OK;
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity<>(actor, httpStatus);
  }

  @PostMapping("/actors")
  public ResponseEntity<Boolean> addActor(@RequestBody Actor actor) {
    var response = actorRepository.save(actor);
    return new ResponseEntity<>(response != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
  }
  
  @PatchMapping("/actors/{id}")
  public ResponseEntity<Boolean> updateActor(@RequestBody Actor newActor, @PathVariable("id") int id) {
    return update(newActor, id, false);
  }

  @PutMapping("/actors/{id}")
  public ResponseEntity<Boolean> replaceActor(@RequestBody Actor newActor, @PathVariable("id") int id) {
    newActor.id = id;
    return update(newActor, id, true);
  }

  /**
   * Internal method used by PUT and PATCH. PUT force updates all fields of RequestBody, while PATCH only updates null fields
   */
  private ResponseEntity<Boolean> update(Actor newActor, int id, boolean updateAllFields) {
    HttpStatus httpStatus;
    
    if(actorRepository.existsById(id)) {
      var actor = actorRepository.findById(id).get();
      
      updateFields(actor, newActor, updateAllFields);

      actorRepository.save(actor);
      httpStatus = HttpStatus.OK;
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    
    return new ResponseEntity<>(httpStatus);
  }
  
  
  /**
   * Update the fields of original object based on newObject (yay reflection)
   * @param original
   * @param newObject
   * @throws RuntimeException if objects are of different types
   */
  private void updateFields(Object original, Object newObject, boolean updateNullFields) throws RuntimeException {
    if(original.getClass() != newObject.getClass()) {
      throw new RuntimeException("Objects are not of the same type");
    }

    var fields = Actor.class.getDeclaredFields();
    for(var field : fields) {
      try {
        var fieldValue = field.get(newObject);
        if(updateNullFields || fieldValue != null) {
          field.set(original, fieldValue);
        }          
      } catch(Exception e) {
        System.err.println("Error");
      }
    }
  }
  

  @DeleteMapping("/actors/{id}")
  public ResponseEntity<Boolean> removeActor(@PathVariable("id") int id) {
    HttpStatus httpStatus;
    if(actorRepository.existsById(id)) {
      actorRepository.deleteById(id);
      httpStatus = HttpStatus.OK;
    } else {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity<>(httpStatus);
  }



}
