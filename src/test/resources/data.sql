--items TABLE
DROP TABLE IF EXISTS items;
CREATE TABLE items(
 id serial primary key,
 name text not null,
 description text not null,
 price_s integer not null,
 price_m integer not null,
 price_l integer not null,
 image_path text not null,
 deleted boolean default false not null
);


--options TABLE
DROP TABLE IF EXISTS options cascade;
CREATE TABLE options (
  id integer primary key,
  name text not null,
  price integer not null
);


--orders TABLE
DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
 id serial primary key,
 user_id integer ,
 status integer not null default 0,
 total_price integer not null default 0,
 order_date date ,
 destination_name varchar(100) ,
 destination_email varchar(100) ,
 destination_zipcode varchar(8) ,
 destination_address varchar(100) ,
 destination_tel varchar(15) ,
 delivery_time timestamp ,
 payment_method integer 
);

--order_items TABLE
DROP TABLE IF EXISTS order_items cascade;
CREATE TABLE order_items(
 id serial primary key,
 item_id integer not null,
 order_id integer not null,
 quantity integer not null,
 size char not null
);


--saunas TABLE
DROP TABLE IF EXISTS saunas cascade;
CREATE TABLE saunas(
 id serial primary key,
 name text not null,
 area text not null,
 price integer not null,
 male_sauna_room_temp integer,
 male_water_bath integer,
 female_sauna_room_temp integer,
 female_water_bath integer,
 description text,
 image_path text,
 url text,
 review text,
 deleted boolean default false not null
);

--users TABLE
DROP TABLE IF EXISTS users cascade;
CREATE TABLE users (
 id serial primary key, 
 name varchar(100) not null, 
 email varchar(100) not null unique, 
 password text not null, 
 zipcode varchar(8) not null, 
 address varchar(200) not null, 
 telephone varchar(15) not null,
 point Integer not null default 0
);

--order_options TABLE
DROP TABLE IF EXISTS order_options cascade;
CREATE TABLE order_options(
 id serial primary key,
 option_id integer not null,
 order_item_id integer not null,
FOREIGN KEY (order_item_id) REFERENCES order_items(id) 
ON DELETE CASCADE 
ON UPDATE CASCADE
);

--reviews TABLE
DROP TABLE IF EXISTS reviews;
CREATE TABLE reviews(
 id serial primary key,
 name text not null,
 review text not null,
 saunas_id integer not null,
 deleted boolean default false not null,
FOREIGN KEY (saunas_id) REFERENCES saunas (id) ON DELETE CASCADE
);


--items TABLE
INSERT INTO items
(name, description, price_s, price_m, price_l, image_path) values
('サウナタオル白' , '吸水・速乾性に優れているサウナタオルです' , 1000 , 2000 , 3000 , 'i1.jpg'),
('サウナタオル青' , '吸水・速乾性に優れているサウナタオルです' , 1000 , 2000 , 3000 , 'i2.jpg'),
('サウナハット青' , '熱から髪を守るですサウナハットです' , 1000 , 2000 , 3000 , 'i3.jpg'),
('サウナハット緑' , '熱から髪を守るですサウナハットです' , 1000 , 2000 , 3000 , 'i4.jpg'),
('サウナマット' , '吸収性に優れているサウナマットです' , 1000 , 2000 , 3000 , 'i5.jpg'),
('靴下白' , 'ラクスサウナオリジナルの靴下です' , 500 , 800 , 1000 , 'i6.jpg'),
('ジャケット青' , '断熱性に優れたジャケットです' , 2000 , 2500 , 3000 , 'i7.jpg'),
('Tシャツ黒' , 'オシャレなオリジナルTシャツです' , 1000 , 1500 , 2000 , 'i8.jpg'),
('パーカー黒' , 'オシャレなオリジナルパーカーです' , 2000 , 2500 , 3000 , 'i9.jpg'),
('パーカー白' , 'オシャレなオリジナルパーカーです' , 2000 , 2500 , 3000 , 'i10.jpg'),
('パーカー黄' , 'オシャレなオリジナルパーカーです' , 2000 , 2500 , 3000 , 'i11.jpg');

--options TABLE
insert into options values
(1, 'ワッペン', 300),
(2, '名前の刺繍', 300);

--saunas TABLE
INSERT INTO saunas (name, area, price, male_sauna_room_temp, male_water_bath, female_sauna_room_temp, female_water_bath, description, image_path, url)
values ('戸越銀座温泉', '品川区',  750,  102, 17, 83, 16, '陽の湯と月の湯が日替わりで楽しめます。屋上で夜空を眺めながらととのうことができます。', 's1.jpg', 'http://togoshiginzaonsen.com/'),
('妙法湯', '豊島区', 790, 114, 16, 94, 15, '熱々のサウナの後に軟水の水風呂で都内にいながらもサウナの聖地しきじにいるような気分を味わえます。', 's2.jpg', 'http://www.angelrock.jp/myouhouyu/company.html'),
('駒の湯', '世田谷区', 800, 82, 17, 85, 15, 'ボナサウナで身体の芯から温まることができます。キンキンの水風呂が人気です。', 's3.jpg', 'https://www.setagaya1010.tokyo/guide/koma-no-yu/'),
('金春湯', '品川区', 880, 90, 16, 89, 14, 'TVなしで静かにじっくり温まることができます。埋め込み式のボナサウナで身体の芯からじんわりと蒸されます。クラフトビールが楽しめるのもおすすめポイントです。', 's4.jpg', 'https://kom-pal.com/'),
('湯どんぶり栄湯', '台東区',  980, 90, 17, 89, 16, 'バリエーション豊富なお風呂が楽しめます。美泡水風呂という細かいバイブラの水風呂がおすすめです。外の空気に触れながらととのうことができます。', 's5.jpg', 'http://sakaeyu.com/'),
('東上野　寿湯', '台東区', 720, 96, 17, 96, 20, '昔ながらの銭湯でタイムスリップしたような気分が味わえます。外気浴もできます。', 's6.jpg', 'http://www7.plala.or.jp/iiyudana/'),
('Smart Stay SHIZUKU 上野駅前', '台東区', 700, 94, 12, 91, 12, '短い時間でも不思議とととのえるサウナ。オロポ購入で利用時間30延長可能。ジョッキで飲むオロポは格別です。', 's7.jpg', 'https://shizuku-hotel.jp/uenoekimae/spa.html'),
('ひだまりの泉　萩の湯' , '台東区', 680, 105, 18, 93, 19, '広々としたサウナ室、深い水風呂、そして外気浴スペース、全てのバランスが取れたおすすめの銭湯です。', 's8.jpg', 'https://haginoyu.jp/'),
('黄金湯', '墨田区',780, 100, 15, 92, 19, '水曜日のみ入れ替わり有り。男湯がわはオートロウリュ、女湯側ではセルフロウリュが楽しめます。深くて大きい水風呂がおすすめです。', 's9.jpg', 'https://koganeyu.com/'),
('大黒湯', '墨田区', 780, 90, 22, 90, 22, 'スカイツリーを眺めながらととのうことができます。', 's10.jpg', 'https://www.daikokuyu.com/');

--users ユーザー登録(pass:morimori)
insert into users
(name, email, password, zipcode, address, telephone, point) values
('テストユーザ', 'test@test.co.jp', 'test','1111111', 'テスト住所', 'テスト電話番号', 0);

--reviews
INSERT INTO reviews 
(name, review, saunas_id) VALUES 
('test', 'test', 1);


