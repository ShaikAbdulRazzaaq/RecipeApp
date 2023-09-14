package com.learning.recipe.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.learning.recipe.utils.Constants.DEFAULT_DIET_TYPE
import com.learning.recipe.utils.Constants.DEFAULT_MEAL_TYPE
import com.learning.recipe.utils.Constants.PREFERENCES_BACK_ONLINE_KEY
import com.learning.recipe.utils.Constants.PREFERENCES_DIET_TYPE_ID_KEY
import com.learning.recipe.utils.Constants.PREFERENCES_DIET_TYPE_KEY
import com.learning.recipe.utils.Constants.PREFERENCES_MEAL_TYPE_ID_KEY
import com.learning.recipe.utils.Constants.PREFERENCES_MEAL_TYPE_KEY
import com.learning.recipe.utils.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE_KEY)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID_KEY)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE_KEY)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID_KEY)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE_KEY)
    }

    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

    suspend fun saveBackOnline(backOnline: Boolean) {
        context.dataStore.edit {
            it[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readBackOnline: Flow<Boolean> = context.dataStore.data.catch { throwable ->
        if (throwable is IOException) {
            emit(emptyPreferences())
        } else throw throwable

    }.map {
        it[PreferenceKeys.backOnline] ?: false
    }


    suspend fun saveMealAndDietType(
        dietType: String, dietTypeId: Int, mealType: String, mealTypeId: Int
    ) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferenceKeys.selectedMealType] = mealType
            mutablePreferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
            mutablePreferences[PreferenceKeys.selectedDietType] = dietType
            mutablePreferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = context.dataStore.data.catch { throwable ->
        if (throwable is IOException) {
            emit(emptyPreferences())
        } else throw throwable

    }.map { prefs: Preferences ->
        val selectedMealType = prefs[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
        val selectedMealTypeId = prefs[PreferenceKeys.selectedMealTypeId] ?: 0
        val selectedDietTypeId = prefs[PreferenceKeys.selectedDietTypeId] ?: 0
        val selectedDietType = prefs[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
        MealAndDietType(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
    }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)