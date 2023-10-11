package br.com.cursojava.tudolist.user;
/*
 Metodos de acesso HTTP
 * GET - Busca um dado/info
 * POST - Adiciona um dado/info
 * DELETE - Remove um dado/info
 * PUT - Altera um dado/info
 * PATCH - Altera apenas um dado/info
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/*
  MODIFICADORES:
  * public
  * private
  * protected
  */


@RestController
@RequestMapping("/Users")
public class UserController {

    /*Aqui precisamos instaciar IUSser para o spring gerenciar o metodo*/
    
    @Autowired //ele vai gerenciar todo o ciclo de vida da interface
    //chamando a interface
    private IUserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
      //cria um erro customizado do IUser para o erro de repetição do username
      var user = this.userRepository.findByUsername(userModel.getUsername());

      if(user != null){
        //mensagem de erro para usuario existente
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existente!!");
      }

      var passwordHashed= BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());//criptografar o password com o Bcrypt

      userModel.setPassword(passwordHashed);//setar o password criptado no banco de dados

       var userCreated = this.userRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    } 
}
