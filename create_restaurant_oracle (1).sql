-----------------------
-- Create UserRoles table
-----------------------
CREATE TABLE UserRoles
(
    role_id    int           NOT NULL,
    role_title varchar2(255) NOT NULL,
    CONSTRAINT PK_UserRoles PRIMARY KEY (role_id),
    CONSTRAINT U_userroles_role UNIQUE (role_title)
);

-----------------------
-- Create UserInfo table
-----------------------
CREATE TABLE UserInfo
(
    id            NUMBER(10)    NOT NULL,
    user_name     varchar2(255) NOT NULL,
    user_password varchar2(255) NOT NULL,
    role_id       int           NOT NULL,
    CONSTRAINT PK_UserInfo PRIMARY KEY (id),
    CONSTRAINT U_userinfo_username UNIQUE (user_name),
    CONSTRAINT FK_UserInfo_UserRole FOREIGN KEY (role_id) REFERENCES UserRoles (role_id)

);
----------------------
-- Define Indexes for UserInfo
----------------------

CREATE INDEX idx_userinfo_namepass ON UserInfo (user_name, user_password);

-----------------------
-- Create DishType table
-----------------------
CREATE TABLE DishType
(
    id    int           NOT NULL,
    title varchar2(255) NOT NULL,
    CONSTRAINT PK_DishType PRIMARY KEY (id),
    CONSTRAINT U_dishtype_title UNIQUE (title)
);

-----------------------
-- Create Menu table
-----------------------
CREATE TABLE Menu
(
    id           int           NOT NULL,
    dish_type_id int           NOT NULL,
    price        int           NOT NULL,
    dish         varchar2(255) NOT NULL,
    weight       int           NOT NULL,
    CONSTRAINT PK_Menu PRIMARY KEY (id),
    CONSTRAINT U_menu_dish UNIQUE (dish),
    CONSTRAINT FK_menu_dishtypeid FOREIGN KEY (dish_type_id) REFERENCES DishType (id)
);

----------------------
-- Define Indexes for Menu
----------------------

CREATE INDEX idx_menu_pricedish ON Menu (price, dish);


-----------------------
-- Create Ingridients table
-----------------------
CREATE TABLE Ingridients
(
    id       int           NOT NULL,
    title    varchar2(255) NOT NULL,
    calories int           NOT NULL,
    CONSTRAINT PK_Ingridients PRIMARY KEY (id),
    CONSTRAINT U_ingr_title UNIQUE (title)
);

----------------------
-- Define Indexes for Ingridients
----------------------

CREATE INDEX idx_ingr_titlecal ON Ingridients (title, calories);

-----------------------
-- Create Menu_ingridients table
-----------------------
CREATE TABLE Menu_ingridients
(
    ingridients_id int NOT NULL,
    menu_id        int NOT NULL,
    CONSTRAINT PK_Menu_ingridients PRIMARY KEY (ingridients_id, menu_id),
    CONSTRAINT FK_MenuIng_Ingridients FOREIGN KEY (ingridients_id) REFERENCES Ingridients (id)
        ON DELETE CASCADE,
    CONSTRAINT FK_MenuIng_DishType FOREIGN KEY (menu_id) REFERENCES Menu (id)
        ON DELETE CASCADE
);

----------------------
-- Define Indexes for Menu_ingridients
----------------------

CREATE INDEX idx_menuing_menuid ON Menu_ingridients (menu_id);
CREATE INDEX idx_menuing_ingrid ON Menu_ingridients (ingridients_id);

-----------------------
-- Create Orders table
-----------------------
CREATE TABLE Orders
(
    id         int                      NOT NULL,
    order_time TimeStamp(2)             NOT NULL,
    user_id    int                      NOT NULL,
    status     char(1 char) default 'N' not null
        constraint check_order_status
            check (status is not null and status in ('D', 'N')),
    CONSTRAINT PK_Orders PRIMARY KEY (id),
    CONSTRAINT FK_Orders_UserInfo FOREIGN KEY (user_id) REFERENCES UserInfo (id)
);
----------------------
-- Define Indexes for Orders
----------------------

