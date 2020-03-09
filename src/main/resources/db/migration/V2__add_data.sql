insert into usr (id, login, first_name, last_name, password) values (1, 'admin','Самый', 'Главный', '123qaz');

insert into user_role (user_id, roles) values (1, 'ADMIN'), (1, 'USER');

create extension if not exists pgcrypto;
update usr set password = crypt(password, gen_salt('bf', 8));