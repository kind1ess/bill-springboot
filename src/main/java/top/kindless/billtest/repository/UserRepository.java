package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.entity.User;

public interface UserRepository extends JpaRepository<User,String> {

    User findByAccount(String account);

    User findByAccountAndPassword(String account,String password);

    @Query(value = "select password from tb_user where account=?1",nativeQuery = true)
    String findPasswordByAccount(String account);
}
