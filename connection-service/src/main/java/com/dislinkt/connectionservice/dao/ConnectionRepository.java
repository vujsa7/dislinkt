package com.dislinkt.connectionservice.dao;

import com.dislinkt.connectionservice.model.ProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRepository extends Neo4jRepository<ProfileEntity, String> {

    @Query("OPTIONAL MATCH (p:Profile {id: $id})-[:FOLLOWS]->(:Profile {id: $followerId}) RETURN p IS NOT NULL AS isFollowing")
    boolean isFollowing(@Param("id") String id, @Param("followerId") String followerId);

    @Query("MERGE (p1:Profile {id: $id}) MERGE (p2:Profile {id: $followerId}) MERGE (p1)-[:FOLLOWS]->(p2)")
    void createNewConnection(@Param("id") String id, @Param("followerId") String followerId);

    @Query("MATCH (p:Profile {id: $id})-[f:FOLLOWS]->(:Profile {id: $followerId}) DELETE f")
    void deleteConnection(String id, String followerId);

    @Query("MATCH (p1:Profile)-[:FOLLOWS]->(p2:Profile) WHERE p1.id=$id RETURN p2.id")
    List<String> findConnectionsById(@Param("id") String id);


}
