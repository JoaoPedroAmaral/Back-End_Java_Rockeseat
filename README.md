# springBootRocketSeat
- Curso de SpringBoot da RocketSeat 2023 - Hacktoberfest 2023

- Configuração do ambiente Java - versão 17 Maven - mvnrepository.com IntelliJ , VsCode ou Eclipse ApiDog - apidog.com


### Utilizando o Lombook para auxiliar os Getters e Setters em todos os atributos utilizando o @Data, e utilizando favre.lib para criptografar a senha.

<br>

### Baixa a dependencia do springData e do h2 para banco de dados, porem não recomendado para mercado por usar memoria do pc.
	
### - após isso utiliza no aplication.properties o codigo:
			spring.datasource.url=jdbc:h2:mem:tudolist
			spring.datasource.driveClassName=org.h2.Driver
			spring.datasource.username=admin
			spring.datasource.password=admin
			spring.jpa.database-plataform=org.hibernate.dialect.H2Dialect
			spring.h2.console.enabled=true

### porem tem como linkar outros banco de dados até mais recomendado como o mysql ou postegree

### altenticar os usuarios por meio da classe "Filter"
