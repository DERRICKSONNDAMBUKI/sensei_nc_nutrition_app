package com.example.ncnutrition.data.dao

import androidx.room.*
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<Food>)

    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("select * from food ORDER BY Code asc")
    fun getFoods(): Flow<List<Food>>

    @Query("select * from food where code = :code")
    fun getFood(code: String): Flow<Food>

    @Query("select * from food where Food_Group_Code = :food_group_code")
    fun getFoodsByFoodGroup(food_group_code: Int): Flow<List<Food>>

    @Query("select * from food where Food_name LIKE :food_name LIMIT 5")
    fun findFoodByName(food_name: String): Flow<List<Food>>

    //    energy_in_kcal
    @Query("select energy_in_kcal from food ORDER BY energy_in_kcal asc")
    fun getEnergyInKcal(): Flow<List<Double>>

    @Query("select * from food where energy_in_kcal >= :Q2  ORDER BY energy_in_kcal asc ")
    fun getRichEnergyFoods(Q2: Double): Flow<List<Food>>

    @Query("select * from food where energy_in_kcal <= :Q2 ORDER BY energy_in_kcal asc ")
    fun getLowEnergyFoods(Q2: Double): Flow<List<Food>>

    @Query("select * from food where energy_in_kcal between :Q1 and :Q3 ORDER BY energy_in_kcal asc ")
    fun getRegularEnergyFoods(Q1: Double, Q3: Double): Flow<List<Food>>

    //     water_in_g,
    @Query("select water_in_g from food ORDER BY water_in_g asc")
    fun getWaterInG(): Flow<List<Double>>

    @Query("select * from food where water_in_g >= :Q2  ORDER BY water_in_g asc ")
    fun getRichWaterInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where water_in_g <= :Q2 ORDER BY water_in_g asc ")
    fun getLowWaterInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where water_in_g between :Q1 and :Q3 ORDER BY water_in_g asc ")
    fun getRegularWaterInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            protein_in_g,
    @Query("select protein_in_g from food ORDER BY protein_in_g asc")
    fun getProteinInG(): Flow<List<Double>>

    @Query("select * from food where protein_in_g >= :Q2  ORDER BY protein_in_g asc ")
    fun getRichProteinInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where protein_in_g <= :Q2 ORDER BY protein_in_g asc ")
    fun getLowProteinInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where protein_in_g between :Q1 and :Q3 ORDER BY protein_in_g asc ")
    fun getRegularProteinInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            fat_in_g,
    @Query("select fat_in_g from food ORDER BY fat_in_g asc")
    fun getFatInG(): Flow<List<Double>>

    @Query("select * from food where fat_in_g >= :Q2  ORDER BY fat_in_g asc ")
    fun getRichFatInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fat_in_g <= :Q2 ORDER BY fat_in_g asc ")
    fun getLowFatInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fat_in_g between :Q1 and :Q3 ORDER BY fat_in_g asc ")
    fun getRegularFatInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            carbohydrate_available_in_g,
    @Query("select carbohydrate_available_in_g from food ORDER BY carbohydrate_available_in_g")
    fun getCarbohydrateAvailableInG(): Flow<List<Double>>

    @Query("select * from food where carbohydrate_available_in_g >= :Q2  ORDER BY carbohydrate_available_in_g asc ")
    fun getRichCarbohydrateAvailableInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where carbohydrate_available_in_g <= :Q2 ORDER BY carbohydrate_available_in_g ")
    fun getLowCarbohydrateAvailableInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where carbohydrate_available_in_g between :Q1 and :Q3 ORDER BY carbohydrate_available_in_g ")
    fun getRegularCarbohydrateAvailableInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            fibre_in_g,
    @Query("select fibre_in_g from food ORDER BY fibre_in_g asc")
    fun getFibreInG(): Flow<List<Double>>

    @Query("select * from food where fibre_in_g >= :Q2  ORDER BY fibre_in_g asc ")
    fun getRichFibreInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fibre_in_g <= :Q2 ORDER BY fibre_in_g asc ")
    fun getLowFibreInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fibre_in_g between :Q1 and :Q3 ORDER BY fibre_in_g asc ")
    fun getRegularFibreInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            ash_in_g,
    @Query("select ash_in_g from food ORDER BY ash_in_g asc")
    fun getAshInG(): Flow<List<Double>>

    @Query("select * from food where ash_in_g >= :Q2  ORDER BY ash_in_g asc ")
    fun getRichAshInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where ash_in_g <= :Q2 ORDER BY ash_in_g asc ")
    fun getLowAshInG(Q2: Double): Flow<List<Food>>

    @Query("select * from food where ash_in_g between :Q1 and :Q3 ORDER BY ash_in_g asc ")
    fun getRegularAshInG(Q1: Double, Q3: Double): Flow<List<Food>>

    //            ca_in_mg,
    @Query("select ca_in_mg from food ORDER BY ca_in_mg asc")
    fun getCaInMg(): Flow<List<Double>>

    @Query("select * from food where ca_in_mg >= :Q2  ORDER BY ca_in_mg asc ")
    fun getRichCaInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where ca_in_mg <= :Q2 ORDER BY ca_in_mg asc ")
    fun getLowCaInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where ca_in_mg between :Q1 and :Q3 ORDER BY ca_in_mg asc ")
    fun getRegularCaInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            fe_in_mg,
    @Query("select fe_in_mg from food ORDER BY fe_in_mg asc")
    fun getFeInMg(): Flow<List<Double>>

    @Query("select * from food where fe_in_mg >= :Q2  ORDER BY fe_in_mg asc ")
    fun getRichFeInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fe_in_mg <= :Q2 ORDER BY fe_in_mg asc ")
    fun getLowFeInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where fe_in_mg between :Q1 and :Q3 ORDER BY fe_in_mg asc ")
    fun getRegularFeInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            mg_in_mg,
    @Query("select mg_in_mg from food ORDER BY mg_in_mg asc")
    fun getMgInMg(): Flow<List<Double>>

    @Query("select * from food where mg_in_mg >= :Q2  ORDER BY mg_in_mg asc ")
    fun getRichMgInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where mg_in_mg <= :Q2 ORDER BY mg_in_mg asc ")
    fun getLowMgInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where mg_in_mg between :Q1 and :Q3 ORDER BY mg_in_mg asc ")
    fun getRegularMgInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            p_in_mg,
    @Query("select p_in_mg from food ORDER BY p_in_mg asc")
    fun getPInMg(): Flow<List<Double>>

    @Query("select * from food where p_in_mg >= :Q2  ORDER BY p_in_mg asc ")
    fun getRichPInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where p_in_mg <= :Q2 ORDER BY p_in_mg asc ")
    fun getLowPInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where p_in_mg between :Q1 and :Q3 ORDER BY p_in_mg asc ")
    fun getRegularPInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            k_in_mg,
    @Query("select k_in_mg from food ORDER BY k_in_mg asc")
    fun getKInMg(): Flow<List<Double>>

    @Query("select * from food where k_in_mg >= :Q2  ORDER BY k_in_mg asc ")
    fun getRichKInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where k_in_mg <= :Q2 ORDER BY k_in_mg asc ")
    fun getLowKInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where k_in_mg between :Q1 and :Q3 ORDER BY k_in_mg asc ")
    fun getRegularKInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            na_in_mg,
    @Query("select na_in_mg from food ORDER BY na_in_mg asc")
    fun getNaInMg(): Flow<List<Double>>

    @Query("select * from food where na_in_mg >= :Q2  ORDER BY na_in_mg asc ")
    fun getRichNaInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where na_in_mg <= :Q2 ORDER BY na_in_mg asc ")
    fun getLowNaInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where na_in_mg between :Q1 and :Q3 ORDER BY na_in_mg asc ")
    fun getRegularNaInMg(Q1: Double, Q3: Double): Flow<List<Food>>

//            zn_in_mg,
@Query("select zn_in_mg from food ORDER BY zn_in_mg asc")
fun getZnInMg(): Flow<List<Double>>

    @Query("select * from food where zn_in_mg >= :Q2  ORDER BY zn_in_mg asc ")
    fun getRichZnInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where zn_in_mg <= :Q2 ORDER BY zn_in_mg asc ")
    fun getLowZnInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where zn_in_mg between :Q1 and :Q3 ORDER BY zn_in_mg asc ")
    fun getRegularZnInMg(Q1: Double, Q3: Double): Flow<List<Food>>
//            se_in_mg,
//            vit_a_rae_in_mcg,
//            vit_a_re_in_mcg,
//            retinol_in_mcg,
//            beta_carotene_equivalent_in_mcg,
//            thiamin_in_mcg,
//            riboflavin_in_mcg,
//            niacin_in_mcg,
//            dietary_folate_eq_in_mcg,
//            food_folate_in_mcg,
//            vit_b12_mcg,
//            vit_c_in_mcg,
//            cholesterol_mg,

}