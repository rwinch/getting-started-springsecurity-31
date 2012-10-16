package messages.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

public interface MessageRepository extends CrudRepository<Message, Long> {

/*    @PostAuthorize("returnObject?.summary == authentication.name")*/
    Message findOne(Long id);
}