CREATE INDEX idx_orders_ordertime ON Orders (order_time);



-----------------------
-- Create OrderToDish table
-----------------------
CREATE TABLE OrderToDish
(
    order_id int NOT NULL,
    menu_id  int NOT NULL,
    count    int NOT NULL,
    CONSTRAINT PK_OrderTodish PRIMARY KEY (order_id, menu_id),
    CONSTRAINT FK_OrdToDish_Ord FOREIGN KEY (order_id) REFERENCES Orders (id),
    CONSTRAINT FK_OrdToDish_Menu FOREIGN KEY (menu_id) REFERENCES Menu (id)
);

----------------------
-- Define Indexes for OrderToDish
----------------------

CREATE INDEX idx_ordtodish_menu_id ON OrderToDish (menu_id);


-----------------------
-- Create Bills table
-----------------------
CREATE TABLE Bills
(
    id            int          NOT NULL,
    order_id      int          NOT NULL,
    paid_sum      int          NOT NULL,
    complete_time TIMEStamp(2) NOT NULL,
    CONSTRAINT PK_Bills PRIMARY KEY (id),
    CONSTRAINT FK_Bills_Ord FOREIGN KEY (order_id) REFERENCES Orders (id)
);

----------------------
-- Define Indexes for Bills
----------------------

CREATE INDEX idx_bills_ordidpaidsum ON Bills (order_id, paid_sum);


-----------------------
-- Create sequence for UserInfo table
-----------------------
CREATE SEQUENCE main_seq
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER UserInfo_on_insert
    BEFORE INSERT
    ON UserInfo
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/

-----------------------
-- Create sequence for UserRoles table
-----------------------

CREATE OR REPLACE TRIGGER UserRoles_on_insert
    BEFORE INSERT
    ON UserRoles
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.role_id
    FROM dual;
END;
/


-----------------------
-- Create sequence for Menu table
-----------------------

CREATE OR REPLACE TRIGGER menu_on_insert
    BEFORE INSERT
    ON Menu
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/

-----------------------
-- Create sequence for DishType table
-----------------------

CREATE OR REPLACE TRIGGER DishType_on_insert
    BEFORE INSERT
    ON DishType
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/


-----------------------
-- Create sequence for Ingridients table
-----------------------

CREATE OR REPLACE TRIGGER Ingr_on_insert
    BEFORE INSERT
    ON Ingridients
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/


-----------------------
-- Create sequence for Orders table
-----------------------

CREATE OR REPLACE TRIGGER orders_on_insert
    BEFORE INSERT
    ON Orders
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/

-----------------------
-- Create sequence for Bills table
-----------------------

CREATE OR REPLACE TRIGGER bills_on_insert
    BEFORE INSERT
    ON Bills
    FOR EACH ROW
BEGIN
    SELECT main_seq.nextval
    INTO :new.id
    FROM dual;
END;
/


---------------------------
-- Create translations
---------------------------

create or replace FUNCTION get_role_id(n_role_title in userroles.role_title%type)
    RETURN INT
    IS
    n_role_id INT;
BEGIN
    SELECT role_id
    INTO n_role_id
    FROM UserRoles
    WHERE role_title = n_role_title;
    RETURN (n_role_id);
exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Role not found'
            );
END get_role_id;
/

create or replace FUNCTION get_user_id(n_user_name in userinfo.user_name%type)
    RETURN INT
    IS
    n_user_id INT;
BEGIN
    SELECT id
    INTO n_user_id
    FROM UserInfo
    WHERE user_name = n_user_name;
    RETURN (n_user_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'User not found'
            );
END get_user_id;
/

create or replace FUNCTION get_dish_type_id(n_title in dishtype.title%type)
    RETURN INT
    IS
    n_id INT;
BEGIN
    SELECT id
    INTO n_id
    FROM DishType
    WHERE title = n_title;
    RETURN (n_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Dish not found'
            );
END get_dish_type_id;
/

create or replace FUNCTION get_ingr_id(n_title in ingridients.title%type)
    RETURN INT
    IS
    n_id INT;
