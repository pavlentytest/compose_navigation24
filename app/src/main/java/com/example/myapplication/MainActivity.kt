package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

data class Student(val id:Int, val name:String, val age:Int)

sealed class StudentRoutes(val route: String) {
    object Students : StudentRoutes("students")
    object Student : StudentRoutes("student")
}
@Composable
fun Main() {
    val navController = rememberNavController()
    val list = listOf(
        Student(1, "Ivanov Ivan", 20),
        Student(2, "Petrov Oleg", 19),
        Student(3, "Sidorova Olga", 18),
        Student(4, "Popova Natalya", 18),
        Student(5, "Ivanov Max", 21)
    )
    NavHost(navController, startDestination = StudentRoutes.Students.route) {
        composable(StudentRoutes.Students.route) { Students(list, navController) }
        composable(StudentRoutes.Student.route + "/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })) {
                stackEntry ->
            val id = stackEntry.arguments?.getInt("studentId")
            Student(id, list)
        }
    }
}
@Composable
fun Students(data: List<Student>, navController: NavController){
    LazyColumn {
        items(data){
                u->
            Row(Modifier.fillMaxWidth()){
                Text(u.name,
                    Modifier.padding(10.dp).clickable { navController.navigate("student/${u.id}") },
                    fontSize = 32.sp)
            }
        }
    }
}
@Composable
fun Student(userId:Int?, data: List<Student>){
    val user = data.find { it.id==userId }
    if(user!=null) {
        Column {
            Text("Id: ${user.id}", Modifier.padding(8.dp), fontSize = 20.sp)
            Text("Name: ${user.name}", Modifier.padding(8.dp), fontSize = 20.sp)
            Text("Age: ${user.age}", Modifier.padding(8.dp), fontSize = 20.sp)
        }
    }
    else{
        Text("Not Found")
    }
}
