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
    @Query("select se_in_mg from food ORDER BY se_in_mg asc")
    fun getSeInMg(): Flow<List<Double>>

    @Query("select * from food where se_in_mg >= :Q2  ORDER BY se_in_mg asc ")
    fun getRichSeInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where se_in_mg <= :Q2 ORDER BY se_in_mg asc ")
    fun getLowSeInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where se_in_mg between :Q1 and :Q3 ORDER BY se_in_mg asc ")
    fun getRegularSeInMg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            vit_a_rae_in_mcg,
    @Query("select vit_a_rae_in_mcg from food ORDER BY vit_a_rae_in_mcg asc")
    fun getVitARaeInMcg(): Flow<List<Double>>

    @Query("select * from food where vit_a_rae_in_mcg >= :Q2  ORDER BY vit_a_rae_in_mcg asc ")
    fun getRichVitARaeInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_a_rae_in_mcg <= :Q2 ORDER BY vit_a_rae_in_mcg asc ")
    fun getLowVitARaeInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_a_rae_in_mcg between :Q1 and :Q3 ORDER BY vit_a_rae_in_mcg asc ")
    fun getRegularVitARaeInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            vit_a_re_in_mcg,
    @Query("select vit_a_re_in_mcg from food ORDER BY vit_a_re_in_mcg asc")
    fun getVitAReInMcg(): Flow<List<Double>>

    @Query("select * from food where vit_a_re_in_mcg >= :Q2  ORDER BY vit_a_re_in_mcg asc ")
    fun getRichVitAReInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_a_re_in_mcg <= :Q2 ORDER BY vit_a_re_in_mcg asc ")
    fun getLowVitAReInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_a_re_in_mcg between :Q1 and :Q3 ORDER BY vit_a_re_in_mcg asc ")
    fun getRegularVitAReInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            retinol_in_mcg,
    @Query("select retinol_in_mcg from food ORDER BY retinol_in_mcg asc")
    fun getRetinolInMcg(): Flow<List<Double>>

    @Query("select * from food where retinol_in_mcg >= :Q2  ORDER BY retinol_in_mcg asc ")
    fun getRichRetinolInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where retinol_in_mcg <= :Q2 ORDER BY retinol_in_mcg asc ")
    fun getLowRetinolInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where retinol_in_mcg between :Q1 and :Q3 ORDER BY retinol_in_mcg asc ")
    fun getRegularRetinolInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            beta_carotene_equivalent_in_mcg,
    @Query("select beta_carotene_equivalent_in_mcg from food ORDER BY beta_carotene_equivalent_in_mcg asc")
    fun getBetaCaroteneEquivalentInMcg(): Flow<List<Double>>

    @Query("select * from food where beta_carotene_equivalent_in_mcg >= :Q2  ORDER BY beta_carotene_equivalent_in_mcg asc ")
    fun getRichBetaCaroteneEquivalentInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where beta_carotene_equivalent_in_mcg <= :Q2 ORDER BY beta_carotene_equivalent_in_mcg asc ")
    fun getLowBetaCaroteneEquivalentInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where beta_carotene_equivalent_in_mcg between :Q1 and :Q3 ORDER BY beta_carotene_equivalent_in_mcg asc ")
    fun getRegularBetaCaroteneEquivalentInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            thiamin_in_mcg,
    @Query("select thiamin_in_mcg from food ORDER BY thiamin_in_mcg asc")
    fun getThiaminInMcg(): Flow<List<Double>>

    @Query("select * from food where thiamin_in_mcg >= :Q2  ORDER BY thiamin_in_mcg asc ")
    fun getRichThiaminInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where thiamin_in_mcg <= :Q2 ORDER BY thiamin_in_mcg asc ")
    fun getLowThiaminInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where thiamin_in_mcg between :Q1 and :Q3 ORDER BY thiamin_in_mcg asc ")
    fun getRegularThiaminInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            riboflavin_in_mcg,
    @Query("select riboflavin_in_mcg from food ORDER BY riboflavin_in_mcg asc")
    fun getRiboflavinInMcg(): Flow<List<Double>>

    @Query("select * from food where riboflavin_in_mcg >= :Q2  ORDER BY riboflavin_in_mcg asc ")
    fun getRichRiboflavinInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where riboflavin_in_mcg <= :Q2 ORDER BY riboflavin_in_mcg asc ")
    fun getLowRiboflavinInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where riboflavin_in_mcg between :Q1 and :Q3 ORDER BY riboflavin_in_mcg asc ")
    fun getRegularRiboflavinInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            niacin_in_mcg,
    @Query("select niacin_in_mcg from food ORDER BY niacin_in_mcg asc")
    fun getNiacinInMcg(): Flow<List<Double>>

    @Query("select * from food where niacin_in_mcg >= :Q2  ORDER BY niacin_in_mcg asc ")
    fun getRichNiacinInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where niacin_in_mcg <= :Q2 ORDER BY niacin_in_mcg asc ")
    fun getLowNiacinInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where niacin_in_mcg between :Q1 and :Q3 ORDER BY niacin_in_mcg asc ")
    fun getRegularNiacinInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            dietary_folate_eq_in_mcg,
    @Query("select dietary_folate_eq_in_mcg from food ORDER BY dietary_folate_eq_in_mcg asc")
    fun getDietaryFolateEqInMcg(): Flow<List<Double>>

    @Query("select * from food where dietary_folate_eq_in_mcg >= :Q2  ORDER BY dietary_folate_eq_in_mcg asc ")
    fun getRichDietaryFolateEqInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where dietary_folate_eq_in_mcg <= :Q2 ORDER BY dietary_folate_eq_in_mcg asc ")
    fun getLowDietaryFolateEqInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where dietary_folate_eq_in_mcg between :Q1 and :Q3 ORDER BY dietary_folate_eq_in_mcg asc ")
    fun getRegularDietaryFolateEqInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            food_folate_in_mcg,
    @Query("select food_folate_in_mcg from food ORDER BY food_folate_in_mcg asc")
    fun getFoodFolateInMcg(): Flow<List<Double>>

    @Query("select * from food where food_folate_in_mcg >= :Q2  ORDER BY food_folate_in_mcg asc ")
    fun getRichFoodFolateInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where food_folate_in_mcg <= :Q2 ORDER BY food_folate_in_mcg asc ")
    fun getLowFoodFolateInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where food_folate_in_mcg between :Q1 and :Q3 ORDER BY food_folate_in_mcg asc ")
    fun getRegularFoodFolateInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            vit_b12_in_mcg,
    @Query("select vit_b12_in_mcg from food ORDER BY vit_b12_in_mcg asc")
    fun getVitB12InMcg(): Flow<List<Double>>

    @Query("select * from food where vit_b12_in_mcg >= :Q2  ORDER BY vit_b12_in_mcg asc ")
    fun getRichVitB12InMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_b12_in_mcg <= :Q2 ORDER BY vit_b12_in_mcg asc ")
    fun getLowVitB12InMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_b12_in_mcg between :Q1 and :Q3 ORDER BY vit_b12_in_mcg asc ")
    fun getRegularVitB12InMcg(Q1: Double, Q3: Double): Flow<List<Food>>

    //            vit_c_in_mcg,
    @Query("select vit_c_in_mcg from food ORDER BY vit_c_in_mcg asc")
    fun getVitCInMcg(): Flow<List<Double>>

    @Query("select * from food where vit_c_in_mcg >= :Q2  ORDER BY vit_c_in_mcg asc ")
    fun getRichVitCInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_c_in_mcg <= :Q2 ORDER BY vit_c_in_mcg asc ")
    fun getLowVitCInMcg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where vit_c_in_mcg between :Q1 and :Q3 ORDER BY vit_c_in_mcg asc ")
    fun getRegularVitCInMcg(Q1: Double, Q3: Double): Flow<List<Food>>

//            cholesterol_in_mg,
@Query("select cholesterol_in_mg from food ORDER BY cholesterol_in_mg asc")
fun getCholesterolInMg(): Flow<List<Double>>

    @Query("select * from food where cholesterol_in_mg >= :Q2  ORDER BY cholesterol_in_mg ")
    fun getRichCholesterolInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where cholesterol_in_mg <= :Q2 ORDER BY cholesterol_in_mg ")
    fun getLowCholesterolInMg(Q2: Double): Flow<List<Food>>

    @Query("select * from food where cholesterol_in_mg between :Q1 and :Q3 ORDER BY cholesterol_in_mg ")
    fun getRegularCholesterolInMg(Q1: Double, Q3: Double): Flow<List<Food>>
}