-- delete of all stats

exec dbms_stats.delete_table_stats(USER, 'userinfo');

exec dbms_stats.delete_table_stats(USER, 'userroles');

exec dbms_stats.delete_table_stats(USER, 'menu');

exec dbms_stats.delete_table_stats(USER, 'menu_ingridients');

exec dbms_stats.delete_table_stats(USER, 'dishtype');

exec dbms_stats.delete_table_stats(USER, 'orders');

exec dbms_stats.delete_table_stats(USER, 'OrderToDish');

exec dbms_stats.delete_table_stats(USER, 'bills');