BEGIN
    SELECT id
    INTO n_id
    FROM Ingridients
    WHERE title = n_title;
    RETURN (n_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Ingredient not found'
            );
END get_ingr_id;
/

create or replace FUNCTION get_menu_id(n_dish in menu.dish%type)
    RETURN INT
    IS
    n_id INT;
BEGIN
    SELECT id
    INTO n_id
    FROM Menu
    WHERE dish = n_dish;
    RETURN (n_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Menu not found'
            );
END get_menu_id;
/


---------------------------
-- Create package insert procedures
---------------------------

create or replace package insert_package
as
    procedure new_UserInfo(n_user_name in UserInfo.user_name%type,
                           n_user_password in UserInfo.user_password%type,
                           n_role_title in UserRoles.role_title%type);


    procedure new_UserRoles(
        n_role_title in UserRoles.role_title%type
    );


    procedure new_menu(n_dish_title in dishtype.title%type,
                       n_price in menu.price%type,
                       n_dish in menu.dish%type,
                       n_weight in menu.weight%type);


    procedure new_menu_ingr(n_ingr_title in ingridients.title%type,
                            n_menu_dish in menu.dish%type);


    procedure new_dishtype(
        n_title in dishtype.title%type
    );


    procedure new_ingridients(n_title in ingridients.title%type,
                              n_calories in ingridients.calories%type);


    procedure new_orders(n_order_time in orders.order_time%type,
                         n_user_name in userinfo.user_name%type,
                         n_status in orders.status%type);


    procedure new_OrderToDish(n_order_id in OrderToDish.order_id%type,
                              n_menu_dish in menu.dish%type,
                              n_count in OrderToDish.count%type);


    procedure new_bills(n_order_id in bills.order_id%type,
                        n_paid_sum in bills.paid_sum%type,
                        n_complete_time in bills.complete_time%type);
end insert_package;
/
---------------------------
-- Create package insert body procedures
---------------------------
    create or replace package body insert_package
as
    procedure new_UserInfo(n_user_name in UserInfo.user_name%type,
                           n_user_password in UserInfo.user_password%type,
                           n_role_title in UserRoles.role_title%type)
    AS
        n_role_id INT;
    begin
        n_role_id := get_role_id(n_role_title);
        INSERT INTO UserInfo(user_name, user_password, role_id)
        values (n_user_name, n_user_password, n_role_id);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this user!
                    User name is used.'
                );
    end new_UserInfo;


    procedure new_UserRoles(
        n_role_title in UserRoles.role_title%type
    )
    as
    begin
        INSERT INTO UserRoles(role_title)
        values (n_role_title);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this role title!
                    Role name is used.'
                );
    end new_UserRoles;


    procedure new_menu(n_dish_title in dishtype.title%type,
                       n_price in menu.price%type,
                       n_dish in menu.dish%type,
                       n_weight in menu.weight%type)
    as
        n_dish_type_id INT;
    begin
        n_dish_type_id := get_dish_type_id(n_dish_title);
        INSERT INTO Menu(dish_type_id, price, dish, weight)
        values (n_dish_type_id, n_price, n_dish, n_weight);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this menu dish!
                    Menu dish name is used.'
                );
    end new_menu;


    procedure new_menu_ingr(n_ingr_title in ingridients.title%type,
                            n_menu_dish in menu.dish%type)
    as
        n_ingridients_id INT;
        n_menu_id        INT;
    begin
        n_ingridients_id := get_ingr_id(n_ingr_title);
        n_menu_id := get_menu_id(n_menu_dish);

        INSERT INTO menu_ingridients(ingridients_id, menu_id)
        values (n_ingridients_id, n_menu_id);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this menu ingridient!
                   Menu Ingridient name is used.'
                );
    end new_menu_ingr;


    procedure new_dishtype(
        n_title in dishtype.title%type
    )
    as
    begin
        INSERT INTO DishType(title)
        values (n_title);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this dishtype!
                    Dish type name is used.'
                );
    end new_dishtype;


    procedure new_ingridients(n_title in ingridients.title%type,
                              n_calories in ingridients.calories%type)
    as
    begin
        INSERT INTO Ingridients(title, calories)
        values (n_title, n_calories);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this ingridient title!
                    ingridient title name is used.'
                );
    end new_ingridients;


    procedure new_orders(n_order_time in orders.order_time%type,
                         n_user_name in userinfo.user_name%type,
                         n_status in orders.status%type)
    as
        n_user_id INT;
    begin
        n_user_id := get_user_id(n_user_name);
        INSERT INTO Orders(order_time, user_id, status)
        values (n_order_time, n_user_id, n_status);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this order!
                    Orders user_id name is used.'
                );
    end new_orders;


    procedure new_OrderToDish(
--    n_user_name in UserInfo.user_name%type,
        n_order_id in OrderToDish.order_id%type,
        n_menu_dish in menu.dish%type,
        n_count in OrderToDish.count%type)
    as
        n_menu_id INT;
    begin
        dbms_output.put_line(n_menu_dish);
        n_menu_id := get_menu_id(n_menu_dish);
