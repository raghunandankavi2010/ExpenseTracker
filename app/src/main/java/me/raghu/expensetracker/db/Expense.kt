package me.raghu.expensetracker.db

import android.os.Parcel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.raghu.expensetracker.utils.KParcelable
import me.raghu.expensetracker.utils.parcelableCreator
import me.raghu.expensetracker.utils.readDate
import me.raghu.expensetracker.utils.writeDate
import java.util.*

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "expense_type",defaultValue = "")
    val expenseType: String?,
    @ColumnInfo(name = "expense_amount",defaultValue = "0")
    val expenseAmt: String?,
    @ColumnInfo(name = "remarks",defaultValue = "")
    val remarks: String?,
    @ColumnInfo(name = "Date")
    val date: Date?
) : KParcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDate()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(expenseType)
        writeString(expenseAmt)
        writeString(remarks)
        writeDate(date)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Expense)
    }
}