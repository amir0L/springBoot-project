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

    // ================== AVERAGE CALCULATION ENDPOINTS ==================

    // Get overall average grades (exam + homework1 + homework2)
    @GetMapping("/average")
    public ResponseEntity<Double> getOverallAverageGrades() {
        try {
            Double average = matiereService.getOverallAverageGrades();
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades by user ID (exam + homework1 + homework2)
    @GetMapping("/user/{userId}/average")
    public ResponseEntity<Double> getOverallAverageGradesByUser(@PathVariable Integer userId) {
        try {
            Double average = matiereService.getOverallAverageGradesByUser(userId);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades with one homework (exam + homework1)
    @GetMapping("/average/one-homework")
    public ResponseEntity<Double> getOverallAverageGradesOneHomework() {
        try {
            Double average = matiereService.getOverallAverageGradesOneHomework();
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades by user ID with one homework (exam + homework1)
    @GetMapping("/user/{userId}/average/one-homework")
    public ResponseEntity<Double> getOverallAverageGradesOneHomeworkByUser(@PathVariable Integer userId) {
        try {
            Double average = matiereService.getOverallAverageGradesOneHomeworkByUser(userId);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades with two homeworks only (homework1 + homework2)
    @GetMapping("/average/two-homeworks")
    public ResponseEntity<Double> getOverallAverageGradesTwoHomeworks() {
        try {
            Double average = matiereService.getOverallAverageGradesTwoHomeworks();
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get overall average grades by user ID with two homeworks only (homework1 + homework2)
    @GetMapping("/user/{userId}/average/two-homeworks")
    public ResponseEntity<Double> getOverallAverageGradesTwoHomeworksByUser(@PathVariable Integer userId) {
        try {
            Double average = matiereService.getOverallAverageGradesTwoHomeworksByUser(userId);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ================== SPECIFIC MATIERE AVERAGE ENDPOINTS ==================

    // Get average grade for a specific matiere by user ID (all three grades)
    @GetMapping("/user/{userId}/matiere/{matiereName}/average")
    public ResponseEntity<Double> getAverageGradeForSpecificMatiereByUser(
            @PathVariable Integer userId,
            @PathVariable String matiereName) {
        try {
            Double average = matiereService.getAverageGradeForSpecificMatiereByUser(userId, matiereName);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get average grade for a specific matiere by user ID (exam + homework1)
    @GetMapping("/user/{userId}/matiere/{matiereName}/average/one-homework")
    public ResponseEntity<Double> getAverageGradeForSpecificMatiereByUserOneHomework(
            @PathVariable Integer userId,
            @PathVariable String matiereName) {
        try {
            Double average = matiereService.getAverageGradeForSpecificMatiereByUserOneHomework(userId, matiereName);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get average grade for a specific matiere by user ID (homework1 + homework2)
    @GetMapping("/user/{userId}/matiere/{matiereName}/average/two-homeworks")
    public ResponseEntity<Double> getAverageGradeForSpecificMatiereByUserTwoHomeworks(
            @PathVariable Integer userId,
            @PathVariable String matiereName) {
        try {
            Double average = matiereService.getAverageGradeForSpecificMatiereByUserTwoHomeworks(userId, matiereName);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get average grade for a specific matiere by user ID and school year
    @GetMapping("/user/{userId}/matiere/{matiereName}/year/{anneeScolaire}/average")
    public ResponseEntity<Double> getAverageGradeForSpecificMatiereByUserAndYear(
            @PathVariable Integer userId,
            @PathVariable String matiereName,
            @PathVariable Integer anneeScolaire) {
        try {
            Double average = matiereService.getAverageGradeForSpecificMatiereByUserAndYear(userId, matiereName, anneeScolaire);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get average grade for a specific matiere by user ID, school year, and trimester
    @GetMapping("/user/{userId}/matiere/{matiereName}/year/{anneeScolaire}/trimester/{trimestre}/average")
    public ResponseEntity<Double> getAverageGradeForSpecificMatiereByUserYearAndTrimester(
            @PathVariable Integer userId,
            @PathVariable String matiereName,
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre) {
        try {
            Double average = matiereService.getAverageGradeForSpecificMatiereByUserYearAndTrimester(userId, matiereName, anneeScolaire, trimestre);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}