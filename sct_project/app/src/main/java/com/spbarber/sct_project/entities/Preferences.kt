package com.spbarber.sct_project.entities

import android.os.Parcel
import android.os.Parcelable

data class Preferences(
    var experience: String?,
    var goal: String?,
    var duration: String?,
    var days: String?,
    var frequency: Int,
    var rmSquat: Float,
    var rmPress: Float,
    var rmDeadlift: Float,
    var name: String?,
    var heigth: Int,
    var weight: Float,
    var genre: String?,
    var birthdate: String?,
    var firstname: String?,
    var surname: String?,
    var username: String?,
    var password: String?,
    var confirmPassword: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
        ){

    }
    constructor() : this("", "", "", "", 1, 0.0F, 0.0F, 0.0F, "", 170, 80F, "", "", "", "", "", "", "")


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(experience)
        parcel.writeString(goal)
        parcel.writeString(duration)
        parcel.writeString(days)
        parcel.writeInt(frequency)
        parcel.writeFloat(rmSquat)
        parcel.writeFloat(rmPress)
        parcel.writeFloat(rmDeadlift)
        parcel.writeString(name)
        parcel.writeInt(heigth)
        parcel.writeFloat(weight)
        parcel.writeString(genre)
        parcel.writeString(birthdate)
        parcel.writeString(firstname)
        parcel.writeString(surname)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(confirmPassword)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Preferences> {
        override fun createFromParcel(parcel: Parcel): Preferences {
            return Preferences(parcel)
        }

        override fun newArray(size: Int): Array<Preferences?> {
            return arrayOfNulls(size)
        }
    }

}
