package hziee.spc.controller;

import hziee.spc.repository.RelationRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationController {

    private final RelationRepository relationRepository;

    public RelationController(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

}
