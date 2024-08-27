package com.example.maintenanceapps.API;

import com.example.maintenanceapps.Model.ModelMaintenance;
import com.example.maintenanceapps.Model.ModelMasalah;
import com.example.maintenanceapps.Model.ResponseAlarm;
import com.example.maintenanceapps.Model.ResponseBagianMesin;
import com.example.maintenanceapps.Model.ResponseDefinisi;
import com.example.maintenanceapps.Model.ResponseDefinisiFoto;
import com.example.maintenanceapps.Model.ResponseDetailMnt;
import com.example.maintenanceapps.Model.ResponseHistory;
import com.example.maintenanceapps.Model.ResponseLine;
import com.example.maintenanceapps.Model.ResponseLogin;
import com.example.maintenanceapps.Model.ResponseMaintenance;
import com.example.maintenanceapps.Model.ResponseMasalah;
import com.example.maintenanceapps.Model.ResponseMesin;
import com.example.maintenanceapps.Model.ResponseMesin2;
import com.example.maintenanceapps.Model.ResponseMesinMnt;
import com.example.maintenanceapps.Model.ResponseModul;
import com.example.maintenanceapps.Model.ResponsePenyebab;
import com.example.maintenanceapps.Model.ResponseSet;
import com.example.maintenanceapps.Model.ResponseSparepart;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("Breakdown.php")
    Observable<ModelMaintenance> getNotif();

    @GET("Breakdown.php")
    Call<ModelMaintenance> getNotif2();

    @GET("ListMaintenance.php")
    Call<ResponseMaintenance> getMaintenance();

    @FormUrlEncoded
    @POST("GetMntData.php")
    Call<ModelMaintenance> getMntData(@Field("id") String id);

    @FormUrlEncoded
    @POST("ListMaintenance.php")
    Call<ResponseMaintenance> getMntByStatus(@Field("status") String status);

    @FormUrlEncoded
    @POST("ListMaintenance.php")
    Call<ResponseMaintenance> getMntByStatus2(@Field("id_user") String idUser,
                                              @Field("status") String status);

    @FormUrlEncoded
    @POST("UpdateStatusMnt.php")
    Call<ResponseSet> setStatusMnt(@Field("id") String id,
                                   @Field("idPIC") String idPIC,
                                   @Field("status") String status);

    @FormUrlEncoded
    @POST("SelesaiMnt.php")
    Call<ResponseSet> setSelesaiMnt(@Field("idMnt") String idMnt,
                                    @Field("idMasalah") String idMasalah,
                                    @Field("idPenyebab") String idPenyebab,
                                    @Field("idSparepart") String idSparepart,
                                    @Field("sppmwh") String sppmwh,
                                    @Field("idLine") String idLine,
                                    @Field("idMesin") String idMesin,
                                    @Field("jenisMnt") String jenisMnt);

    @FormUrlEncoded
    @POST("Preventive.php")
    Call<ResponseSet> setPreventive(@Field("idMesin") String idMesin,
                                    @Field("idPIC") String idPIC);

    @FormUrlEncoded
    @POST("AddMnt.php")
    Call<ResponseSet> setNewMnt(@Field("idMesin") String idMesin,
                                @Field("idPIC") String idPIC,
                                @Field("status") String status);

    @FormUrlEncoded
    @POST("AddMnt.php")
    Call<ResponseSet> uploadPictMnt(@Field("idMnt") String idMnt,
                                    @Field("foto") String foto);

// -------------------------------------------------------------------------------------------------

    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseLogin> login(@Field("uname") String username, @Field("password") String password);

// -------------------------------------------------------------------------------------------------

    @FormUrlEncoded
    @POST("GetMesinMnt0.php")
    Call<ResponseMesin> getMesinByID(@Field("id") String id);

// -------------------------------------------------------------------------------------------------

    @GET("ListMasalah.php")
    Call<ResponseMasalah> getMasalah();

    @FormUrlEncoded
    @POST("GetSparepart.php")
    Call<ResponseSparepart> getSparepart(@Field("id_bagian_mesin") String id_bagian_mesin);

    @FormUrlEncoded
    @POST("GetBagianMesin.php")
    Call<ResponseBagianMesin> getBagianMesin(@Field("id_mesin") String id_mesin);

    @FormUrlEncoded
    @POST("GetMasalahData.php")
    Call<ModelMasalah> getMasalahData(@Field("id_masalah") String id_masalah);

// -------------------------------------------------------------------------------------------------

    @GET("ListModul.php")
    Call<ResponseModul> getModul();

    @FormUrlEncoded
    @POST("ListAlarm.php")
    Call<ResponseAlarm> getAlarm(@Field("id_series") String id_series);

    @FormUrlEncoded
    @POST("ListDefinisi.php")
    Call<ResponseDefinisi> getDefinisi(@Field("id_alarm") String id_alarm);

    @FormUrlEncoded
    @POST("ListPenyebab.php")
    Call<ResponsePenyebab> getPenyebab(@Field("id_definisi") String id_definisi);

    @FormUrlEncoded
    @POST("ListDefinisiFoto.php")
    Call<ResponseDefinisiFoto> getDefinisiFoto(@Field("id_definisi") String id_definisi);

// -------------------------------------------------------------------------------------------------


    @GET("ListLine.php")
    Call<ResponseLine> getLine();

    @FormUrlEncoded
    @POST("ListMesin.php")
    Call<ResponseMesin2> getMesinByLine(@Field("idLine") String id_line);

    @FormUrlEncoded
    @POST("HistoryMntByMesin.php")
    Call<ResponseMesinMnt> getMntByMesin(@Field("idMesin") String idMesin);

    @FormUrlEncoded
    @POST("GetAllInfoMnt.php")
    Call<ResponseDetailMnt> getAllInfoMnt(@Field("idMnt") String idMnt);

// -------------------------------------------------------------------------------------------------

    @FormUrlEncoded
    @POST("History.php")
    Call<ResponseHistory> getHistory(@Field("ID") String ID);

// -------------------------------------------------------------------------------------------------

}


