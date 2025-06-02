--liquibase formatted sql

--changeset hakyur:1
insert into sight_translations (sight_id, language_id, name, description, city)
values
    (1, 1, 'Парк Орлёнок', 'Парк "Орлёнок" — это не просто зона отдыха, а настоящая живая летопись Воронежа. ' ||
                           'Расположенный в самом центре города, он сочетает в себе культурную, историческую ' ||
                           'и семейную атмосферу. Здесь под кронами старых деревьев вьются уютные тропинки, а в центре парка ' ||
                           'звенит фонтан, будто бы замерший во времени.', 'Воронеж'),
    (1, 2, 'Orlyonok Park', 'Orlyonok Park is not just a recreation area, but a real living ' ||
                            'chronicle of Voronezh. Located in the heart of the city, it combines cultural, historical and family ' ||
                            'atmosphere. Cozy paths wind under the crowns of old trees, and a fountain rings in the center of the' ||
                            ' park, as if frozen in time.', 'Voronezh'),
    (2, 1, 'Адмиралтейская площадь', 'Знаковая площадь Воронежа на правом берегу водохранилища, созданная ' ||
                                     'к 300-летию Российского флота. Именно здесь по указу Петра I была построена первая верфь ' ||
                                     'и заложен первый линейный корабль «Гото Предестинация». Сегодня это центр культурной ' ||
                                     'жизни города с историческими памятниками, музеем и храмом.', 'Воронеж'),
    (2, 2, 'Admiralteyskaya Square', 'The iconic Voronezh square on the right bank of the reservoir, ' ||
                                     'created for the 300th anniversary of the Russian Navy. It was here that, by decree of Peter the Great,' ||
                                     ' the first shipyard was built and the first battleship Goto Predestination was laid down. Today it is' ||
                                     ' the center of the cultural life of the city with historical monuments, a museum and a temple.', 'Voronezh');

