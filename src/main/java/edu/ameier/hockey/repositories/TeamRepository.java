package edu.ameier.hockey.repositories;

import edu.ameier.hockey.models.HockeyTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<HockeyTeam, Long> {
}
