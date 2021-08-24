# sistema-de-documentacao

## Rodando o programa
Para rodar o programa é preciso ter o java 11 instalado em sua maquina. Em seguida é preciso criar um container com o mysql e um para o redis:

### MySQL

```
docker run --name mysql -p 32779:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql
```

Em seguida é preciso criar uma database chamada document, para isso acesse a CLI do container e logue e crie a database com os comandos:

```
mysql -u root -p
password
create database document;
```

### Redis
```
docker run --name rediscache -p 6379:6379 -d -t redis
```

Agora basta rodar o programa.
