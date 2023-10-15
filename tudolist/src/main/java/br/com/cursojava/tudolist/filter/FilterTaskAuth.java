package br.com.cursojava.tudolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cursojava.tudolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//toda requisição antes de ir para rota irá passar pelo filtro

@Component //Toda classe que o spring gerencie vai ter q ter o @component, os @restController tem o @Component dentro deles
public class FilterTaskAuth extends OncePerRequestFilter {


    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        

        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")){
            //pegar a autenticação (user se senha)
            var authorization = request.getHeader("Authorization"); // ele pega os dados user e senha no auth da api, no meu caso o tunderClient

            var authEncoded = authorization.substring("Basic".length()).trim(); //o trim remove todos os espaços e pega a senha criptada em b64
            
            byte[] authDecode = Base64.getDecoder().decode(authEncoded); //decodifica a base64 em um array de byres
            var authString = new String(authDecode);//transforma os bytes na string user:senha


            String[] credentials = authString.split(":"); //divide a String em 2 arrays usando como referencia o :
            String username = credentials[0]; //["user"]
            String password = credentials[1];//["senha"]

            
            //validar usuario
            var user = this.userRepository.findByUsername(username); // procura o usuario do auth no username do banco de dados do usuario

            if(user == null){
                response.sendError(401, "User sem autorização");
            }else{
                //validar senha 
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified/*retorna booleano se for true é verdadeiro logo não precisa confirmar*/){
                    request.setAttribute("IdUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401, "User sem autorização");
                }
                //continuar

                
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }


   
    
}
