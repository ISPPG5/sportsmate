package repositories;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	@Query("select a from Actor a where a.userAccount.id=?1")
	Actor findActorByUserAccountId(int userAccountId);
	
	@Query("select p from Actor p where p.name like concat('%', ?1, '%') or p.surname like concat('%', ?1, '%') or p.email like concat('%', ?1, '%')")
	Collection<Actor> findActorBySingleKeyword (String keyWord);
	
	@Query("select a.imagen from Actor a where a.id=?1")
	byte[] findImagenOfActorId(int actorId);
	
}