--     n_oreder_id := LAST_OPENED_ORDER_ID_BY_USER(n_user_name)
        dbms_output.put_line(n_order_id);
        dbms_output.put_line(n_menu_id);
        INSERT INTO OrderToDish(order_id, menu_id, count)
        values (n_order_id, n_menu_id, n_count);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this dish order!
                    menu id name is used.'
                );
    end new_OrderToDish;


    procedure new_bills(
--    n_user_name in UserInfo.user_name%type,
        n_order_id in bills.order_id%type,
        n_paid_sum in bills.paid_sum%type,
        n_complete_time in bills.complete_time%type)
    as
    begin
        --     n_oreder_id := LAST_OPENED_ORDER_ID_BY_USER(n_user_name)
        INSERT INTO Bills(order_id, paid_sum, complete_time)
        values (n_order_id, n_paid_sum, n_complete_time);
    exception
        WHEN DUP_VAL_ON_INDEX then
            raise_application_error(
                    -20003,
                    'Can not store this bill!
                    Bills order id name is used.'
                );
    end new_bills;

end insert_package;
/

-----------------------
-- Create Functions
----------------------
create or replace FUNCTION dish_count_by_order(ord_id INT)
    RETURN INT
    IS
    order_count INT;
BEGIN
    SELECT SUM(OrderToDish.count)
    INTO order_count
    FROM OrderToDish
    WHERE order_id = ord_id;
    RETURN (order_count);

END dish_count_by_order;
/



create or replace FUNCTION dish_count_by_username(u_name userinfo.user_name%type)
    RETURN INT
    IS
    order_count INT;
BEGIN
    SELECT SUM(OrderToDish.count)
    INTO order_count
    FROM OrderToDish
             INNER JOIN Orders
                        ON OrderToDish.order_id = Orders.id
             INNER JOIN UserInfo
                        ON UserInfo.id = Orders.user_id
    WHERE UserInfo.user_name = u_name;
    RETURN (order_count);

END dish_count_by_username;
/


create or replace FUNCTION find_id_by_role_title(
    u_role_title userroles.role_title%type)
    RETURN INT
    IS
    rid INT;
BEGIN
    SELECT role_id
    INTO rid
    FROM UserRoles
    WHERE UserRoles.role_title = u_role_title;
    RETURN (rid);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Role not found'
            );
END find_id_by_role_title;
/


create or replace FUNCTION ORDER_COUNT_PER_INTERVAL(from_time Timestamp, to_time Timestamp)
    RETURN INT
    IS
    order_count INT;
BEGIN
    SELECT COUNT(ORDERS.id)
    INTO order_count
    FROM Orders
         -- WHERE order_time BETWEEN from_time AND to_time;
    WHERE order_time >= from_time
      AND order_time < to_time;
    RETURN (order_count);

END ORDER_COUNT_PER_INTERVAL;
/



create or replace FUNCTION ORDERS_SUM_PER_INTERVAL(from_time Timestamp, to_time Timestamp)
    RETURN INT
    IS
    order_sum INT;
