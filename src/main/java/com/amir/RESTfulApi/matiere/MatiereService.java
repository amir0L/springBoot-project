package com.amir.RESTfulApi.matiere;

import com.amir.RESTfulApi.user.User;
import com.amir.RESTfulApi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatiereService {

    private final MatiereRepository matiereRepository;
    private final UserRepository userRepository;

    @Transactional
    public MatiereDto.MatiereResponse createMatiere(MatiereDto.CreateMatiereRequest request) {
        // Check if user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // Check if matiere already exists for this user, year and trimester
        if (matiereRepository.existsByNameAndAnneeScolaireAndTrimestreAndUser_Id(
                request.getName(), request.getAnneeScolaire(), request.getTrimestre(), request.getUserId())) {
            throw new RuntimeException("Matiere already exists for this user, year and trimester");
        }

        Matiere matiere = Matiere.builder()
                .anneeScolaire(request.getAnneeScolaire())
                .trimestre(request.getTrimestre())
                .name(request.getName())
                .noteExam(request.getNoteExam())
                .noteDevoir1(request.getNoteDevoir1())
                .noteDevoir2(request.getNoteDevoir2())
                .user(user)
                .build();

        Matiere savedMatiere = matiereRepository.save(matiere);
        return mapToResponse(savedMatiere);
    }

    public List<MatiereDto.MatiereResponse> getAllMatieres() {
        List<Matiere> matieres = matiereRepository.findAllOrderedByYearAndTrimester();
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> getMatieresByUserId(Integer userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        List<Matiere> matieres = matiereRepository.findByUserIdOrderedByYearAndTrimester(userId);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<MatiereDto.MatiereResponse> getMatiereById(Integer matiereId) {
        return matiereRepository.findById(matiereId)
                .map(this::mapToResponse);
    }

    @Transactional
    public Optional<MatiereDto.MatiereResponse> updateMatiere(Integer matiereId, MatiereDto.UpdateMatiereRequest request) {
        return matiereRepository.findById(matiereId)
                .map(matiere -> {
                    // Update user if provided
                    if (request.getUserId() != null) {
                        User user = userRepository.findById(request.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
                        matiere.setUser(user);
                    }

                    // Update other fields if they are not null
                    if (request.getAnneeScolaire() != null) {
                        matiere.setAnneeScolaire(request.getAnneeScolaire());
                    }
                    if (request.getTrimestre() != null) {
                        matiere.setTrimestre(request.getTrimestre());
                    }
                    if (request.getName() != null) {
                        matiere.setName(request.getName());
                    }
                    if (request.getNoteExam() != null) {
                        matiere.setNoteExam(request.getNoteExam());
                    }
                    if (request.getNoteDevoir1() != null) {
                        matiere.setNoteDevoir1(request.getNoteDevoir1());
                    }
                    if (request.getNoteDevoir2() != null) {
                        matiere.setNoteDevoir2(request.getNoteDevoir2());
                    }

                    Matiere updatedMatiere = matiereRepository.save(matiere);
                    return mapToResponse(updatedMatiere);
                });
    }

    @Transactional
    public boolean deleteMatiere(Integer matiereId) {
        if (matiereRepository.existsById(matiereId)) {
            matiereRepository.deleteById(matiereId);
            return true;
        }
        return false;
    }

    public List<MatiereDto.MatiereResponse> getMatieresByYear(Integer anneeScolaire) {
        List<Matiere> matieres = matiereRepository.findByAnneeScolaire(anneeScolaire);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> getMatieresByUserAndYear(Integer userId, Integer anneeScolaire) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        List<Matiere> matieres = matiereRepository.findByUser_IdAndAnneeScolaire(userId, anneeScolaire);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> getMatieresByYearAndTrimester(Integer anneeScolaire, Integer trimestre) {
        List<Matiere> matieres = matiereRepository.findByAnneeScolaireAndTrimestre(anneeScolaire, trimestre);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> getMatieresByUserYearAndTrimester(Integer userId, Integer anneeScolaire, Integer trimestre) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        List<Matiere> matieres = matiereRepository.findByUser_IdAndAnneeScolaireAndTrimestre(userId, anneeScolaire, trimestre);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> searchMatieresByName(String name) {
        List<Matiere> matieres = matiereRepository.findByNameContainingIgnoreCase(name);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MatiereDto.MatiereResponse> searchMatieresByNameAndUser(String name, Integer userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        List<Matiere> matieres = matiereRepository.findByNameContainingIgnoreCaseAndUser_Id(name, userId);
        return matieres.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Average grades with all three components (exam + homework1 + homework2)
    public Double getOverallAverageGrades() {
        return matiereRepository.getOverallAverageGrades();
    }

    public Double getOverallAverageGradesByUser(Integer userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getOverallAverageGradesByUserId(userId);
    }

    // Average grades with one homework (exam + homework1)
    public Double getOverallAverageGradesOneHomework() {
        return matiereRepository.getOverallAverageGradesOneHomework();
    }

    public Double getOverallAverageGradesOneHomeworkByUser(Integer userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getOverallAverageGradesOneHomeworkByUserId(userId);
    }

    // Average grades with two homeworks only (homework1 + homework2)
    public Double getOverallAverageGradesTwoHomeworks() {
        return matiereRepository.getOverallAverageGradesTwoHomeworks();
    }

    public Double getOverallAverageGradesTwoHomeworksByUser(Integer userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getOverallAverageGradesTwoHomeworksByUserId(userId);
    }

    // Average grade for a specific matiere by user (all three grades)
    public Double getAverageGradeForSpecificMatiereByUser(Integer userId, String matiereName) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getAverageGradeForSpecificMatiereByUser(userId, matiereName);
    }

    // Average grade for a specific matiere by user (exam + homework1)
    public Double getAverageGradeForSpecificMatiereByUserOneHomework(Integer userId, String matiereName) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getAverageGradeForSpecificMatiereByUserOneHomework(userId, matiereName);
    }

    // Average grade for a specific matiere by user (homework1 + homework2)
    public Double getAverageGradeForSpecificMatiereByUserTwoHomeworks(Integer userId, String matiereName) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getAverageGradeForSpecificMatiereByUserTwoHomeworks(userId, matiereName);
    }

    // Average grade for a specific matiere by user and school year
    public Double getAverageGradeForSpecificMatiereByUserAndYear(Integer userId, String matiereName, Integer anneeScolaire) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getAverageGradeForSpecificMatiereByUserAndYear(userId, matiereName, anneeScolaire);
    }

    // Average grade for a specific matiere by user, school year, and trimester
    public Double getAverageGradeForSpecificMatiereByUserYearAndTrimester(Integer userId, String matiereName, Integer anneeScolaire, Integer trimestre) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return matiereRepository.getAverageGradeForSpecificMatiereByUserYearAndTrimester(userId, matiereName, anneeScolaire, trimestre);
    }

    // Helper method to calculate average grade for a single matiere
    private Double calculateAverageGrade(Matiere matiere) {
        double sum = 0;
        int count = 0;

        if (matiere.getNoteExam() != null) {
            sum += matiere.getNoteExam();
            count++;
        }
        if (matiere.getNoteDevoir1() != null) {
            sum += matiere.getNoteDevoir1();
            count++;
        }
        if (matiere.getNoteDevoir2() != null) {
            sum += matiere.getNoteDevoir2();
            count++;
        }

        return count > 0 ? sum / count : null;
    }

    // Helper method to map entity to response DTO
    private MatiereDto.MatiereResponse mapToResponse(Matiere matiere) {
        return MatiereDto.MatiereResponse.builder()
                .matiereId(matiere.getMatiereId())
                .anneeScolaire(matiere.getAnneeScolaire())
                .trimestre(matiere.getTrimestre())
                .name(matiere.getName())
                .noteExam(matiere.getNoteExam())
                .noteDevoir1(matiere.getNoteDevoir1())
                .noteDevoir2(matiere.getNoteDevoir2())
                .averageGrade(calculateAverageGrade(matiere))
                .userId(matiere.getUser().getId())
                .userFirstname(matiere.getUser().getFirstname())
                .userLastname(matiere.getUser().getLastname())
                .build();
    }
}