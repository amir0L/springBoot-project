package com.amir.RESTfulApi.matiere;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/matieres")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    // Create a new matiere
    @PostMapping
    public ResponseEntity<MatiereDto.MatiereResponse> createMatiere(
            @RequestBody MatiereDto.CreateMatiereRequest request) {
        try {
            MatiereDto.MatiereResponse matiere = matiereService.createMatiere(request);
            return ResponseEntity.ok(matiere);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all matieres
    @GetMapping
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getAllMatieres() {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.getAllMatieres();
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matieres by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getMatieresByUserId(@PathVariable Integer userId) {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.getMatieresByUserId(userId);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matiere by ID
    @GetMapping("/{matiereId}")
    public ResponseEntity<MatiereDto.MatiereResponse> getMatiereById(@PathVariable Integer matiereId) {
        try {
            Optional<MatiereDto.MatiereResponse> matiere = matiereService.getMatiereById(matiereId);
            return matiere.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update matiere
    @PutMapping("/{matiereId}")
    public ResponseEntity<MatiereDto.MatiereResponse> updateMatiere(
            @PathVariable Integer matiereId,
            @RequestBody MatiereDto.UpdateMatiereRequest request) {
        try {
            Optional<MatiereDto.MatiereResponse> updatedMatiere = matiereService.updateMatiere(matiereId, request);
            return updatedMatiere.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete matiere
    @DeleteMapping("/{matiereId}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Integer matiereId) {
        try {
            boolean deleted = matiereService.deleteMatiere(matiereId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matieres by school year
    @GetMapping("/year/{anneeScolaire}")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getMatieresByYear(
            @PathVariable Integer anneeScolaire) {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.getMatieresByYear(anneeScolaire);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matieres by user ID and school year
    @GetMapping("/user/{userId}/year/{anneeScolaire}")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getMatieresByUserAndYear(
            @PathVariable Integer userId,
            @PathVariable Integer anneeScolaire) {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.getMatieresByUserAndYear(userId, anneeScolaire);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matieres by school year and trimester
    @GetMapping("/year/{anneeScolaire}/trimester/{trimestre}")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getMatieresByYearAndTrimester(
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre) {
        try {
            List<MatiereDto.MatiereResponse> matieres =
                    matiereService.getMatieresByYearAndTrimester(anneeScolaire, trimestre);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get matieres by user ID, school year and trimester
    @GetMapping("/user/{userId}/year/{anneeScolaire}/trimester/{trimestre}")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> getMatieresByUserYearAndTrimester(
            @PathVariable Integer userId,
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre) {
        try {
            List<MatiereDto.MatiereResponse> matieres =
                    matiereService.getMatieresByUserYearAndTrimester(userId, anneeScolaire, trimestre);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search matieres by name
    @GetMapping("/search")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> searchMatieresByName(
            @RequestParam String name) {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.searchMatieresByName(name);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search matieres by name and user ID
    @GetMapping("/user/{userId}/search")
    public ResponseEntity<List<MatiereDto.MatiereResponse>> searchMatieresByNameAndUser(
            @PathVariable Integer userId,
            @RequestParam String name) {
        try {
            List<MatiereDto.MatiereResponse> matieres = matiereService.searchMatieresByNameAndUser(name, userId);
            return ResponseEntity.ok(matieres);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades
    @GetMapping("/average")
    public ResponseEntity<Double> getOverallAverageGrades() {
        try {
            Double average = matiereService.getOverallAverageGrades();
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades by user ID
    @GetMapping("/user/{userId}/average")
    public ResponseEntity<Double> getOverallAverageGradesByUser(@PathVariable Integer userId) {
        try {
            Double average = matiereService.getOverallAverageGradesByUser(userId);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}