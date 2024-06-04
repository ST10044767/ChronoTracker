package com.example.chronotracker

import android.os.Parcel
import android.os.Parcelable

data class WatchDetails(
    val name: String?,
    val color: String?,
    val manufacturer: String?,
    val year: String?,
    val price: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeString(manufacturer)
        parcel.writeString(year)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WatchDetails> {
        override fun createFromParcel(parcel: Parcel): WatchDetails {
            return WatchDetails(parcel)
        }

        override fun newArray(size: Int): Array<WatchDetails?> {
            return arrayOfNulls(size)
        }
    }
}
