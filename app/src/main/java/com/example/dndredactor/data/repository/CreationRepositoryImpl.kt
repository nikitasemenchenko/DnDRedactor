package com.example.dndredactor.data.repository

import com.example.dndredactor.data.model.Archetype
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.domain.repository.CreationRepository
import javax.inject.Inject

class CreationRepositoryImpl @Inject constructor(): CreationRepository {
    override fun getRaces(): List<Race> {
        return listOf(
            Race(
                id = "gnome",
                name = "Гном",
                description = "Гномы - маленькие и ловкие существа с острым умом и любопытством",
                subraces = listOf(
                    Subrace("forest_gnome", "Лесной гном", "Лесные гномы обладают природной магией и общением с животными"),
                    Subrace("rock_gnome", "Скальный гном", "Скальные гномы - искусные ремесленники и изобретатели")
                )
            ),
            Race(
                id = "dwarf",
                name = "Дварф",
                description = "Дварфы - крепкие и выносливые создания, живущие в горах",
                subraces = listOf(
                    Subrace("hill_dwarf", "Холмовой дварф", "Холмовые дварфы обладают повышенной выносливостью"),
                    Subrace("mountain_dwarf", "Горный дварф", "Горные дварфы сильны и хорошо владеют доспехами")
                )
            ),
            Race(
                id = "dragonborn",
                name = "Драконорожденный",
                description = "Драконорожденные - гордые потомки драконов с врожденной магической силой",
                subraces = emptyList()
            ),
            Race(
                id = "half_orc",
                name = "Полуорк",
                description = "Полуорки - сильные и выносливые потомки людей и орков",
                subraces = emptyList()
            ),
            Race(
                id = "halfling",
                name = "Полурослик",
                description = "Полурослики - маленькие и проворные существа, любящие комфорт",
                subraces = listOf(
                    Subrace("lightfoot", "Мягконог", "Мягконогие полурослики ловки и могут легко скрываться"),
                    Subrace("stout", "Коренастый", "Коренастые полурослики более выносливы")
                )
            ),
            Race(
                id = "half_elf",
                name = "Полуэльф",
                description = "Полуэльфы - грациозные потомки людей и эльфов с даром дипломатии",
                subraces = emptyList()
            ),
            Race(
                id = "tiefling",
                name = "Тифлинг",
                description = "Тифлинги - потомки демонов с темной магией в крови",
                subraces = emptyList()
            ),
            Race(
                id = "human",
                name = "Человек",
                description = "Люди - универсальные и амбициозные жители мира",
                subraces = emptyList()
            ),
            Race(
                id = "elf",
                name = "Эльф",
                description = "Эльфы - грациозные и долгоживущие создания, связанные с природой",
                subraces = listOf(
                    Subrace("high_elf", "Высший эльф", "Высшие эльфы владеют магией и изучают древние знания"),
                    Subrace("wood_elf", "Лесной эльф", "Лесные эльфы - искусные охотники с связи с природой"),
                    Subrace("dark_elf", "Темный эльф", "Темные эльфы обитают в Подземье и владеют теневой магией")
                )
            )
        )
    }

