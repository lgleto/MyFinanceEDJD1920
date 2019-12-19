/*
 * //  MindProber
 * //
 * //  Created by Lourenço Gomes on 23/11/17.
 * //  Copyright © 2017 Lourenço Gomes. All rights reserved.
 * //
 */

package ipca.examples.myfinance.models

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1 )
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<AppDatabase>(context.applicationContext,
                                AppDatabase::class.java!!, "db_transactions")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}
