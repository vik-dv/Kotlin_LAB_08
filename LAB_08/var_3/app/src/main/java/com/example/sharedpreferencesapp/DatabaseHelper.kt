package com.example.sharedpreferencesapp

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "quiz.db", null, 1) {

    // Создание базы данных
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question TEXT NOT NULL,
                answer1 TEXT NOT NULL,
                answer2 TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Добавление вопросов
        addQuestion(db, "Луна круглая или квадратная?")
        addQuestion(db, "Андроид — это круто или нет?")
        addQuestion(db, "Можно ли проглотить арбуз?")
        addQuestion(db, "Есть ли польза от солнца?")
        addQuestion(db, "Хочу ли я лето?")
    }

    // Добавление одного вопроса
    private fun addQuestion(
        db: SQLiteDatabase,
        question: String
    ) {
        val values = ContentValues()

        values.put("question", question)
        values.put("answer1", "ДА")
        values.put("answer2", "НЕТ")

        db.insertOrThrow("questions", null, values)
    }

    // Получение всех вопросов
    fun getQuestions(): ArrayList<Question> {

        val questions = ArrayList<Question>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM questions ORDER BY id",
            null
        )

        cursor.use {

            while (it.moveToNext()) {

                val question = Question(
                    it.getInt(0),
                    it.getString(1),
                    it.getString(2),
                    it.getString(3)
                )

                questions.add(question)
            }
        }

        return questions
    }

    // Обновление версии базы данных
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS questions")
        onCreate(db)
    }
}