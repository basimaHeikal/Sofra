package com.examlpe.com.sofra.data.rest;

import com.examlpe.com.sofra.data.model.client.ClientLoginResponse;
import com.examlpe.com.sofra.data.model.client.ConfirmOrder.ConfirmClientOrderResponse;
import com.examlpe.com.sofra.data.model.client.DeclineOrder.DeclineOrderResponse;
import com.examlpe.com.sofra.data.model.client.MyOrders.MyOrdersResponse;
import com.examlpe.com.sofra.data.model.client.NewOrder.NewOrderResponse;
import com.examlpe.com.sofra.data.model.general.Categories.CategoriesResponse;
import com.examlpe.com.sofra.data.model.general.CitiesList.CitiesListResponse;
import com.examlpe.com.sofra.data.model.general.ClientRegister.ClientRegisterResponse;
import com.examlpe.com.sofra.data.model.general.NotificationsList.NotificationsListResponse;
import com.examlpe.com.sofra.data.model.general.OffersList.OffersListResponse;
import com.examlpe.com.sofra.data.model.general.RegionList.RegionListResponse;
import com.examlpe.com.sofra.data.model.general.RestaurantDetails.RestaurantDetailsResponse;
import com.examlpe.com.sofra.data.model.general.RestaurantList.RestaurantListResponse;
import com.examlpe.com.sofra.data.model.general.contact.ContactResponse;
import com.examlpe.com.sofra.data.model.general.paymentMethods.PaymentMethodsResponse;
import com.examlpe.com.sofra.data.model.general.reviews.ReviewsResponse;
import com.examlpe.com.sofra.data.model.restaurant.AcceptOrder.AcceptOrderResponse;
import com.examlpe.com.sofra.data.model.restaurant.AddOffer.AddOfferResponse;
import com.examlpe.com.sofra.data.model.restaurant.AddProduct.AddProductResponse;
import com.examlpe.com.sofra.data.model.restaurant.ChangeState.ChangeStateResponse;
import com.examlpe.com.sofra.data.model.restaurant.Commissions.CommissionsResponse;
import com.examlpe.com.sofra.data.model.restaurant.ConfirmOrder.ConfirmOrderResponse;
import com.examlpe.com.sofra.data.model.restaurant.FoodMenu.FoodMenuResponse;
import com.examlpe.com.sofra.data.model.restaurant.MyOrders.RestaurantOrdersResponse;
import com.examlpe.com.sofra.data.model.restaurant.Register.RegisterResponse;
import com.examlpe.com.sofra.data.model.restaurant.RejectOrder.RejectOrderResponse;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantLoginResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    /***----------------------------------------CLIENT------------------------------------------***/
    //client login api
    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLoginResponse> UserLogin(@Field("email") String email,
                                        @Field("password") String password);
    //client Register api
    @POST("client/register")
    @FormUrlEncoded
    Call<ClientRegisterResponse>Register(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("password") String password,
                                         @Field("password_confirmation") String password_confirmation,
                                         @Field("phone") String phone,
                                         @Field("address") String address,
                                         @Field("region_id") String region_id);

    //client notifications list api
    @GET("client/notifications")
    Call<NotificationsListResponse>getClientNotifications(@Query("api_token") String api_token);

    //new order api
    @POST("client/new-order")
    @FormUrlEncoded
    Call<NewOrderResponse> NewOrder(@Field("items[item_id]") List<Integer> id,
                                    @Field("items[quantity]") List<String> quantity,
                                    @Field("items[note]") List<String> note,
                                    @Field("restaurant_id") String restaurant_id,
                                    @Field("note") String note1,
                                    @Field("address") String address,
                                    @Field("payment_method_id") String payment_method_id,
                                    @Field("phone") String phone,
                                    @Field("name") String name,
                                    @Field("api_token") String api_token);

    //client my order api
    @GET("client/my-orders")
    Call<MyOrdersResponse> ClientOrders(@Query("api_token") String api_token,
                                    @Query("state") String state);

    //client decline-order api
    @POST("client/decline-order")
    @FormUrlEncoded
    Call<DeclineOrderResponse> DeclineOrder(@Field("api_token") String api_token,
                                            @Field("order_id") String order_id);

    //restaurant confirm-order api
    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<ConfirmClientOrderResponse> ConfirmClientOrder(@Field("api_token") String api_token,
                                                        @Field("order_id") String order_id);


    /***-----------------------------------------------RESTAURANT---------------------------------------------***/
    //restaurant login api
    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLoginResponse> RestaurantLogin(@Field("email") String email,
                                                  @Field("password") String password);
    //restaurant Register api
    @Multipart
    @POST("restaurant/register")
    Call<RegisterResponse>Register(@Part("name") RequestBody name,
                                   @Part("email") RequestBody email,
                                   @Part("password") RequestBody password,
                                   @Part("password_confirmation") RequestBody password_confirmation,
                                   @Part("phone") RequestBody phone,
                                   @Part("whatsapp") RequestBody whatsapp,
                                   @Part("region_id") RequestBody region_id,
                                   @Part("delivery_cost") RequestBody delivery_cost,
                                   @Part("minimum_charger") RequestBody minimum_charger,
                                   @Part("availability") RequestBody availability,
                                   @PartMap() HashMap<String, String> categories,
                                   @Part("photo\"; filename=\"myfile.jpg\" ") RequestBody file);
    //restaurant food menu list api
    @GET("restaurant/my-items")
    Call<FoodMenuResponse>getFoodMenu(@Query("api_token") String api_token);

    //restaurant add product api
    @Multipart
    @POST("restaurant/new-item")
    Call<AddProductResponse>AddProduct(@Part("api_token") RequestBody api_token,
                                       @Part("description") RequestBody description,
                                       @Part("price") RequestBody price,
                                       @Part("preparing_time") RequestBody preparing_time,
                                       @Part("name") RequestBody name,
                                       @Part("photo\"; filename=\"myfile.jpg\" ") RequestBody file);

    //restaurant offers list api
    @GET("restaurant/my-offers")
    Call<OffersListResponse>getMyOffersList(@Query("api_token") String api_token);

    //restaurant add product api
    @Multipart
    @POST("restaurant/new-offer")
    Call<AddOfferResponse>AddOffer(@Part("api_token") RequestBody api_token,
                                   @Part("description") RequestBody description,
                                   @Part("price") RequestBody price,
                                   @Part("starting_at") RequestBody starting_at,
                                   @Part("name") RequestBody name,
                                   @Part("ending_at") RequestBody ending_at,
                                   @Part("photo\"; filename=\"myfile.jpg\" ") RequestBody file);

    //Restaurant notifications list api
    @GET("restaurant/notifications")
    Call<NotificationsListResponse>getRestaurantNotifications(@Query("api_token") String api_token);


    //Reviews list api
    @GET("restaurant/reviews")
    Call<ReviewsResponse>getReviewsList(@Query("api_token") String api_token,
                                        @Query("restaurant_id") String restaurant_id,
                                        @Query("page") String page);

    //restaurant change state api
    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<ChangeStateResponse> ChangeState(@Field("api_token") String api_token,
                                          @Field("state") String state);


    //restaurant orders api
    @GET("restaurant/my-orders")
    Call<RestaurantOrdersResponse> RestaurantOrders(@Query("api_token") String api_token,
                                                    @Query("state") String state);

    //restaurant accept-order api
    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrderResponse> AcceptOrder(@Field("api_token") String api_token,
                                          @Field("order_id") String order_id);

    //restaurant reject-order api
    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<RejectOrderResponse> RejectOrder(@Field("api_token") String api_token,
                                          @Field("order_id") String order_id);

    //restaurant confirm-order api
    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<ConfirmOrderResponse> ConfirmOrder(@Field("api_token") String api_token,
                                            @Field("order_id") String order_id);

    //restaurant commissions api
    @GET("restaurant/commissions")
    Call<CommissionsResponse> Commissions(@Query("api_token") String api_token);


    /***--------------------------------------GENERAL-------------------------------***/
    //restaurants list api
    @GET("restaurants")
    Call<RestaurantListResponse>getRestaurantList();

    //restaurant details api
    @GET("restaurant")
    Call<RestaurantDetailsResponse>getRestaurantDetails(@Query("restaurant_id") int id);

    //food menu list api
    @GET("items")
    Call<FoodMenuResponse>getFoodMenu(@Query("restaurant_id") int id);

    //offers list api
    @GET("offers")
    Call<OffersListResponse>getOffersList();

    //cities list api
    @GET("cities")
    Call<CitiesListResponse>getCitiesList();

    //Region list api
    @GET("regions")
    Call<RegionListResponse>getRegionsList(@Query("city_id") String city_id);


    //categories list api
    @GET("categories")
    Call<CategoriesResponse>getCategoriesList();

    //Payment Methods list api
    @GET("payment-methods")
    Call<PaymentMethodsResponse>getPaymentMethodsList();

    //contact us api
    @POST("contact")
    @FormUrlEncoded
    Call<ContactResponse> ContactUs(@Field("type") String type,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("content") String content);



}

