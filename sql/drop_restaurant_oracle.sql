--------------------
-- drop all views
---------------------
drop view UserDetails;
drop view BillDetails;
DROP VIEW Orders_N_Bills;
DROP VIEW UserInfo_N_UserRoles;


--------------------
-- drop all indexes
---------------------
drop index idx_menuing_menuid;
drop index idx_menuing_ingrid;
drop index idx_ordtodish_menu_id;
drop index idx_bills_ordidpaidsum;
drop index idx_orders_ordertime;
drop index idx_menu_pricedish;
drop index idx_ingr_titlecal;
drop index idx_userinfo_namepass;



---------------------
-- drop all packages
---------------------
drop package insert_package;

---------------------
-- drop all functions
---------------------
drop function DISH_COUNT_BY_ORDER;
drop function DISH_COUNT_BY_USERNAME;
drop function ORDER_COUNT_PER_INTERVAL;
drop function ORDERS_SUM_PER_INTERVAL;
drop function CALORIES_IN_DISH;
drop function CALORIES_IN_DISH_2;
DROP FUNCTION ORDER_PRICE;
drop function first_incomplete_order;
drop function last_incomplete_order;
drop procedure create_order;
drop procedure add_dish_to_order;
drop function value_of_incomplete_order;
drop function paid_of_incomplete_order;
DROP PROCEDURE add_payment_for_order;


----------------------
-- drop all translations
----------------------
DROP FUNCTION get_role_id;
DROP FUNCTION get_user_id;
DROP FUNCTION get_dish_type_id;
DROP FUNCTION get_ingr_id;
DROP FUNCTION get_menu_id;
DROP FUNCTION find_id_by_role_title;


----------------------
-- drop all triggers
----------------------

drop trigger BILLS_ON_INSERT;
drop trigger DISHTYPE_ON_INSERT;
drop trigger Ingr_on_insert;
drop trigger MENU_ON_INSERT;
drop trigger ORDERS_ON_INSERT;
drop trigger UserRoles_ON_INSERT;
drop trigger UserInfo_ON_INSERT;


---------------------
-- drop all sequences
---------------------

drop sequence main_seq;

----------------------
-- drop all tables
----------------------

drop table Bills;
drop table Menu_Ingridients;
drop table OrderToDish;
drop table Menu;
drop table Orders;
drop table Ingridients;
drop table DishType;

drop table UserInfo;
drop table UserRoles;

