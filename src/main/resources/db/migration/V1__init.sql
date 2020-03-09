create table usr
(
  id int8 not null,
  login varchar(50) unique,
  o_auth_id varchar(255),
  first_name varchar(255),
  last_name varchar(255),
  email varchar(255),
  last_visit timestamp,
  gender varchar(10),
  picture varchar(255),
  password varchar(255),
  primary key (id)
);

create sequence sequence_usr
start with 3
increment by 1
maxvalue 1000000
no cycle;

---------------------------------

create table user_role (
 user_id int8 not null,
 roles varchar(255)
);

alter table user_role
  add constraint user_role_fk
  foreign key (user_id) references usr (id);
--------------------------------------

create table message_user (
id int8 not null,
message varchar(255),
user_id int8 not null,
create_date timestamp,
image_name varchar(255),
primary key (id)
);

alter table message_user
add constraint user_id_fk
foreign key (user_id) references usr (id);

create sequence message_user_sequence
start with 5
increment by 1
maxvalue 1000000
no cycle;
-------------------------------------

create table liked_messages_by_users (
user_id int8 not null,
message_id int8 not null
);

alter table liked_messages_by_users
add constraint pk_liked_messages_by_users
primary key (user_id, message_id);

alter table liked_messages_by_users
add constraint fk_user_id
foreign key (user_id)
references usr (id);

alter table liked_messages_by_users
add constraint fk_message_id
foreign key (message_id)
references message_user (id);
--------------------------------------

create table user_followers (
left_user_id int8,
right_user_id int8
);

alter table user_followers
add constraint fk_left_user_id
foreign key (left_user_id)
references usr (id);

alter table user_followers
add constraint fk_right_user_id
foreign key (right_user_id)
references usr (id);



