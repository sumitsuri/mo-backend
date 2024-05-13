-- schema for generic business-management-system
-- org/location/store comms will be seperated out later for accomodating details like "customer-support","customer-support-email" etc..

--will be used for customer-facing website from where user can clock on "contact-us" and we will store details here and will send meeting invite and email/message notifications to them.
 CREATE TABLE `lead` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,--will be helpful tomorrow to identify from which channel it is coming. To start with, it will be from website
  `additional_details` TEXT DEFAULT NULL,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (org_id) references organisation(id),
  );


CREATE TABLE `organisation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  );

 CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  org_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (org_id) references organisation(id),
  );

CREATE TABLE `user_metadata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gender` varchar(255) NOT NULL, --MALE,FEMALE (--will be used for recommendation of servcies and offers based on usertype)
  `age` int default null, --will be used for recommendation of servcies and offers based on usertype
  user_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id)
);

CREATE TABLE `organisation_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `pincode` varchar(255) NOT NULL,
  `phonenumber` varchar(255),
  org_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  FOREIGN KEY (org_id) references organisation(id),
  PRIMARY KEY (`id`)
);

CREATE TABLE `organisation_location_store` (
  `id` int NOT NULL AUTO_INCREMENT,
   store_identifier varchar(255) NOT NULL,--this can be like B123,Coomonarea234
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,--salon,gym,convenient-store,creche
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  org_loc_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (org_loc_id) references organisation_location(id),
  );


CREATE TABLE `user_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `auth_token` varchar(255) NOT NULL, --jwt which will contain token expiry
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id)
);

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL, --  // can be 'admin','owner', 'employee','customer'
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`)
);
CREATE TABLE `page_entity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
   added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  page_entity_id int default null,
  FOREIGN KEY (page_entity_id) references page_entity(id),
  PRIMARY KEY (`id`)
);


CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
   page_entity_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
   FOREIGN KEY (page_entity_id) references page_entity(id)
);

CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
   role_id int not null,
  user_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (role_id) references role(id),
  FOREIGN KEY (user_id) references user(id)
);

--servicename,description,gender,category,service,price,notes,
price create table service_category(
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `category_id` int default null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (category_id) references service_category(id)
);

CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) default NULL,
  `estimated_time_to_complete` DECIMAL(6, 4) NOT NULL,
  `service_category_id` int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
   FOREIGN KEY (service_category_id) references service_category(id)
  );

-- this price can be imported by admin for a given new store(stored in service_store_price) and then user can edit it for customisation.

CREATE TABLE `service_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(6, 4) NOT NULL,
  `service_id` int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (service_id) references service(id) -- organisation_store
  );

  CREATE TABLE `store_service` (
    `id` int NOT NULL AUTO_INCREMENT,
    `store_id` int not null,
    `service_id` int not null,
    `status` varchar(255) NOT NULL,--active,inactive,discontinued
    `estimated_time_to_complete` DECIMAL(6, 4) NOT NULL,
    added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    added_by int not null,
    updated_by int not null,
    PRIMARY KEY (`id`),
    FOREIGN KEY (service_id) references service(id),
    FOREIGN KEY (store_id) references organisation_store(id),
    UNIQUE KEY `store_service_idx` (`service_id`, `store_id`)
    );

CREATE TABLE `store_service_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(6, 4) NOT NULL,
  `store_service_id` int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (store_service_id) references store_service(id)
);


CREATE TABLE `user_availability` (
  `id` int NOT NULL AUTO_INCREMENT,
   user_id int not null,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id)
);

CREATE TABLE `user_booking` (
  `id` int NOT NULL AUTO_INCREMENT,
   user_id int not null,
   booking_start_time TIMESTAMP NOT NULL,
   store_service_id int not null,
   status varchar(255) NOT NULL,-- requested, cancelled, completed, accepted
   notes text NOT NULL, -- can be used for addition additional-details
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id),
  FOREIGN KEY (store_service_id) references store_service(id),
  UNIQUE KEY `booking_store_service_idx` (`store_service_id`, `booking_id`)
);

CREATE TABLE `user_booking_details` (
  `id` int NOT NULL AUTO_INCREMENT,
   booking_id int not null,
   store_service_id int not null,
   status varchar(255) NOT NULL,-- cancelled, completed
   notes text NOT NULL, -- can be used for addition additional-details
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (booking_id) references user_booking(id),
  FOREIGN KEY (store_service_id) references store_service(id)
  UNIQUE KEY `booking_store_service_idx` (`store_service_id`, `booking_id`)
);

CREATE TABLE `user_booking_service_details` (
  `id` int NOT NULL AUTO_INCREMENT,
   user_booking_details_id int not null,
   start_time TIMESTAMP default NULL,
   end_time TIMESTAMP default NULL,
   user_id int not null,--always be employee working at store
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id),
  FOREIGN KEY (user_booking_details_id) references user_booking_details(id)
  UNIQUE KEY `booking_assignee_idx` (`user_id`, `user_booking_details_id`)
);

--tomorrow this can be extended for payment collection and sending invoices.
CREATE TABLE `booking_payment` (
  `id` int NOT NULL AUTO_INCREMENT,
   user_booking_id int not null,
   discount DECIMAL(6, 4) NOT NULL default 0,
   total_amount DECIMAL(6, 4) NOT NULL,
   paid_amount DECIMAL(6, 4) NOT NULL,
   payment_method varchar(255) NOT NULL,-- card, cash
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  added_by int not null,
  updated_by int not null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) references user(id),
  FOREIGN KEY (user_booking_id) references user_booking(id)
);




