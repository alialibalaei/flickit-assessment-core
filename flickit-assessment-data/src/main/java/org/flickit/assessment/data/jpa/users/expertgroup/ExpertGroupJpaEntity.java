package org.flickit.assessment.data.jpa.users.expertgroup;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "fau_expert_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExpertGroupJpaEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fau_expert_group_id_seq")
    @SequenceGenerator(name = "fau_expert_group_id_seq", sequenceName = "fau_expert_group_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "bio", length = 200, nullable = false)
    private String bio;

    @Column(name = "about", nullable = false, columnDefinition = "TEXT")
    private String about;

    @Column(name = "picture", length = 100)
    private String picture;

    @Column(name = "website", length = 200)
    private String website;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "created_by_id")
    private UUID createdBy;

    @Column(name =  "last_modified_by")
    private UUID lastModifiedBy;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name =  "last_modification_time")
    private LocalDateTime lastModificationTime;

    @NoArgsConstructor(access = PRIVATE)
    public static class Fields {
        public static final String NAME = "title";
    }
}
