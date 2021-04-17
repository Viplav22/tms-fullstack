drop database tms;
create database tms;
use tms;

show tables;

desc admin;
desc customer;
desc home_maker;
desc orders;

SELECT * FROM admin;
SELECT * FROM customer;
SELECT * FROM home_maker;
SELECT * FROM orders;

insert into admin values (101,"9822225420","admin@gmail.com","Admin","admin@123","9753134989");

insert into customer values (default,"Jabalpur","India","viplavsirsikar22@gmail.com","Viplav Sirsikar","vip@123","9753134689","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh",null);

insert into customer values (default,"Jabalpur","India","nilkanthsirsikar@gmail.com","Nilkanth Sirsikar","nil@123","9425672390","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh",null);

insert into home_maker values (default,"Jabalpur","India","archanasirsikar@gmail.com","Archana Sirsikar","arc@123","9827225420","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh");

insert into customer values (default,"Jabalpur","India","abc-1@gmail.com","ABC-1","abc@123","9448132390","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh",null);
insert into customer values (default,"Mumbai","India","abc-2@gmail.com","ABC-2","abc@123","93571390","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra",null);
insert into customer values (default,"Pune","India","abc-3@gmail.com","ABC-3","abc@123","942574610","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra",null);
insert into customer values (default,"Jabalpur","India","abc-4@gmail.com","ABC-4","abc@123","926657613","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh",null);
insert into customer values (default,"Mumbai","India","abc-5@gmail.com","ABC-5","abc@123","9285257613","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra",null);
insert into customer values (default,"Pune","India","abc-6@gmail.com","ABC-6","abc@123","9368527613","482002",null,null,"Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra",null);

insert into home_maker values (default,"Pune","India","hm-1@gmail.com","HM-1","abc@123","874634390","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra");
insert into home_maker values (default,"Mumbai","India","hm-2@gmail.com","HM-2","abc@123","725475390","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra");
insert into home_maker values (default,"Jabalpur","India","hm-3@gmail.com","HM-3","abc@123","9657423407","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh");
insert into home_maker values (default,"Pune","India","hm-4@gmail.com","HM-4","abc@123","9785314590","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra");
insert into home_maker values (default,"Mumbai","India","hm-5@gmail.com","HM-5","abc@123","8745134390","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Maharashtra");
insert into home_maker values (default,"Jabalpur","India","hm-6@gmail.com","HM-6","abc@123","9746272390","482002","Plot No.13, Chandrika Heritage Colony Lamti, Karmeta","Madhya Pradesh");

use tms2;
show tables;

insert into roles(name) values('ROLE_CUSTOMER');
insert into roles(name) values('ROLE_HOMEMAKER');
insert into roles(name) values('ROLE_ADMIN');

select * from users;
select * from roles;
select * from user_roles;

{
"username" : "Viplav",
"email" : "viplavsirsikar22@gmail.com",
"password" : "vip@123",
"role" : ["admin"]
}

{
"username" : "Nilkanth",
"email" : "nilkanthsirsikar@gmail.com",
"password" : "nil@123",
"role" : ["cus"]
}

{
"username" : "Archana",
"email" : "archanasirsikar@gmail.com",
"password" : "arc@123",
"role" : ["hm"]
}
