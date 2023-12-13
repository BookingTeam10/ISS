INSERT INTO locations (country, city, street, number_street)
VALUES
  ('Srbija', 'Novi Sad', 'Gunduliceva', 21),
  ('Srbija', 'Sabac', 'Save Mrkalja', 12),
  ('Crna Gora', 'Podgorica', 'Njegoševa', 30),
  ('Bosna i Hercegovina', 'Sarajevo', 'Titova', 40),
  ('Severna Makedonija', 'Skoplje', 'Makedonska', 50),
  ('Slovenija', 'Ljubljana', 'Prešernova', 60),
  ('Crna Gora', 'Budva', 'Jadranska', 70),
  ('Srbija', 'Novi Sad', 'Trg Slobode', 80),
  ('Bosna i Hercegovina', 'Mostar', 'Bulevar', 90),
  ('Severna Makedonija', 'Bitola', 'Pelagoniska', 100);

INSERT INTO users (email,is_blocked, firstname, surname,is_reported, phone, address,user_password)
VALUES
    ('popovic.sv4.2021@uns.ac.rs','false','Luka','Popovic','false', '0655197633', 'Adresa1','abc'),
    ('aleksa@gmail.com', 'false', 'Aleksa', 'Janjic', 'false', '854574324', 'Bulevar', '1234');


INSERT INTO owners (id,total_price,owner_rating,created_notification,rate_notification,cancelled_notification,accommodation_notification)
VALUES (1,0,0,'false','false','false','false');

INSERT INTO guests (id, number_canceled_notification,turn_notification)
VALUES (2, 0, 'false');

insert into taken_date(first_date,last_date)
VALUES ('2023-01-01', '2023-01-05'),
       ('2023-02-01', '2023-02-05');

INSERT INTO accommodations(description,accepted,automatic_activation,cancel_deadline, min_people,max_people,rating,location_id,type_acc,owner_id)
VALUES ('Apartman','true','true', 24,3,6,0,1,'Apartment',1),
       ('Apartman','true','true', 24,2,4,0,1,'Apartment',1),
       ('Apartman','true','true', 24,2,4,0,2,'Apartment',1);

INSERT INTO accommodation_photoes (accommodation_accommodation_id, photo)
VALUES (1,'assets/images/apartment1.png'),
       (1,'assets/images/apartment2.png'),
       (1,'assets/images/apartment3.png'),
       (2,'assets/images/apartment4.png'),
       (2,'assets/images/apartment5.png'),
       (3,'assets/images/apartment6.png'),
       (3,'assets/images/apartment7.png');


insert into amenity(amenity_name)
VALUES  ('WIFI'),
        ('Parking'),
        ('Air-conditioning'),
        ('Kitchen');

INSERT INTO accommodation_taken_dates (accommodation_id, first_date, last_date)
VALUES (1, '2023-01-01', '2023-01-05'),
       (1, '2023-02-01', '2023-02-05');

INSERT INTO accommodation_amenity (accommodation_id,amenity_name)
VALUES (1,'WIFI'),
       (1, 'Parking');

INSERT INTO administrators (email,user_password)
VALUES ('abcd','123');

INSERT INTO reservations (total_price, reservation_status, start_date, end_date, number_of_nights, accommodation_id, guest_id)
VALUES
    (200, 'ACCEPTED', '2022-12-12', '2022-12-15', 3, 1, 2),
    (200, 'ACCEPTED', '2023-10-10', '2023-10-15', 5, 1, 2),
    (100, 'REJECTED', '2023-01-01', '2023-01-03', 3, 1, 2),
    (50, 'WAITING', '2023-01-01', '2023-01-02', 1, 1, 2),
    (150, 'WAITING', '2023-02-01', '2023-02-02', 2, 2, 2);

INSERT INTO notifications (text, notification_status, guest_id, owner_id, sent_date)
VALUES
    ('Your reservation has been accepted.', 'VISIBLE', 2, 1, '2023-01-01'),
    ('Your reservation has been rejected.', 'VISIBLE', 2, 1, '2023-01-02'),
    ('New reservation request received.', 'NOT_VISIBLE', 2, 1, '2023-01-03');

INSERT INTO review (rate, review_comment, status, reservation_id)
VALUES
     (10, 'Great experience!', 'ACTIVE', 1),
     (10, 'Great experience!', 'ACTIVE', 2);



