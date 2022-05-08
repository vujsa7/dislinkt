package com.dislinkt.connectionservice.dao;

import com.dislinkt.connectionservice.model.ProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface ConnectionRepository extends Neo4jRepository<ProfileEntity, String> {

    @Query("MERGE (p:Profile {id: $id}) MERGE (p)-[:FOLLOWS]->(:Profile {id: $followerId})")
    void createNewConnection(@Param("id") String id, @Param("followerId") String followerId);
}
