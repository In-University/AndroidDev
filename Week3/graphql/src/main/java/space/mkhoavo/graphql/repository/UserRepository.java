package space.mkhoavo.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.mkhoavo.graphql.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
