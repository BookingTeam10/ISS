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

INSERT INTO users (email,blocked, firstname, surname,is_reported)
VALUES ('popovic.sv4.2021@uns.ac.rs','false','Luka','Popovic','false');

INSERT INTO owners (id,total_price,owner_rating,created_notification,rate_notification,cancelled_notification,accommodation_notification)
VALUES (1,0,0,'false','false','false','false');

INSERT INTO accommodations(description,accepted,automatic_activation,cancel_deadline, min_people,max_people,rating,location_id,type_acc,owner_id)
VALUES ('Apartman','true','true', 24,2,3,0,1,'Apartment',1);


