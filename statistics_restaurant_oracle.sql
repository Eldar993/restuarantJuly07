-------------------------
-- stats for table USERINFO
-------------------------
exec dbms_stats.gather_table_stats(USER, 'userinfo', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';

select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'USERINFO' AND owner = 'GULIYEE';

-------------------------
-- stats for table USERROLES
-------------------------
exec dbms_stats.gather_table_stats(USER, 'userroles', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'USERROLES' AND owner = 'GULIYEE';

-------------------------
-- stats for table MENU
-------------------------
exec dbms_stats.gather_table_stats(USER, 'menu', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'MENU' AND owner = 'GULIYEE';

-------------------------
-- stats for table MENU_INGRIDIENTS
-------------------------
exec dbms_stats.gather_table_stats(USER, 'menu_ingridients', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'MENU_INGRIDIENTS' AND owner = 'GULIYEE';

-------------------------
-- stats for table DISHTYPE
-------------------------
exec dbms_stats.gather_table_stats(USER, 'dishtype', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'DISHTYPE' AND owner = 'GULIYEE';

-------------------------
-- stats for table INGRIDIENTS
-------------------------
exec dbms_stats.gather_table_stats(USER, 'ingridients', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'INGRIDIENTS' AND owner = 'GULIYEE';

-------------------------
-- stats for table ORDERS
-------------------------
exec dbms_stats.gather_table_stats(USER, 'orders', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'ORDERS' AND owner = 'GULIYEE';

-------------------------
-- stats for table ORDER_DISH
-------------------------
exec dbms_stats.gather_table_stats(USER, 'OrderToDish', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'ORDER_DISH' AND owner = 'GULIYEE';

-------------------------
-- stats for table BILLS
-------------------------
exec dbms_stats.gather_table_stats(USER, 'bills', cascade => TRUE);
select *
from ALL_TAB_COLUMNS
where owner = 'GULIYEE';


select column_name, last_analyzed, nullable, num_distinct, num_nulls, density, histogram
from ALL_TAB_COLUMNS
where table_name = 'BILLS' AND owner = 'GULIYEE';