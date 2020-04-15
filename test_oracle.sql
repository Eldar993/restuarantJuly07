select ui.id      as "user_id",
       user_name,
       ui.role_id as "role_id_from_users",
       r.role_id  as "role_id_from_roles",
       r.role_title
from UserInfo ui
         LEFT JOIN UserRoles r ON r.role_id = ui.role_id;

select ui.id      as "user_id",
       user_name,
       ui.role_id as "role_id_from_users",
       r.role_id  as "role_id_from_roles",
       r.role_title
from UserInfo ui
         RIGHT JOIN UserRoles r ON r.role_id = ui.role_id;

select *
from UserInfo ui
         RIGHT OUTER JOIN UserRoles r ON ui.role_id = r.role_id;


SELECT r.role_title, SUM(ui.role_id) as "role counter"
FROM UserRoles r
         LEFT join UserInfo ui ON r.role_id = ui.role_id
group by role_title;



------------------------------------
-- TEST DISH_COUNT_BY_ORDER FUNCTION
------------------------------------

SELECT DISH_COUNT_BY_ORDER(22)
FROM DUAL;

------------------------------------
-- TEST DISH_COUNT_BY_USERNAME FUNCTION
------------------------------------

SELECT DISH_COUNT_BY_USERNAME('Ben')
FROM DUAL;

------------------------------------
-- TEST ORDER_COUNT_PER_INTERVAL FUNCTION
------------------------------------ 

SELECT ORDER_COUNT_PER_INTERVAL(timestamp '2015-10-12 21:22:23', timestamp '2020-08-18 15:37:57')
FROM DUAL;

------------------------------------
-- TEST ORDER_SUM_PER_INTERVAL FUNCTION
------------------------------------ 

SELECT ORDERS_SUM_PER_INTERVAL(timestamp '2015-10-12 21:22:23', timestamp '2020-08-18 15:37:57')
FROM DUAL;

------------------------------------
-- TEST CALORIES_IN_DISH FUNCTION
------------------------------------

SELECT CALORIES_IN_DISH('Unknown dish')
FROM dual;
SELECT CALORIES_IN_DISH('Borsh')
FROM dual;


------------------------------------
-- TEST ORDER_PRICE FUNCTION
------------------------------------

SELECT ORDER_PRICE(23)
FROM DUAL;


------------------------------------
-- TEST value_of_incomplete_order FUNCTION
------------------------------------

SELECT value_of_incomplete_order('Eldar') 
from dual;

SELECT value_of_incomplete_order('Batman') 
from dual;

------------------------------------
-- TEST paid_of_incomplete_order FUNCTION
------------------------------------

SELECT paid_of_incomplete_order('Eldar')
from dual;


------------------------------------
-- TEST find_id_by_role_title FUNCTION
------------------------------------

SELECT find_id_by_role_title('admin')
from dual;


------------------------------------
-- TEST first_incomplete_order FUNCTION
------------------------------------

SELECT first_incomplete_order('Ben') from dual;

------------------------------------
-- TEST last_incomplete_order FUNCTION
------------------------------------

SELECT last_incomplete_order('Ivan') from dual;


------------------------------------
-- TEST get_role_id FUNCTION
------------------------------------

SELECT get_role_id('admin') from dual;

------------------------------------
-- TEST get_user_id FUNCTION
------------------------------------

SELECT get_user_id('James') from dual;
 
------------------------------------
-- TEST get_dish_type_id FUNCTION
------------------------------------

SELECT get_dish_type_id('Beverage') 
from dual;

------------------------------------
-- TEST get_ingr_id FUNCTION
------------------------------------

SELECT get_ingr_id('potato')
from dual;

------------------------------------
-- TEST get_menu_id FUNCTION
------------------------------------

SELECT get_menu_id('Pizza')
from dual;

------------------------------------
-- TEST find_id_by_role_title FUNCTION
------------------------------------

SELECT find_id_by_role_title('customer')
from dual;



------------------------------------
-- TEST create_order PROCEDURE
------------------------------------

exec create_order('Ivan');

SELECT COUNT(id)
from ORDERS;

------------------------------------
-- TEST add_dish_to_order PROCEDURE
------------------------------------

exec add_dish_to_order('Ben', 'Pizza', 7);

SELECT DISH_COUNT_BY_ORDER(30)
FROM DUAL;

------------------------------------
-- TEST add_payment_for_order PROCEDURE
------------------------------------

exec add_payment_for_order('Ben',30);

SELECT order_id,paid_sum FROM Bills
WHERE paid_sum = 30;












