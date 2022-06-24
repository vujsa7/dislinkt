package com.dislinkt.connectionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("Profile")
public class ProfileEntity {

    @Id
    private String id;

    @Property
    private Boolean isPrivate;

    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<ProfileEntity> profiles = new HashSet<>();

}
