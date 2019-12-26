package me.raghu.expensetracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "expense_type")
    val expenseType: String?,
    @ColumnInfo(name = "expense_amount")
    val expenseAmt: String?,
    @ColumnInfo(name = "remarks")
    val remarks: String?
    )