exec insert_package.new_userroles('buyer');
exec insert_package.new_userroles('customer');
exec insert_package.new_userroles('admin');
exec insert_package.new_userroles('seller');

exec insert_package.new_userinfo('Ben','ben10','admin');
exec insert_package.new_userinfo('Ivan','ivanov798','admin');
exec insert_package.new_userinfo('James','bond007','customer');
exec insert_package.new_userinfo('Michail','misha1980','buyer');
exec insert_package.new_userinfo('Eldar','eldar993','admin');


exec insert_package.new_dishtype('Beverage');
exec insert_package.new_dishtype('Main dish');
exec insert_package.new_dishtype('Soup');
exec insert_package.new_dishtype('Desert');


exec insert_package.new_menu('Soup',10,'Donut123',20);
exec insert_package.new_menu('Beverage',25,'Burger',100);
exec insert_package.new_menu('Main dish',15,'Tomato soup',50);
exec insert_package.new_menu('Main dish',18,'Borsh',50);
exec insert_package.new_menu('Beverage',20,'Steyk',150);
exec insert_package.new_menu('Soup',10,'Pizza',30);

exec insert_package.new_ingridients('potato',50);
exec insert_package.new_ingridients('onion',50);


exec insert_package.new_menu_ingr('potato','Borsh');
exec insert_package.new_menu_ingr('onion','Burger');
exec insert_package.new_menu_ingr('potato','Donut123');
exec insert_package.new_menu_ingr('onion','Borsh');


exec insert_package.new_orders(timestamp '2015-10-12 21:22:23','Ben','D');
exec insert_package.new_orders(timestamp '2020-08-18 15:37:57','Eldar','D');

exec create_order('Ivan');
exec create_order('Eldar');


exec add_dish_to_order('Ivan','Borsh',2);
exec add_dish_to_order('Ivan','Burger',5);
exec add_dish_to_order('Eldar','Donut123',7);

exec add_payment_for_order('Ivan',87);
exec add_payment_for_order('Ivan',13);
exec add_payment_for_order('Ivan',50);
exec add_payment_for_order('Eldar',99999);