--changeset hakyur:2
insert into moment_translations (moment_id, language_id, name, content)
values
    (1, 1, 'Мемориальный некрополь', 'Некрополь — одно из самых значимых и трогательных мест в парке. ' ||
                                     'Здесь покоятся герои трёх войн: бойцы Красной армии времён Гражданской, лётчики ' ||
                                     '1930-х годов и солдаты Великой Отечественной. Тихая аллея с чугунной оградой, надгробиями ' ||
                                     'и бюстом Михаила Вайцеховского напоминает о цене мира. На фоне этого монумента особенно ' ||
                                     'остро чувствуется контраст с шумом детских площадок — парк словно сам говорит: ' ||
                                     '"Жизнь продолжается, потому что кто-то за неё боролся"'),
    (1, 2, 'Memorial Necropolis', 'The necropolis is one of the most significant and touching ' ||
                                  'places in the park. Heroes of three wars are buried here: soldiers of the Red Army during the ' ||
                                  'Civil War, pilots of the 1930s and soldiers of the Great Patriotic War. A quiet alley with ' ||
                                  'a cast-iron fence, tombstones and a bust of Mikhail Vaitsekhovsky reminds of the price of peace. ' ||
                                  'Against the background of this monument, the contrast with the noise of playgrounds is especially ' ||
                                  'acute — the park itself seems to say: "Life goes on because someone fought for it."'),
    (2, 1, 'Скульптура "Орлёнок" (Трубач)', 'Этот тонкий мальчик с трубой — символ не только парка, ' ||
                                            'но и детства в его лучших проявлениях. Народное прозвище "мальчик со жвачкой" ' ||
                                            'давно стало привычным. Скульптор Иван Дикунов вложил в образ идею силы духа: ' ||
                                            'ребёнок зовёт бойцов в атаку, не давая им упасть духом. Скульптура находится ' ||
                                            'недалеко от фуд-зоны, и кажется, будто он и сегодня зовёт нас не забывать подвиг ' ||
                                            'и держаться друг за друга.'),
    (2, 2, 'Sculpture "Eaglet" (Trumpeter)', 'This thin boy with a pipe is a symbol not only of ' ||
                                             'the park, but also of childhood at its best. The popular nickname "the boy with the ' ||
                                             'chewing gum" has long become familiar. The sculptor Ivan Dikunov put the idea of ' ||
                                             'fortitude into the image: the child calls the fighters to attack, not letting them' ||
                                             ' lose heart. The sculpture is located not far from the food area, and it seems as if' ||
                                             ' today he is calling us not to forget the feat and hold on to each other.'),
    (3, 1, 'Памятник Троепольскому и его собаке', 'У входа в парк, под мостом, вы найдете душевную ' ||
                                                  'скульптуру — писатель Гавриил Троепольский, автор знаменитой повести ' ||
                                                  '«Белый Бим Чёрное ухо», сидит рядом со своим литературным псом. ' ||
                                                  'Это не просто памятник — это символ любви к животным, гуманизма и доброты. ' ||
                                                  'Скульптура вызывает искреннюю эмоцию: дети обнимают собаку, взрослые задумываются.'),
    (3, 2, 'Monument to Troepolsky and his dog', 'At the entrance to the park, under the bridge, ' ||
                                                 'you will find a soulful sculpture — writer Gavriil Troepolsky, author of the ' ||
                                                 'famous story "White Beam Black Ear", sits next to his literary dog. This is ' ||
                                                 'not just a monument — it is a symbol of love for animals, humanism and kindness. ' ||
                                                 'The sculpture evokes sincere emotion: children hug a dog, adults think.'),
    (4, 1, 'Памятник Осипу Мандельштаму', 'На юго-восточной стороне парка расположен памятник ' ||
                                          'великому русскому поэту Осипу Мандельштаму, открытый в 2008 году. Скромный, ' ||
                                          'интеллигентный монумент — напоминание о хрупкости судьбы и силе слова. Он немного ' ||
                                          'в стороне от оживлённых тропинок, как и сам Мандельштам — в стороне от официальной ' ||
                                          'истории, но в её глубокой тени.'),
    (4, 2, 'Monument to Osip Mandelstam', 'On the southeastern side of the park there is a ' ||
                                          'monument to the great Russian poet Osip Mandelstam, opened in 2008. The modest, ' ||
                                          'intelligent monument is a reminder of the fragility of fate and the power of words. ' ||
                                          'He is a little away from the busy paths, just like Mandelstam himself — away from ' ||
                                          'the official history, but in its deep shadow.'),
    (5, 1, 'Успенский (Адмиралтейский) храм', 'Старейший сохранившийся храм Воронежа, построенный в ' ||
                                              '1694 году. Именно здесь проходили богослужения при спуске судов на воду. В ' ||
                                              '1700 году в храме был освящён флаг первого линейного корабля «Гото Предестинация». ' ||
                                              'Внутри можно увидеть мемориальные доски погибшим морякам и Андреевский флаг. ' ||
                                              'Сегодня храм снова действует и считается «морским» духовным центром.'),
    (5, 2, 'Assumption (Admiralty) Church', '
The oldest preserved church in Voronezh, built in 1694. It was here that divine services were held during the launching of ships. ' ||
                                            'In 1700, the flag of the first battleship Goto Predestination was consecrated in the temple. ' ||
                                            'Inside, you can see memorial plaques to the fallen sailors and the St. Andrew''s flag. Today,' ||
                                            ' the temple is back in operation and is considered a "maritime" spiritual center.'),
    (6, 1, 'Корабль-музей "Гото Предестинация', 'Историческая реконструкция первого российского линейного ' ||
                                                'корабля времён Петра I. Построен в 2011–2014 годах, длина — 36 м, 58 пушек, ' ||
                                                'декоративные резные элементы. Внутри работает музей с экспозицией об истории флота. ' ||
                                                'Особенно красиво смотреть на корабль на закате — паруса и снасти подсвечиваются ' ||
                                                'оранжевым светом.'),
    (6, 2, 'Goto Predestination Museum Ship', 'Historical reconstruction of the first Russian battleship' ||
                                              ' from the time of Peter I. Built in 2011-2014, length — 36 m, 58 cannons, decorative carved ' ||
                                              'elements. Inside there is a museum with an exhibition about the history of the fleet. ' ||
                                              'It is especially beautiful to look at the ship at sunset — the sails and rigging are ' ||
                                              'illuminated with orange light.'),
    (7, 1, 'Ростральная колонна', 'Архитектурная композиция, установленная в честь основания '' ||
                               ''Российского флота. Ростральная колонна высотой 28 метров украшена носами кораблей — '' ||
                               ''символами морских побед. Элементы комплекса формируют торжественный вход в Адмиралтейскую '' ||
                               ''площадь и часто служат фоном для фото и свадебных съёмок.'),
    (7, 2, 'Rostral column', 'An architectural composition erected in honor of the founding of ' ||
                             'the Russian Navy. The 28—meter-high rostral column is decorated with the noses of ships, symbols of ' ||
                             'naval victories. The elements of the complex form the ceremonial entrance to Admiralteyskaya Square and ' ||
                             'often serve as a backdrop for photos and wedding shoots.');