BEGIN
    SELECT SUM(Bills.paid_sum)
    INTO order_sum
    FROM Bills
             INNER JOIN Orders
                        ON Orders.id = Bills.order_id
    WHERE order_time BETWEEN from_time AND to_time;
    RETURN (order_sum);

END ORDERS_SUM_PER_INTERVAL;
/



create or replace FUNCTION CALORIES_IN_DISH(n_dish Menu.dish%type)
    RETURN INT
    IS
    DISH_CALORIES INT;
BEGIN
    SELECT Sum(calories)
    INTO DISH_CALORIES
    FROM Ingridients
             INNER JOIN Menu_ingridients
                        ON menu_ingridients.ingridients_id = ingridients.id
             INNER JOIN menu
                        ON menu_ingridients.menu_id = menu.id
    WHERE Menu.dish = n_dish;
    RETURN (DISH_CALORIES);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'Dish calories not found.'
            );

END CALORIES_IN_DISH;
/

create or replace FUNCTION CALORIES_IN_DISH_2(n_dish Menu.dish%type)
    RETURN INT
    IS
    DISH_CALORIES INT;
    n_menu_id     INT;
BEGIN
    n_menu_id := get_menu_id(n_dish);
    IF n_menu_id IS NULL THEN
        raise_application_error(
                -20003,
                'Can''t finish calculation. Dish not found.'
            );
    END IF;

    SELECT Sum(calories)
    INTO DISH_CALORIES
    FROM Ingridients
             INNER JOIN Menu_ingridients
                        ON menu_ingridients.ingridients_id = ingridients.id
    WHERE Menu_ingridients.menu_id = n_menu_id;

    RETURN (DISH_CALORIES);
END CALORIES_IN_DISH_2;
/



create or replace FUNCTION ORDER_PRICE(n_order_id Orders.id%type)
    RETURN INT
    IS
    price_of_order INT;
BEGIN
    SELECT SUM(Menu.price)
    INTO price_of_order
    FROM Menu
             INNER JOIN OrderToDish
                        ON OrderToDish.menu_id = Menu.id
    WHERE OrderToDish.order_id = n_order_id;
    RETURN (price_of_order);

END ORDER_PRICE;
/

-----------------------
-- Work with orders
-----------------------

create or replace FUNCTION first_incomplete_order(n_user_name UserInfo.user_name%type)
    RETURN INT
    IS
    n_order_id INT;
BEGIN
    SELECT MIN(Orders.id)
    INTO n_order_id
    FROM Orders
             INNER JOIN UserInfo
                        ON UserInfo.id = ORDERS.user_id
    WHERE user_name = n_user_name
      AND status = 'N';
    RETURN (n_order_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'User has no incomplete order'
            );

END first_incomplete_order;
/

create or replace FUNCTION last_incomplete_order(n_user_name UserInfo.user_name%type)
    RETURN INT
    IS
    n_order_id INT;
BEGIN
    SELECT MAX(Orders.id)
    INTO n_order_id
    FROM Orders
             INNER JOIN UserInfo
                        ON UserInfo.id = ORDERS.user_id
    WHERE user_name = n_user_name
      AND status = 'N';
    RETURN (n_order_id);

exception
    when NO_DATA_FOUND then
        raise_application_error(
                -20003,
                'User has no incomplete order'
            );

END last_incomplete_order;
/

create or replace PROCEDURE create_order(n_user_name UserInfo.user_name%type)
    IS
    n_user_id INT;
BEGIN
    n_user_id := get_user_id(n_user_name);

    insert_package.new_orders(CURRENT_TIMESTAMP, n_user_name, 'N');
END create_order;
/


create or replace PROCEDURE add_dish_to_order(n_user_name UserInfo.user_name%type,
                                              n_dish Menu.dish%type,
                                              n_count OrderToDish.count%type)
    IS
    n_user_id          UserInfo.user_name%type;
    n_added_menu_id    Menu.dish%type;
    n_incomplete_order Orders.id%type;
    n_prev_count       OrderToDish.count%type;
