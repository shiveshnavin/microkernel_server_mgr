package sn.microkernel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sn.microkernel.component.model.ServerModel;

@Repository
public interface ServerRepo extends JpaRepository<ServerModel, Integer>{
	
	@Query("SELECT u FROM ServerModel u WHERE u.name = ?1")
	public ServerModel findByName(String name);

}
