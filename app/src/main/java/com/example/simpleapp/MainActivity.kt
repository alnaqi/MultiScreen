
package com.example.simpleapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.simpleapp.app.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import androidx.compose.material3.Scaffold
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

var item: List<Event> = listOf()
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            LoadJsonScreen()

            AppNavigation()
        }
    }
}

fun parseJsonToModel(jsonString: String): List<Event> {
    val gson = Gson()
    val listType = object : TypeToken<List<Event>>() {}.type
    return gson.fromJson(jsonString, listType)
}
@Composable
fun LoadJsonScreen() {
    val context = LocalContext.current
    var jsonContent by remember { mutableStateOf<String?>(null) }


    jsonContent = loadJsonFromAssets(context, "news.json")
    item = parseJsonToModel(jsonString = jsonContent!!)

}
fun loadJsonFromAssets(context: Context, fileName: String): String {
   return  try {
        context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }


    } catch (e: IOException) {
        e.printStackTrace()
        return e.message.toString()
    }

}

@Composable
fun AppNavigation() {

        val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
        // إعداد NavHost مع تحديد الوجهة الافتراضية
        NavHost(
            navController = navController,
            startDestination = "MainScreen", // الوجهة الأولى عند بداية التطبيق
            modifier = Modifier.fillMaxSize()
        ) {
            composable("MainScreen") { MainScreen(navController,sharedViewModel) }
            composable("DetailScreen") {
                DetailScreen(
                    navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Scaffold(
        topBar = { TopAppBar(title = { Text("Main Screen") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(item) { i ->

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                         val data =   mapOf(
                                "id" to i.id,
                                "title" to i.title,
                                "content" to i.content,
                                "url" to i.url,
                                "headerImageUrl" to i.headerImageUrl,
                                "publishDate" to i.publishDate,
                                "type" to i.type,
                                "topics" to i.topics,
                                "authors" to i.authors,
                            )

                            sharedViewModel.dataMap = data
                            navController.navigate("DetailScreen")
                        },// يملأ الشاشة بالكامل
                    horizontalAlignment = Alignment.CenterHorizontally, // يضع المحتوى في المنتصف
                            verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = i.headerImageUrl+"",
                        contentDescription = "صورة من الإنترنت",
                        modifier = Modifier.size(screenWidth,(screenHeight/3)) // تحديد حجم الصورة
                    )
                    ListItem(
                        {  Text(i.title)
                        }, modifier = Modifier
                            .padding(2.dp))
                    HorizontalDivider( thickness = 2.dp)
                }


            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavController,sharedViewModel: SharedViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Log.d("TAG", "DetailScreen:n==-- ")
    val dataMap = sharedViewModel.dataMap

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (dataMap != null) {
            dataMap.forEach { (key, value) ->
                if(key == "headerImageUrl") {
                    AsyncImage(
                        model = value,
                        contentDescription = "صورة من الإنترنت",
                        modifier = Modifier.size(screenWidth,(screenHeight/3)) // تحديد حجم الصورة
                    )
                }
                Text(text = "$key : $value")
            }
        } else {
            Text("لم يتم استلام البيانات", )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // العودة إلى الشاشة السابقة
            navController.popBackStack()
        }) {
            Text("عودة")
        }
    }
}


class SharedViewModel : ViewModel() {
    var dataMap: Map<String, Any>? = null
}
