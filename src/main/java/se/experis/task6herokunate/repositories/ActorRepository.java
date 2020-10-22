package se.experis.task6herokunate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.experis.task6herokunate.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
