insert into users(id, username, password, active) values(1, 'user1', 'password1',1);
insert into users(id, username, password, active) values(2, 'user2', 'password2',1);
insert into users(id, username, password, active) values(3, 'pro_user1', 'password3',1);
insert into users(id, username, password, active) values(4, 'admin1', 'password4',0);
insert into users(id, username, password, active) values(5, 'admin2', 'password5',1);

insert into groups(id, name) values(1, 'users');
insert into groups(id, name) values(2, 'pro_users');
insert into groups(id, name) values(3, 'admins');

insert into users_groups_xref(user_id, group_id) values(1,1);
insert into users_groups_xref(user_id, group_id) values(2,1);
insert into users_groups_xref(user_id, group_id) values(3,1);
insert into users_groups_xref(user_id, group_id) values(3,2);
insert into users_groups_xref(user_id, group_id) values(4,3);
insert into users_groups_xref(user_id, group_id) values(5,3);