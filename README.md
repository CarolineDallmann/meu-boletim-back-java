# meu-boletim-back

instalar java 17 https://download.oracle.com/java/17/archive/jdk-17.0.3_windows-x64_bin.exe

maven https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.zip

instalar o posgres versão 13.x
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
(atenção com senha usada durante a instalação, pois será necessária para acesso posterior)

instalar no eclipse o lombok 
https://projectlombok.org/setup/eclipse (seguir o tutorial)

no eclipse, importar um projeto maven existente. Rodar a classe MeuboletimApplication.java como um java application. Isso vai gerar uma configuração no workspace. Clicar no menu Run>Run configurations. Em Environment, preencha as seguintes variáveis de ambiente:

DATABASE_NAME=postgres

DATABASE_PASSWORD=(senha usada na instalação do postgres)

DATABASE_PORT=5432

DATABASE_URL=localhost

DATABASE_USER=postgres

*OBS: esses são os dados padrões do postgres, mas talvez os seus dados estejam diferentes. Configure de acordo.
