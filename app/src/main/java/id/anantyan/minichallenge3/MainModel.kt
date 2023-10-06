package id.anantyan.minichallenge3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainModel(
	val name: String? = null,
	val id: Int? = null,
	val cumlaude: Boolean? = null
) : Parcelable