BEGIN
    n_user_id := get_user_id(n_user_name);
    n_added_menu_id := get_menu_id(n_dish);
    n_incomplete_order := last_incomplete_order(n_user_name);

    insert_package.new_OrderToDish(n_incomplete_order, n_dish, n_count);


exception
    when DUP_VAL_ON_INDEX then
        UPDATE OrderToDish
        SET count = count + n_count
        WHERE menu_id = n_added_menu_id
          AND order_id = n_incomplete_order;

END add_dish_to_order;

/


create or replace FUNCTION value_of_incomplete_order(n_user_name UserInfo.user_name%type)
    RETURN INT
    IS
    n_user_id          UserInfo.user_name%type;
    n_incomplete_order Orders.id%type;
    n_value            INT;
BEGIN
    n_user_id := get_user_id(n_user_name);
    n_incomplete_order := first_incomplete_order(n_user_name);

    SELECT NVL(SUM(Menu.price * OrderToDish.count), 0)
    INTO n_value
    FROM OrderToDish
             INNER JOIN Menu
                        ON Menu.id = OrderToDish.menu_id
    WHERE OrderToDish.order_id = n_incomplete_order;
    RETURN (n_value);

exception
    when NO_DATA_FOUND then
        RETURN (0);
END value_of_incomplete_order;
/

create or replace FUNCTION paid_of_incomplete_order(n_user_name UserInfo.user_name%type)
    RETURN INT
    IS
    n_user_id          UserInfo.user_name%type;
    n_incomplete_order Orders.id%type;
    n_value            INT;
BEGIN
    n_user_id := get_user_id(n_user_name);
    n_incomplete_order := first_incomplete_order(n_user_name);

    SELECT SUM(paid_sum)
    INTO n_value
    FROM Bills
    WHERE order_id = n_incomplete_order;
    RETURN (n_value);
exception
    when NO_DATA_FOUND then
        RETURN (0);
END paid_of_incomplete_order;
/


create or replace PROCEDURE add_payment_for_order(n_user_name UserInfo.user_name%type,
                                                  n_paid_sum Bills.paid_sum%type)
    IS
    n_user_id          UserInfo.user_name%type;
    n_incomplete_order Orders.id%type;
    n_need_pay         INT;
    n_already_paid     INT;
BEGIN
    n_user_id := get_user_id(n_user_name);

    n_incomplete_order := first_incomplete_order(n_user_name);

    insert_package.new_bills(n_incomplete_order, N_PAID_SUM, CURRENT_TIMESTAMP);

    n_need_pay := value_of_incomplete_order(n_user_name);
    n_already_paid := paid_of_incomplete_order(n_user_name);

    IF n_need_pay <= n_already_paid THEN
        UPDATE Orders
        SET status = 'D'
        WHERE Orders.id = n_incomplete_order;
    END IF;
END add_payment_for_order;
/
-----------------------
-- Create Views
-----------------------
-- view of UserInfo and their UserRoles
create or replace view UserDetails
as
select user_name,
       role_title
from UserInfo
         LEFT OUTER JOIN UserRoles
                         ON UserInfo.role_id = UserRoles.role_id
;


create or replace view BillDetails
as
select orders.id order_id,
       user_name,
       bills.id  bill_id,
       paid_sum,
       complete_time
from Orders
         INNER JOIN Bills
                    ON orders.id = bills.id
         INNER JOIN UserInfo
                    ON orders.user_id = UserInfo.id
ORDER BY order_id, user_name, bill_id
;

-- view of UserInfo and their UserRoles
create or replace view UserInfo_n_UserRoles
as
select user_name,
       role_title,
       UserRoles.role_id
from UserInfo
         LEFT OUTER JOIN UserRoles
                         ON UserInfo.role_id = UserRoles.role_id
;

create or replace view orders_n_bills
as
select orders.id order_id,
       bills.id  bill_id,
       user_id,
       paid_sum
from Orders
         INNER JOIN Bills
                    ON orders.id = bills.id
;