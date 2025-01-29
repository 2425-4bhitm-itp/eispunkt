package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScoreRepository implements PanacheRepository<Score> {
    public Score create(Team team, Turn turn) {
        Score score = new Score(team, turn);

        persist(score);

        return score;
    }

    public Score create() {
        Score score = new Score();

        persist(score);

        return score;
    }

    public Score findById(long id) {
        return find("id", id).firstResult();
    }

    public void updateScore(Score score, int scoreValue) {
        score.setScore(scoreValue);
    }
}
