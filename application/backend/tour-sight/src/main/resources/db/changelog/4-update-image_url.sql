--liquibase formatted sql

--changeset hakyur:1
update sights set image_url = 'https://storage.yandexcloud.net/giga-guide/sights/1502e164-acb1-4b2a-9a87-733bd779c1a3.jpg' where image_url = '1.jpg';
update sights set image_url = 'https://storage.yandexcloud.net/giga-guide/sights/c7db1c9d-70d3-4982-9d17-8142cd62e0d5.jpg' where image_url = '2.jpg';

update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/6f367c5b-a699-4b70-9e8b-d2c476ff7a28.jpg' where image_url = '3.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/76b4e84f-634e-491f-ac6a-6f8763cf287b.jpg' where image_url = '4.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/b64773b2-ce3c-49af-b7b6-6b25b0972e97.jpg' where image_url = '5.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/e39c0c37-d817-4c58-9e13-734ca9a6841d.jpg' where image_url = '6.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/c88979dd-24ab-45fc-a63c-393442db44bc.jpg' where image_url = '7.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/5f42ee10-8205-48e1-8407-43947b7a828e.jpg' where image_url = '8.jpg';
update moments set image_url = 'https://storage.yandexcloud.net/giga-guide/moments/377a1280-1302-48b3-b67c-969cec97f74a.jpg' where image_url = '9.jpg';