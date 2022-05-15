package com.coderkprathore.androdcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderkprathore.androdcompose.data.BottomNavItemData
import com.coderkprathore.androdcompose.data.getItemData
import com.coderkprathore.androdcompose.screen.*
import com.coderkprathore.androdcompose.ui.theme.AndrodComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndrodComposeTheme {


                MyApp(items = getItemData())


            }
        }
    }
}

@Composable
fun MyApp(items: List<BottomNavItemData>) {

        

    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBarCompose()},
       // content = { },// this is the main content like home screen
        bottomBar = {
            BottomNavigationBar(items = items,
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                })
        }
    ) {
        Navigation(navController = navController)
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue))
        {
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 5.dp))
        }

    }

    
}



@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen()
        }
        composable("categories") {
            CategoriesScreen()
        }
        composable("search") {
            SearchScreen()
        }
        composable("wishlist") {
            WishlistScreen()
        }
        composable("cart") {
            CartScreen()
        }
    }

}

@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current
    TopAppBar(

        title = {
            Box(modifier = Modifier.fillMaxWidth())
            {
                Text(
                    text = "Home",
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Center)
                        .clickable {
                            Toast
                                .makeText(context, "Refresh", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
                
            }


        },
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context , "Menu", Toast.LENGTH_SHORT).show()
            })
            {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            } },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context , "Profile", Toast.LENGTH_SHORT).show()
            })
            {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")

            }
        },
        backgroundColor = Color.White,
        contentColor = Color.Black
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,

            singleLine = true,
            textStyle = TextStyle(color = Color.Black),

            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(6.dp))
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .onFocusChanged {
                    isHintDisplayed = it != it
                }
        ){
            
            if (isHintDisplayed) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 5.dp)
                )
            }

        }
    }

}
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItemData> = getItemData(),
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItemData) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(modifier = modifier,
                    backgroundColor = Color.White,
                    elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.LightGray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name)
                                
                            }
                        }else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name)
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp)
                        }
                        
                    }
                }
            )
        }

    }
    
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndrodComposeTheme {
        Navigation(navController = rememberNavController())

    }
}