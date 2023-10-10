package br.com.cursojava.tudolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//criando a interface do User - ter os metodos mas não a implementação deles
/*JpaRepository<> o "<>" são generator (atributos dinamicos) no caso do JpaRepository<> ele serece um <T(a classe que representa),ID(o tipo de ID)>*/

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
}
