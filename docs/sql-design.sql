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


