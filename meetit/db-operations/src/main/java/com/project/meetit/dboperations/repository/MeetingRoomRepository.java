package com.project.meetit.dboperations.repository;

import com.project.meetit.dboperations.model.MeetingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetingRoomRepository extends MongoRepository<MeetingRoom, String> {
}
