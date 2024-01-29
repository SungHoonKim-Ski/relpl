package com.gdd.data.api

import com.gdd.data.model.ExistBooleanData
import com.gdd.data.model.DefaultResponse
import com.gdd.data.model.point.TotalPointResponse
import com.gdd.data.model.UserIdRequest
import com.gdd.data.model.point.PointRecordResponse
import com.gdd.data.model.profile.ChangePasswordRequest
import com.gdd.data.model.signin.SignInRequest
import com.gdd.data.model.signin.SignInResponse
import com.gdd.data.model.signup.IsDupPhoneRequest
import com.gdd.data.model.signup.IsDupUidRequest
import com.gdd.data.model.signup.SignupRequest
import com.gdd.data.model.signup.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @POST("user/login")
    suspend fun signIn(
        @Body signinRequest: SignInRequest
    ): Result<DefaultResponse<SignInResponse>>

    @POST("user/isExist/phone")
    suspend fun isDuplicatedPhone(
        @Body isDupPhoneRequest: IsDupPhoneRequest
    ): Result<DefaultResponse<ExistBooleanData>>

    @POST("user/isExist/uid")
    suspend fun isDuplicatedId(
        @Body isDupUidRequest: IsDupUidRequest
    ): Result<DefaultResponse<ExistBooleanData>>

    @GET("user/isExist/nickname/{nickname}")
    suspend fun isDuplicatedNickname(
        @Path(value = "nickname") nickname: String
    ): Result<DefaultResponse<ExistBooleanData>>

    @POST("user/signup")
    suspend fun signUp(
        @Body signupRequest: SignupRequest
    ): Result<DefaultResponse<SignupResponse>>

    @Multipart
    @POST("user/mypage/image")
    suspend fun registerProfileImage(
        @Part file: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Result<DefaultResponse<Boolean>>

    @PUT("user/mypage/password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Result<DefaultResponse<Boolean>>

    @POST("user/mypage/coinbarcode")
    suspend fun getCurrentPointByUserId(
        @Body userIdBody: UserIdRequest
    ): Result<DefaultResponse<TotalPointResponse>>

    @POST("user/mypage/coinscore")
    suspend fun getPointRecordByUserId(
        @Body userIdBody: UserIdRequest
    ): Result<DefaultResponse<PointRecordResponse>>

    @Multipart
    @PUT("user/mypage")
    suspend fun updateProfile(
        @Part userProfilePhoto: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): Result<DefaultResponse<Boolean>>
}