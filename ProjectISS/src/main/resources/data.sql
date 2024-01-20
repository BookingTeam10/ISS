INSERT INTO locations (country, city, street, number_street)
VALUES
    ('Srbija', 'Novi Sad', 'Gunduliceva', 21),
    ('Srbija', 'Belgrade', 'Sanska', 10),
    ('Srbija', 'Sabac', 'Save Mrkalja', 12),
    ('Srbija', 'Novi Sad', 'Danila Kisa', 10);

--sifra kod svih je abc
INSERT INTO users (email,is_blocked, firstname, surname,is_reported, phone, address, password,is_active,activationc,activatione)
VALUES
    ('popovic.sv4.2021@uns.ac.rs','false','Luka','Popovic','false', '0655197633', 'Adresa1','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('popovic.sv5.2021@uns.ac.rs','false','Matija','Popovic','false', '0655197633', 'Adresa12','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('aleksa@gmail.com', 'false', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('abc@gmail.com', 'true', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null),
    ('test@gmail.com', 'true', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i','true',null,null);


INSERT INTO owners (id,total_price,owner_rating,created_notification,rate_notification,cancelled_notification,accommodation_notification)
VALUES (1,0,0,'true','true','true','true'),
       (4,0,0,'false','false','false','false');

INSERT INTO guests (id, number_canceled_notification,turn_notification)
VALUES (3, 0, 'true'),
       (5, 0, 'true');

INSERT INTO administrators (id)
VALUES (2);


INSERT INTO accommodations(description,accepted,automatic_activation,cancel_deadline, min_people,max_people,rating,location_id,type_acc,owner_id, acc_name,weekend_price,holiday_price,summer_price,night_person, acc_status, auto_conf)

VALUES ('Apartman','true','true', 2,3,6,10,1,'Apartment',1,'Naziv1',0,0,0,'true','CREATED', 'true'),
       ('Apartman','true','true', 10,2,4,10,1,'Room',1,'Naziv2',0,0,0,'true','EDITED', 'false'),
       ('Apartman','true','true', 5000,2,4,10,2,'Apartment',1,'Naziv3',0,0,0,'true','CREATED', 'false');

INSERT INTO accommodation_photos (accommodation_accommodation_id, photo)
VALUES (1,'assets/images/apartment1.png'),
       (1,'assets/images/apartment2.png'),
       (1,'assets/images/apartment3.png'),
       (2,'assets/images/apartment4.png'),
       (2,'assets/images/apartment5.png'),
       (3,'assets/images/apartment6.png'),
       (3,'assets/images/apartment7.png');


INSERT INTO accommodation_taken_dates (accommodation_id, first_date, last_date)
VALUES (1, '2024-02-01', '2023-02-05'),
       (1, '2024-02-10', '2024-02-15');

INSERT INTO accommodation_amenity (accommodation_id,amenity_name)
VALUES (1,'WIFI'),
       (2,'WIFI'),
       (1, 'Parking');

INSERT INTO reservations (total_price, reservation_status, start_date, end_date, number_of_nights, accommodation_id, guest_id)
VALUES (200, 'CANCELLED', '2022-12-12', '2022-12-15', 3, 1, 3),
       (200, 'WAITING', '2023-10-10', '2023-10-15', 5, 1, 3),
       (100, 'REJECTED', '2025-01-02', '2025-01-05', 3, 2, 3),
       (50, 'ACCEPTED', '2025-01-10', '2025-05-12', 1, 2, 3),
       (150, 'ACCEPTED', '2025-01-02', '2025-01-05', 2, 3, 3),
       (50, 'ACCEPTED', '2025-01-10', '2025-05-12', 1, 3, 3),
       (200, 'ACCEPTED', '2025-10-10', '2025-10-15', 5, 1, 3),
       (150, 'ACCEPTED', '2025-01-10', '2025-01-15', 2, 1, 3);

INSERT INTO notifications (text, notification_status, guest_id, owner_id, sent_date)
VALUES
    ('Your reservation has been accepted.', 'VISIBLE', 3, 1, '2023-01-01'),
    ('Your reservation has been rejected.', 'VISIBLE', 3, 1, '2023-01-02'),
    ('New reservation request received.', 'NOT_VISIBLE', 3, 1, '2023-01-03');

INSERT INTO review (rate, review_comment, status, reservation_id)
VALUES
    (3, 'Great experience!', 'WAITING', 3),
    (3, 'Bad experience!', 'REPORTED', 4);

INSERT INTO review_owner (rate, review_comment,comment_date, status, owner_id, guest_id,is_reported)
VALUES
    (5, 'Great experience!', '2021-12-01', 'REPORTED', 1,5,'false');



INSERT INTO accommodation_price (accommodation_id, price, start_date, end_date)
VALUES
    (1, 3000, '2001-01-01', '2030-07-01'),
    (2, 2000, '2001-01-01', '2030-08-01'),
    (3, 4000, '2001-01-01', '2030-09-01');


INSERT INTO report (review_comment,status,owner_id, guest_id,user_report)
VALUES
    ('Bad experience!','ACTIVE', 4,3,'OG');


-- INSERT INTO guests_favourite_accommodations (guest_id,favourite_accommodations_accommodation_id)
-- VALUES (3,3);

INSERT INTO notifications_visible (text, guest_id, owner_id, user_rate)
VALUES
    ('Your reservation has been accepted.', 3, 1, 'OG'),
    ('Your reservation has been rejected.', 3, 4, 'OG'),
    ('New reservation request received.', 3, 1, 'GO');

INSERT INTO notifications_user_visible (text, guest_id, owner_id, user_rate,sent_date,notification_status)
VALUES
    ('Your reservation has been accepted.', 3, 1, 'OG','2024-01-20','NOT_VISIBLE'),
    ('Your reservation has been rejected.', 3, 4, 'OG','2024-01-23','NOT_VISIBLE'),
    ('New reservation request received.', 3, 1, 'GO','2024-01-22','NOT_VISIBLE');