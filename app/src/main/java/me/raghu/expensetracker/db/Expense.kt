package me.raghu.expensetracker.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "expense_type")
    val expenseType: String?,
    @ColumnInfo(name = "expense_amount")
    val expenseAmt: String?,
    @ColumnInfo(name = "remarks")
    val remarks: String?,
    @ColumnInfo(name = "Date")
    val date: Date?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readSerializable() as Date?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(expenseType)
        writeString(expenseAmt)
        writeString(remarks)
        writeSerializable(date)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Expense> = object : Parcelable.Creator<Expense> {
            override fun createFromParcel(source: Parcel): Expense = Expense(source)
            override fun newArray(size: Int): Array<Expense?> = arrayOfNulls(size)
        }
    }
}