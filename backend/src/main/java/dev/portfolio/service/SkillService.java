package dev.portfolio.service;

import dev.portfolio.dto.SkillDto;
import dev.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service métier pour les compétences (skills) du portfolio.
 * <p>
 * Récupère toutes les compétences triées par catégorie et ordre,
 * mappées en {@link SkillDto}. Try/catch/finally et logs sur chaque accès.
 * </p>
 *
 * @see SkillRepository
 * @see dev.portfolio.dto.SkillDto
 */
@Service
@RequiredArgsConstructor
public class SkillService {

    private static final Logger log = LoggerFactory.getLogger(SkillService.class);
    private final SkillRepository skillRepository;

    /**
     * Retourne toutes les compétences en DTOs.
     *
     * @return liste des SkillDto (jamais null)
     */
    public List<SkillDto> findAll() {
        log.debug("SkillService.findAll() appelé");
        try {
            try {
                log.trace("Accès repository findAll");
                var entities = skillRepository.findAllByOrderByCategoryAscSortOrderAsc();
                try {
                    List<SkillDto> dtos = new ArrayList<>(entities.size());
                    try {
                        entities.forEach(s -> dtos.add(SkillDto.fromEntity(s)));
                        log.debug("{} compétence(s) trouvée(s)", dtos.size());
                        return dtos;
                    } finally {
                        log.trace("Mapping terminé");
                    }
                } finally {
                    log.trace("Liste entités lue");
                }
            } finally {
                log.trace("Accès repository terminé");
            }
        } catch (Exception e) {
            log.error("Erreur SkillService.findAll: {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("SkillService.findAll() terminé");
        }
    }
}
