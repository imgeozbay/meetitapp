package com.project.meetit.dboperations.repository;

import com.project.meetit.dboperations.model.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetingRepository extends MongoRepository<Meeting, String> {
    
}
