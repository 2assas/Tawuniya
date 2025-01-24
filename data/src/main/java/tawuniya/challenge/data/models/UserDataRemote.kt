package tawuniya.challenge.data.models


import com.google.gson.annotations.SerializedName

class UserDataRemote : ArrayList<UserDataRemote.UserDataRemoteItem?>(){
    data class UserDataRemoteItem(
        @SerializedName("address")
        val address: Address? = null,
        @SerializedName("company")
        val company: Company? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("phone")
        val phone: String? = null,
        @SerializedName("username")
        val username: String? = null,
        @SerializedName("website")
        val website: String? = null
    ) {
        data class Address(
            @SerializedName("city")
            val city: String? = null,
            @SerializedName("geo")
            val geo: Geo? = null,
            @SerializedName("street")
            val street: String? = null,
            @SerializedName("suite")
            val suite: String? = null,
            @SerializedName("zipcode")
            val zipcode: String? = null
        ) {
            data class Geo(
                @SerializedName("lat")
                val lat: String? = null,
                @SerializedName("lng")
                val lng: String? = null
            )
        }
    
        data class Company(
            @SerializedName("bs")
            val bs: String? = null,
            @SerializedName("catchPhrase")
            val catchPhrase: String? = null,
            @SerializedName("name")
            val name: String? = null
        )
    }
}