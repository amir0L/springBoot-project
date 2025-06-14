package com.amir.RESTfulApi.matiere;


import com.amir.RESTfulApi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matieres")
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matiereId")
    private Integer matiereId;

    @Column(name = "anneeScolaire")
    private Integer anneeScolaire;

    @Column(name = "trimestre")
    private Integer trimestre;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "noteExam")
    private Double noteExam;

    @Column(name = "noteDevoir1")
    private Double noteDevoir1;

    @Column(name = "noteDevoir2")
    private Double noteDevoir2;

    // Add user relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
