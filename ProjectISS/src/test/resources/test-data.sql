INSERT INTO users (email,is_blocked, firstname, surname,is_reported, phone, address, password,is_active,activationc,activatione)
VALUES
    ('popovic.sv4.2021@uns.ac.rs','false','Luka123','Popovic','false', '0655197633', 'Adresa1','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('popovic.sv5.2021@uns.ac.rs','false','Matija','Popovic','false', '0655197633', 'Adresa12','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('aleksa@gmail.com', 'false', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('abc@gmail.com', 'true', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null);


INSERT INTO accommodations(description,accepted,automatic_activation,cancel_deadline, min_people,max_people,rating,location_id,type_acc,owner_id, acc_name,weekend_price,holiday_price,summer_price,night_person, acc_status, auto_conf)

VALUES ('Apartman','true','true', 2,3,6,10,null,'Apartment',null,'Naziv1',0,0,0,'true','CREATED', 'true'),
       ('Apartman','true','true', 10,2,4,10,null,'Apartment',null,'Naziv2',0,0,0,'true','EDITED', 'false');

INSERT INTO reservations (total_price, reservation_status, start_date, end_date, number_of_nights, accommodation_id, guest_id)
VALUES (200, 'CANCELLED', '2022-12-12', '2022-12-15', 3, 1, null),
       (200, 'WAITING', '2023-10-10', '2023-10-15', 5, 1, null),
       (100, 'ACCEPTED', '2025-01-01', '2025-01-10', 7, 2, null),
       (50, 'ACCEPTED', '2025-01-11', '2025-01-15', 4, 2, null),
       (150, 'ACCEPTED', '2024-01-25', '2024-01-28', 2, 1, null);