    override fun getClasses(): List<CharacterClass> {
        return listOf(

            CharacterClass(
                id = "barbarian",
                name = "Варвар",
                description = "Дикий воин, который обрушивает ярость на врагов.",
                archetypes = listOf(
                    Archetype(
                        id = "berserker",
                        name = "Путь берсерка",
                        description = "Берсерки впадают в боевое бешенство, усиливая разрушительную ярость."
                    ),
                    Archetype(
                        id = "totem_warrior",
                        name = "Путь тотемного воина",
                        description = "Следуя духам животных, тотемный воин получает мистические способности."
                    )
                )
            ),

            CharacterClass(
                id = "bard",
                name = "Бард",
                description = "Мастер магии вдохновения и искусства.",
                archetypes = listOf(
                    Archetype(
                        id = "college_of_valor",
                        name = "Коллегия доблести",
                        description = "Бардов-воители вдохновляют союзников и сражаются в первых рядах."
                    ),
                    Archetype(
                        id = "college_of_lore",
                        name = "Коллегия знаний",
                        description = "Учёные барды, обладающие огромной эрудицией и магической гибкостью."
                    )
                )
            ),

            CharacterClass(
                id = "cleric",
                name = "Жрец",
                description = "Слуга божества, способный исцелять и разрушать.",
                archetypes = listOf(
                    Archetype("tempest_domain", "Домен бури",
                        "Жрецы, повелевающие грозами, штормами и разрушительной силой ветра."),
                    Archetype("war_domain", "Домен войны",
                        "Воины-божьи посланцы, ведущие союзников к победе."),
                    Archetype("life_domain", "Домен жизни",
                        "Жрецы исцеления и благословления, защищающие жизнь."),
                    Archetype("knowledge_domain", "Домен знаний",
                        "Мудрецы, обретшие силу через истинное знание."),
                    Archetype("trickery_domain", "Домен обмана",
                        "Жрецы хитрости, иллюзий и скрытности."),
                    Archetype("nature_domain", "Домен природы",
                        "Поклонники природы и её первородной силы."),
                    Archetype("light_domain", "Домен света",
                        "Жрецы света, несущие сияние и очищающий огонь.")
                )
            ),

            CharacterClass(
                id = "druid",
                name = "Друид",
                description = "Маг природы, способный принимать звериные формы.",
                archetypes = listOf(
                    Archetype("circle_of_the_land", "Круг земли",
                        "Друиды, черпающие силу из определённых земель и природных мест."),
                    Archetype("circle_of_the_moon", "Круг луны",
                        "Мастера превращений, способные принимать сильные звериные формы.")
                )
            ),

            CharacterClass(
                id = "fighter",
                name = "Воин",
                description = "Универсальный мастер боя любого типа.",
                archetypes = listOf(
                    Archetype("battle_master", "Мастер боевых искусств",
                        "Тактики боя, использующие манёвры для контроля и доминирования."),
                    Archetype("eldritch_knight", "Мистический рыцарь",
                        "Воины, сочетающие мастерство оружия с арканной магией."),
                    Archetype("champion", "Чемпион",
                        "Бескомпромиссные бойцы, полагающиеся на физическую мощь.")
                )
            ),

            CharacterClass(
                id = "monk",
                name = "Монах",
                description = "Воин дисциплины, использующий силу Ци.",
                archetypes = listOf(
                    Archetype("open_hand", "Путь открытой ладони",
                        "Мастера боевых искусств, владеющие техникой гармонии тела и духа."),
                    Archetype("shadow", "Путь тени",
                        "Стелсовые монахи, использующие силу тени и скрытности."),
                    Archetype("four_elements", "Путь четырёх стихий",
                        "Монахи, управляющие силами огня, воды, земли и воздуха.")
                )
            ),

            CharacterClass(
                id = "paladin",
                name = "Паладин",
                description = "Святой рыцарь, служащий клятве.",
                archetypes = listOf(
                    Archetype("oath_of_devotion", "Клятва преданности",
                        "Паладины добра, следящие за честью и справедливостью."),
                    Archetype("oath_of_the_ancients", "Клятва древних",
                        "Защитники природы и света, связанные со старыми традициями."),
                    Archetype("oath_of_vengeance", "Клятва мести",
                        "Паладины, преследующие зло с непреклонной решимостью.")
                )
            ),

            CharacterClass(
                id = "ranger",
                name = "Следопыт",
                description = "Охотник и следопыт, мастер выживания.",
                archetypes = listOf(
                    Archetype("hunter", "Охотник",
                        "Следопыты, специализирующиеся на уничтожении определённых целей."),
                    Archetype("beast_master", "Повелитель зверей",
                        "Воины, сражающиеся бок о бок с верным зверем-компаньоном.")
                )
            ),

            CharacterClass(
                id = "rogue",
                name = "Плут",
                description = "Ловкий, скрытный и смертоносный персонаж.",
                archetypes = listOf(
                    Archetype("thief", "Вор",
                        "Мастера ловкости, акробатики и воровского ремесла."),
                    Archetype("assassin", "Убийца",
                        "Профессиональные ликвидаторы, атакующие из тени."),
                    Archetype("arcane_trickster", "Мистический ловкач",
                        "Плуты, использующие сочетание хитрости и магии.")
                )
            ),

            CharacterClass(
                id = "sorcerer",
                name = "Чародей",
                description = "Маг, чьи силы идут от врожденной магии.",
                archetypes = listOf(
                    Archetype("draconic_bloodline", "Наследие драконьей крови",
                        "Чародеи, чья магия связана с древней драконьей силой."),
                    Archetype("wild_magic", "Дикая магия",
                        "Заклинатели, чья магия нестабильна и порождает случайные эффекты.")
                )
            ),

            CharacterClass(
                id = "warlock",
                name = "Колдун",
                description = "Заключил пакт с потусторонним покровителем.",
                archetypes = listOf(
                    Archetype("archfey", "Архифея",
                        "Колдуны, получившие силу от прихотливых сущностей фей."),
                    Archetype("fiend", "Исчадие",
                        "Служители адских существ, владеющие огненной и разрушительной силой."),
                    Archetype("great_old_one", "Великий Древний",
                        "Колдуны, коснувшиеся тайн древних космических разумов.")
                )
            ),

            CharacterClass(
                id = "wizard",
                name = "Волшебник",
                description = "Учёный маг, изучающий структуру арканной магии.",
                archetypes = listOf(
                    Archetype("evocation", "Школа воплощения",
                        "Маги, создающие разрушительные элементы энергии."),
                    Archetype("conjuration", "Школа вызова",
                        "Заклинатели, специализирующиеся на призыве существ и объектов."),
                    Archetype("illusion", "Школа иллюзии",
                        "Мастера обмана, создающие убедительные иллюзии."),
                    Archetype("necromancy", "Школа некромантии",
                        "Волшебники, контролирующие энергию жизни и смерти."),
                    Archetype("abjuration", "Школа ограждения",
                        "Маги, специализирующиеся на защите и барьерах."),
                    Archetype("enchantment", "Школа очарования",
                        "Специалисты по влиянию на разум и эмоции."),
                    Archetype("transmutation", "Школа преобразования",
                        "Мастера изменения свойств объектов и существ."),
                    Archetype("divination", "Школа прорицания",
                        "Заклинатели, предсказывающие будущее и раскрывающие тайны.")
                )
            )
        )
    }

}