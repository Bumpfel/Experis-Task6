package se.experis.task6herokunate.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Actor {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    
    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = false)
    public String dateOfBirth;

    @Column(nullable = false)
    public String imdbUrl;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(var field : getClass().getDeclaredFields()) {
            try {
                builder.append(field.getName() + ": " + field.get(this) + "\n");
            } catch(Exception e) {
            }
        }
        return builder.toString();
    }
}
