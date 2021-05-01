insert into job (description, name)
VALUES ('برنامه نویسی یکی از جذاب ترین مشاغل دنیاست', 'برنامه نویس');
insert into job (description, name)
VALUES ('مدیران به دنیا نمیآیند ساخته میشوند', 'مدیریت');

insert into address (city, person_id, street, unit)
values ('سمنان', 1, 'یوسف آباد', '20');
insert into address (city, person_id, street, unit)
values ('تهران', 3, 'کریمخان', '50');
insert into address (city, person_id, street, unit)
values ('تهران', 2, 'هروی', '50');

insert into address_tel (address_id, tel)
VALUES (1, '88878787');
insert into address_tel (address_id, tel)
VALUES (2, '88568787');

insert into Person (age, birthdate, first_name, last_name, job_id)
values (23, null, 'رضا', 'اسلامی', 2);
insert into Person (age, birthdate, first_name, last_name, job_id)
values (44, null, 'رضا', 'فاطمی', 1);
insert into Person (age, birthdate, first_name, last_name, job_id)
values (44, null, 'علی', 'اسلامی', 2);
insert into person (age, birthdate, first_name, last_name, job_id)
values (21, null, 'علی', 'فاطمی', 1)