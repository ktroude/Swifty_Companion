package com.example.swifty.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    val email: String?,
    @SerializedName("first_name")  val firstName: String?,
    @SerializedName("last_name")  val lastName: String?,
    val displayName: String?,
    @SerializedName("image") val image: Image?,
    val wallet: Int,
    val level: Float,
    val location: String?,
    @SerializedName("cursus_users") val cursusUsers: List<CursusUser>?,
    @SerializedName("projects_users") val projectsUsers: List<ProjectUser>?,
) : Parcelable;


@Parcelize
data class Image(
    @SerializedName("link") val link: String?,
    val versions: ImageVersions?
) : Parcelable;

@Parcelize
data class ImageVersions(
    val large: String?,
    val medium: String?,
    val small: String?,
    val micro: String?
) : Parcelable;

@Parcelize
data class CursusUser(
    val id: Int,
    @SerializedName("cursus_id") val cursusId: Int,
    @SerializedName("grade") val grade: String?,
    @SerializedName("level") val level: Float,
    val skills: List<Skill>?,
    @SerializedName("begin_at") val beginAt: String?,
    @SerializedName("end_at") val endAt: String?,
    val user: User?,
    val cursus: Cursus?
) : Parcelable;

@Parcelize
data class Cursus(
    val id: Int,
    val name: String,
    val slug: String
) : Parcelable;

@Parcelize
data class Skill(
    val id: Int,
    val name: String,
    val level: Float
) : Parcelable;

@Parcelize
data class ProjectUser(
    val id: Int,
    val status: String,
    @SerializedName("final_mark") val finalMark: Int?,
    @SerializedName("validated?") val validated: Boolean?,
    val project: Project?,
    @SerializedName("cursus_ids") val cursusIds: List<Int>?
) : Parcelable;

@Parcelize
data class Project(
    val id: Int,
    val name: String,
    val slug: String
) : Parcelable;

@Parcelize
data class Achievement(
    val id: Int,
    val name: String,
    val description: String?,
    val tier: String?,
    val kind: String?,
    val image: String?,
    @SerializedName("nbr_of_success") val numberOfSuccess: Int?
) : Parcelable;
