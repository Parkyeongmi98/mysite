-- guestbook
desc guestbook;
select * from guestbook;

select no, name, password, message, reg_date from guestbook order by no desc;
insert into guestbook values(null, "박영미", password("1234"), "반갑습니다", date_format(now(), "%Y-%m-%d"));

-- user
desc user;

select * from user;

-- join
insert into user values(null, '박영미', 'yeongmi_@naver.com', password('1234'), 'female', now());

-- login
select no, name from user where email='yeongmi_@naver.com' and password = password('1234');


alter table user add column role enum("ADMIN", "USER") default "USER" after gender;
insert into user values (null, '관리자', 'admin@mysite.com', password('1234'), 'male', "ADMIN", now());


desc site;
insert into site values(null, 'MySite', '안녕하세요. 박영미의 mysite에 오신 것을 환영합니다.', '/assets/images/profile.png', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n메뉴는 사이트 소개, 방명록, 게시판이 있구요.\n JAVA 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는거 다 합쳐서 만들어 놓은 사이트 입니다.');
select * from site;

select title, welcome, profile, description from site order by no asc limit 0, 1;
update site set title='YourSite' where no = 1;

desc gallery;
select * from gallery;

delete from user where email = "yeongmi_@naver.com";
