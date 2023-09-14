package com.learning.recipe.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class RecipeResult(
    @SerializedName("results") val results: List<Result>,
    @SerializedName("offset") val offset: Int?, // 0
    @SerializedName("number") val number: Int?, // 50
    @SerializedName("totalResults") val totalResults: Int? // 249
) {
    @Parcelize
    data class Result(
        @SerializedName("vegetarian") val vegetarian: Boolean?, // true
        @SerializedName("vegan") val vegan: Boolean?, // true
        @SerializedName("glutenFree") val glutenFree: Boolean?, // true
        @SerializedName("dairyFree") val dairyFree: Boolean?, // true
        @SerializedName("veryHealthy") val veryHealthy: Boolean?, // true
        @SerializedName("cheap") val cheap: Boolean?, // false
        @SerializedName("veryPopular") val veryPopular: Boolean?, // true
        @SerializedName("sustainable") val sustainable: Boolean?, // false
        @SerializedName("lowFodmap") val lowFodmap: Boolean?, // false
        @SerializedName("weightWatcherSmartPoints") val weightWatcherSmartPoints: Int?, // 4
        @SerializedName("gaps") val gaps: String?, // no
        @SerializedName("preparationMinutes") val preparationMinutes: Int?, // -1
        @SerializedName("cookingMinutes") val cookingMinutes: Int?, // -1
        @SerializedName("aggregateLikes") val aggregateLikes: Int?, // 3689
        @SerializedName("healthScore") val healthScore: Int?, // 75
        @SerializedName("creditsText") val creditsText: String?, // Full Belly Sisters
        @SerializedName("license") val license: String?, // CC BY-SA 3.0
        @SerializedName("sourceName") val sourceName: String?, // Full Belly Sisters
        @SerializedName("pricePerServing") val pricePerServing: Double?, // 112.39
        @SerializedName("extendedIngredients") val extendedIngredients: @RawValue List<ExtendedIngredient>?,
        @SerializedName("id") val id: Int?, // 716426
        @SerializedName("title") val title: String?, // Cauliflower, Brown Rice, and Vegetable Fried Rice
        @SerializedName("readyInMinutes") val readyInMinutes: Int?, // 30
        @SerializedName("servings") val servings: Int?, // 8
        @SerializedName("sourceUrl") val sourceUrl: String?, // http://fullbellysisters.blogspot.com/2012/01/cauliflower-fried-rice-more-veggies.html
        @SerializedName("image") val image: String?, // https://spoonacular.com/recipeImages/716426-312x231.jpg
        @SerializedName("imageType") val imageType: String?, // jpg
        @SerializedName("summary") val summary: String?, // You can never have too many Chinese recipes, so give Cauliflower, Brown Rice, and Vegetable Fried Rice a try. This recipe serves 8 and costs $1.12 per serving. This hor d'oeuvre has <b>192 calories</b>, <b>7g of protein</b>, and <b>6g of fat</b> per serving. Head to the store and pick up broccoli, sesame seeds, soy sauce, and a few other things to make it today. 3689 people were impressed by this recipe. It is brought to you by fullbellysisters.blogspot.com. It is a good option if you're following a <b>gluten free, dairy free, lacto ovo vegetarian, and vegan</b> diet. From preparation to the plate, this recipe takes roughly <b>30 minutes</b>. Overall, this recipe earns a <b>spectacular spoonacular score of 100%</b>. If you like this recipe, you might also like recipes such as <a href="https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1584601">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>, <a href="https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1238897">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>, and <a href="https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1230097">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>.
        @SerializedName("cuisines") val cuisines: List<String?>?,
        @SerializedName("dishTypes") val dishTypes: List<String?>?,
        @SerializedName("diets") val diets: List<String?>?,
        @SerializedName("occasions") val occasions: List<String?>?,
        @SerializedName("spoonacularSourceUrl") val spoonacularSourceUrl: String?, // https://spoonacular.com/cauliflower-brown-rice-and-vegetable-fried-rice-716426
        @SerializedName("usedIngredientCount") val usedIngredientCount: Int?, // 0
        @SerializedName("likes") val likes: Int?, // 0
    ) : Parcelable {
        @Parcelize
        data class ExtendedIngredient(
            @SerializedName("id") val id: Int?, // 11090
            @SerializedName("aisle") val aisle: String?, // Produce
            @SerializedName("image") val image: String?, // broccoli.jpg
            @SerializedName("consistency") val consistency: String?, // SOLID
            @SerializedName("name") val name: String?, // broccoli
            @SerializedName("nameClean") val nameClean: String?, // broccoli
            @SerializedName("original") val original: String?, // 2 cups cooked broccoli, chopped small
            @SerializedName("originalName") val originalName: String?, // cooked broccoli, chopped small
            @SerializedName("amount") val amount: Double?, // 0.5
            @SerializedName("unit") val unit: String?, // cups
            @SerializedName("meta") val meta: List<String?>?,
            @SerializedName("measures") val measures: Measures?
        ) : Parcelable {
            @Parcelize
            data class Measures(
                @SerializedName("us") val us: Us?, @SerializedName("metric") val metric: Metric?
            ) : Parcelable {
                @Parcelize
                data class Us(
                    @SerializedName("amount") val amount: Double?, // 0.643
                    @SerializedName("unitShort") val unitShort: String?, // cups
                    @SerializedName("unitLong") val unitLong: String? // cups
                ) : Parcelable

                @Parcelize
                data class Metric(
                    @SerializedName("amount") val amount: Double?, // 0.643
                    @SerializedName("unitShort") val unitShort: String?, // g
                    @SerializedName("unitLong") val unitLong: String? // grams
                ) : Parcelable
            }
        }

    }
}