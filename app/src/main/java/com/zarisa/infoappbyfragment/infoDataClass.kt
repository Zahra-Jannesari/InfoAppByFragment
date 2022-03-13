package com.zarisa.infoappbyfragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InfoDataClass (var name: String,var userName:String,var Email:String,var password:String, var gender:String):Parcelable