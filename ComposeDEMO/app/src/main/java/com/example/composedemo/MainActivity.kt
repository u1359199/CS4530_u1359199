package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.composedemo.ui.theme.ComposeDEMOTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import com.example.composedemo.ui.theme.LightRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDEMOTheme {
               Concatenate()
            }
        }
    }
}

@Composable
fun Concatenate() {
    Column(Modifier.fillMaxWidth().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var concatText by remember { mutableStateOf("") }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Padding around the whole row
            horizontalArrangement = Arrangement.Center, // Center the row content
            verticalAlignment = Alignment.CenterVertically)
        {
            OutlinedTextField(
                value = text1,
                onValueChange = { newText -> text1 = newText },
                label = { Text("Word1") },
                modifier = Modifier
                    .weight(1f)           // Take equal space
                    .padding(horizontal = 8.dp)
            )

            OutlinedTextField(
                value = text2,
                onValueChange = { newText -> text2 = newText },
                label = { Text("Word2") },
                modifier = Modifier
                    .weight(1f)           // Take equal space
                    .padding(horizontal = 8.dp)
            )
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Padding around the whole row
            horizontalArrangement = Arrangement.Center, // Center the row content
            verticalAlignment = Alignment.CenterVertically){

            Button(onClick = { concatText = text1 + text2 },
                modifier = Modifier
                    .weight(1f)           // Take equal space
                    .padding(horizontal = 8.dp)) {
                Text("Concatenate Text")

            }
            Button(onClick = { text1 = ""; text2 = "" },
                modifier = Modifier
                    .weight(1f)           // Take equal space
                    .padding(horizontal = 8.dp)) {
                Text("Clear Text")
            }
        }
        Text(text = concatText,
            Modifier.padding(8.dp),
            color = Color.Black,
            fontSize = 16.sp,
            )

    }
}
@Composable
fun JPCPractice(name: String) {

    Column(modifier = Modifier.fillMaxSize().padding(10.dp),
           verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.background(Color.LightGray)) {
            Text(
                text = "$name!",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
            Text("CS 3505",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
        }

        Row(Modifier.background(LightRed).padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("String",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
            Text("BS Major",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
        }
    }
}

