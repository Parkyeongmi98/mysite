desc board;

insert into board (no, title, contents, hit, reg_date, g_no, o_no, depth, user_no)
select null, '안녕', '반가워', 0, now(), ifnull(max(g_no), 0) + 1, 1, 0, 2
from board;

select a.no, a.title, a.contents, a.hit, a.reg_date, b.name, b.no, a.g_no, a.o_no, a.depth
	from board a, user b
    where a.user_no = b.no
    order by a.g_no desc, a.o_no asc;