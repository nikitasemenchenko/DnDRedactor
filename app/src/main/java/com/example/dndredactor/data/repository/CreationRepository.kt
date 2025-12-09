package com.example.dndredactor.data.repository

import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.data.remote.CharacterApi

class CreationRepository(
    private val api: CharacterApi
) {
    fun getRaces(): List<Race> {
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
}