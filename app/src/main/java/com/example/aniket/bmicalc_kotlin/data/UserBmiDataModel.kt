package com.example.aniket.bmicalc_kotlin.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by aniket on 17-09-2017.
 */


data class UserBmiDataModel( var id: Int? = null,
                            var name: String = "",
                            var height: String = "",
                            var weight: String = "",
                            var bmi: String = "",
                            var gender: String = "",
                            var age: Int = 0) : Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    constructor():this(null,"","","","","",0)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.run {
            writeValue(id)
            writeString(name)
            writeString(height)
            writeString(weight)
            writeString(bmi)
            writeString(gender)
            writeInt(age)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserBmiDataModel> {
        override fun createFromParcel(parcel: Parcel): UserBmiDataModel {
            return UserBmiDataModel(parcel)
        }

        override fun newArray(size: Int): Array<UserBmiDataModel?> {
            return arrayOfNulls(size)
        }
    }


}
