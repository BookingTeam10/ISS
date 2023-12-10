INSERT INTO locations (country, city, street, number_street)
VALUES
    ('Srbija', 'Beograd', 'Kneza Milosa', 10),
    ('Hrvatska', 'Zagreb', 'Ilica', 20),
    ('Crna Gora', 'Podgorica', 'Njegoševa', 30),
    ('Bosna i Hercegovina', 'Sarajevo', 'Titova', 40),
    ('Severna Makedonija', 'Skoplje', 'Makedonska', 50),
    ('Slovenija', 'Ljubljana', 'Prešernova', 60),
    ('Crna Gora', 'Budva', 'Jadranska', 70),
    ('Srbija', 'Novi Sad', 'Trg Slobode', 80),
    ('Bosna i Hercegovina', 'Mostar', 'Bulevar', 90),
    ('Severna Makedonija', 'Bitola', 'Pelagoniska', 100);

INSERT INTO users (email,is_blocked, firstname, surname,is_reported, phone, address, password)
VALUES
    ('popovic.sv4.2021@uns.ac.rs','false','Luka','Popovic','false', '0655197633', 'Adresa1','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i'),
    ('popovic.sv5.2021@uns.ac.rs','false','Matija','Popovic','false', '0655197633', 'Adresa12','$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i'),
    ('aleksa@gmail.com', 'false', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '$2a$12$uI4adYfDz9yGq1ExBaiZmODKYxEFOKBKLvYanhV1ys0JsY3STJ92i');

INSERT INTO owners (id,total_price,owner_rating,created_notification,rate_notification,cancelled_notification,accommodation_notification)
VALUES (1,0,0,'false','false','false','false');

INSERT INTO guests (id, number_canceled_notification,turn_notification)
VALUES (3, 0, 'false');

INSERT INTO administrators (id)
VALUES (2);

INSERT INTO accommodations(description,accepted,automatic_activation,cancel_deadline, min_people,max_people,rating,location_id,type_acc,owner_id,photo)
VALUES ('Apartman','true','true', 24,2,3,0,1,'Apartment',1,'assets/images/apartment1.png'),
       ('Apartman','true','true', 24,2,3,0,2,'Apartment',1,'assets/images/apartment1.png');

INSERT INTO reservations (total_price, reservation_status, start_date, end_date, number_of_nights, accommodation_id, guest_id)
VALUES
    (200, 'ACCEPTED', '2023-01-01', '2023-01-05', 5, 1, 3),
    (100, 'REJECTED', '2023-01-01', '2023-01-03', 3, 1, 3),
    (50, 'WAITING', '2023-01-01', '2023-01-02', 1, 1, 3);

INSERT INTO notifications (text, notification_status, guest_id, owner_id, sent_date)
VALUES
    ('Your reservation has been accepted.', 'VISIBLE', 3, 1, '2023-01-01'),
    ('Your reservation has been rejected.', 'VISIBLE', 3, 1, '2023-01-02'),
    ('New reservation request received.', 'NOT_VISIBLE', 3, 1, '2023-01-03');

-- Insert Reviews
INSERT INTO review (rate, review_comment, status, reservation_id)
VALUES
    (1, 'Great experience!', 'ACTIVE', 1),
    (2, 'Could be better.', 'DELETED', 2),
    (3, 'Amazing service!', 'REPORTED', 3);