package com.dislinkt.connectionservice.dao;

import com.dislinkt.connectionservice.model.ProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRepository extends Neo4jRepository<ProfileEntity, String> {

    @Query("OPTIONAL MATCH (p:Profile {id: $id})-[:FOLLOWS|:REQUESTS_FOLLOW]->(:Profile {id: $followerId}) RETURN p IS NOT NULL AS isFollowing")
    boolean hasRelationship(@Param("id") String id, @Param("followerId") String followerId);

    @Query("MERGE (p1:Profile {id: $id}) MERGE (p2:Profile {id: $followerId}) MERGE (p1)-[:FOLLOWS]->(p2) SET p2.isPrivate = $isFollowerPrivate")
    void createNewFollowing(@Param("id") String id, @Param("followerId") String followerId, @Param("isFollowerPrivate") Boolean isFollowerPrivate);

    @Query("MATCH (p:Profile {id: $id})-[f:FOLLOWS|REQUESTS_FOLLOW]->(:Profile {id: $followerId}) DELETE f")
    void deleteFollowingOrFollowingRequest(String id, String followerId);

    @Query("MATCH (p1:Profile)-[:FOLLOWS]->(p2:Profile) WHERE p1.id=$id RETURN p2.id")
    List<String> findFollowingById(@Param("id") String id);

    @Query("MATCH (p1:Profile)<-[:FOLLOWS]-(p2:Profile) WHERE p1.id=$id RETURN p2.id")
    List<String> findFollowersById(String id);

    @Query("MATCH (p1:Profile)-[:REQUESTS_FOLLOW]->(p2:Profile) WHERE p1.id=$id RETURN p2.id")
    List<String> findFollowingRequestsById(@Param("id") String id);

    @Query("MATCH (p1:Profile)<-[:REQUESTS_FOLLOW]-(p2:Profile) WHERE p1.id=$id RETURN p2.id")
    List<String> findFollowerRequestsById(String id);

    @Query("MERGE (p1:Profile {id: $id}) MERGE (p2:Profile {id: $followerId}) MERGE (p1)-[:REQUESTS_FOLLOW]->(p2) SET p2.isPrivate = $isFollowerPrivate")
    void createNewFollowingRequest(@Param("id") String id, @Param("followerId") String followerId, @Param("isFollowerPrivate") Boolean isFollowerPrivate);

    @Query("MATCH (p1:Profile {id: $id})-[f:REQUESTS_FOLLOW]->(p2:Profile {id: $principalId}) MERGE (p1)-[:FOLLOWS]->(p2) DELETE f")
    void approveFollowRequest(String id, String principalId);

    @Query("MATCH (p1:Profile {id: $id})-[f:REQUESTS_FOLLOW]->(p2:Profile {id: $principalId}) DELETE f")
    void deleteFollowRequest(String id, String principalId);

    @Query("OPTIONAL MATCH (p:Profile {id: $principalId})<-[:REQUESTS_FOLLOW]-(:Profile {id: $id}) RETURN p IS NOT NULL AS isFollowing")
    boolean hasFollowRequest(String principalId, String id);


}
